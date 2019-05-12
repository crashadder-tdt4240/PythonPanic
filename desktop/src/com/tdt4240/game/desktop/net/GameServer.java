package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.UUID;

import com.tdt4240.game.net.*;

// matchmaking server, keeps track of active games and host matches
public class GameServer{
  
  private INetServerSocket serverSocket;
  
  private ArrayList<INetSocket> waitingSockets = new ArrayList<>();
  private ArrayList<com.tdt4240.game.net.DesktopNetServerSession> serverSessions = new ArrayList<>();
  
  public GameServer(){
    serverSocket = new NetServerSocket();
    serverSocket.bind("0.0.0.0", 9909);
    serverSocket.accept().subscribe(this::onConnection);
  }


  private void onConnection(INetSocket socket){
    System.out.println("New player looking for a game");
    waitingSockets.add(socket);
    if(waitingSockets.size() >= 2){
      System.out.println("Creating new server session");
      // create new session
      com.tdt4240.game.net.DesktopNetServerSession session = new com.tdt4240.game.net.DesktopNetServerSession(UUID.randomUUID(), null);
      session.createSession(waitingSockets);
      waitingSockets = new ArrayList<>();
      serverSessions.add(session);
    }
  }


}