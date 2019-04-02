package com.tdt4240.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

// set gl settings
public class GLSettings{
  public static void create(){
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    Gdx.gl.glDepthMask(true);
    Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
  }

  public static void preRender(){
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }

  public static void postRender(){
  
  }
}