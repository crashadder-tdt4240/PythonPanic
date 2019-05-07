package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.KillBoxComponent;

public class KillBoxSystem extends IteratingSystem{
  
  private ComponentMapper<Box2dComponent> bComponentMapper;

  public KillBoxSystem(){
    super(Aspect.all(KillBoxComponent.class, Box2dComponent.class));
  }
  
  public void process(int entity){
    Box2dComponent bComponent = bComponentMapper.get(entity);
    if(bComponent.contact && bComponent.contactWith >= 0){
      int other = bComponent.contactWith;
      getWorld().delete(other);
      //bComponent.contact = false;
      //bComponent.contactWith = -1;
    }
  }
}