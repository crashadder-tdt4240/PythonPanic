package com.tdt4240.game.mvc.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.MusicManager;

public class MainMenuModel extends GdxStageModel{
  private Stage stage;
  private TextButton startButton, exitButton, settingsButton;
  private TextButton.TextButtonStyle style;
  private Table table;
  private Skin skin;


  public MainMenuModel(){
    Assets assets = Assets.getInstance();
    Texture splashTexture = assets.getAsset("texture.python-panic.png");
    Drawable splash = new TextureRegionDrawable(new TextureRegion(splashTexture));
    MusicManager music = MusicManager.getInstance();
        
    skin = assets.getAsset("skin.uiskin.json");
    music.playMusic();


    table = new Table();
    table.setBackground(splash);

  
    
    stage = new Stage();
    startButton = new TextButton("START",skin);

    exitButton = new TextButton("EXIT",skin);
    settingsButton = new TextButton("SETTINGS",skin);

    startButton.setColor(Color.YELLOW);
    settingsButton.setColor(Color.BLUE);

    TextButton hostButton = new TextButton("HOST", skin);
    TextButton joinButton = new TextButton("JOIN", skin);


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

    table.setHeight(Gdx.graphics.getHeight());
    table.setWidth(Gdx.graphics.getWidth());
    stage.addActor(table);


    bindActor("START", startButton);
    bindActor("HOST", hostButton);
    bindActor("JOIN", joinButton);
    bindActor("EXIT", exitButton);
    bindActor("SETTINGS", settingsButton);


  }

  
  public void update(float dtime){
    stage.act(dtime);
  }

  public Stage getStage(){
    return stage;
  }

  

}