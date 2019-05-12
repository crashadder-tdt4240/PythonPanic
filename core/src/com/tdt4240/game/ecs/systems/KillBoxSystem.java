package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Filter;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.KillBoxComponent;
import com.tdt4240.game.ecs.components.NetworkComponent;
import com.tdt4240.game.utils.Box2DUtils;

public class KillBoxSystem extends IteratingSystem{
  
  private ComponentMapper<Box2dComponent> bComponentMapper;
  private ComponentMapper<NetworkComponent> netMapper;
  private ComponentMapper<KillBoxComponent> killMapper;
  

  public KillBoxSystem(){
    super(Aspect.all(KillBoxComponent.class, Box2dComponent.class));
  }
  
  public void process(int entity){
    // needs better checking, and has to be mp safe
    Box2dComponent bComponent = bComponentMapper.get(entity);
    KillBoxComponent killBox = killMapper.get(entity);
    if(killBox.grace <= 0){
      
      if(bComponent.contact && bComponent.contactWith >= 0){
        int other = bComponent.contactWith;
        if(netMapper.has(other)){
          NetworkComponent netComponent = netMapper.get(other);
          if(!netComponent.remote){
            //todo notify others that entity has died
            getWorld().delete(other);
          }
        } else {
          getWorld().delete(other);
        }
        //bComponent.contact = false;
        //bComponent.contactWith = -1;
      }
    } else {
      killBox.grace -= 1;
      if(killBox.grace == 0){
        Filter filter = bComponent.body.getFixtureList().get(0).getFilterData();
        filter.maskBits = Box2DUtils.Category.PLAYER;
        bComponent.body.getFixtureList().get(0).setFilterData(filter); 
      }
    }
  }
}