/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-3-8 下午12:43:09
 */
package com.absir.aserv.system.bean.proxy;

import com.absir.aserv.system.bean.base.JbPermission;

import javax.persistence.Entity;

public interface JpPermission {

    @Entity
    class Permission extends JbPermission {

    }
}
