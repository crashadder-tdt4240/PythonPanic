package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

// stores data about the player inputs
public class PlayerInputComponent extends Component{
  public boolean activatePowerup = false;
  public Vector2 steerInput = new Vector2(0, 0);
}