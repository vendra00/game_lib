package com.gamelib.game_lib.view;

import com.gamelib.game_lib.model.Game;
import com.gamelib.game_lib.service.CompanyService;
import com.gamelib.game_lib.service.ConsoleService;
import com.gamelib.game_lib.service.GameService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Game Page")
@Route(value="game", layout = MainLayout.class)
public class GameView extends VerticalLayout {
    Grid<Game> grid = new Grid<>(Game.class);
    TextField filterText = new TextField();
    GameForm form;
    GameService gameService;
    CompanyService companyService;
    ConsoleService consoleService;

    public GameView(GameService gameService, CompanyService companyService, ConsoleService consoleService) {
        this.consoleService = consoleService;
        this.gameService = gameService;
        this.companyService = companyService;
        addClassName("game-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateGameList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new GameForm(companyService.getAllCompanies(), consoleService.getAllConsoles());
        form.setWidth("25em");
        form.addListener(GameForm.SaveEvent.class, this::saveGame);
        form.addListener(GameForm.DeleteEvent.class, this::deleteGame);
        form.addListener(GameForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("game-grid");
        grid.setSizeFull();
        grid.setColumns("name", "year");
        grid.addColumn(game -> game.getConsole().getName()).setHeader("Console");
        grid.addColumn(game -> game.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editGame(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGameList());

        Button addGameButton = new Button("Add Game");
        addGameButton.addClickListener(click -> addGame());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addGameButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveGame(GameForm.SaveEvent event) {
        gameService.createGame(event.getGame());
        updateGameList();
        closeEditor();
    }

    private void deleteGame(GameForm.DeleteEvent event) {
        gameService.deleteGame(event.getGame());
        updateGameList();
        closeEditor();
    }

    public void editGame(Game game) {
        if (game == null) {
            closeEditor();
        } else {
            form.setGame(game);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setGame(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addGame() {
        grid.asSingleSelect().clear();
        editGame(new Game());
    }

    private void updateGameList() {
        grid.setItems(gameService.findAllGames(filterText.getValue()));
    }
}
