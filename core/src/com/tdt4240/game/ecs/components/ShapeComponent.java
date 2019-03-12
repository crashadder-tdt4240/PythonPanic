package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;

public class ShapeComponent extends Component{
  public Shape2D shape2d = new Circle();
}