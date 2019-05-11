package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.tdt4240.game.ecs.powerups.Powerup;

public class PowerupComponent extends Component{
  // powerup that will be given to the entity that picks it up
  public Powerup powerup;
}