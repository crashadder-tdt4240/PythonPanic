package com.tdt4240.game.ecs;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.PixmapComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.utils.Box2DUtils;

public class TestMap{

  private World world;
  private com.badlogic.gdx.physics.box2d.World physicsWorld;
  
  public TestMap(World world, com.badlogic.gdx.physics.box2d.World physicsWorld){
    this.world = world;
    this.physicsWorld = physicsWorld;
  }

  public void setup(){
    // Create surface for drawing to
    Pixmap surface = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGB888);
    Texture surfaceTexture = new Texture(surface);

    Texture testTexture = new Texture(Gdx.files.internal("test.png"));
    //surfaceTexture.bind(1);
    //Gdx.gl.glActiveTexture(0);

    // Create sprite for the snakes
    Decal surfaceSprite = Decal.newDecal(
      Gdx.graphics.getWidth(), 
      Gdx.graphics.getHeight(), 
      new TextureRegion(surfaceTexture)
    );


    Decal sprite = Decal.newDecal(16, 16, new TextureRegion(testTexture), true);  
    Decal sprite2 = Decal.newDecal(16, 16, new TextureRegion(testTexture), true);

    Body body1 = Box2DUtils.createBody(physicsWorld, Box2DUtils.KINEMATIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    Body body2 = Box2DUtils.createBody(physicsWorld, Box2DUtils.DYNAMIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    int entity1 = world.create();
    int entity2 = world.create();
    int surfaceEntity = world.create();

    ComponentMapper<Box2dComponent> box2dMapper = world.getMapper(Box2dComponent.class);
    ComponentMapper<TransformComponent> transformMapper = world.getMapper(TransformComponent.class);
    ComponentMapper<PlayerInputComponent> inputMapper = world.getMapper(PlayerInputComponent.class);
    ComponentMapper<SpriteComponent> spriteMapper = world.getMapper(SpriteComponent.class);
    ComponentMapper<SnakeComponent> snakeMapper = world.getMapper(SnakeComponent.class);
    ComponentMapper<DrawComponent> drawMapper = world.getMapper(DrawComponent.class);
    ComponentMapper<PixmapComponent> pixmapMapper = world.getMapper(PixmapComponent.class);

    Box2dComponent box2dComponent = box2dMapper.create(entity1);
    TransformComponent transformComponent = transformMapper.create(entity1);
    box2dComponent.body = body1;

    Box2dComponent box2dComponent2 = box2dMapper.create(entity2);
    TransformComponent transformComponent2 = transformMapper.create(entity2);

    TransformComponent transformComponent3 = transformMapper.create(surfaceEntity);

    SpriteComponent spriteComponent1 = spriteMapper.create(entity1);
    SpriteComponent spriteComponent2 = spriteMapper.create(entity2);
    SpriteComponent spriteComponent3 = spriteMapper.create(surfaceEntity);

    SnakeComponent snakeComponent = snakeMapper.create(entity1);
    DrawComponent drawComponent = drawMapper.create(entity1);
    DrawComponent drawComponent2 = drawMapper.create(entity2);

    drawComponent.drawTo = surfaceEntity;
    drawComponent2.drawTo = surfaceEntity;

    PixmapComponent pixmapComponent = pixmapMapper.create(surfaceEntity);

    pixmapComponent.pixmap = surface;
    
    drawComponent.color = Color.GREEN;
    drawComponent2.color = Color.RED;


    transformComponent.transform.translate(0, 0, -50);
    transformComponent2.transform.translate(0, 0, -50);
    transformComponent3.transform.translate(0, 0, -100);
   // transformComponent3.transform.rotate(0, 1, 0, 60f);

    spriteComponent1.sprite = sprite;
    spriteComponent2.sprite = sprite2;
    spriteComponent3.sprite = surfaceSprite;

    box2dComponent2.body = body2;
    body2.setTransform(new Vector2(8, 32), 0);
    body2.setLinearVelocity(0, -32);

    inputMapper.create(entity1);

    //surfaceTexture.draw(surface, 0, 0);
    
  }
}