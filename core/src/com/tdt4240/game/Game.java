package com.tdt4240.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.tdt4240.game.assets.Assets;

import com.tdt4240.game.mvc.MVCManager;

import com.tdt4240.game.net.NetInst;
import com.tdt4240.game.net.NetSessionService;
import com.tdt4240.game.net.NetUserService;


public class Game extends ApplicationAdapter {
  

  private Assets assets;

  private MVCManager manager = MVCManager.getInstance();

  public Game(NetSessionService sessionService, NetUserService userService){
    NetInst.sessionService = sessionService;
    NetInst.userService = userService;
  }

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
