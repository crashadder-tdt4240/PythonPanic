package com.tdt4240.game.ecs;

import java.util.Random;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.KillBoxComponent;
import com.tdt4240.game.ecs.components.PixmapComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.factory.PowerupFactory;
import com.tdt4240.game.ecs.factory.SnakeFactory;
import com.tdt4240.game.utils.Box2DUtils;

public class TestMap extends GameLevel{
  private Vector2 worldSize;
  public TestMap(World w, com.badlogic.gdx.physics.box2d.World box2d, Vector2 worldSize){
    super(w, box2d);
    this.worldSize = worldSize;
  }
  

  public void setup(){
    World world = getWorld();
    com.badlogic.gdx.physics.box2d.World physicsWorld = getBox2dWorld();
    // Create surface for drawing to

    SnakeFactory snakeFactory = new SnakeFactory(world);

    Pixmap surface = new Pixmap((int)worldSize.x, (int)worldSize.y, Format.RGB888);
    Texture surfaceTexture = new Texture(surface);


    Texture testTexture = Assets.getInstance().getAsset("texture.python-head-blue.png");//new Texture(Gdx.files.internal("textures/test.png"));
    //surfaceTexture.bind(1);
    //Gdx.gl.glActiveTexture(0);

    // Create sprite for the snakes
    Decal surfaceSprite = Decal.newDecal(
      surface.getWidth()*2,
      surface.getHeight()*2, 
      new TextureRegion(surfaceTexture)
    );

    TextureRegion snakeRegion = new TextureRegion(testTexture);
    

    int snake1 = snakeFactory.createEntity(physicsWorld, Vector3.Zero.cpy(), 0, snakeRegion, Color.GREEN);
    int snake2 = snakeFactory.createEntity(physicsWorld, Vector3.Zero.cpy(), 0, snakeRegion, Color.YELLOW);

    Body body3 = Box2DUtils.createBody(physicsWorld, Box2DUtils.STATIC_BODY_DEF, Box2DUtils.WALL_FIXTURE_DEF);
    Body body1 = Box2DUtils.createBody(physicsWorld, Box2DUtils.DYNAMIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    Body body2 = Box2DUtils.createBody(physicsWorld, Box2DUtils.DYNAMIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    Body wall = Box2DUtils.createBody(physicsWorld, Box2DUtils.STATIC_BODY_DEF, Box2DUtils.CreatChainWallFix(worldSize.cpy().scl(-0.25f) , worldSize.cpy().scl(0.25f)));
    int entity1 = world.create();
    int entity2 = world.create();
    int wallEntity = world.create();
    int surfaceEntity = world.create();
    int killEntity = world.create();

    ComponentMapper<Box2dComponent> box2dMapper = world.getMapper(Box2dComponent.class);
    ComponentMapper<TransformComponent> transformMapper = world.getMapper(TransformComponent.class);
    ComponentMapper<PlayerInputComponent> inputMapper = world.getMapper(PlayerInputComponent.class);
    ComponentMapper<SpriteComponent> spriteMapper = world.getMapper(SpriteComponent.class);
    ComponentMapper<SnakeComponent> snakeMapper = world.getMapper(SnakeComponent.class);
    ComponentMapper<DrawComponent> drawMapper = world.getMapper(DrawComponent.class);
    ComponentMapper<PixmapComponent> pixmapMapper = world.getMapper(PixmapComponent.class);
    ComponentMapper<KillBoxComponent> killBoxMapper = world.getMapper(KillBoxComponent.class);

    TransformComponent transformComponent = transformMapper.get(snake1);

    TransformComponent transformComponent2 = transformMapper.get(snake2);

    Box2dComponent box2dComponent3 = box2dMapper.create(killEntity);
    TransformComponent transformComponent4 = transformMapper.create(killEntity);
    KillBoxComponent killBoxComponent = killBoxMapper.create(killEntity);
    box2dComponent3.body = body3;
    box2dComponent3.body.setTransform(50, -50, 0);

    Box2dComponent box2dComponent4 = box2dMapper.create(wallEntity);
    box2dComponent3.body = wall;

    TransformComponent transformComponent3 = transformMapper.create(surfaceEntity);

    TransformComponent transformComponent5 = transformMapper.create(surfaceEntity);
    SpriteComponent spriteComponent3 = spriteMapper.create(surfaceEntity);

    DrawComponent drawComponent = drawMapper.get(snake1);
    DrawComponent drawComponent2 = drawMapper.get(snake2);

    drawComponent.drawTo = surfaceEntity;
    drawComponent2.drawTo = surfaceEntity;

    PixmapComponent pixmapComponent = pixmapMapper.create(surfaceEntity);

    pixmapComponent.pixmap = surface;
    


    transformComponent.transform.translate(0, 0, -50);
    transformComponent2.transform.translate(0, 0, -50);
    transformComponent3.transform.translate(0, 0, -100);

   // transformComponent3.transform.rotate(0, 1, 0, 60f);

    spriteComponent3.sprite = surfaceSprite;

    box2dMapper.get(snake2).body.setTransform(new Vector2(-32, 32), 0);
    
    inputMapper.create(snake1);


    PowerupFactory powerupFactory = new PowerupFactory(world);
    Random random = new Random();
    powerupFactory.spawnRandomPowerup(new Vector2(-100, -100), random);
    powerupFactory.spawnRandomPowerup(new Vector2(-200, -200), random);
    powerupFactory.spawnRandomPowerup(new Vector2(-300, -300), random);
    powerupFactory.spawnRandomPowerup(new Vector2(100, -100), random);
    powerupFactory.spawnRandomPowerup(new Vector2(200, -200), random);
    powerupFactory.spawnRandomPowerup(new Vector2(300, -300), random);
    powerupFactory.spawnRandomPowerup(new Vector2(-100, 100), random);
    powerupFactory.spawnRandomPowerup(new Vector2(-200, 200), random);
    powerupFactory.spawnRandomPowerup(new Vector2(-300, 300), random);
    powerupFactory.spawnRandomPowerup(new Vector2(100, 100), random);
    powerupFactory.spawnRandomPowerup(new Vector2(200, 200), random);
    powerupFactory.spawnRandomPowerup(new Vector2(300, 300), random);

    //surfaceTexture.draw(surface, 0, 0);
    
  }
}