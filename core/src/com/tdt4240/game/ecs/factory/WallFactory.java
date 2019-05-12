package com.tdt4240.game.ecs.factory;

import java.util.HashMap;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.KillBoxComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.utils.Box2DUtils;

public class WallFactory extends EntityFactory{
  

  private ComponentMapper<Box2dComponent> box2dMapper;
  private HashMap<Integer, FixtureDef> fixtures = new HashMap<>();

  private Archetype wallArchetype;

  public WallFactory(World world){
    super(world);
    wallArchetype = new ArchetypeBuilder()
      .add(TransformComponent.class)
      .add(Box2dComponent.class)
      .add(KillBoxComponent.class)
      .build(world);


    box2dMapper = world.getMapper(Box2dComponent.class);

  }
  
  @Override
  public int createEntity() {
    int entity = getWorld().create(wallArchetype);
    Body body = Box2DUtils.createBody(Box2DUtils.world, Box2DUtils.STATIC_BODY_DEF);
  

    Box2dComponent box2d = box2dMapper.get(entity);

    box2d.body = body;

    return entity;
  }

  public int createEntity(Vector2 position, int radius){
    if(!fixtures.containsKey(radius)){
      FixtureDef fixturedef = new FixtureDef(){{
        shape = new CircleShape();
        shape.setRadius(radius);
        density = 1;
        filter.categoryBits = Box2DUtils.Category.WALL;
        filter.maskBits = 0;
      }};
      fixtures.put(radius, fixturedef);
    }
    
    int entity = createEntity();

    Box2dComponent box2d = box2dMapper.get(entity);
    box2d.body.createFixture(fixtures.get(radius));

    box2d.body.setTransform(position, 0);

    return entity;
  }

}