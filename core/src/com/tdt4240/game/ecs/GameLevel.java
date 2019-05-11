package com.tdt4240.game.ecs;
import com.artemis.World;


public abstract class GameLevel{

  private World world;
  private com.badlogic.gdx.physics.box2d.World physicsWorld;

  public GameLevel(World world, com.badlogic.gdx.physics.box2d.World physicsWorld){
    this.world = world;
    this.physicsWorld = physicsWorld;
  }

  public World getWorld(){
    return world;
  }

  public com.badlogic.gdx.physics.box2d.World getBox2dWorld(){
    return physicsWorld;
  }

  public abstract void setup();
}