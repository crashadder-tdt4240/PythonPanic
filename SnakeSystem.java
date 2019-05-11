package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.DrawComponent;

public class SnakeSystem extends IteratingSystem{

  private ComponentMapper<Box2dComponent> box2dMapper;
  private ComponentMapper<SnakeComponent> snakeMapper;
  private ComponentMapper<DrawComponent> drawMapper;

  public SnakeSystem(){
    super(Aspect.all(Box2dComponent.class, SnakeComponent.class));
  
  }


  public void process(int entity){
	SnakeComponent snake = snakeMapper.get(entity);
	if(drawMapper.has(entity) ) {
		DrawComponent draw = drawMapper.get(entity);
		if(draw.draw){
			snake.holeCooldown -= getWorld().getDelta();			
			if(snake.holeCooldown <= 0){
				snake.tickHole = 2;
				snake.holeCooldown = 5;
				draw.draw = false;
			}
		} else {
			snake.tickHole -= getWorld().getDelta();
			if(snake.tickHole <= 0){
				draw.draw = true;
			}
		}
		
	}
    Box2dComponent box2dComponent = box2dMapper.get(entity);

    Body body = box2dComponent.body;
    float angle = body.getAngle();
    Vector2 newVeloc = new Vector2((float)Math.cos(angle), (float) Math.sin(angle));
    newVeloc.scl(50f);

    body.setLinearVelocity(newVeloc);

  }
}