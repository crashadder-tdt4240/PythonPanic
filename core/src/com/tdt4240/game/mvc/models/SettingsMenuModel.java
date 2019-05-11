package com.tdt4240.game.mvc.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tdt4240.game.assets.Assets;


public class SettingsMenuModel extends GdxStageModel {


    private Stage stage;
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


        soundButton.setColor(Color.YELLOW);
        musicButton.setColor(Color.YELLOW);
        exitButton.setColor(Color.YELLOW);
        backButton.setColor(Color.YELLOW);

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