package com.gamelib.game_lib.view;

import com.gamelib.game_lib.model.Company;
import com.gamelib.game_lib.model.Console;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ConsoleForm extends FormLayout {

    private Console console;

    TextField name = new TextField("Console Name");
    TextField year = new TextField("Release Year");
    ComboBox<Company> company = new ComboBox<>("Company");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<Console> binder = new BeanValidationBinder<>(Console.class);

    public ConsoleForm(List<Company> companies) {
        addClassName("console-form");
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);

        add(name, year, company, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new ConsoleForm.DeleteEvent(this, console)));
        close.addClickListener(event -> fireEvent(new ConsoleForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(console);
            fireEvent(new ConsoleForm.SaveEvent(this, console));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setConsole(Console console) {
        this.console = console;
        binder.readBean(console);
    }

    public static abstract class ConsoleFormEvent extends ComponentEvent<ConsoleForm> {
        private Console console;

        protected ConsoleFormEvent(ConsoleForm source, Console console) {
            super(source, false);
            this.console = console;
        }

        public Console getConsole() {
            return console;
        }
    }

    public static class SaveEvent extends ConsoleForm.ConsoleFormEvent {
        SaveEvent(ConsoleForm source, Console console) {
            super(source, console);
        }
    }

    public static class DeleteEvent extends ConsoleForm.ConsoleFormEvent {
        DeleteEvent(ConsoleForm source, Console console) {
            super(source, console);
        }
    }

    public static class CloseEvent extends ConsoleForm.ConsoleFormEvent {
        CloseEvent(ConsoleForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
