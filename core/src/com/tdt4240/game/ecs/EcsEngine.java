package com.tdt4240.game.ecs;

import com.artemis.ComponentType;
import com.artemis.ComponentTypeFactory;
import com.artemis.InvocationStrategy;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.Aspect.Builder;
import com.artemis.EntitySubscription.SubscriptionListener;

class EcsEngine{
  private World world;
  private int tps = 128;
  private int tickCounter = 0;
  private float acc = 0;
  public EcsEngine(){
    WorldConfiguration config = new WorldConfiguration();
    config.expectedEntityCount(2048);
    world = new World(config);
  }

  public void getEntities(Builder filter, SubscriptionListener listener){
    world.getAspectSubscriptionManager().get(filter).addSubscriptionListener(listener);
    
  }



  public void setTPS(int tps){
    this.tps = tps;
    this.world.setDelta(1/tps);
  }

  public void update(float dtime){
    acc += dtime;
    int ticks = (int) (tps*acc);
    for(int i=0; i < ticks; i++){
      world.process();
    }
    acc -= world.getDelta()*ticks;
    tickCounter += ticks;
  }

}