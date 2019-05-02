package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.UUID;

import com.tdt4240.game.net.INetServerSocket;
import com.tdt4240.game.net.INetSocket;
import com.tdt4240.game.net.NetServerSocket;
import com.tdt4240.game.net.NetUser;

public class DesktopNetServerSession extends DesktopNetSession{
  
  private INetServerSocket serverSocket = null;
  

  private ArrayList<INetSocket> sockets = new ArrayList<>();

  public DesktopNetServerSession(UUID id, NetUser local){
    super(id, local);
  }


  public void hostSession(){

    serverSocket = new NetServerSocket();
    serverSocket.bind("localhost", 8888);
    serverSocket.accept(4).subscribe((INetSocket socket) -> {
      System.out.println("New connection accepted");
      sockets.add(socket);
    });
  }


  public void dispose(){
    if(serverSocket != null){
      serverSocket.dispose();
    }
  }
}