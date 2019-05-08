package com.tdt4240.game.ecs.systems;

import java.util.stream.Collector;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.powerups.Powerup;
import com.tdt4240.game.ecs.powerups.SpeedPowerup;

public class SnakeSystem extends IteratingSystem{

  private ComponentMapper<Box2dComponent> box2dMapper;
  private ComponentMapper<PowerupModifiersComponent> powerupModMapper;
  private float baseSpeed = 100f;
  public SnakeSystem(){
    super(Aspect.all(Box2dComponent.class, SnakeComponent.class));
  
  }


  public void process(int entity){
    // "hack" so final speed can be modified in lambda
    final float[] finalSpeed = {baseSpeed};
    

    if(powerupModMapper.has(entity)){
      
      PowerupModifiersComponent modComponent = powerupModMapper.get(entity);
      if (modComponent.powerups.size() > 0) {finalSpeed[0] =  0;}
      modComponent.powerups.stream().filter((Powerup p) -> (p instanceof SpeedPowerup)).forEachOrdered((Powerup powerup) -> {
        SpeedPowerup speedmod = (SpeedPowerup)powerup;
        finalSpeed[0] += baseSpeed * speedmod.modifier; 
      });
    }

    

    Box2dComponent box2dComponent = box2dMapper.get(entity);

    Body body = box2dComponent.body;
    float angle = body.getAngle();
    Vector2 newVeloc = new Vector2((float)Math.cos(angle), (float) Math.sin(angle));
    newVeloc.scl(finalSpeed[0]);

    body.setLinearVelocity(newVeloc);

  }
}