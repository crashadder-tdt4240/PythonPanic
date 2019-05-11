package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;

public class PlayerInputSystem extends IteratingSystem{
  
  private ComponentMapper<PlayerInputComponent> pInputMapper;
  private ComponentMapper<Box2dComponent> box2dMapper;
  
  public PlayerInputSystem(){
    super(Aspect.all(PlayerInputComponent.class, Box2dComponent.class));
  }

  @Override
  protected void process(int entityId) {
    PlayerInputComponent playerInputComponent = pInputMapper.get(entityId);
    Box2dComponent box2dComponent = box2dMapper.get(entityId);
    Body body = box2dComponent.body;
    float angVeloc = 2.1f*(playerInputComponent.steerInput.x - playerInputComponent.steerInput.y);
    body.setAngularVelocity(angVeloc);
  }
}