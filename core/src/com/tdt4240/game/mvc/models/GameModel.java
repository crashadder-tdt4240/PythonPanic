package com.tdt4240.game.mvc.models;

import java.util.ArrayList;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;

public class GameModel extends MVCModel{
  private EcsEngine engine;
  

  private ComponentMapper<SpriteComponent> spriteMapper;
  private ComponentMapper<PlayerInputComponent> playerInputMapper;

  public GameModel(){
    
    engine = new EcsEngine();
    spriteMapper = engine.getMapper(SpriteComponent.class);
    playerInputMapper = engine.getMapper(PlayerInputComponent.class);

  }

  public EcsEngine getEngine(){
    return engine;
  }

  public List<Decal> getDecals(){
    ArrayList<Decal> decals = new ArrayList<>();
    IntBag entities = engine.getEntities(Aspect.one(SpriteComponent.class));
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      SpriteComponent spriteComponent = spriteMapper.get(entity);
      decals.add(spriteComponent.sprite);
    }
    return decals;
  }

  public List<PlayerInputComponent> getPlayerInputs(){
    ArrayList<PlayerInputComponent> playerInputs = new ArrayList<>();
    IntBag entities = engine.getEntities(Aspect.one(PlayerInputComponent.class));
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      PlayerInputComponent playerInput = playerInputMapper.get(entity);
      playerInputs.add(playerInput);
    }

    return playerInputs;
  }


  public void update(float dtime){
    engine.update(dtime);
  }




}