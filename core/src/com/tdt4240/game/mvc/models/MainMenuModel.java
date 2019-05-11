package com.tdt4240.game.mvc.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.MusicManager;

public class MainMenuModel extends GdxStageModel{
  private Stage stage;
  private TextButton.TextButtonStyle style;
  private TextureAtlas atlas;
  private BitmapFont font;
  private Table table;
  private Skin skin;
  private Music music;

  public MainMenuModel(){
    Assets assets = Assets.getInstance();
    MusicManager music = MusicManager.getInstance();
        
    skin = assets.getAsset("skin.uiskin.json");
    music.playMusic();

    table = new Table();



    
    stage = new Stage();
    TextButton startButton = new TextButton("START",skin);
    TextButton hostButton = new TextButton("HOST", skin);
    TextButton joinButton = new TextButton("JOIN", skin);
    TextButton exitButton = new TextButton("EXIT",skin);
    TextButton settingsButton = new TextButton("SETTINGS",skin);
    TextButton helpButton = new TextButton("HELP", skin);

    startButton.setColor(Color.YELLOW);
    hostButton.setColor(Color.YELLOW);
    joinButton.setColor(Color.YELLOW);
    exitButton.setColor(Color.YELLOW);
    settingsButton.setColor(Color.YELLOW);
    helpButton.setColor(Color.YELLOW);

    table.setFillParent(true);
    table.setDebug(false); //debugger
    table.row();
    table.add(hostButton);
    table.row();
    table.add(joinButton);
    table.row();
    table.add(startButton);
    table.row();
    table.add(exitButton);
    table.row();
    table.add(settingsButton);
    table.row();
    table.add(helpButton);

    table.setHeight(Gdx.graphics.getHeight());
    table.setWidth(Gdx.graphics.getWidth());
    stage.addActor(table);


    bindActor("START", startButton);
    bindActor("HOST", hostButton);
    bindActor("JOIN", joinButton);
    bindActor("EXIT", exitButton);
    bindActor("SETTINGS", settingsButton);
    bindActor("HELP", helpButton);

  }

  
  public void update(float dtime){
    stage.act(dtime);
  }

  public Stage getStage(){
    return stage;
  }

  

}