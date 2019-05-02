package com.tdt4240.game.ecs.managers;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Manager;
import com.artemis.systems.IteratingSystem;
import com.tdt4240.game.ecs.components.NetworkComponent;

public class NetworkManager extends IteratingSystem{
  
  
  private ComponentMapper<NetworkComponent> componentMapper;

  public NetworkManager(){
    super(Aspect.one(NetworkComponent.class));
  }



  @Override
  protected void process(int entityId) {
    NetworkComponent component = componentMapper.get(entityId);
    if(!component.remote){
      if(component.lastSync != -1){
        component.lastSync++;
      }
    }
  }

  @Override
  protected void inserted(int entityId) {

  }

  @Override
  protected void removed(int entityId) {
  
  }


}