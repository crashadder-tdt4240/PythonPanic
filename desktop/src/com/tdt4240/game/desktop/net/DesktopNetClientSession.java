package com.tdt4240.game.desktop.net;

import java.util.UUID;

import com.tdt4240.game.net.INetSocket;
import com.tdt4240.game.net.NetSocket;
import com.tdt4240.game.net.NetUser;

import io.reactivex.Single;

public class DesktopNetClientSession extends DesktopNetSession{
  public DesktopNetClientSession(UUID id, NetUser local){
    super(id, local);
  }

  public Single<INetSocket> connectToHost(){
    INetSocket socket = new NetSocket();
    socket.bind("localhost", 8888);

    return socket.connect().toObservable().map((Object obj) -> {
      return socket;
    }).singleOrError();
  }

}