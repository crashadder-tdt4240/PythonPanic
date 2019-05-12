package com.tdt4240.game.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.game.ecs.EcsEngine;
import com.tdt4240.game.ecs.GameLevel;
import com.tdt4240.game.ecs.MpTestMap;
import com.tdt4240.game.ecs.TestMap;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.NetworkComponent;
import com.tdt4240.game.ecs.components.PlayerInputComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.managers.NetworkManager;
import com.tdt4240.game.ecs.systems.PowerupSystem;
import com.tdt4240.game.mvc.GameMVCParams;
import com.tdt4240.game.net.message.IMessageSocket;
import com.tdt4240.game.net.message.INetData;
import com.tdt4240.game.net.message.NetMessage;
import com.tdt4240.game.net.session.NetUser;
import com.tdt4240.game.utils.Box2DUtils;

public class GameModel extends MVCModel{
  private EcsEngine engine;
  

  private ComponentMapper<SpriteComponent> spriteMapper;
  private ComponentMapper<PlayerInputComponent> playerInputMapper;

  private ComponentMapper<NetworkComponent> netMapper;
  private ComponentMapper<Box2dComponent> box2dMapper;

  // list of players in game
  private List<NetUser> players;

  private GameMVCParams params = new GameMVCParams();

  private Random random = new Random();


  private Vector2 worldSize = new Vector2(1000, 1000);

  public GameModel(){
    
    engine = new EcsEngine();
    spriteMapper = engine.getMapper(SpriteComponent.class);
    playerInputMapper = engine.getMapper(PlayerInputComponent.class);

    netMapper = engine.getMapper(NetworkComponent.class);
    box2dMapper = engine.getMapper(Box2dComponent.class);

    Box2DUtils.world = getEngine().getBox2dWorld();

  }

  public GameModel(GameMVCParams params){
    this();
    // do something if multiplayer
    GameLevel level = null;
    this.params = params;
    if(params.isMultiplayer){
      IMessageSocket socket = params.session.getSessionSocket();
      
      // listen to game events, keeps track of when a player has died
      socket.getMessages(100).subscribe((INetData data) -> 
        onUserLost((NetMessage)data)
      );
      random.setSeed(params.session.getSeed());
      NetworkManager manager = engine.getWorld().getSystem(NetworkManager.class);
      PowerupSystem powerup = engine.getWorld().getSystem(PowerupSystem.class);
      powerup.setRandom(random);
      
      int localUserIndex = 0;
      NetUser localUser = params.session.getLocalUser();
      // 'sort' local user
      for(NetUser user : params.session.getConnectedUsers()){
        if(!user.equals(localUser)){
          long i1 = user.getUserId().getLeastSignificantBits() ^ user.getUserId().getMostSignificantBits();
          long i2 = localUser.getUserId().getLeastSignificantBits() ^ localUser.getUserId().getMostSignificantBits(); 
          if(i2 > i1){
            localUserIndex++;
          }
        }
      }


      MpTestMap testMap = new MpTestMap(engine.getWorld(), engine.getBox2dWorld(), getWorldSize()); 
      level = testMap;
      manager.setSocket(params.session.getSessionSocket(), testMap);
      testMap.setPlayerIndex(localUserIndex);
      testMap.setRandom(random);
      level.setup();
      testMap.createSnake();
      

    } else {
      level = new TestMap(engine.getWorld(), engine.getBox2dWorld(), getWorldSize());
      level.setup();
    }

  }

  public Vector2 getWorldSize(){
    return worldSize.cpy();
  }

  private void onUserLost(NetMessage message){
    // todo: set user to dead, make sure to end game when there is only one player left
  }

  public EcsEngine getEngine(){
    return engine;
  }

  public List<NetworkComponent> getNetworkComponents(){
    ArrayList<NetworkComponent> networkComponents = new ArrayList<>();
    IntBag entities = engine.getEntities(Aspect.all(Box2dComponent.class, NetworkComponent.class));
    for(int i = 0; i < entities.size(); i++){
      NetworkComponent networkComponent = netMapper.get(entities.get(i));
      networkComponents.add(networkComponent);
    }
    return networkComponents;
  }

  public List<Decal> getDecals(){
    ArrayList<Decal> decals = new ArrayList<>();
    IntBag entities = engine.getEntities(Aspect.one(SpriteComponent.class));
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      SpriteComponent spriteComponent = spriteMapper.get(entity);
      decals.add(spriteComponent.sprite);
    }
    return decals;
  }

  public List<PlayerInputComponent> getPlayerInputs(){
    ArrayList<PlayerInputComponent> playerInputs = new ArrayList<>();
    IntBag entities = engine.getEntities(Aspect.one(PlayerInputComponent.class));
    for(int i = 0; i < entities.size(); i++){
      int entity = entities.get(i);
      PlayerInputComponent playerInput = playerInputMapper.get(entity);
      playerInputs.add(playerInput);
    }

    return playerInputs;
  }


  public void update(float dtime){
    engine.update(dtime);
  }




}