package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Matrix4;

public class TransformComponent extends Component{
  public Matrix4 transform = new Matrix4().idt();
  public Matrix4 prevTransform = null;
  public float deltaLength = 0;
}