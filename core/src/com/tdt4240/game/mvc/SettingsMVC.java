package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.SettingsMenuController;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.models.SettingsMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;

public class SettingsMVC extends MVC<SettingsMenuModel, GdxScreenView<SettingsMenuModel>, SettingsMenuController, MVCParams<SettingsMenuModel, GdxScreenView<SettingsMenuModel>, SettingsMenuController>>{
  public SettingsMVC(){
  }
  
  
  public void create(){
    setModel(new SettingsMenuModel());
    setView(new GdxScreenView<SettingsMenuModel>(getModel()));
    setController(new SettingsMenuController(getView(), getModel()));
  }
}