package com.tdt4240.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.controllers.MainMenuController;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.views.MainMenuScreen;
import com.tdt4240.game.views.SplashScreen;


public class Game extends ApplicationAdapter {
  
  private EcsEngine engine;
  private boolean preloaded = false;

  private Assets assets;
  private MainMenuController controller;
  private MainMenuScreen screen;

  @Override
  public void create () {
    GLSettings.create();
    assets = Assets.getInstance();
    assets.setup();
    assets.preload().subscribe((List<?> assets) -> {
      System.out.println("All assets finished loading!");
      for(Object obj : assets){
        System.out.println(obj);
      }
      screen = new MainMenuScreen();
      controller = new MainMenuController(screen);
      preloaded = true;
      Gdx.input.setInputProcessor(controller);
    });

  }

  @Override
  public void render () {
    float delta = Gdx.graphics.getDeltaTime();
    assets.loadUpdate();
    if(preloaded){
      engine.update(delta);
      GLSettings.preRender();
      screen.render(delta);
      GLSettings.postRender();
    }

  }

  @Override
  public void dispose () {
  }
}
