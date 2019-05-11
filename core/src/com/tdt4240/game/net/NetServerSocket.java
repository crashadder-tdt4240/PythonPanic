package com.tdt4240.game.net;

import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.NetJavaServerSocketImpl;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class NetServerSocket implements INetServerSocket{
  
  private String boundAddress;
  private int boundPort;
  private boolean bound = false;
  private boolean active = false;
  private int connectionsToAccept = 0;
  private ServerSocket socket = null;

  private Subject<INetSocket> connectionSubject = null; 
  

  public void bind(String address, int port){
    boundAddress = address;
    boundPort = port;
    bound = true;
  }

  public Observable<INetSocket> accept(){
    return accept(-1);
  }

  private void _waitForConnections(){
    SocketHints hints = new SocketHints();
    active = true;
    Thread t = new Thread(){
      public void run(){
        while(connectionsToAccept > 0 || connectionsToAccept <= -1){
          connectionSubject.onNext(new NetSocket(socket.accept(hints)));
          connectionsToAccept--;
        }
        active = false;
      }
    };

    t.start();
  }

  public Observable<INetSocket> accept(int count){
    if(!bound){
      throw new RuntimeException("Server socket has to be bound before accepting incomming connections");
    }
    
    if(active){
      throw new RuntimeException("Socket is already waiting for connections");
    }

    ServerSocketHints hints = new ServerSocketHints();
    hints.acceptTimeout = 0;
    
    connectionsToAccept = count;
    connectionSubject = PublishSubject.create();
    socket = new NetJavaServerSocketImpl(Protocol.TCP, boundAddress, boundPort, hints);

    _waitForConnections();

    return connectionSubject;
  }


  public void dispose(){
    if(socket != null){
      connectionsToAccept = 0;
      socket.dispose();
    }
  }
}