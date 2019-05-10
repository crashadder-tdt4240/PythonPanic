package com.tdt4240.game.mvc.models;

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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240.game.assets.Assets;


public class SettingsMenuModel extends GdxStageModel {


    private Stage stage;
    private TextButton musicButton, soundButton;
    private Slider soundSlider, musicSlider;
    private Actor actor;
    private TextButton.TextButtonStyle style;
    private Slider.SliderStyle sliderStyle;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Table table;
    private Skin skin;


    public SettingsMenuModel(){
        Assets assets = Assets.getInstance();
        skin = assets.getAsset("skin.uiskin.json");
        
        table = new Table();

        
        stage = new Stage();
        TextButton soundButton = new TextButton("ENABLE SOUND",skin);
        TextButton musicButton = new TextButton("ENABLE MUSIC",skin);
        TextButton exitButton = new TextButton("EXIT GAME", skin);
        TextButton backButton = new TextButton("BACK", skin);
        Slider musicSlider = new Slider(0.1f,1, 0.1f, false, skin);


        soundButton.setColor(Color.YELLOW);
        musicButton.setColor(Color.YELLOW);
        exitButton.setColor(Color.YELLOW);
        backButton.setColor(Color.YELLOW);
        musicSlider.setColor(Color.YELLOW);

        table.setFillParent(true);
        table.setDebug(false); //debugger
        table.row();
        table.add(soundButton);
        table.row();
        table.add(musicButton);
        table.row();
        table.add(exitButton);
        table.row();
        table.add(backButton);
    

        table.setHeight(Gdx.graphics.getHeight());
        table.setWidth(Gdx.graphics.getWidth());
        stage.addActor(table);


        bindActor("ENABLE SOUND", soundButton);
        bindActor("ENABLE MUSIC", musicButton);
        bindActor("EXIT GAME", exitButton);
        bindActor("BACK", backButton);



    }


    public void update(float dtime){
        stage.act(dtime);
    }

    public Stage getStage(){
        return stage;
    }
}