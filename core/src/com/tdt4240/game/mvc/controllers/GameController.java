package com.tdt4240.game.mvc.controllers;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.mvc.models.GameModel;
import com.tdt4240.game.mvc.views.GameView;

public class GameController extends MVCController<GameView, GameModel>{
  


  public GameController(GameView view, GameModel model){
    super(view, model);
  }



  private void updateInput(Vector2 newInput){
    for(PlayerInputComponent playerInputs : getModel().getPlayerInputs()){
      playerInputs.steerInput.add(newInput);
    }
  }

  @Override
  public boolean keyDown(int keycode) {
    // update all playerinputcomponents
    Vector2 newInput = new Vector2(0,0);
    boolean handled = false;
    if(keycode == Input.Keys.A){
      newInput.x = 1;
      handled = true;
    }
    if(keycode == Input.Keys.D){
      newInput.y = 1;
      handled = true;
    }
    updateInput(newInput);
    return handled;
  }

  @Override
  public boolean keyUp(int keycode) {
    // update all playerinputcomponents
    Vector2 newInput = new Vector2(0,0);
    boolean handled = false;
    if(keycode == Input.Keys.A){
      newInput.x = -1;
      handled = true;
    }
    if(keycode == Input.Keys.D){
      newInput.y = -1;
      handled = true;
    }
    updateInput(newInput);
    return handled;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    Vector2 newInput = new Vector2(0, 0);
    if(screenX <= Gdx.graphics.getWidth()/2 ){
      newInput.x = -1;
    } else {
      newInput.x = 1;
    }
    updateInput(newInput);
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    Vector2 newInput = new Vector2(0, 0);
    if(screenX <= Gdx.graphics.getWidth()/2 ){
      newInput.x = 1;
    } else {
      newInput.x = -1;
    }
    updateInput(newInput);
    return true;
  }

}