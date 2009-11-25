/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bindgen.wicket;

import org.apache.wicket.model.IModel;
import org.bindgen.Binding;
import org.bindgen.BindingRoot;

/**
 * A model that uses a {@link Binding} to push and pull data
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * &#064;Bindable
 * public class EditPersonPage extends Webpage {
 *  IModel&lt;Person&gt; person;
 *  
 *  public EditPersonPage(IModel&lt;Person&gt; person) {
 *    this.person=person;
 *    
 *    Form&lt;?&gt; form=new Form&lt;Void&gt;("form");
 *    form.add(new TextField&lt;String&gt;("street1", new BindingModel&lt;String&gt;(new EditPersonPageBinding(this).person().address().street1()));
 * </pre>
 * 
 * or using the shorthand provided by {@link Bindings} class:
 * 
 * <pre>
 * import static org.bindgen.wicket.Bindings.*;
 * import static org.bindgen.BindKeyword.*;
 * 
 * &#064;Bindable
 * public class EditPersonPage extends Webpage {
 *  IModel&lt;Person&gt; person;
 *  
 *  public EditPersonPage(IModel&lt;Person&gt; person) {
 *    this.person=person;
 *    
 *    Form&lt;?&gt; form=new Form&lt;Void&gt;("form");
 *    form.add(new TextField&lt;String&gt;("street1", model(bind(this).person().address().street1()));
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * Example using a root object:
 * 
 * <pre>
 * add(new TextField("street1", new BindingModel&lt;String&gt;(person, new PersonBinding().address().street1()));
 * </pre>
 * 
 * or using the shorthand provided by {@link Bindings} class:
 * 
 * <pre>
 * import static org.bindgen.wicket.Bindings.*;
 * import static org.bindgen.BindKeyword.*;
 * 
 * add(new TextField("street1", model(person, new PersonBinding().address().street1())));
 * 
 * </p>
 * 
 * @author igor.vaynberg
 * 
 * @param <R>
 *            type of object that is the root of the expression
 * @param <T>
 *            type of object returned by the expression
 */
public class BindingModel<R, T> implements IModel<T>
{
    private final Binding<T> binding;
    private final IModel<R> root;

    /**
     * Constructor
     * 
     * @param binding
     */
    public BindingModel(Binding<T> binding)
    {
        this.binding = binding;
        root = null;
    }

    public BindingModel(IModel<R> root, BindingRoot<R, T> binding)
    {
        this.root = root;
        this.binding = binding;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public T getObject()
    {
        if (root == null)
        {
            return binding.get();
        }
        else
        {
            return ((BindingRoot<R, T>)binding).getWithRoot(root.getObject());
        }
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public void setObject(T object)
    {
        if (root == null)
        {
            binding.set(object);
        }
        else
        {
            ((BindingRoot<R, T>)binding).setWithRoot(root.getObject(), object);
        }
    }

    /** {@inheritDoc} */
    public void detach()
    {
        if (root != null)
        {
            root.detach();
        }
    }
    
    /**
     * Convenience method to convert a {@link Binding} into a {@link BindingModel}
     * 
     * @param <T>
     * @param binding
     * @return binding model for {@code binding}
     */
    public static <R, T> IModel<T> of(Binding<T> binding)
    {
        return new BindingModel<R, T>(binding);
    }

    /**
     * Convenience method to convert a {@link BindingRoot} into a {@link BindingRootModel}
     * 
     * @param <T>
     * @param binding
     * @return binding model for {@code binding}
     */
    public static <R, T> IModel<T> of(IModel<R> root, BindingRoot<R, T> binding)
    {
        return new BindingModel<R, T>(root, binding);
    }

}
