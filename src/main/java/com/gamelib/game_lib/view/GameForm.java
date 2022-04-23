package com.gamelib.game_lib.view;

import com.gamelib.game_lib.model.Company;
import com.gamelib.game_lib.model.Console;
import com.gamelib.game_lib.model.Game;
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

public class GameForm extends FormLayout {

    private Game game;

    TextField name = new TextField("Game Title");
    TextField year = new TextField("Release Year");
    ComboBox<Console> console = new ComboBox<>("Console");
    ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Game> binder = new BeanValidationBinder<>(Game.class);

    public GameForm(List<Company> companies, List<Console> consoles) {
        addClassName("game-form");
        binder.bindInstanceFields(this);

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        console.setItems(consoles);
        console.setItemLabelGenerator(Console::getName);

        add(name, year, company, console, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, game)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(game);
            fireEvent(new SaveEvent(this, game));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setGame(Game game) {
        this.game = game;
        binder.readBean(game);
    }

    public static abstract class GameFormEvent extends ComponentEvent<GameForm> {
        private Game game;

        protected GameFormEvent(GameForm source, Game game) {
            super(source, false);
            this.game = game;
        }

        public Game getGame() {
            return game;
        }
    }

    public static class SaveEvent extends GameFormEvent {
        SaveEvent(GameForm source, Game game) {
            super(source, game);
        }
    }

    public static class DeleteEvent extends GameFormEvent {
        DeleteEvent(GameForm source, Game game) {
            super(source, game);
        }

    }

    public static class CloseEvent extends GameFormEvent {
        CloseEvent(GameForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}


