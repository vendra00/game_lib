package com.gamelib.game_lib;

import com.gamelib.game_lib.model.Game;
import com.gamelib.game_lib.view.GameForm;
import com.gamelib.game_lib.view.GameView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameViewTest {

    @Autowired
    private GameView gameView;

    @Test
    public void formShownWhenGameSelected() {
        Grid<Game> grid = gameView.grid;
        Game firstGame = getFirstItem(grid);

        GameForm form = gameView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstGame);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstGame.getName(), form.name.getValue());
    }

    private Game getFirstItem(Grid<Game> grid) {
        return( (ListDataProvider<Game>) grid.getDataProvider()).getItems().iterator().next();
    }
}
