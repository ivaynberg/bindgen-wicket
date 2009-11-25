/*
 * $Id: ListContactsPage.java 903 2006-08-30 09:08:51Z ivaynberg $
 * $Revision: 903 $
 * $Date: 2006-08-30 02:08:51 -0700 (Wed, 30 Aug 2006) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.bindgen.wicket.phonebook.web.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.bindgen.wicket.BindingColumn;
import org.bindgen.wicket.phonebook.Contact;
import org.bindgen.wicket.phonebook.ContactBinding;
import org.bindgen.wicket.phonebook.ContactDao;
import org.bindgen.wicket.phonebook.web.CheckBoxColumn;
import org.bindgen.wicket.phonebook.web.ContactsDataProvider;

/**
 * Display a Pageable List of Contacts.
 * 
 * @author igor
 */
public class ListContactsPage extends BasePage
{
    @SpringBean(name = "contactDao")
    private ContactDao dao;

    /**
     * Provides a composite User Actions panel for the Actions column.
     * 
     * @author igor
     */
    private static class UserActionsPanel extends Panel
    {
        public UserActionsPanel(String id, IModel<Contact> contactModel)
        {
            super(id);
            addEditLink(contactModel);
            addDeleteLink(contactModel);

        }

        private void addDeleteLink(IModel<Contact> contactModel)
        {
            add(new Link<Contact>("deleteLink", contactModel)
            {
                /**
                 * Go to the Delete page, passing this page and the id of the Contact involved.
                 */
                @Override
                public void onClick()
                {
                    setResponsePage(new DeleteContactPage(getPage(), getModel()));
                }
            });
        }

        private void addEditLink(IModel<Contact> contactModel)
        {
            add(new Link<Contact>("editLink", contactModel)
            {
                /**
                 * Go to the Edit page, passing this page and the id of the Contact involved.
                 */
                @Override
                public void onClick()
                {
                    setResponsePage(new EditContactPage(getPage(), getModel()));
                }
            });
        }

    }

    /**
     * Constructor. Having this constructor public means that the page is 'bookmarkable' and hence
     * can be called/ created from anywhere.
     */
    public ListContactsPage()
    {

        addCreateLink();

        // set up data provider
        ContactsDataProvider dataProvider = new ContactsDataProvider(dao);

        // create the data table
        add(new DefaultDataTable<Contact>("users", createColumns(), dataProvider, 10));
    }

    private List<IColumn<Contact>> createColumns()
    {
        List<IColumn<Contact>> columns = new ArrayList<IColumn<Contact>>();
        columns.add(createActionsColumn());
        columns.add(new BindingColumn<Contact>(new ContactBinding().firstname()).setHeader(
                "first.name").setSortToData());
        columns.add(new BindingColumn<Contact>(new ContactBinding().lastname()).setHeader(
                "last.name").setSortToData());
        columns.add(new BindingColumn<Contact>(new ContactBinding().phone()).setHeader("phone")
                .setSortToData());
        columns.add(new BindingColumn<Contact>(new ContactBinding().email()).setHeader("email")
                .setSortToData());
        return columns;
    }

    private AbstractColumn<Contact> createActionsColumn()
    {
        return new AbstractColumn<Contact>(new Model<String>(getString("actions")))
        {
            // add the UserActionsPanel to the cell item
            public void populateItem(Item<ICellPopulator<Contact>> cellItem, String componentId,
                    IModel<Contact> rowModel)
            {
                cellItem.add(new UserActionsPanel(componentId, rowModel));
            }
        };
    }

    private void addCreateLink()
    {
        add(new Link<Void>("createLink")
        {
            /**
             * Go to the Edit page when the link is clicked, passing an empty Contact details
             */
            @Override
            public void onClick()
            {
                setResponsePage(new EditContactPage(getPage(), new Model<Contact>(new Contact())));
            }
        });
    }
}
