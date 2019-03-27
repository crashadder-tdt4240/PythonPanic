package com.tdt4240.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.game.controllers.GameController;
import com.tdt4240.game.controllers.MainMenuController;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.views.GameScreen;
import com.tdt4240.game.views.MainMenuScreen;

public class Game extends ApplicationAdapter {
  
  private EcsEngine engine;
  private MainMenuController controller;
  private MainMenuScreen screen;
  @Override
  public void create () {
    GLSettings.create();
    engine = new EcsEngine();
    screen = new MainMenuScreen();
    controller = new MainMenuController(screen);
    //Gdx.input.setInputProcessor(controller);
  }

  @Override
  public void render () {
    float delta = Gdx.graphics.getDeltaTime();
    engine.update(delta);
    
    GLSettings.preRender();
    screen.render(delta);
    GLSettings.postRender();
    
  }

  @Override
  public void dispose () {
  }
}
