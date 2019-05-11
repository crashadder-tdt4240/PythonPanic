package com.tdt4240.game.ecs.factory;

import java.util.Random;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.assets.Assets;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.PowerupComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.powerups.Powerup;
import com.tdt4240.game.ecs.powerups.SizePowerup;
import com.tdt4240.game.ecs.powerups.SpeedPowerup;
import com.tdt4240.game.utils.Box2DUtils;

public class PowerupFactory extends EntityFactory{
  private Archetype powerupArchetype;

  // powerup textures
  private Assets assets = Assets.getInstance();
  private Texture texPowerupFast, texPowerupSlow, texPowerupThick, texPowerupThin;


  private ComponentMapper<Box2dComponent> box2dMapper;
  private ComponentMapper<TransformComponent> transformMapper;
  private ComponentMapper<PowerupComponent> powerupMapper;
  private ComponentMapper<SpriteComponent> spriteMapper;

  public PowerupFactory(World world){
    super(world);
    

    texPowerupFast = assets.getAsset("texture.power-up-fast.png");
    texPowerupSlow = assets.getAsset("texture.power-up-slow.png");
    texPowerupThick = assets.getAsset("texture.power-up-thick.png");
    texPowerupThin = assets.getAsset("texture.power-up-thin.png");

    powerupArchetype = new ArchetypeBuilder()
      .add(TransformComponent.class)
      .add(SpriteComponent.class)
      .add(PowerupComponent.class)
      .add(Box2dComponent.class)
      .build(getWorld());

    box2dMapper = world.getMapper(Box2dComponent.class);
    transformMapper = world.getMapper(TransformComponent.class);
    powerupMapper = world.getMapper(PowerupComponent.class);
    spriteMapper = world.getMapper(SpriteComponent.class);

  }

  @Override
  public int createEntity() {
    int entity = getWorld().create(powerupArchetype);

    com.badlogic.gdx.physics.box2d.World world = Box2DUtils.world;

    box2dMapper.get(entity).body = Box2DUtils.createBody(world, Box2DUtils.STATIC_BODY_DEF, Box2DUtils.ITEM_FIXTURE_DEF);


    return entity;
  }


  public int spawnRandomPowerup(Vector2 position, Random random){
    int entity = createEntity();


    int rnd = random.nextInt(4);
    boolean down = false;
    Powerup powerup = null;
    TextureRegion texture = null;

    switch(rnd){
      case 0:
        down = true;
      case 1:
        powerup = new SpeedPowerup(down);
        texture = new TextureRegion(down ? texPowerupSlow : texPowerupFast);
        break;
      case 2:
        down = true;
      case 3:
        powerup = new SizePowerup(down);
        texture = new TextureRegion(down ? texPowerupThin : texPowerupThick);
        break;
    }

    PowerupComponent powerupComponent = powerupMapper.get(entity);
    powerupComponent.powerup = powerup;

    TransformComponent transformComponent = transformMapper.get(entity);
    Vector3 vec = new Vector3(position.x, position.y, -80);
    transformComponent.transform.setTranslation(vec);

    Box2dComponent box2d = box2dMapper.get(entity);
    box2d.body.setTransform(position, 90);

    Decal sprite = Decal.newDecal(64,64,texture, true);


    SpriteComponent spriteComponent = spriteMapper.get(entity);
    spriteComponent.sprite = sprite;

    return entity;
  }


}