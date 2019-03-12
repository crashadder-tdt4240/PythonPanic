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
    float rotation = box2dComponent.body.getAngle();
    transformPosition.x = position.x;
    transformPosition.y = position.y;
    transformComponent.transform.set(transformPosition, new Quaternion(new Vector3(0,0,1), rotation));
  }
}