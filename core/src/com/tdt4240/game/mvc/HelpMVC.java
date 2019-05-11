package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.HelpController;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.models.HelpModel;
import com.tdt4240.game.mvc.views.GdxScreenView;

public class HelpMVC extends MVC<HelpModel, GdxScreenView<HelpModel>, HelpController, MVCParams<HelpModel, GdxScreenView<HelpModel>, HelpController>>{
  public HelpMVC(){
  }
  
  
  public void create(){
    setModel(new HelpModel());
    setView(new GdxScreenView<HelpModel>(getModel()));
    setController(new HelpController(getView(), getModel()));
  }
}