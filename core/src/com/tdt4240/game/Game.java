package com.tdt4240.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.tdt4240.game.assets.Assets;
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
  
  private EcsEngine engine;
  private boolean preloaded = false;

  private Assets assets;

  private MVCManager manager = new MVCManager();

  private MVC<MainMenuModel, GdxScreenView<MainMenuModel>, MainMenuController> mainMenuMVC;

  @Override
  public void create () {
    GLSettings.create();
    assets = Assets.getInstance();
    assets.setup();

    mainMenuMVC = new MainMenuMVC();
    manager.registerMVC("MAIN_MENU", mainMenuMVC);



    assets.preload().subscribe((List<?> assets) -> {
      System.out.println("All assets finished loading!");
      for(Object obj : assets){
        System.out.println(obj);
      }
      manager.createMVC("MAIN_MENU");
      Gdx.input.setInputProcessor(manager.getInputProcessor());
      preloaded = true;
    });

  }

  @Override
  public void render () {
    float delta = Gdx.graphics.getDeltaTime();
    assets.loadUpdate();
    if(preloaded){
      manager.update(delta);
      GLSettings.preRender();
      manager.render(delta);
      GLSettings.postRender();
    }

  }

  @Override
  public void dispose () {
  }
}
