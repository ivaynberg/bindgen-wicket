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

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.bindgen.Binding;
import org.bindgen.BindingRoot;


/**
 * A column that displays the string value of the specified binding. This class is similar to
 * {@link PropertyColumn}, but uses a {@link Binding} instead of an unsafe string expression
 */
public class BindingColumn<R> extends AbstractBindingColumn<R, Object>
{

    public BindingColumn(BindingRoot<R, ? extends Object> binding)
    {
        super(binding);
    }

    @Override
    protected void populateItem(Item<ICellPopulator<R>> item, String componentId,
            BindingModel<R, Object> model)
    {
        item.add(new Label(componentId, model));
    }


}
