/**
 * Copyright 2014 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2014-1-13 下午12:37:45
 */
package com.absir.context.config;

import com.absir.bean.basis.BeanConfig;
import com.absir.bean.basis.BeanDefine;
import com.absir.bean.basis.BeanFactory;
import com.absir.bean.basis.BeanScope;
import com.absir.bean.core.*;
import com.absir.bean.inject.InjectBeanDefine;
import com.absir.bean.inject.InjectBeanFactory;
import com.absir.bean.inject.InjectInvoker;
import com.absir.core.base.Environment;
import com.absir.core.helper.HelperFileName;
import com.absir.core.kernel.KernelArray;
import com.absir.core.kernel.KernelLang;
import com.absir.core.kernel.KernelLang.CallbackTemplate;
import com.absir.core.kernel.KernelLang.ObjectEntry;
import com.absir.core.kernel.KernelReflect;
import com.absir.core.kernel.KernelString;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public class BeanProviderContext extends BeanFactoryProvider {

    private Map<String, Boolean> contextFilenames = new HashMap<String, Boolean>();

    private Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers = new HashMap<String, List<Entry<String, InjectInvoker>>>();

    public BeanProviderContext(Collection<String> includePackages, Collection<String> excludePackages,
                               Collection<String> unMatchPatterns) {
        super(includePackages, excludePackages, unMatchPatterns);
    }

    public static void registerBeanDefine(String filename, BeanFactory beanFactory, Set<String> beanDefineNames,
                                          List<BeanDefine> beanDefines, Map<String, Boolean> contextFilenames,
                                          Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers) {
        if (contextFilenames != null) {
            if (KernelLang.isBoolean(contextFilenames.get(filename))) {
                return;
            }

            contextFilenames.put(filename, Boolean.TRUE);
        }

        File xmlFile = new File(filename);
        if (!xmlFile.exists()) {
            return;
        }

        if (xmlFile.isDirectory()) {
            final String environment = beanFactory.getBeanConfig().getEnvironment().name().toLowerCase();
            File[] files = xmlFile.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".xml")) {
                        int index = name.indexOf('_');
                        return index <= 0 || name.substring(0, index).equals(environment) ? true : false;
                    }

                    return false;
                }
            });

            for (File file : files) {
                registerBeanDefine(file.getPath(), beanFactory, beanDefineNames, beanDefines, contextFilenames,
                        beanNameMapAtInjectInvokers);
            }

        } else {
            SAXReader saxReader = new SAXReader();
            try {
                Element element = saxReader.read(xmlFile).getRootElement();
                Iterator<Element> iterator = element.elementIterator();
                while (iterator.hasNext()) {
                    Element beanElement = iterator.next();
                    if ("bean".equals(beanElement.getName())) {
                        BeanDefine beanDefine = registerBeanDefineBean(beanElement, true, beanFactory, beanDefineNames,
                                beanNameMapAtInjectInvokers);
                        if (beanDefines != null) {
                            beanDefines.add(beanDefine);
                        }
                    }
                }

            } catch (Exception e) {
                if (BeanFactoryUtils.getEnvironment().compareTo(Environment.DEBUG) <= 0) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static BeanDefine registerBeanDefine(boolean noded, Element element, BeanFactory beanFactory,
                                                Set<String> beanDefineNames, Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers) {
        Iterator<Element> iterator = element.elementIterator();
        if (iterator.hasNext()) {
            Element node = noded ? element : iterator.next();
            String name = node.getName();
            if (!KernelString.isEmpty(name)) {
                if ("bean".equals(name)) {
                    return registerBeanDefineBean(node, false, beanFactory, beanDefineNames, beanNameMapAtInjectInvokers);

                } else if ("array".equals(name)) {
                    return registerBeanDefineArray(node, beanFactory, beanDefineNames, beanNameMapAtInjectInvokers);

                } else if ("map".equals(name)) {
                    return registerBeanDefineMap(node, beanFactory, beanDefineNames, beanNameMapAtInjectInvokers);
                }
            }

        } else {
            String ref = element.attributeValue("ref");
            String required = element.attributeValue("required");
            if (ref != null || required != null) {
                return new BeanDefineReference(ref, required);
            }

            String value = element.attributeValue("value");
            if (value == null) {
                value = element.getText();

            } else if (value.equals("null")) {
                value = null;
            }

            return new BeanDefineSingleton(value == null ? null : beanFactory.getBeanConfig().getExpression(value));
        }

        return new BeanDefineSingleton(element.getText());
    }

    public static BeanDefine registerBeanDefineBean(Element beanElement, boolean root, BeanFactory beanFactory,
                                                    Set<String> beanDefineNames, Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers) {
        Class<?> beanType = null;
        try {
            String classname = beanElement.attributeValue("class");
            beanType = Thread.currentThread().getContextClassLoader().loadClass(classname);

        } catch (Exception e) {
            if (BeanFactoryUtils.getEnvironment().compareTo(Environment.DEBUG) <= 0) {
                e.printStackTrace();
            }

            return null;
        }

        List<InjectInvoker> injectInvokers = new ArrayList<InjectInvoker>();
        List<Entry<String, InjectInvoker>> atInjectInvokers = new ArrayList<Entry<String, InjectInvoker>>();
        String beanName = beanElement.attributeValue("name");
        String beanScope = beanElement.attributeValue("scope");
        BeanDefineArray constructorBeanDefine = null;
        Iterator<Element> iterator = beanElement.elementIterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String name = element.getName();
            if ("constructor".equals(name)) {
                constructorBeanDefine = registerBeanDefineArray(element, beanFactory, beanDefineNames, beanNameMapAtInjectInvokers);

            } else if ("property".equals(name)) {
                String propertyName = element.attributeValue("name");
                if (propertyName == null) {
                    continue;
                }

                injectInvokers.add(new InjectFieldBean(propertyName, registerBeanDefine(false, element, beanFactory,
                        beanDefineNames, beanNameMapAtInjectInvokers)));

            } else if ("method".equals(name)) {
                String methodName = element.attributeValue("name");
                if (methodName == null) {
                    continue;
                }

                Iterator<Element> methodIterator = element.elementIterator();
                BeanDefineArray beanDefineArray = methodIterator.hasNext() ? registerBeanDefineArray(methodIterator.next(),
                        beanFactory, beanDefineNames, beanNameMapAtInjectInvokers) : null;
                int size = beanDefineArray == null ? 0 : beanDefineArray.getBeanDefines().size();
                Method method = size == 0 ? KernelReflect.assignableMethod(beanType, methodName, 0, true, true, false)
                        : KernelReflect.assignableMethod(beanType, methodName, 0, true, true, false,
                        KernelArray.repeat(Object.class, size));
                if (method == null) {
                    continue;
                }

                String methodAt = element.attributeValue("at");
                InjectMethodBean injectMethodBean = new InjectMethodBean(method, beanDefineArray);
                if (KernelString.isEmpty(methodAt)) {
                    injectInvokers.add(injectMethodBean);

                } else {
                    atInjectInvokers.add(new ObjectEntry<String, InjectInvoker>(methodAt, injectMethodBean));
                }
            }
        }

        BeanDefine beanDefine = new BeanDefineBean(beanType, beanName, constructorBeanDefine);
        ((BeanDefineBean) beanDefine).getInjectInvokers().addAll(injectInvokers);
        if (root || !KernelString.isEmpty(beanName) || !KernelString.isEmpty(beanScope)) {
            BeanScope scope = BeanScope.SINGLETON;
            try {
                scope = BeanScope.valueOf(beanScope);

            } catch (Exception e) {
            }

            beanDefine = new InjectBeanDefine(beanDefine, scope);
            if (beanDefineNames != null) {
                beanDefineNames.add(beanDefine.getBeanName());
            }

            if (beanNameMapAtInjectInvokers != null) {
                beanFactory.registerBeanDefine(beanDefine);
                beanNameMapAtInjectInvokers.put(beanDefine.getBeanName(), atInjectInvokers);
            }

        } else {
            if (!atInjectInvokers.isEmpty()) {
                if (beanFactory.getBeanConfig().getEnvironment().compareTo(Environment.DEBUG) <= 0) {
                    System.out.println("method at can not define for " + beanType);
                }
            }
        }

        return BeanDefineObject.getBeanDefine(beanType, beanDefine);
    }

    public static BeanDefineArray registerBeanDefineArray(Element arrayElement, BeanFactory beanFactory,
                                                          Set<String> beanDefineNames, Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers) {
        BeanDefineArray beanDefineArray = new BeanDefineArray();
        Iterator<Element> iterator = arrayElement.elementIterator();
        while (iterator.hasNext()) {
            beanDefineArray.getBeanDefines().add(
                    registerBeanDefine(true, iterator.next(), beanFactory, beanDefineNames, beanNameMapAtInjectInvokers));
        }

        return beanDefineArray;
    }

    public static BeanDefineMap registerBeanDefineMap(Element MapElement, BeanFactory beanFactory, Set<String> beanDefineNames,
                                                      Map<String, List<Entry<String, InjectInvoker>>> beanNameMapAtInjectInvokers) {
        BeanDefineMap beanDefineMap = new BeanDefineMap();
        Iterator<Element> iterator = MapElement.elementIterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String key = element.attributeValue("key");
            beanDefineMap.getBeanDefines().put(key,
                    registerBeanDefine(false, element, beanFactory, beanDefineNames, beanNameMapAtInjectInvokers));
        }

        return beanDefineMap;
    }

    @Override
    public synchronized void scan(String classPath, String resourcePath, BeanScanner beanScanner, Collection<Class<?>> beanTypes, Object... beanNameAndObjects) {
        super.scan(classPath, resourcePath, beanScanner, beanTypes, beanNameAndObjects);
        BeanFactoryStopping.addStopping(getBeanFactory(), getBeanFactoryStoppings());
    }

    @Override
    public void loadBeanConfig(final BeanConfig beanConfig, Set<String> propertyFilenames, Set<String> loadedPropertyFilenames,
                               Map<String, CallbackTemplate<String>> beanConfigTemplates) {
        super.loadBeanConfig(beanConfig, propertyFilenames, loadedPropertyFilenames, beanConfigTemplates);
        beanConfigTemplates.put("context", new CallbackTemplate<String>() {

            @Override
            public void doWith(String template) {
                for (String filename : template.split(",")) {
                    filename = filename.trim();
                    if (filename.length() > 0) {
                        filename = beanConfig.getClassPath(filename);
                        filename = HelperFileName.normalizeNoEndSeparator(filename);
                        if (!contextFilenames.containsKey(filename)) {
                            contextFilenames.put(filename, null);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void registerBeanDefine(BeanFactoryImpl beanFactory, Set<String> beanDefineNames) {
        super.registerBeanDefine(beanFactory, beanDefineNames);
        registerBeanDefine(beanFactory.getBeanConfig().getClassPath() + "context.xml", beanFactory, beanDefineNames, null,
                contextFilenames, beanNameMapAtInjectInvokers);
        registerBeanDefine(beanFactory.getBeanConfig().getClassPath() + "context-"
                        + beanFactory.getBeanConfig().getEnvironment().name().toLowerCase() + ".xml", beanFactory, beanDefineNames, null,
                contextFilenames, beanNameMapAtInjectInvokers);
        registerBeanDefine(beanFactory.getBeanConfig().getClassPath() + "contexts", beanFactory, beanDefineNames, null,
                contextFilenames, beanNameMapAtInjectInvokers);
        for (String filename : contextFilenames.keySet()) {
            registerBeanDefine(filename, beanFactory, beanDefineNames, null, contextFilenames, beanNameMapAtInjectInvokers);
        }
    }

    @Override
    protected void processorBeanDefineObject(BeanFactoryImpl beanFactory, BeanDefine beanDefine, Object beanObject) {
        super.processorBeanDefineObject(beanFactory, beanDefine, beanObject);
        List<Entry<String, InjectInvoker>> atInjectInvokers = beanNameMapAtInjectInvokers.get(beanDefine.getBeanName());
        if (atInjectInvokers != null) {
            for (Entry<String, InjectInvoker> entry : atInjectInvokers) {
                String[] ats = entry.getKey().split(" ");
                if (KernelArray.contain(ats, "started")) {
                    InjectBeanFactory.getInstance().addStated(beanObject, entry.getValue());
                }

                if (KernelArray.contain(ats, "stopping")) {
                    InjectBeanFactory.getInstance().addStopping(beanObject, entry.getValue());
                }
            }

            if (beanDefine.getBeanScope() != BeanScope.PROTOTYPE) {
                beanNameMapAtInjectInvokers.remove(beanDefine.getBeanName());
            }
        }
    }
}
