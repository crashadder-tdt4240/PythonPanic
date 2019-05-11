package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.UUID;

import com.tdt4240.game.net.INetServerSocket;
import com.tdt4240.game.net.INetSocket;
import com.tdt4240.game.net.NetServerSocket;

// matchmaking server, keeps track of active games and host matches
public class GameServer{
  
  private INetServerSocket serverSocket;
  
  private ArrayList<INetSocket> waitingSockets = new ArrayList<>();
  private ArrayList<DesktopNetServerSession> serverSessions = new ArrayList<>();
  
  public GameServer(){
    serverSocket = new NetServerSocket();
    serverSocket.bind("0.0.0.0", 8888);
    serverSocket.accept().subscribe(this::onConnection);
  }


  private void onConnection(INetSocket socket){
    System.out.println("New player looking for a game");
    waitingSockets.add(socket);
    if(waitingSockets.size() >= 2){
      System.out.println("Creating new server session");
      // create new session
      DesktopNetServerSession session = new DesktopNetServerSession(UUID.randomUUID(), null);
      session.createSession(waitingSockets);
      waitingSockets = new ArrayList<>();
      serverSessions.add(session);
    }
  }


}