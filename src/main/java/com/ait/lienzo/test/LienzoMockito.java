/*
 *
 *    Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ait.lienzo.test;

import com.ait.lienzo.test.annotation.JSOMocks;
import com.ait.lienzo.test.annotation.JSOStubs;
import com.ait.lienzo.test.annotation.Mocks;
import com.ait.lienzo.test.annotation.PrepareForTest;
import com.ait.lienzo.test.annotation.Stubs;
import com.ait.lienzo.test.annotation.Translators;
import com.ait.lienzo.test.loader.LienzoMockitoClassLoader;
import com.ait.lienzo.test.settings.Settings;
import com.ait.lienzo.test.settings.SettingsBuilder;
import com.ait.lienzo.test.util.LienzoMockitoLogger;
import javassist.ClassPool;
import javassist.LoaderClassPath;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

/**
 * Entry point class for loading this testing framework.
 *
 * Initializes the test classpath for the given unit test class by loading the
 * class loader <code>com.ait.lienzo.test.loader.LienzoMockitoClassLoader</code>.
 *
 * It obtains the concrete custom settings for the test class as well, if any.
 *
 * @author Roger Martinez
 * @since 1.0
 *
 */
public class LienzoMockito
{
    public static Class<?> init(Class<?> unitTestClass) throws Exception
    {
        Settings settings = getSettings(unitTestClass);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPool classPool = new ClassPool();

        classPool.appendClassPath(new LoaderClassPath(loader));

        LienzoMockitoClassLoader lienzoMockitoClassLoader = new LienzoMockitoClassLoader(settings, loader, classPool);

        Thread.currentThread().setContextClassLoader(lienzoMockitoClassLoader);

        return unitTestClass;
    }

    private static Settings getSettings(Class<?> clazz) throws Exception
    {
        com.ait.lienzo.test.annotation.Settings settingsAnn = null;
        Stubs stubsAnn = null;
        JSOStubs jsoStubsAnn = null;
        JSOMocks jsoMocksAnn = null;
        Translators translatorAnn = null;
        Mocks mocksAnn = null;
        Class prepareForTest = null;

        while (!Object.class.getName().equals(clazz.getName()))
        {
            // Global settings.
            if (clazz.isAnnotationPresent(com.ait.lienzo.test.annotation.Settings.class))
            {
                settingsAnn = clazz.getAnnotation(com.ait.lienzo.test.annotation.Settings.class);
            }

            // Additional stubs.
            if (clazz.isAnnotationPresent(Stubs.class))
            {
                stubsAnn = clazz.getAnnotation(Stubs.class);
            }

            // Additional JSO stubs.
            if (clazz.isAnnotationPresent(JSOStubs.class))
            {
                jsoStubsAnn = clazz.getAnnotation(JSOStubs.class);
            }

            // Additional JSO mocks.
            if (clazz.isAnnotationPresent(JSOMocks.class))
            {
                jsoMocksAnn = clazz.getAnnotation(JSOMocks.class);
            }

            // Additional mocks.
            if (clazz.isAnnotationPresent(Mocks.class))
            {
                mocksAnn = clazz.getAnnotation(Mocks.class);
            }

            // Additional translators.
            if (clazz.isAnnotationPresent(Translators.class))
            {
                translatorAnn = clazz.getAnnotation(Translators.class);
            }

            if (clazz.isAnnotationPresent(PrepareForTest.class))
            {
                prepareForTest = clazz;
            }

            clazz = clazz.getSuperclass();
        }
        handleLogSetting(settingsAnn);

        return SettingsBuilder.build(settingsAnn, stubsAnn, jsoStubsAnn, jsoMocksAnn, mocksAnn, translatorAnn, prepareForTest);
    }

    private static void handleLogSetting(com.ait.lienzo.test.annotation.Settings settingsAnn)
    {
        if (settingsAnn != null && settingsAnn.logEnabled())
        {
            LienzoMockitoLogger.enable(System.out);
        }
        else
        {
            LienzoMockitoLogger.disable();
        }
    }

    public static <T> OngoingStubbing<T> when(T methodCall) {
        return Mockito.when(methodCall);
    }
}
