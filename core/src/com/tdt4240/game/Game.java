package com.tdt4240.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.game.controllers.GameController;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.views.GameScreen;

public class Game extends ApplicationAdapter {
  
  private EcsEngine engine;
  private GameController controller;
  private GameScreen screen;
  
  @Override
  public void create () {
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    Gdx.gl.glDepthMask(true);
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    engine = new EcsEngine();
    controller = new GameController(engine);
    screen = new GameScreen(engine);
    Gdx.input.setInputProcessor(controller);

    
  }

  @Override
  public void render () {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    float delta = Gdx.graphics.getDeltaTime();
    engine.update(delta);
    screen.render(delta);

  }

  @Override
  public void dispose () {
  }
}
