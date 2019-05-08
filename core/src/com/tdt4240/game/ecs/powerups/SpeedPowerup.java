package com.tdt4240.game.ecs.powerups;

public class SpeedPowerup extends Powerup{
  public float modifier = 3;


  public SpeedPowerup(){
    this(false);
  }

  public SpeedPowerup(boolean powerdown){
    life = 5;
    if(powerdown){
      modifier = 1/modifier;
    }
  }
}