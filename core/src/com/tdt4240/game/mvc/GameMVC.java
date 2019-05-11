package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.GameController;
import com.tdt4240.game.mvc.models.GameModel;
import com.tdt4240.game.mvc.views.GameView;

public class GameMVC extends MVC<GameModel, GameView, GameController, MVCParams<GameModel, GameView, GameController>>{
  public GameMVC(){

  }

  public void create(){
    setModel(new GameModel());
    setView(new GameView(getModel()));
    setController(new GameController(getView(), getModel()));
  }
}