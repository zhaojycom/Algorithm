package com.zhaojy;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // 初始化工具类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.zhaojy"))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner()
                        , new MethodAnnotationsScanner()));

        // 获取某个包下类型注解对应的类
        Set<Method> typeClass = reflections.getMethodsAnnotatedWith(MyAnnotationDefinition.class);
        for (Method classz : typeClass) {
            MyAnnotationDefinition annotation = classz.getAnnotation(MyAnnotationDefinition.class);
            System.out.println(classz.getName() + "  " + annotation.name() + "  " + annotation.path() + "  " + annotation.value());
        }
    }

}
