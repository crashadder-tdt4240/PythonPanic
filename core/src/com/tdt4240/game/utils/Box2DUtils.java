package com.tdt4240.game.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DUtils{


  public class Category{
    public static final short PLAYER = 1;
    public static final short WALL = 2;
    public static final short ITEM = 4;
  }

  public static final BodyDef KINEMATIC_BODY_DEF = new BodyDef() {{
    type = BodyType.KinematicBody;
    fixedRotation = true;
    active = true;
  }};

  public static final BodyDef STATIC_BODY_DEF = new BodyDef() {{
    type = BodyType.StaticBody;
    fixedRotation = true;
    active = true;
  }};

  public static final BodyDef DYNAMIC_BODY_DEF = new BodyDef() {{
    type = BodyType.DynamicBody;
    fixedRotation = false;
    active = true;
  }};

  public static final FixtureDef PLAYER_FIXTURE_DEF = new FixtureDef(){{
    shape = new CircleShape();
    shape.setRadius(7);
    density = 1;
    filter.categoryBits = Category.PLAYER;
  }};

  public static Body createBody(World world, BodyDef bodyDef, FixtureDef ...fixtureDefs){
    Body body = world.createBody(bodyDef);
    for(FixtureDef fixtureDef : fixtureDefs){
      body.createFixture(fixtureDef);
    }
    return body;
  }

}