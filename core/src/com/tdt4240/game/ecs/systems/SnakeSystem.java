package com.tdt4240.game.ecs.systems;

import java.util.stream.Collector;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.factory.WallFactory;
import com.tdt4240.game.ecs.powerups.Powerup;
import com.tdt4240.game.ecs.powerups.SizePowerup;
import com.tdt4240.game.ecs.powerups.SpeedPowerup;

public class SnakeSystem extends IteratingSystem{

  private ComponentMapper<Box2dComponent> box2dMapper;
  private ComponentMapper<SnakeComponent> snakeMapper;
  private ComponentMapper<DrawComponent> drawMapper;
  private ComponentMapper<TransformComponent> transformMapper;
  
  private ComponentMapper<PowerupModifiersComponent> powerupModMapper;
  
  private float baseSpeed = 100f;
  private float baseSize = 4f;
  private WallFactory wallFactory;
  public SnakeSystem(){
    super(Aspect.all(Box2dComponent.class, SnakeComponent.class));
  }

  @Override
  protected void setWorld(World world) {
    super.setWorld(world);
    wallFactory = new WallFactory(getWorld());
  }

  public void process(int entity){
    // "hack" so final speed can be modified in lambda
    final float[] finalSpeed = {baseSpeed, 0};
    final float[] finalSize = {baseSize, 0};
    

  

    if(powerupModMapper.has(entity)){
      
      PowerupModifiersComponent modComponent = powerupModMapper.get(entity);
      modComponent.powerups.stream().filter((Powerup p) -> (p instanceof SpeedPowerup)).forEachOrdered((Powerup powerup) -> {
        SpeedPowerup speedmod = (SpeedPowerup)powerup;
        finalSpeed[1] += baseSpeed * speedmod.modifier; 
      });

      modComponent.powerups.stream().filter((Powerup p) -> (p instanceof SizePowerup)).forEachOrdered((Powerup powerup) -> {
        SizePowerup sizemod = (SizePowerup)powerup;
        finalSize[1] += baseSize * sizemod.modifier; 
      });

      if(finalSpeed[1] > 0){
        finalSpeed[0] = finalSpeed[1];
      }
      if(finalSize[1] > 0){
        finalSize[0] = finalSize[1];
      }
    }
    

    SnakeComponent snake = snakeMapper.get(entity);

    if(transformMapper.has(entity)){
      snake.pixacc += transformMapper.get(entity).deltaLength;
      snake.pixacc2 += transformMapper.get(entity).deltaLength;
    }

    if(drawMapper.has(entity) ) {
      DrawComponent draw = drawMapper.get(entity);

      draw.radius = Math.max ((int)finalSize[0], 2);
      if(!draw.draw){
        snake.pixacc2 = 0;
      }


      if(draw.draw && snake.pixacc >= snake.wallLength){
        snake.pixacc = 0;
        draw.draw = false;
      } else if(!draw.draw && snake.pixacc >= snake.holeLength) {
        snake.pixacc = 0;
        draw.draw = true;
      }
      
    }

    

    Box2dComponent box2dComponent = box2dMapper.get(entity);

    Body body = box2dComponent.body;
    float angle = body.getAngle();
    Vector2 newVeloc = new Vector2((float)Math.cos(angle), (float) Math.sin(angle));
    newVeloc.scl(finalSpeed[0]);

    body.setLinearVelocity(newVeloc);

    if(snake.pixacc2 >= (finalSize[0] * 2)){
      snake.pixacc2 = 0;
      // spawn wall
      Vector2 pos = body.getPosition().cpy();
      Vector2 back = new Vector2((float)Math.cos(angle),(float)Math.sin(angle)).scl(-1).scl(1);
      pos.add(back);
      wallFactory.createEntity(pos, (int) finalSize[0]/3);

    }


  }
}