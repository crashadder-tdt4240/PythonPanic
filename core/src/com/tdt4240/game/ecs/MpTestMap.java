package com.tdt4240.game.ecs;

import java.util.Random;

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
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.NetworkComponent;
import com.tdt4240.game.ecs.components.PixmapComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.factory.PowerupFactory;
import com.tdt4240.game.ecs.factory.SnakeFactory;
import com.tdt4240.game.ecs.powerups.Powerup;
import com.tdt4240.game.ecs.powerups.SpeedPowerup;

public class MpTestMap extends GameLevel{

  private SnakeFactory factory;
  private Random random = new Random();
  private TextureRegion snakeRegion;
  private TextureRegion snakeRegion2;
  private int drawSurface = -1;
  private int playerIndex = 0;
  private Vector2 worldSize;

  public MpTestMap(World world, com.badlogic.gdx.physics.box2d.World box2d, Vector2 worldSize){
    super(world, box2d);
    factory = new SnakeFactory(world);
    Assets assets = Assets.getInstance();
    Texture tex = assets.getAsset("texture.python-head-green.png");;
    Texture tex2 = assets.getAsset("texture.python-head-red.png");;
    snakeRegion = new TextureRegion(tex);
    snakeRegion2 = new TextureRegion(tex2);
    this.worldSize = worldSize;
  }

  public void setRandom(Random random ) {
    this.random = random;
  }

  public void setPlayerIndex(int index) {
    this.playerIndex = index;
  }

  @Override
  public void setup() {
    
    Pixmap surface = new Pixmap((int)worldSize.x, (int)worldSize.y, Format.RGB888);
    Texture surfaceTexture = new Texture(surface);

    Decal surfaceSprite = Decal.newDecal(
      surface.getWidth()*2, 
      surface.getHeight()*2,
      new TextureRegion(surfaceTexture)
    );


    int surfaceEntity = getWorld().create();

    ComponentMapper<TransformComponent> transformMapper = getWorld().getMapper(TransformComponent.class);
    ComponentMapper<SpriteComponent> spriteMapper = getWorld().getMapper(SpriteComponent.class);
    ComponentMapper<PixmapComponent> pixmapMapper = getWorld().getMapper(PixmapComponent.class);

    TransformComponent transformComponent3 = transformMapper.create(surfaceEntity);
    SpriteComponent spriteComponent3 = spriteMapper.create(surfaceEntity);

    PixmapComponent pixmapComponent = pixmapMapper.create(surfaceEntity);
    pixmapComponent.pixmap = surface;
    transformComponent3.transform.translate(0, 0, -100);
   // transformComponent3.transform.rotate(0, 1, 0, 60f);

    spriteComponent3.sprite = surfaceSprite;

    drawSurface = surfaceEntity;
    
  }

  private final Vector2[] spawnLocations = {new Vector2(-500, -500), new Vector2(500, 500), new Vector2(-500, 500), new Vector2(500, -500)};
  private final float[] angles = {90, -90, 0, 180};
  public void createSnake(){
    Vector2 spawn = spawnLocations[playerIndex].cpy().scl(1);
    
    int entity = factory.createEntity(getBox2dWorld(), new Vector3(spawn.x, spawn.y, -50), angles[playerIndex], snakeRegion, Color.GREEN);

    ComponentMapper<DrawComponent> drawMapper = getWorld().getMapper(DrawComponent.class);
    ComponentMapper<NetworkComponent> netMapper = getWorld().getMapper(NetworkComponent.class);
    ComponentMapper<PlayerInputComponent> inputMapper = getWorld().getMapper(PlayerInputComponent.class);
    
    inputMapper.create(entity);
    
    NetworkComponent netComponent = netMapper.create(entity);
    netComponent.localId = entity;
    netComponent.remote = false;
    netComponent.syncFreq = 6;//64/12;

   
    drawMapper.get(entity).drawTo = drawSurface;
  }
  
  // should be done in factory or in netmanager
  public void createSnake(int remoteId, Vector3 pos, float angle){
    int entity = factory.createEntity(getBox2dWorld(), pos, angle, snakeRegion2, Color.RED);
    ComponentMapper<DrawComponent> drawMapper = getWorld().getMapper(DrawComponent.class);
    ComponentMapper<NetworkComponent> netMapper = getWorld().getMapper(NetworkComponent.class);
    
    NetworkComponent netComponent = netMapper.create(entity);
    netComponent.localId = entity;
    netComponent.remoteId = remoteId;
    netComponent.remote = true;
    
    
    drawMapper.get(entity).drawTo = drawSurface;

  }


}