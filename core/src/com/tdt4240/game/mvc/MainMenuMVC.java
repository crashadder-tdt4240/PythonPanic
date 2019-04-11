package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.MainMenuController;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;

public class MainMenuMVC extends MVC<MainMenuModel, GdxScreenView<MainMenuModel>, MainMenuController>{
  public MainMenuMVC(){
  }
  
  
  public void create(){
    setModel(new MainMenuModel());
    setView(new GdxScreenView(getModel()));
    setController(new MainMenuController(getView(), getModel()));
  }
}