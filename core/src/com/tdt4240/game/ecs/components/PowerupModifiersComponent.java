package com.tdt4240.game.ecs.components;

import java.util.ArrayList;

import com.artemis.Component;
import com.tdt4240.game.ecs.powerups.Powerup;

public class PowerupModifiersComponent extends Component{
  public ArrayList<Powerup> powerups = new ArrayList<>();
  
}