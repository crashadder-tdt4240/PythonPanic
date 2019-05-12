package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;


@DelayedComponentRemoval
public class SnakeComponent extends Component{
  public float holeCooldown = 0;
  public float tickHole = 2;

  public float holeLength = 75;
  public float wallLength = 400;

  public float pixacc = 0;

  public float pixacc2 = 0;
  public float wallboxLen = 16;

}
