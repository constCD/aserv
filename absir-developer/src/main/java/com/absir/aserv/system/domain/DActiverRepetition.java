/**
 * Copyright 2014 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2014年7月7日 下午2:45:02
 */
package com.absir.aserv.system.domain;

import com.absir.aserv.system.bean.value.JiActiveRepetition;
import com.absir.aserv.system.dao.BeanDao;
import com.absir.aserv.system.dao.utils.QueryDaoUtils;
import org.hibernate.Session;

import java.util.List;

@SuppressWarnings("unchecked")
public class DActiverRepetition<T extends JiActiveRepetition> extends DActiver<T> {

    private boolean repeated;

    private String allQueryString;

    public DActiverRepetition(String entityName) {
        super(entityName);
        allQueryString = "SELECT o FROM " + entityName + " o WHERE o.beginTime <= ? AND o.passTime <= ?";
    }

    public List<T> reloadActives(long contextTime) {
        if (!repeated) {
            repeated = true;
            Session session = BeanDao.getSession();
            List<T> actives = QueryDaoUtils.createQueryArray(session, allQueryString, contextTime, contextTime).list();
            for (JiActiveRepetition active : actives) {
                long nextPassTime = active.getNextPassTime(contextTime);
                if (nextPassTime > contextTime) {
                    active.setBeginTime(active.getBeginTime() + nextPassTime - active.getPassTime());
                    active.setPassTime(nextPassTime);
                    session.merge(active);
                }
            }
        }

        return super.reloadActives(contextTime);
    }

}
