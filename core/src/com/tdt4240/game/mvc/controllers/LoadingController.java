package com.tdt4240.game.mvc.controllers;

import com.badlogic.gdx.Gdx;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.models.LoadingModel;
import com.tdt4240.game.mvc.views.LoadingView;

public class LoadingController extends MVCController<LoadingView, LoadingModel>{
  public LoadingController(LoadingView view, LoadingModel model){
    super(view, model);

    Assets.getInstance().preload().subscribe((Object obj) -> {
        MVCManager.getInstance().createMVC("MAIN_MENU");
      });
  }
}