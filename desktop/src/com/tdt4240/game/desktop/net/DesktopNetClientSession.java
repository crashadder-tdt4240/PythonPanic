package com.tdt4240.game.desktop.net;

import java.util.UUID;

import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.NetJavaSocketImpl;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.tdt4240.game.net.INetSocket;
import com.tdt4240.game.net.NetSocket;
import com.tdt4240.game.net.NetUser;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

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