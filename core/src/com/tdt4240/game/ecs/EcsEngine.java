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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalMaterial;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.systems.ContactSystem;
import com.tdt4240.game.ecs.systems.DrawSystem;
import com.tdt4240.game.ecs.systems.PhysicsSystem;
import com.tdt4240.game.ecs.systems.PlayerInputSystem;
import com.tdt4240.game.ecs.systems.SnakeSystem;
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
    
    DrawSystem drawSystem = new DrawSystem();
    ContactSystem contactSystem = new ContactSystem();
    PlayerInputSystem playerInputSystem = new PlayerInputSystem();
    PhysicsSystem physicsSystem = new PhysicsSystem();
    SpriteSystem spriteSystem = new SpriteSystem();
    SnakeSystem snakeSystem = new SnakeSystem();

    WorldConfiguration config = new WorldConfigurationBuilder()
      .with(drawSystem)
      .with(contactSystem)
      .with(playerInputSystem)
      .with(physicsSystem)
      .with(spriteSystem)
      .with(snakeSystem)
      .build();
    

    config.expectedEntityCount(2048);
    
    world = new World(config);
    bWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(), false);
    bWorld.setContactListener(contactSystem);
    debugRenderer = new Box2DDebugRenderer(
      true,
      true,
      true,
      true,
      true,
      true
    );

    TestMap testMap = new TestMap(world, bWorld);
    testMap.setup();

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