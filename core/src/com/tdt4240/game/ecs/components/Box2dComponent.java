package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;
import com.badlogic.gdx.physics.box2d.Body;

@DelayedComponentRemoval
public class Box2dComponent extends Component{
  public Body body;
  // true if collided with entity
  public boolean contact = false;
  // What entity we are in contact with
  public int contactWith = -1;
}