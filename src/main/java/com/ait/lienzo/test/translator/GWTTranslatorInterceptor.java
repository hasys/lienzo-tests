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

package com.ait.lienzo.test.translator;

import com.google.gwt.core.client.GWT;
import javassist.*;

/**
 * Translator interceptor for changing some behaviors on <code>com.google.gwt.core.client.GWT</code>.
 * 
 * @author Roger Martinez
 * @since 1.0
 * 
 */
public class GWTTranslatorInterceptor implements LienzoMockitoClassTranslator.TranslatorInterceptor {

    private static final String JSO_CLASS = GWT.class.getName();
    private static final String METHOD_IS_SCRIPT = "isScript";
    
    public GWTTranslatorInterceptor() {
    }

    public boolean interceptBeforeParent( ClassPool classPool, 
                                          String name ) throws NotFoundException, CannotCompileException {
        
        if ( name.equals( JSO_CLASS ) ) {
            
            CtClass clazz = classPool.get( name );
            
            for ( CtMethod method : clazz.getDeclaredMethods() ) {
                
                if ( METHOD_IS_SCRIPT.equals( method.getName() ) ) {
                    
                    method.setBody( "{ return false; }" );
                    
                }
                
            }
            
        }

        // Let parent loader do additional job.
        return false;
        
    }

    public void interceptAfterParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        // Nothing required for now.
    }

}
