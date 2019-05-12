package com.tdt4240.game.ecs.powerups;

public class SizePowerup extends Powerup{
  public float modifier = 3f;
  public SizePowerup(){
    this(false);
  }
  public SizePowerup(boolean powerdown){
    life = 5;
    if(powerdown){
      modifier = 1/modifier;
    }
  }
}