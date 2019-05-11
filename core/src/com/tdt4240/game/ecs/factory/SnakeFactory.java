package com.tdt4240.game.ecs.factory;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.utils.Box2DUtils;

public class SnakeFactory extends EntityFactory{
  
  private Archetype snakeType;


  private ComponentMapper<SpriteComponent> sComponentMapper;
  private ComponentMapper<Box2dComponent> bComponentMapper;
  private ComponentMapper<DrawComponent> dComponentMapper;
  private ComponentMapper<TransformComponent> tComponentMapper;

  public SnakeFactory(World world){
    super(world);
    snakeType = new ArchetypeBuilder()
      .add(TransformComponent.class)
      .add(Box2dComponent.class)
      .add(SpriteComponent.class)
      .add(DrawComponent.class)
      .add(SnakeComponent.class)
      .add(PowerupModifiersComponent.class)
      .build(world);

    sComponentMapper = world.getMapper(SpriteComponent.class);
    bComponentMapper = world.getMapper(Box2dComponent.class);
    dComponentMapper = world.getMapper(DrawComponent.class);
    tComponentMapper = world.getMapper(TransformComponent.class);
  }
  
  public int createEntity(){
    int entity = getWorld().create(snakeType);
    return entity;
  }


  public int createEntity(com.badlogic.gdx.physics.box2d.World bWorld, Vector3 pos, TextureRegion texture, Color color){
    int entity = createEntity();
    
    Body body = Box2DUtils.createBody(bWorld, Box2DUtils.DYNAMIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    tComponentMapper.get(entity).transform.setTranslation(pos);
    sComponentMapper.get(entity).sprite = Decal.newDecal(32,32, texture, true);
    dComponentMapper.get(entity).color = color;
    bComponentMapper.get(entity).body = body;
    body.setTransform(new Vector2(pos.x, pos.y), 0);

    return entity;
  }

}