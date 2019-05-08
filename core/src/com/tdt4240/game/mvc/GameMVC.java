package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.GameController;
import com.tdt4240.game.mvc.controllers.LoadingController;
import com.tdt4240.game.mvc.models.GameModel;
import com.tdt4240.game.mvc.models.LoadingModel;
import com.tdt4240.game.mvc.views.GameView;
import com.tdt4240.game.mvc.views.LoadingView;

public class GameMVC extends MVC<GameModel, GameView, GameController, GameMVCParams>{
  public GameMVC(){

  }

  public void create(){
    GameMVCParams params = new GameMVCParams();
    params.isMultiplayer = false;
    setModel(new GameModel(params));
    setView(new GameView(getModel()));
    setController(new GameController(getView(), getModel()));
  }
  public void create(GameMVCParams params){
    setModel(new GameModel(params));
    setView(new GameView(getModel()));
    setController(new GameController(getView(), getModel()));
  }
}