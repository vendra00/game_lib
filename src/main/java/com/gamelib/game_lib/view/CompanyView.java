package com.gamelib.game_lib.view;

import com.gamelib.game_lib.model.Company;
import com.gamelib.game_lib.service.CompanyService;
import com.gamelib.game_lib.service.CountryService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Company Page")
@Route(value="company", layout = MainLayout.class)
public class CompanyView extends VerticalLayout {

    Grid<Company> grid = new Grid<>(Company.class);
    TextField filterText = new TextField();
    CompanyForm form;
    CountryService countryService;
    CompanyService companyService;


    public CompanyView(CompanyService companyService, CountryService countryService) {
        this.countryService = countryService;
        this.companyService = companyService;
        addClassName("company-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateCompanyList();
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
        form = new CompanyForm(countryService.getAllCountries());
        form.setWidth("25em");
        form.addListener(CompanyForm.SaveEvent.class, this::saveCompany);
        form.addListener(CompanyForm.DeleteEvent.class, this::deleteCompany);
        form.addListener(CompanyForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("company-grid");
        grid.setSizeFull();
        grid.setColumns("name");
        grid.addColumn(company -> company.getCountry().getName()).setHeader("Country");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editCompany(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateCompanyList());

        Button addCompanyButton = new Button("Add Company");
        addCompanyButton.addClickListener(click -> addCompany());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCompanyButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveCompany(CompanyForm.SaveEvent event) {
        companyService.createCompany(event.getCompany());
        updateCompanyList();
        closeEditor();
    }

    private void deleteCompany(CompanyForm.DeleteEvent event) {
        companyService.deleteCompany(event.getCompany());
        updateCompanyList();
        closeEditor();
    }

    public void editCompany(Company company) {
        if (company == null) {
            closeEditor();
        } else {
            form.setCompany(company);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCompany(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addCompany() {
        grid.asSingleSelect().clear();
        editCompany(new Company());
    }

    private void updateCompanyList() {
        grid.setItems(companyService.findAllCompanies(filterText.getValue()));
    }
}
