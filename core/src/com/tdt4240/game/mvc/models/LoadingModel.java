package com.tdt4240.game.mvc.models;

import com.tdt4240.game.assets.Assets;

public class LoadingModel extends MVCModel{
  
  private Assets assets;
  private float progress = 0;
  private boolean done = false;

  public LoadingModel(){
    assets = Assets.getInstance();
    assets.getProgress().subscribe((Integer p) ->
      progress = p
    );
  }

  public int getProgress(){
    return (int)progress;
  }

  public boolean hasFinished(){
    return done;
  }


  public void update(float dtime){
    assets.loadUpdate();
  }
}