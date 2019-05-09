package com.tdt4240.game.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.models.MainMenuModel;

public class MainMenuView extends GdxScreenView<MainMenuModel> {

    private Texture background;
    public MainMenuView(MainMenuModel model) {
        super(model);
        background = Assets.getInstance().getAsset("texture.main-menu.jpg");

    }

    public void render(float dtime){
        Stage stage = getModel().getStage();
        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }
}
