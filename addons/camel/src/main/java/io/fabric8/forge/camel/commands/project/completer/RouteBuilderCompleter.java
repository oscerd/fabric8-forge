/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.forge.camel.commands.project.completer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.parser.java.resources.JavaResourceVisitor;
import org.jboss.forge.addon.resource.visit.VisitContext;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.input.InputComponent;
import org.jboss.forge.addon.ui.input.UICompleter;
import org.jboss.forge.roaster.model.JavaClass;

public class RouteBuilderCompleter implements UICompleter<String> {

    private final Set<String> routeBuilders = new TreeSet<String>();
    private final Set<String> packages = new TreeSet<String>();

    public RouteBuilderCompleter(JavaSourceFacet facet) {
        // find package names in the source code
        facet.visitJavaSources(new JavaResourceVisitor() {
            @Override
            public void visit(VisitContext context, JavaResource javaResource) {
                try {
                    // avoid package-info.java files
                    if (!javaResource.getName().contains("package-info")) {
                        JavaClass clazz = javaResource.getJavaType();
                        String superType = clazz.getSuperType();
                        if (superType != null && isRouteBuilder(superType)) {
                            routeBuilders.add(clazz.getQualifiedName());
                            packages.add(clazz.getPackage());
                        }
                    }
                } catch (FileNotFoundException e) {
                    // ignore
                }
            }
        });
    }

    private boolean isRouteBuilder(String superType) {
        return "org.apache.camel.builder.RouteBuilder".equals(superType) || "org.apache.camel.spring.boot.FatJarRouter".equals(superType);
    }

    public Set<String> getPackages() {
        return packages;
    }

    public Set<String> getRouteBuilders() {
        return routeBuilders;
    }

    @Override
    public Iterable<String> getCompletionProposals(UIContext context, InputComponent input, String value) {
        List<String> answer = new ArrayList<String>();

        for (String name : routeBuilders) {
            if (value == null || name.startsWith(value)) {
                answer.add(name);
            }
        }

        return answer;
    }
}
