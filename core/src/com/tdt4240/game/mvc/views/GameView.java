package com.tdt4240.game.mvc.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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