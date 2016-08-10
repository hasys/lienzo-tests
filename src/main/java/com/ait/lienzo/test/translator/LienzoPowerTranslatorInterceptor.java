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
    private void mockStaticMethod(String className, CtMethod method) throws NotFoundException, CannotCompileException {
        String returnType = method.getReturnType().getName();
        String methodSignature = createMethodSignature(className, method, returnType);
        String mockCode = String.format("if ( %s.isStaticMocked(\"%s\") ) {", BRIDGE, className)
                            + String.format("%s.methodCalled(%s);", BRIDGE, methodSignature)
                            + createReturnFromMethod(returnType, methodSignature)
                        + "}";
        method.insertAt(1, mockCode);
    }

    private String createReturnFromMethod(String returnType, String methodSignature) {
        String capitalizedReturnType = returnType.equals("int")? "Integer" : returnType.substring(0, 1).toUpperCase() + returnType.substring(1);
        switch(returnType) {
            case "byte":
            case "short":
            case "int":
            case "float":
            case "double":
            case "boolean":
            case "char": return String.format("return (%s).%sValue();", generateInvokeMethod(capitalizedReturnType, methodSignature), returnType);
            case "void": return "return;";
            default: return String.format("return %s;", generateInvokeMethod(returnType, methodSignature));
        }
    }

    private String generateInvokeMethod(String returnType, String methodSignature) {
        return String.format("(%s)%s.invokeMethod(%s)", returnType, BRIDGE, methodSignature);
    }

    private String createMethodSignature(String className, CtMethod method, String returnType) throws NotFoundException {
        String methodName = method.getName();
        String parameters = "";
        for(CtClass parameter : method.getParameterTypes()) {
            parameters += parameter.getName() + ",";
        }
        parameters = "\"" + (parameters.length() > 0 ? parameters.substring(0, parameters.length() - 1) + "\"" : "\"");
        return '"' + className + "\", \"" + methodName + "\"," + parameters + ", \"" + returnType + '"';
    }

    @Override public void interceptAfterParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        //Do nothing for now
    }

    @Override public void useSettings(Settings settings) {
        this.settings = settings;
    }
}
