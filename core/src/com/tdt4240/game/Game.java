package com.tdt4240.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.LoadingMVC;
import com.tdt4240.game.mvc.MVC;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.MainMenuMVC;
import com.tdt4240.game.mvc.controllers.MainMenuController;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.tdt4240.game.mvc.views.SplashScreen;


public class Game extends ApplicationAdapter {
  

  private Assets assets;

  private MVCManager manager = MVCManager.getInstance();

  @Override
  public void create () {
    GLSettings.create();
    assets = Assets.getInstance();
    assets.setup();
    MVCSetup.setup();


  }

  @Override
  public void render () {
    float delta = Gdx.graphics.getDeltaTime();
    //assets.loadUpdate();
    manager.update(delta);
    GLSettings.preRender();
    manager.render(delta);
    GLSettings.postRender();
  }

  @Override
  public void dispose () {
  }
}
