package com.tdt4240.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DUtils{

  public static World world;

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

  public static final BodyDef WALL_BODY_DEF = new BodyDef() {{
    type = BodyType.DynamicBody;
    fixedRotation = false;
    active = true;
  }};

  public static final FixtureDef PLAYER_FIXTURE_DEF = new FixtureDef(){{
    shape = new CircleShape();
    shape.setRadius(4);
    density = 1;
    filter.categoryBits = Category.PLAYER;
    filter.maskBits = 0b110;
  }};

  public static final FixtureDef WALL_FIXTURE_DEF = new FixtureDef(){{
    shape = new CircleShape();
    shape.setRadius(5);
    density = 1;
    filter.categoryBits = Category.WALL;
    filter.maskBits = Category.PLAYER;
  }};

  public static final FixtureDef ITEM_FIXTURE_DEF = new FixtureDef(){{
    shape = new CircleShape();
    shape.setRadius(10);
    density = 1;
    filter.categoryBits = Category.ITEM;
    filter.maskBits = Category.PLAYER;
  }};

  public static final FixtureDef CreatChainWallFix(Vector2 min, Vector2 max){
    return new FixtureDef(){{
      ChainShape chainShape = new ChainShape();
      Vector2[] vertices = new Vector2[5];
      vertices[0] = new Vector2(min.x, min.y);
      vertices[1] = new Vector2(max.x, min.y);
      vertices[2] = new Vector2(max.x, max.y);
      vertices[3] = new Vector2(min.x, max.y);
      vertices[4] = new Vector2(min.x, min.y);
      chainShape.createChain(vertices);
      shape = chainShape;
      filter.categoryBits = Category.WALL;
      density = 1;
    }};
  }

  

  public static Body createBody(World world, BodyDef bodyDef, FixtureDef ...fixtureDefs){
    Body body = world.createBody(bodyDef);
    for(FixtureDef fixtureDef : fixtureDefs){
      body.createFixture(fixtureDef);
    }
    return body;
  }

}