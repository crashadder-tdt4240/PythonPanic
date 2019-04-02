package com.tdt4240.game.views;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.ecs.components.SpriteComponent;

public class GameScreen extends ScreenAdapter{
  
  private EcsEngine engine;
  private ComponentMapper<SpriteComponent> spriteMapper;
  private DecalBatch decalBatch;
  private CameraGroupStrategy cameraGroupStrategy;
  private Camera gameCamera;
  private Viewport viewport;
  public GameScreen(EcsEngine engine){
    this.engine = engine;
    spriteMapper = engine.getMapper(SpriteComponent.class);
    gameCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    gameCamera.far = 10000f;
    gameCamera.position.set(0, 0, 0);

    viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera);
    cameraGroupStrategy = new CameraGroupStrategy(gameCamera);
    decalBatch = new DecalBatch(2048, cameraGroupStrategy);
  }


  public void render(float dtime){
    gameCamera.update();
    engine.update(dtime);
    IntBag entities = engine.getEntities(Aspect.one(SpriteComponent.class));
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      SpriteComponent spriteComponent = spriteMapper.get(entity);
      decalBatch.add(spriteComponent.sprite);
    }
    decalBatch.flush();
  }
}