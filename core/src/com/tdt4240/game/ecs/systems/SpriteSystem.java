package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController.Transform;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;

public class SpriteSystem extends IteratingSystem{
  
  ComponentMapper<TransformComponent> transformMap;
  ComponentMapper<SpriteComponent> spriteMap;

  public SpriteSystem(){
    super(Aspect.all(TransformComponent.class, SpriteComponent.class));
  }
  
  @Override
  protected void process(int entityId) {
    TransformComponent transformComponent = transformMap.get(entityId);
    SpriteComponent spriteComponent = spriteMap.get(entityId);

    Vector3 pos = transformComponent.transform.getTranslation(new Vector3());
    Quaternion rotation = transformComponent.transform.getRotation(new Quaternion());
    
    spriteComponent.sprite.setPosition(pos);
    spriteComponent.sprite.setRotation(rotation);

  }
}