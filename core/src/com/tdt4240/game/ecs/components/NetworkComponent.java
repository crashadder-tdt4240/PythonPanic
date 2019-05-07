package com.tdt4240.game.ecs.components;

import com.artemis.Component;

public class NetworkComponent extends Component{
  public int remoteId;
  public int localId;
  public boolean remote = false;
  
  // only sync creation is syncFreq = 0
  public int syncFreq = 0;

  // last tick this entity was synced
  public int lastSync = -1;


}