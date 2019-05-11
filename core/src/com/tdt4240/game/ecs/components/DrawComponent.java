package com.tdt4240.game.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;


// Component that draws to a sprite
public class DrawComponent extends Component{
  public int radius = 4;
  // What entity to draw to, this entity need to have a pixmap component
  public int drawTo = -1;
  public Color color = Color.WHITE;
}