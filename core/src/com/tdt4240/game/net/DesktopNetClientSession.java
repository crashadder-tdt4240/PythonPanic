package com.tdt4240.game.net;

import java.util.UUID;
import java.nio.ByteBuffer;

import com.tdt4240.game.net.message.INetData;
import com.tdt4240.game.net.message.MessageSocket;
import com.tdt4240.game.net.message.NetMessage;
import com.tdt4240.game.net.session.NetUser;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

public class DesktopNetClientSession extends DesktopNetSession{
  
  private MessageSocket socket;
  
  public DesktopNetClientSession(UUID id, NetUser local){
    super(id, local);
    
  }

  public Single<MessageSocket> connectToHost(){
    INetSocket socket = new NetSocket();
    socket.bind("dock1.spaceway.network", 9909);
    
    SingleSubject<MessageSocket> callback = SingleSubject.create();
    

    socket.connect().subscribe(() -> {
      this.socket = new MessageSocket(socket);
      // exchange user info
      NetMessage message = new NetMessage(1);
      NetUser localUser = getLocalUser();
      message.putString(localUser.getUserName());
      message.getBuffer().putLong(localUser.getUserId().getMostSignificantBits());
      message.getBuffer().putLong(localUser.getUserId().getLeastSignificantBits());
      message.getBuffer().putLong(getLocalSeed());
      this.socket.sendMessage(message);
      this.socket.getMessages(1).subscribe((INetData m) -> {
        NetMessage msg = (NetMessage)m;
        ByteBuffer buffer = msg.getBuffer();
        String name = msg.getString();
        UUID userId = new UUID(buffer.getLong(), buffer.getLong());
        long remoteSeed = buffer.getLong();
        addRandomNumber(remoteSeed);
        System.out.printf("User joined session %s, %s\n", name, userId);
        NetUser user = new DesktopNetUser(userId, name);
        addUser(user);
        
      });
      callback.onSuccess(this.socket);
      
    });
    return callback;
  }

  @Override
  public MessageSocket getSessionSocket() {
    return socket;
  }

  @Override
  public Observable<NetUser> onUserJoin() {
    return null;
  }

  @Override
  public Observable<NetUser> onUserLeft() {
    return null;
  }
  
  @Override
  public void leaveSession() {
    
  }

}