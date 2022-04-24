package com.gamelib.game_lib;

import com.gamelib.game_lib.model.Company;
import com.gamelib.game_lib.model.Console;
import com.gamelib.game_lib.model.Game;
import com.gamelib.game_lib.view.GameForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GameFormTest {

    private List<Company> companies;
    private List<Console> consoles;
    private Game game;
    private Company company1;
    private Company company2;
    private Console console1;
    private Console console2;

    @Before
    public void setupData() {

        companies = new ArrayList<>();
        consoles = new ArrayList<>();

        game = new Game();

        company1 = new Company();
        company2 = new Company();

        console1 = new Console();
        console2 = new Console();

        company1.setName("Nintendo");
        company2.setName("Sega");

        companies.add(company1);
        companies.add(company2);

        console1.setName("Nintendo 64");
        console2.setName("Mega Drive");

        consoles.add(console1);
        consoles.add(console1);

        game.setName("Mario 64");
        game.setYear("1998");
        game.setConsole(console1);
        game.setCompany(company1);
    }

    @Test
    public void formFieldsPopulated() {
        GameForm form = new GameForm(companies, consoles);
        form.setGame(game);
        Assert.assertEquals("Mario 64", form.name.getValue());
        Assert.assertEquals("1998", form.year.getValue());
        Assert.assertEquals(company1, form.company.getValue());
        Assert.assertEquals(console1, form.console.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        GameForm form = new GameForm(companies, consoles);
        Game game = new Game();
        form.setGame(game);
        form.name.setValue("Super Mario");
        form.year.setValue("1990");
        form.company.setValue(company1);
        form.console.setValue(console1);

        AtomicReference<Game> savedContactRef = new AtomicReference<>(null);
        form.addListener(GameForm.SaveEvent.class, e -> savedContactRef.set(e.getGame()));
        form.save.click();
        Game savedGame = savedContactRef.get();

        Assert.assertEquals("Super Mario", savedGame.getName());
        Assert.assertEquals("1990", savedGame.getYear());
        Assert.assertEquals(company1, savedGame.getCompany());
        Assert.assertEquals(console1, savedGame.getConsole());
    }
}
