package com.tdt4240.game.mvc.views;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.GroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.SimpleOrthoGroupStrategy;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.mvc.models.GameModel;

public class GameView extends MVCView<GameModel>{
  
  private DecalBatch decalBatch;
  private CameraGroupStrategy cameraGroupStrategy;
  private Camera gameCamera;

  
  private Box2DDebugRenderer debugRenderer;
  public GameView(GameModel model){
    super(model);
    
    gameCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    gameCamera.far = 10000f;
    gameCamera.position.set(0, 0, 0);

    //Viewport viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera);
    cameraGroupStrategy = new CameraGroupStrategy(gameCamera);
    decalBatch = new DecalBatch(2048, cameraGroupStrategy);
    debugRenderer = new Box2DDebugRenderer(
      true,
      true,
      true,
      true,
      true,
      true
    );

  }

  public void render(float dtime){
    gameCamera.update();
    for(Decal sprite : getModel().getDecals()){
      decalBatch.add(sprite);
    }
    decalBatch.flush();
    debugRenderer.render(getModel().getEngine().getBox2dWorld(), gameCamera.combined);
  }

 





}