package com.tdt4240.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MainMenuScreen extends ScreenAdapter {


    private Stage stage;
    private TextButton startButton, exitButton, settingsButton;
    private Actor actor;
    private TextButton.TextButtonStyle style;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Table table;
    private Skin skin;


    public MainMenuScreen(){

        atlas = new TextureAtlas(new FileHandle("./uiskin.atlas"));
        skin = new Skin(new FileHandle("./uiskin.json"));
        skin.addRegions(atlas);

        style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(atlas.findRegion("default-rect"));
        style.down = new TextureRegionDrawable(atlas.findRegion("default-rect-down"));
        table = new Table();

        //actor = new Actor();
        font = new BitmapFont(new FileHandle("./default.fnt"));
        style.font = font;

        stage = new Stage();
        startButton = new TextButton("START",skin);
        exitButton = new TextButton("EXIT",skin);
        settingsButton = new TextButton("SETTINGS",skin);

        startButton.setColor(Color.YELLOW);
        settingsButton.setColor(Color.BLUE);

        table.setFillParent(true);
        table.setDebug(false); //debugger
        table.row();
        table.add(startButton);
        table.row();
        table.add(exitButton);
        table.row();
        table.add(settingsButton);

        table.setHeight(Gdx.graphics.getHeight());
        table.setWidth(Gdx.graphics.getWidth());
        stage.addActor(table);
    }

    public void render(float dtime){
        stage.act(dtime);
        stage.draw();
    }

    public Stage getStage(){
        return stage;
    }
}
