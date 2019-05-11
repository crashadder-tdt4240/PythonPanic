package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.MainMenuController;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.tdt4240.game.mvc.views.MainMenuView;

public class MainMenuMVC extends MVC<MainMenuModel, GdxScreenView<MainMenuModel>, MainMenuController, MVCParams<MainMenuModel, GdxScreenView<MainMenuModel>, MainMenuController>>{
  public MainMenuMVC(){
  }
  
  
  public void create(){
    setModel(new MainMenuModel());
    setView(new MainMenuView(getModel()));
    setController(new MainMenuController(getView(), getModel()));
    
  }
}



