package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.LoadingController;
import com.tdt4240.game.mvc.models.LoadingModel;
import com.tdt4240.game.mvc.views.LoadingView;

public class LoadingMVC extends MVC<LoadingModel, LoadingView, LoadingController>{
  public LoadingMVC(){

  }

  public void create(){
    setModel(new LoadingModel());
    setView(new LoadingView(getModel()));
    setController(new LoadingController(getView(), getModel()));
  }
}