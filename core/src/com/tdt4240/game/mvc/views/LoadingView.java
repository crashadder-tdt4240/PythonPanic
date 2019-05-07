package com.tdt4240.game.mvc.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.mvc.models.LoadingModel;

public class LoadingView extends MVCView<LoadingModel>{
  
  private Stage stage;
  
  private ProgressBar bar;

  public LoadingView(LoadingModel model){
    super(model);
    Assets assets = Assets.getInstance();
    Skin skin = assets.getSync("skins/uiskin.json", Skin.class);

    Table table = new Table();
    table.setFillParent(true);

    bar = new ProgressBar(0, 100, 1, false, skin);
    bar.getStyle().knob = null;
    table.add(bar);

    stage = new Stage();
    stage.addActor(table);
  }

  public void render(float dtime){
    int progress = getModel().getProgress();
    bar.setValue(progress);
    stage.act();
    stage.draw();
  }
}