package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;

public class PhysicsSystem extends IteratingSystem{
  
  private ComponentMapper<TransformComponent> transformMapper;
  private ComponentMapper<Box2dComponent> box2dMapper;


  public PhysicsSystem(){
    super(Aspect.all(TransformComponent.class, Box2dComponent.class));
  }

  public void process(int entity){


    TransformComponent transformComponent = transformMapper.get(entity);
    Box2dComponent box2dComponent = box2dMapper.get(entity);
    Vector2 position = box2dComponent.body.getPosition();
    Vector3 transformPosition = transformComponent.transform.getTranslation(new Vector3());
    
    // modify interpolation vector
    if(box2dComponent.interpolate) {
    }
    
    final float alpha = 0.2f;
    final float invAlpha = 1.0f - alpha;
    
    if(box2dComponent.interpolate && box2dComponent.ticksToInterpolate > 0){
      Vector2 diff = position.cpy().sub(transformPosition.x/2f, transformPosition.y/2f);
      box2dComponent.intVector.add(diff.scl(0.5f));
        
      Vector2 interpolatedVector = position.cpy().lerp(box2dComponent.intVector, alpha);
      float interpolatedAng = ( box2dComponent.body.getAngle() * invAlpha) + (box2dComponent.intAngle * alpha); 
      float interpolateOmega = (box2dComponent.body.getAngularVelocity() * invAlpha) + (box2dComponent.intOmega *alpha);
      
      box2dComponent.body.setTransform(interpolatedVector, interpolatedAng);
      box2dComponent.body.setAngularVelocity(interpolateOmega);
      box2dComponent.ticksToInterpolate -= 1;

    } else if(box2dComponent.interpolate) {
      System.out.println("Interpolate done");
      box2dComponent.body.setTransform(box2dComponent.intVector, box2dComponent.intAngle);
      box2dComponent.body.setAngularVelocity(box2dComponent.intOmega);
      box2dComponent.ticksToInterpolate = 0;
      box2dComponent.interpolate = false;
    }

    //todo: handle this better 
    float rotation = box2dComponent.body.getAngle();
    transformPosition.x = position.x * 2;
    transformPosition.y = position.y * 2;
    transformComponent.transform.set(transformPosition, new Quaternion(new Vector3(0,0,1), -90 + MathUtils.radDeg * rotation));
  }
}