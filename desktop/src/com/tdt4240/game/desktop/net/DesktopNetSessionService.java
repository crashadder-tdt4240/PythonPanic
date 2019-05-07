package com.tdt4240.game.desktop.net;

import java.util.UUID;

import com.badlogic.gdx.net.Socket;
import com.tdt4240.game.net.message.MessageSocket;
import com.tdt4240.game.net.session.NetSession;
import com.tdt4240.game.net.session.NetSessionService;
import com.tdt4240.game.net.session.NetUser;

import io.reactivex.Single;

public class DesktopNetSessionService implements NetSessionService{
  public DesktopNetSessionService(){

  }

  public Single<NetSession> createSession(NetUser user){
    return null;
  }

  public Single<NetSession> hostSession(NetUser user){
    DesktopNetServerSession serverSession = new DesktopNetServerSession(UUID.randomUUID(), user);
    serverSession.hostSession();
    return Single.just(serverSession);
  }

  public Single<NetSession> clientSession(NetUser user){
    DesktopNetClientSession clientSession = new DesktopNetClientSession(UUID.randomUUID(), user);
    return clientSession.connectToHost().map((MessageSocket socket) -> {
      System.out.println("Got socket");
      System.out.println(clientSession);
      return clientSession;
    });
  }


  public void closeSession(NetSession session){

  }

}