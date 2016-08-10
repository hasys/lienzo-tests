package com.ait.lienzo.test.translator;

import java.lang.reflect.Modifier;

import com.ait.lienzo.test.settings.Settings;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class LienzoPowerTranslatorInterceptor implements LienzoMockitoClassTranslator.TranslatorInterceptor, HasSettings {

    private Settings settings = null;

    @Override public boolean interceptBeforeParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        if(!settings.getPowerMockPreparations().contains(name)) {
            return false;
        }

        CtClass ctClass = classPool.get(name);
        for (CtMethod method : ctClass.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                mockStaticMethod(name, method);
            }
        }

        return false;
    }

    private static final String BRIDGE = "com.ait.lienzo.test.Bridge";
    private static final String VOID = "void";
    private void mockStaticMethod(String className, CtMethod method) throws NotFoundException, CannotCompileException {
        String methodName = method.getName();
        String mockCode = String.format("%s.methodCalled(\"%s\", \"%s\"); ", BRIDGE, className, methodName)
                        + String.format("if ( %s.isStaticMocked(\"%s\") ) ", BRIDGE, className);
        String returnType = method.getReturnType().getName();

        if (isPrimitive(returnType)) {

        } else if (VOID.equals(returnType)) {
            method.insertAt(1, mockCode + "return;");
        } else {
            method.insertAt(1, mockCode + String.format("return (%s)%s.invokeMethod(\"%s\", \"%s\");", returnType, BRIDGE, className, methodName));
        }
    }

    private boolean isPrimitive(String returnType) {
        return returnType.equals("int");
    }

    @Override public void interceptAfterParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        //Do nothing for now
    }

    @Override public void useSettings(Settings settings) {
        this.settings = settings;
    }
}
