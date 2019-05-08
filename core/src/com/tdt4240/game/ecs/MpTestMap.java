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
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.NetworkComponent;
import com.tdt4240.game.ecs.components.PixmapComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.PowerupModifiersComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.factory.SnakeFactory;
import com.tdt4240.game.ecs.powerups.Powerup;
import com.tdt4240.game.ecs.powerups.SpeedPowerup;

public class MpTestMap extends GameLevel{

  private SnakeFactory factory;
  private Random random = new Random();
  private TextureRegion snakeRegion;
  private int drawSurface = -1;

  public MpTestMap(World world, com.badlogic.gdx.physics.box2d.World box2d){
    super(world, box2d);
    factory = new SnakeFactory(world);
    Assets assets = Assets.getInstance();
    Texture tex = assets.getAsset("texture.test.png");
    snakeRegion = new TextureRegion(tex);
  }

  @Override
  public void setup() {
    
    Pixmap surface = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGB888);
    Texture surfaceTexture = new Texture(surface);

    Decal surfaceSprite = Decal.newDecal(
      Gdx.graphics.getWidth(), 
      Gdx.graphics.getHeight(), 
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


  public void createSnake(){
    float x = (random.nextFloat() - 0.5f) * 40;
    float y = (random.nextFloat() - 0.5f) * 40;
    int entity = factory.createEntity(getBox2dWorld(), new Vector3(x, y, -50), snakeRegion, Color.GREEN);

    ComponentMapper<DrawComponent> drawMapper = getWorld().getMapper(DrawComponent.class);
    ComponentMapper<NetworkComponent> netMapper = getWorld().getMapper(NetworkComponent.class);
    ComponentMapper<PlayerInputComponent> inputMapper = getWorld().getMapper(PlayerInputComponent.class);
    ComponentMapper<PowerupModifiersComponent> powerupModMapper = getWorld().getMapper(PowerupModifiersComponent.class);
    
    inputMapper.create(entity);
    
    NetworkComponent netComponent = netMapper.create(entity);
    netComponent.localId = entity;
    netComponent.remote = false;
    netComponent.syncFreq = 6;//64/12;

    Powerup test = new SpeedPowerup();
    test.life = 20;

    powerupModMapper.get(entity).powerups.add(test);


    drawMapper.get(entity).drawTo = drawSurface;
  }

  public void createSnake(int remoteId, Vector3 pos){
    int entity = factory.createEntity(getBox2dWorld(), pos, snakeRegion, Color.RED);
    ComponentMapper<DrawComponent> drawMapper = getWorld().getMapper(DrawComponent.class);
    ComponentMapper<NetworkComponent> netMapper = getWorld().getMapper(NetworkComponent.class);
    
    NetworkComponent netComponent = netMapper.create(entity);
    netComponent.localId = entity;
    netComponent.remoteId = remoteId;
    netComponent.remote = true;
    
    
    drawMapper.get(entity).drawTo = drawSurface;

  }


}