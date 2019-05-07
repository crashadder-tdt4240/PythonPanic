package com.tdt4240.game.ecs.factory;


import com.artemis.World;

public abstract class EntityFactory{
  private World world;
  public EntityFactory(World world){
    this.world = world;
  }

  public World getWorld(){
    return world;
  }

  public abstract int createEntity();
}