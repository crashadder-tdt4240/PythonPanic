package com.tdt4240.game.desktop.net;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import com.tdt4240.game.net.INetServerSocket;
import com.tdt4240.game.net.INetSocket;
import com.tdt4240.game.net.NetServerSocket;
import com.tdt4240.game.net.message.INetData;
import com.tdt4240.game.net.message.MessageSocket;
import com.tdt4240.game.net.message.NetMessage;
import com.tdt4240.game.net.session.NetUser;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

public class DesktopNetServerSession extends DesktopNetSession{
  
  private INetServerSocket serverSocket = null;
  

  private ArrayList<MessageSocket> sockets = new ArrayList<>();
  

  public DesktopNetServerSession(UUID id, NetUser local){
    super(id, local);
  }


  public void hostSession(){

    serverSocket = new NetServerSocket();
    serverSocket.bind("localhost", 8888);
    serverSocket.accept(1).subscribe((INetSocket socket) -> {
      System.out.println("New connection accepted");
      MessageSocket messageSocket = new MessageSocket(socket);
      sockets.add(messageSocket);

      // exchange user info
      NetMessage message = new NetMessage(1);
      NetUser localUser = getLocalUser();
      message.putString(localUser.getUserName());
      message.getBuffer().putLong(localUser.getUserId().getMostSignificantBits());
      message.getBuffer().putLong(localUser.getUserId().getLeastSignificantBits());
      message.getBuffer().putLong(getLocalSeed());
      messageSocket.sendMessage(message);
      messageSocket.getMessages(1).subscribe((INetData m) -> {
        NetMessage msg = (NetMessage)m;
        String name = msg.getString();
        ByteBuffer buffer = msg.getBuffer();
        UUID userId = new UUID(buffer.getLong(), buffer.getLong());
        long remoteSeed = buffer.getLong();
        addRandomNumber(remoteSeed);
        System.out.printf("User joined session %s, %s\n", name ,userId.toString());
        NetUser user = new DesktopNetUser(userId, name);
        addUser(user);
      });
    });
  }


  public void dispose(){
    if(serverSocket != null){
      serverSocket.dispose();
    }
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
  public MessageSocket getSessionSocket() {
    return sockets.get(0);
  }

  @Override
  public void leaveSession() {
    
  }

}