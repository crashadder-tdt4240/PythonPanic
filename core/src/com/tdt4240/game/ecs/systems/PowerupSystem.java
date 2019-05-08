package com.tdt4240.game.ecs.systems;

import java.util.ArrayList;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.KillBoxComponent;
import com.tdt4240.game.ecs.components.PowerupComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.powerups.Powerup;

public class PowerupSystem extends IteratingSystem{
  
  private ComponentMapper<Box2dComponent> bComponentMapper;
  private ComponentMapper<PowerupModifiersComponent> powerupModMapper;
  private ComponentMapper<PowerupComponent> powerupMapper;

  public PowerupSystem(){
    super(Aspect.all(Box2dComponent.class).one(PowerupComponent.class, PowerupModifiersComponent.class));
  }
  
  public void process(int entity){
    // needs better checking, and has to be mp safe
    if(powerupMapper.has(entity)){
    
      Box2dComponent bComponent = bComponentMapper.get(entity);
      
      if(bComponent.contact && bComponent.contactWith >= 0){
        int other = bComponent.contactWith;
        if(powerupModMapper.has(other)){
          PowerupModifiersComponent modComponent = powerupModMapper.get(other);
          Powerup powerup = powerupMapper.get(entity).powerup;
          powerup.active = true;
          modComponent.powerups.add(powerup);

          getWorld().delete(entity);
        }
      }
    } else if(powerupModMapper.has(entity)){
      PowerupModifiersComponent modComponent = powerupModMapper.get(entity);
      ArrayList<Powerup> toRemove = new ArrayList<Powerup>(modComponent.powerups.size());
      for(Powerup powerup : modComponent.powerups){
        powerup.life -= getWorld().getDelta();
        if(powerup.life <= 0){
          toRemove.add(powerup);
        }
      }
      for(Powerup powerup : toRemove){
        modComponent.powerups.remove(powerup);
      }
    }
  }
}