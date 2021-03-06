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

package com.ait.lienzo.test.stub;

import com.ait.lienzo.test.annotation.StubClass;
import com.ait.lienzo.test.util.LienzoMockitoLogger;
import com.ait.tooling.nativetools.client.NUtils;

import java.util.*;

/**
 * In-memory Map implementation stub stub for class <code>com.ait.tooling.nativetools.client.collection.NFastStringMap</code>.
 * 
 * Results easier creating this stub class for this wrapper of NFastStringMapJSO than creating concrete stubs for NFastStringMapJSO and
 * its super classes.
 * 
 * @author Roger Martinez
 * @since 1.0
 *
 */
@StubClass("com.ait.tooling.nativetools.client.collection.NFastStringMap")
public class NFastStringMap <V>
{
    private final Map<String, V> map = new HashMap<String, V>();

    public NFastStringMap()
    {
        LienzoMockitoLogger.log("NFastStringMap", "Creating custom Lienzo overlay type.");
    }

    public NFastStringMap<V> put(final String key, final V value)
    {
        map.put(NUtils.doKeyRepair(key), value);

        return this;
    }

    public V get(final String key)
    {
        return map.get(NUtils.doKeyRepair(key));
    }

    public NFastStringMap<V> remove(final String key)
    {
        map.remove(key);

        return this;
    }

    public boolean isDefined(final String key)
    {
        return map.containsKey(key);
    }

    public boolean isNull(final String key)
    {
        return map.get(key) == null;
    }

    public int size()
    {
        return map.size();
    }

    public NFastStringMap<V> clear()
    {
        map.clear();

        return this;
    }

    public Collection<String> keys()
    {
        return map.keySet();
    }

    public Collection<V> values()
    {
        final ArrayList<V> list = new ArrayList<V>(map.values());
        
        return Collections.unmodifiableList(list);
    }

    public boolean isEmpty()
    {
        return map.isEmpty();
    }
}
