/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.forge.camel.commands.project.helper;

/**
 * Helper functions for working with collections
 */
public class CollectionHelper {

    /**
     * Returns the first item in the given collection
     */
    public static <T> T first(Iterable<T> objects) {
        if (objects != null) {
            for (T object : objects) {
                return object;
            }
        }
        return null;
    }

    /**
     * Returns the last item in the given collection
     */
    public static <T> T last(Iterable<T> objects) {
        T answer = null;
        if (objects != null) {
            for (T object : objects) {
                answer = object;
            }
        }
        return answer;
    }
}
