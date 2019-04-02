package com.tdt4240.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.controllers.GameController;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.views.GameScreen;

import io.reactivex.functions.Consumer;

public class Game extends ApplicationAdapter {
  
  private EcsEngine engine;
  private GameController controller;
  private GameScreen screen;

  private Assets assets;

  @Override
  public void create () {
    GLSettings.create();
    engine = new EcsEngine();
    controller = new GameController(engine);
    screen = new GameScreen(engine);
    Gdx.input.setInputProcessor(controller);
    assets = new Assets();
    assets.setup();
    assets.preload();
  }

  @Override
  public void render () {
    float delta = Gdx.graphics.getDeltaTime();
    engine.update(delta);
    assets.loadUpdate();
    GLSettings.preRender();
    screen.render(delta);
    GLSettings.postRender();
    
  }

  @Override
  public void dispose () {
  }
}
