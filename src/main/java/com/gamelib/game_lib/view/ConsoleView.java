package com.gamelib.game_lib.view;

import com.gamelib.game_lib.model.Console;
import com.gamelib.game_lib.service.CompanyService;
import com.gamelib.game_lib.service.ConsoleService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Console Page")
@Route(value="console", layout = MainLayout.class)
public class ConsoleView extends VerticalLayout {

    Grid<Console> grid = new Grid<>(Console.class);
    TextField filterText = new TextField();
    ConsoleForm form;
    CompanyService companyService;
    ConsoleService consoleService;

    public ConsoleView(CompanyService companyService, ConsoleService consoleService) {
        this.consoleService = consoleService;
        this.companyService = companyService;
        addClassName("console-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateConsoleList();
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
        form = new ConsoleForm(companyService.getAllCompanies());
        form.setWidth("25em");
        form.addListener(ConsoleForm.SaveEvent.class, this::saveConsole);
        form.addListener(ConsoleForm.DeleteEvent.class, this::deleteConsole);
        form.addListener(ConsoleForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("console-grid");
        grid.setSizeFull();
        grid.setColumns("name", "year");
        grid.addColumn(console -> console.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editConsole(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateConsoleList());

        Button addConsoleButton = new Button("Add Console");
        addConsoleButton.addClickListener(click -> addConsole());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addConsoleButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveConsole(ConsoleForm.SaveEvent event) {
        consoleService.createConsole(event.getConsole());
        updateConsoleList();
        closeEditor();
    }

    private void deleteConsole(ConsoleForm.DeleteEvent event) {
        consoleService.deleteConsole(event.getConsole());
        updateConsoleList();
        closeEditor();
    }

    public void editConsole(Console console) {
        if (console == null) {
            closeEditor();
        } else {
            form.setConsole(console);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setConsole(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addConsole() {
        grid.asSingleSelect().clear();
        editConsole(new Console());
    }

    private void updateConsoleList() {
        grid.setItems(consoleService.findAllConsoles(filterText.getValue()));
    }
}
