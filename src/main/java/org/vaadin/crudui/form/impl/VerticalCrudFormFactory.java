package org.vaadin.crudui.form.impl;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;
import org.vaadin.crudui.CrudOperation;
import org.vaadin.crudui.form.AbstractAutoGeneratedCrudFormFactory;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Alejandro Duarte
 */
public class VerticalCrudFormFactory<T> extends AbstractAutoGeneratedCrudFormFactory<T> {

    public VerticalCrudFormFactory(Class<T> domainType) {
        super(domainType);
    }

    @Override
    public Component buildNewForm(CrudOperation operation, T domainObject, boolean readOnly, Consumer<T> crudOperationListener) {
        Button button = new Button(buttonCaptions.get(operation), buttonIcons.get(operation));
        buttonStyleNames.get(operation).forEach(styleName -> button.addStyleName(styleName));

        HorizontalLayout footerLayout = new HorizontalLayout(button);
        footerLayout.setSizeUndefined();

        BeanFieldGroup fieldGroup = new BeanFieldGroup<>(domainObject.getClass());
        List<Field> fields = buildAndBind(operation, domainObject, readOnly, fieldGroup);

        FormLayout formLayout = new FormLayout();
        fields.stream().forEach(field -> formLayout.addComponent(field));

        VerticalLayout mainLayout = new VerticalLayout(formLayout, footerLayout);
        mainLayout.setComponentAlignment(footerLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setMargin(true);

        button.addClickListener(e -> {
            try {
                fieldGroup.commit();
                crudOperationListener.accept(domainObject);

            } catch (FieldGroup.CommitException exception) {
                Notification.show(validationErrorMessage);
            }
        });

        return mainLayout;
    }

}
