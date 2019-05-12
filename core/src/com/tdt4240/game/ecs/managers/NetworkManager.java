package com.tdt4240.game.ecs.managers;

import java.nio.ByteBuffer;
import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.ecs.MpTestMap;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.NetworkComponent;
import com.tdt4240.game.ecs.components.SnakeComponent;
import com.tdt4240.game.ecs.components.TransformComponent;
import com.tdt4240.game.ecs.net.NetHelper;
import com.tdt4240.game.net.message.IMessageSocket;
import com.tdt4240.game.net.message.INetData;
import com.tdt4240.game.net.message.NetMessage;
import com.tdt4240.game.net.session.NetUser;

public class NetworkManager extends IteratingSystem{
  
  private HashMap<Integer, Integer> entityMap = new HashMap<>();

  private ComponentMapper<NetworkComponent> componentMapper;

  private ComponentMapper<Box2dComponent> box2ComponentMapper;
  private ComponentMapper<TransformComponent> tComponentMapper;
  private ComponentMapper<SnakeComponent> snakeMapper;


  private IMessageSocket socket;
  private MpTestMap testMap;
  private NetUser localUser;
  private int currentTick = 0;
  public NetworkManager(){
    super(Aspect.one(NetworkComponent.class));
  }

  public void setLocalPlayer(NetUser user){
    localUser = user;
  }

  public void setSocket(IMessageSocket socket, MpTestMap testMap){
    this.socket = socket;
    this.testMap = testMap;

    socket.getMessages(1000).subscribe((INetData data) -> {
      recieveCreateMessage((NetMessage)data);
    });

    socket.getMessages(1001).subscribe((INetData data) -> {
      recieveUpdateMessage((NetMessage)data);
    
    });
    
    socket.getMessages(1005).subscribe((INetData data) -> {
      recieveDeleteMessage((NetMessage) data);
    });


  }

  @Override
  protected boolean checkProcessing() {
    return super.checkProcessing() && socket != null;
  }

  private void recieveCreateMessage(NetMessage message){
    ByteBuffer buffer = message.getBuffer();
    int remoteId = buffer.getInt();
    Vector3 pos = NetHelper.readVector3(buffer, new Vector3(0, 0, 0));
    float angle = buffer.getFloat();
    testMap.createSnake(remoteId, pos, angle);
  }
  
  private void recieveUpdateMessage(NetMessage message){
    ByteBuffer buffer = message.getBuffer();
    int remoteId = buffer.getInt();
    int entity = entityMap.get(remoteId);
    Box2dComponent box2dComponent = box2ComponentMapper.get(entity);
    NetHelper.readBox2dComponent(buffer, box2dComponent);
  }

  private void recieveDeleteMessage(NetMessage message){
    int remoteId = message.getBuffer().getInt();
    int entity = entityMap.get(remoteId);
    System.out.println("Delete message recieve");
    System.out.println(remoteId);
    System.out.println(entity);
    
    getWorld().delete(entity);
  }

  private void sendCreateMessage(int entityId){

    NetMessage message = new NetMessage(1000);
    ByteBuffer buffer = message.getBuffer();
    buffer.putInt(entityId);
    NetHelper.writeVector3(buffer, tComponentMapper.get(entityId).transform.getTranslation(new Vector3(0, 0,0 )));
    buffer.putFloat(MathUtils.radiansToDegrees * box2ComponentMapper.get(entityId).body.getAngle());
    
    socket.sendMessage(message);
  }

  private void sendUpdateMessage(int entityId){
    NetMessage message = new NetMessage(1001);
    ByteBuffer buffer = message.getBuffer();
    buffer.putInt(entityId);
    NetHelper.writeBox2dComponent(buffer, box2ComponentMapper.get(entityId));
    socket.sendMessage(message);
  }

  @Override
  protected void process(int entityId) {
    currentTick++;
    NetworkComponent component = componentMapper.get(entityId);
    if(!component.remote){
      if(component.lastSync != -1){
        component.lastSync++;
        if(component.syncFreq != 0 && component.lastSync+component.syncFreq < currentTick){
          sendUpdateMessage(entityId);
          component.lastSync = currentTick;
        }
      } else {
        sendCreateMessage(entityId);
        component.lastSync = currentTick;
      }
    }
  }
  @Override
  public void inserted(int entity) {
    super.inserted(entity);
    NetworkComponent component = componentMapper.get(entity);
    if(component.remote){
      entityMap.put(component.remoteId, entity);
    }
  }

  @Override
  public void removed(int entity) {
    super.removed(entity);
    NetworkComponent component = componentMapper.get(entity);
    if(component.remote){
      entityMap.remove(component.remoteId);
    } else {
      System.out.println("Delete message");
      NetMessage deleteMessage = new NetMessage(1005);
      deleteMessage.getBuffer().putInt(entity);
      if(snakeMapper.has(entity)){
        //NetMessage playerDiedMessage = new NetMessage(100);
        //long least = localUser.getUserId().getLeastSignificantBits();
        //long most = localUser.getUserId().getMostSignificantBits();
        //playerDiedMessage.getBuffer().putLong(least);
        //playerDiedMessage.getBuffer().putLong(most);
        // send message
      }
      socket.sendMessage(deleteMessage);
    }
    
  }

}