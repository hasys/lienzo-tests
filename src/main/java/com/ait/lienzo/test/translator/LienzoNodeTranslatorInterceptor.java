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

import com.ait.lienzo.client.core.shape.Node;
import javassist.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides default method stubbing for nodes.
 * 
 * Currently it stubs the <code>cast</code> or <code>shade</code> methods by returning a stub object instance 
 * and removes the <code>final</code> modifiers for the class methods as well, so those can be further mocked 
 * using regular mockito API.
 * 
 * @author Roger Martinez
 * @since 1.0
 * 
 */
public class LienzoNodeTranslatorInterceptor extends AbstractStripFinalModifiersTranslatorInterceptor {

    private static final String NODE_CLASS = Node.class.getName();
    private static final String METHOD_CAST = "cast";
    private static final String METHOD_SHADE = "shade";

    @Override
    protected Set<String> getClassNames() {
        
        return new HashSet<String>(1) {{
            add( NODE_CLASS );
        }};
        
    }

    public boolean interceptBeforeParent( ClassPool classPool, 
                                          String name ) throws NotFoundException, CannotCompileException {
        
        super.interceptBeforeParent( classPool, name );
        
        if ( name.equals( NODE_CLASS ) ) {
            
            CtClass clazz = classPool.get( name );
            
            for ( CtMethod method : clazz.getMethods() ) {
                
                boolean isCastMethod = isCastMethod( method.getName() );
                
                if ( isCastMethod ) {
                    
                    method.setModifiers( method.getModifiers() & ~Modifier.FINAL );
                    
                    method.setBody( String.format( "{ return (%s) $0; }", clazz.getName() ) );
                    
                }
            }

            // Intercept class loading. Avoid parent's job..
            return true;
        }

        // Let parent loader do the job.
        return false;
    }

    public void interceptAfterParent(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
        // Nothing required for now.
    }

    private boolean isCastMethod( String name ) {
        return METHOD_SHADE.equals( name ) || METHOD_CAST.equals( name );
    }

}
