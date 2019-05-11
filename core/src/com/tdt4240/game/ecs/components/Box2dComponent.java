package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

@DelayedComponentRemoval
public class Box2dComponent extends Component{
  public Body body;
  // true if collided with entity
  public boolean contact = false;
  // What entity we are in contact with
  public int contactWith = -1;

  // enable to interpolate
  public boolean interpolate = false;
  public float intAngle = 0;
  public float intOmega = 0;
  public Vector2 intVector;
  public int ticksToInterpolate = 0;

}