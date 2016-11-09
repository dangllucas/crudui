package org.vaadin.crudui.impl.form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;

/**
 * @author Alejandro Duarte
 */
public class AutoGeneratedGridLayoutCrudForm<T> extends AbstractAutoGeneratedCrudFormBuilder<T> {

    private GridLayout mainLayout;
    private FieldGroupFieldFactory fieldFactory;
    private int columns;
    private int rows;

    public AutoGeneratedGridLayoutCrudForm() {
        super(DefaultCrudFieldFactory.get());
    }

    public AutoGeneratedGridLayoutCrudForm(int columns, int rows) {
        this(new DefaultCrudFieldFactory(), columns, rows);
    }

    public AutoGeneratedGridLayoutCrudForm(FieldGroupFieldFactory fieldFactory, int columns, int rows) {
        super(fieldFactory);
        this.columns = columns;
        this.rows = rows;
        mainLayout = new GridLayout(columns, rows);
        mainLayout.setSpacing(true);
        mainLayout.setSizeUndefined();
    }

    @Override
    public Component buildNewForm(T domainObject, Object[] visiblePropertyIds, Object disabledPropertyIds[], String[] fieldCaptions, boolean readOnly, String buttonCaption, String errorMessage, Resource buttonIcon, String buttonStyle, ClickListener buttonClickListener) {
        mainLayout.removeAllComponents();
        mainLayout.setColumns(columns);
        mainLayout.setRows(rows);

        FieldGroup fieldGroup = new FieldGroup(new BeanItem<T>(domainObject));
        fieldGroup.setFieldFactory(fieldFactory);

        addFields(domainObject.getClass(), visiblePropertyIds, disabledPropertyIds, fieldCaptions, readOnly, fieldGroup, mainLayout);

        Button button = new Button(buttonCaption, buttonIcon);
        button.setStyleName(buttonStyle);
        mainLayout.setRows(mainLayout.getRows() + 1);
        mainLayout.addComponent(button, 0, mainLayout.getRows() - 1);
        button.addClickListener(e -> {
            try {
                fieldGroup.commit();
                buttonClickListener.buttonClick(e);

            } catch (CommitException e1) {
                Notification.show(errorMessage);
            }

        });

        return mainLayout;
    }

    public GridLayout getMainLayout() {
        return mainLayout;
    }

}