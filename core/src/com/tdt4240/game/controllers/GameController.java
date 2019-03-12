package com.tdt4240.game.controllers;

import java.util.Vector;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.ecs.components.PlayerInputComponent;

public class GameController extends InputAdapter{
  
  private EcsEngine engine;

  private IntBag entities;

  private ComponentMapper<PlayerInputComponent> pComponentMapper;

  public GameController(EcsEngine engine){
    this.engine = engine;
    this.pComponentMapper = engine.getMapper(PlayerInputComponent.class);
  }

  private void updateEntities(){
    entities = engine.getEntities(Aspect.all(PlayerInputComponent.class));
  }

  private void updateInput(Vector2 newInput){
    updateEntities();
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      PlayerInputComponent playerInputComponent = pComponentMapper.get(entity);
      playerInputComponent.steerInput.add(newInput);
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

}