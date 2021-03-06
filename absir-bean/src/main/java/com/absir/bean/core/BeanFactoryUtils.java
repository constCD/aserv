/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-6-18 下午1:47:59
 */
package com.absir.bean.core;

import com.absir.bean.basis.BeanConfig;
import com.absir.bean.basis.BeanDefine;
import com.absir.bean.basis.BeanFactory;
import com.absir.bean.basis.BeanScope;
import com.absir.core.base.Environment;
import com.absir.core.helper.HelperFileName;
import com.absir.core.kernel.KernelList;
import com.absir.core.kernel.KernelList.Orderable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class BeanFactoryUtils {

    public static final Map<Class<?>, Object> TYPE_MAP_INSTANCE = new ConcurrentHashMap<Class<?>, Object>();

    public static Environment getEnvironment() {
        BeanConfig beanConfig = getBeanConfig();
        return beanConfig == null ? Environment.PRODUCT : beanConfig.getEnvironment();
    }

    public static BeanConfig getBeanConfig() {
        BeanFactory beanFactory = get();
        return beanFactory == null ? null : beanFactory.getBeanConfig();
    }

    public static String getBeanConfigClassPath() {
        BeanConfig beanConfig = getBeanConfig();
        return beanConfig == null ? HelperFileName.getClassPath(null) : beanConfig.getClassPath();
    }

    public static <T> T getBeanConfigValue(String expression, Class<T> toClass) {
        BeanConfig beanConfig = getBeanConfig();
        return beanConfig == null ? null : beanConfig.getExpressionObject(expression, null, toClass);
    }

    public static BeanFactory get() {
        return getBeanFactoryImpl();
    }

    public static <T> T get(Class<T> beanType) {
        BeanFactory beanFactory = get();
        if (beanFactory == null) {
            return null;

        } else {
            boolean opening = BeanDefineAbstractor.openProccessDelay();
            T beanObject = beanFactory.getBeanObject(beanType);
            if (opening) {
                BeanDefineAbstractor.removeProccessDelay();
            }

            return beanObject;
        }
    }

    private static BeanFactoryImpl getBeanFactoryImpl() {
        return BeanFactoryImpl.getInstance();
    }

    public static void processBeanObject(Object beanObject) {
        processBeanObject(null, beanObject);
    }

    public static void processBeanObject(BeanScope beaScope, Object beanObject) {
        getBeanFactoryImpl().processBeanObject(beaScope, null, beanObject);
    }

    public static <T> List<T> getOrderBeanObjects(Class<T> beanType) {
        List<T> beanObjects = get().getBeanObjects(beanType);
        if (!Orderable.class.isAssignableFrom(beanType)) {
            KernelList.sortCommonObjects(beanObjects);
        }

        return beanObjects;
    }

    public static void onCreate(Object contextBean) {
        getBeanFactoryImpl().registerStackBeanObject(contextBean);
        processBeanObject(contextBean);
    }

    public static void onDestroy(Object contextBean) {
        getBeanFactoryImpl().unRegisterStackBeanObject(contextBean);
    }

    public static <T> T getRegisterBeanObject(Class<T> beanType) {
        BeanFactoryImpl beanFactory = getBeanFactoryImpl();
        T beanObject = beanFactory.getBeanObject(beanType);
        if (beanObject == null) {
            BeanDefine beanDefine = getRegisterBeanDefine(BeanScope.SINGLETON, beanType);
            beanFactory.registerBeanDefine(beanDefine);
            beanObject = (T) beanDefine.getBeanObject(beanFactory);
        }

        return beanObject;
    }

    public static <T> T getPrototypeBeanObject(Class<T> beanType) {
        BeanFactoryImpl beanFactory = getBeanFactoryImpl();
        BeanDefine beanDefine = beanFactory.getBeanDefine(null, beanType);
        if (beanDefine == null) {
            beanDefine = getRegisterBeanDefine(BeanScope.PROTOTYPE, beanType);
            beanFactory.registerBeanDefine(beanDefine);
        }

        return (T) beanDefine.getBeanObject(beanFactory);
    }

    public static BeanDefine getRegisterBeanDefine(BeanScope beanScope, Class<?> beanType) {
        BeanDefine beanDefine = new BeanDefineType(beanType);
        return getBeanFactoryImpl().processBeanDefine(
                new BeanDefineMerged(beanDefine, beanDefine.getBeanName(), beanScope, beanDefine.getBeanComponent()));
    }

    public static <T> T getBeanTypeInstance(Class<T> beanType) {
        Object beanObject = TYPE_MAP_INSTANCE.get(beanType);
        if (beanObject == null) {
            synchronized (beanType) {
                beanObject = TYPE_MAP_INSTANCE.get(beanType);
                BeanFactoryImpl beanFactory = getBeanFactoryImpl();
                if (beanObject == null) {
                    beanObject = beanFactory.getBeanObject(beanType);
                    if (beanObject == null) {
                        BeanDefine beanDefine = getRegisterBeanDefine(BeanScope.SINGLETON, beanType);
                        beanFactory.registerBeanDefine(beanDefine);
                        beanObject = beanDefine.getBeanObject(beanFactory);
                        beanFactory.unRegisterBeanObject(beanDefine.getBeanName());
                    }

                    TYPE_MAP_INSTANCE.put(beanType, beanObject);
                }
            }
        }

        return (T) beanObject;
    }

    public static <T> T getRegisterBeanObject(String beanName, Class<T> beanType, Class<? extends T> beanClass) {
        if (beanClass == null || beanType == void.class || beanClass == void.class) {
            return (T) get().getBeanObject(beanName);
        }

        T beanObject = get().getBeanObject(beanName, beanClass);
        if (beanObject == null) {
            beanObject = getRegisterBeanObject(beanClass);
        }

        return beanObject;
    }
}
