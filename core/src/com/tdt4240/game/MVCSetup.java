package com.tdt4240.game;

import com.badlogic.gdx.Gdx;
import com.tdt4240.game.mvc.GameMVC;
import com.tdt4240.game.mvc.LoadingMVC;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.MainMenuMVC;
import com.tdt4240.game.mvc.SessionMVC;

public class MVCSetup{



  public static void setup(){
    MVCManager manager = MVCManager.getInstance();
    Gdx.input.setInputProcessor(manager.getInputProcessor());
    manager.registerMVC("LOADING", new LoadingMVC());
    manager.registerMVC("MAIN_MENU", new MainMenuMVC());
    manager.registerMVC("GAME", new GameMVC());
    manager.registerMVC("SESSION", new SessionMVC());


    manager.createMVC("LOADING");
  }
}