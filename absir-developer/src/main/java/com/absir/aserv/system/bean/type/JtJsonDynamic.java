/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-4-18 下午3:48:04
 */
package com.absir.aserv.system.bean.type;

import com.absir.client.helper.HelperJson;
import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelObject;
import com.absir.core.kernel.KernelReflect;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

@SuppressWarnings({"rawtypes", "serial"})
public class JtJsonDynamic implements UserType, DynamicParameterizedType, Serializable {

    private Type dynamicType;

    @Override
    public void setParameterValues(Properties parameters) {
        Class<?> entityClass = KernelClass.forName(parameters.getProperty(ENTITY));
        if (entityClass != null) {
            Field field = KernelReflect.declaredField(entityClass, parameters.getProperty(PROPERTY));
            if (field != null) {
                dynamicType = field.getGenericType();
                return;
            }
        }

        dynamicType = KernelClass.forName(parameters.getProperty(RETURNED_CLASS));
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.LONGVARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        return HelperJson.decodeNull(value, dynamicType);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                st.setString(index, HelperJson.encode(value));

            } catch (IOException e) {
                st.setString(index, "");
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return KernelObject.clone(value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
