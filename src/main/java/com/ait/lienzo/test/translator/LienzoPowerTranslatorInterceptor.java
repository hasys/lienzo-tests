package com.ait.lienzo.test.translator;

import com.ait.lienzo.test.settings.Settings;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class LienzoPowerTranslatorInterceptor implements LienzoMockitoClassTranslator.TranslatorInterceptor, HasSettings {

    private Settings settings = null;

    @Override public boolean interceptBeforeParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        for (String preparedName : settings.getPowerMockPreparations()) {
            if (preparedName.equals(name)) {
                CtClass ctClass = classPool.get(name);
                for (CtMethod method : ctClass.getDeclaredMethods()) {
                    String returnType = method.getReturnType().getName();
                    if (!isVoidOrPrimitive(returnType)) {
                        method.insertAt(1,
                                "com.ait.lienzo.test.Bridge.methodCalled(\"" + name + "\", \"" + method.getName() + "\"); " +
                                "if ( com.ait.lienzo.test.Bridge.isStaticMocked() ) " +
                                    "return (" + method.getReturnType().getName() + ")com.ait.lienzo.test.Bridge.invokeMethod(\"" + name + "\", \"" + method.getName() + "\");");
                    }
                }
            }
        }
        return false;
    }

    private boolean isVoidOrPrimitive(String returnType) {
        return returnType.equals("void") || returnType.equals("int");
    }

    @Override public void interceptAfterParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        //Do nothing for now
    }

    @Override public void useSettings(Settings settings) {
        this.settings = settings;
    }
}
