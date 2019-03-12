package com.tdt4240.game.ecs;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeFactory;
import com.artemis.InvocationStrategy;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.Aspect.Builder;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.systems.ContactSystem;
import com.tdt4240.game.ecs.systems.PhysicsSystem;
import com.tdt4240.game.ecs.systems.PlayerInputSystem;
import com.tdt4240.game.ecs.systems.SpriteSystem;
import com.tdt4240.game.utils.Box2DUtils;

public class EcsEngine{
  private World world;
  private int tps = 128;
  private int tickCounter = 0;
  private float acc = 0;

  private com.badlogic.gdx.physics.box2d.World bWorld;
  private Box2DDebugRenderer debugRenderer;
  Camera cam1 = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  public EcsEngine(){
    ContactSystem contactSystem = new ContactSystem();
    PlayerInputSystem playerInputSystem = new PlayerInputSystem();
    PhysicsSystem physicsSystem = new PhysicsSystem();
    SpriteSystem spriteSystem = new SpriteSystem();
    WorldConfiguration config = new WorldConfigurationBuilder()
      .with(contactSystem)
      .with(playerInputSystem)
      .with(physicsSystem)
      .with(spriteSystem)
      .build();

    Texture testTexture = new Texture(Gdx.files.internal("test.png"));

    Decal sprite = Decal.newDecal(16, 16, new TextureRegion(testTexture));
  
    Decal sprite2 = Decal.newDecal(16, 16, new TextureRegion(testTexture));
    config.expectedEntityCount(2048);
    
    world = new World(config);
    bWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(), false);
    bWorld.setContactListener(contactSystem);
    debugRenderer = new Box2DDebugRenderer(
      true,
      false,
      false,
      true,
      true,
      false
    );


    // test entity
    Body body1 = Box2DUtils.createBody(bWorld, Box2DUtils.KINEMATIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    Body body2 = Box2DUtils.createBody(bWorld, Box2DUtils.DYNAMIC_BODY_DEF, Box2DUtils.PLAYER_FIXTURE_DEF);
    int entity1 = world.create();
    int entity2 = world.create();
    ComponentMapper<Box2dComponent> box2dMapper = world.getMapper(Box2dComponent.class);
    ComponentMapper<TransformComponent> transformMapper = world.getMapper(TransformComponent.class);
    ComponentMapper<PlayerInputComponent> inputMapper = world.getMapper(PlayerInputComponent.class);
    ComponentMapper<SpriteComponent> spriteMapper = world.getMapper(SpriteComponent.class);

    Box2dComponent box2dComponent = box2dMapper.create(entity1);
    TransformComponent transformComponent = transformMapper.create(entity1);
    box2dComponent.body = body1;

    Box2dComponent box2dComponent2 = box2dMapper.create(entity2);
    TransformComponent transformComponent2 = transformMapper.create(entity2);

    SpriteComponent spriteComponent1 = spriteMapper.create(entity1);
    SpriteComponent spriteComponent2 = spriteMapper.create(entity2);


    transformComponent.transform.translate(0, 0, -50);
    transformComponent2.transform.translate(0, 0, -50);

    spriteComponent1.sprite = sprite;
    spriteComponent2.sprite = sprite2;

    box2dComponent2.body = body2;
    body2.setTransform(new Vector2(8, 32), 0);
    body2.setLinearVelocity(0, -32);

    inputMapper.create(entity1);

    
  }

  public <T extends Component> ComponentMapper<T> getMapper(Class<T> cls){
    return world.getMapper(cls);
  }

  public void addEntityListener(Builder filter, SubscriptionListener listener){
    world.getAspectSubscriptionManager().get(filter).addSubscriptionListener(listener);
  }

  public IntBag getEntities(Builder filter){
    return world.getAspectSubscriptionManager().get(filter).getEntities();
  }

  public void setTPS(int tps){
    this.tps = tps;
  }

  public void update(float dtime){
    acc += dtime;
    int ticks = (int) (tps*acc);
    float worldDelta = 1f/tps;
    bWorld.step(dtime, 4, 4);
    for(int i=0; i < ticks; i++){
      // The documentation sugests setting delta before each process call
      this.world.setDelta(worldDelta);
      world.process();
    }
    acc -= worldDelta*ticks;
    tickCounter += ticks;
    //debugRenderer.render(bWorld, cam1.combined);
  }

}