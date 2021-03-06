/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-10-21 下午2:03:24
 */
package com.absir.aserv.system.context.value;

import com.absir.aserv.system.helper.HelperAccessor;
import com.absir.core.dyna.DynaBinder;
import com.absir.core.kernel.KernelReflect;

import java.lang.reflect.Field;
import java.util.List;

@SuppressWarnings("unchecked")
public class ObjectParameters {

    public ObjectParameters(String[] parameters) {
        List<Field> fields = HelperAccessor.getFields(getClass());
        int size = fields.size();
        int length = parameters == null ? 0 : parameters.length;
        if (size > length) {
            size = length;
        }

        for (int i = 0; i < size; i++) {
            Field field = fields.get(i);
            KernelReflect.set(this, field, DynaBinder.to(parameters[i], field.getType()));
        }
    }
}
