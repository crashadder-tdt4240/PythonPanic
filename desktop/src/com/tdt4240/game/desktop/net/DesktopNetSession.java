package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.net.Socket;
import com.tdt4240.game.net.NetSession;
import com.tdt4240.game.net.NetUser;

import io.reactivex.Observable;

public class DesktopNetSession implements NetSession{
  
  private UUID id;
  private NetUser localuser;
  private ArrayList<NetUser> users = new ArrayList<>();


  public DesktopNetSession(UUID id, NetUser localuser){
    this.id = id;
    this.localuser = localuser;
    this.users.add(localuser);
  }
  
  public UUID getSessionId(){
    return this.id;
  }

  public List<NetUser> getConnectedUsers(){
    return this.users;
  }

  public Observable<NetUser> onUserLeft(){
    return null;
  }

  public Observable<NetUser> onUserJoin(){
    return null;
  }

  public void leaveSession(){

  }

  public NetUser getLocalUser(){
    return localuser;
  }

  // get socket to user
  public Socket getSocket(NetUser user){
    return null;
  }

  // Socket that proxies all messages from and to all users
  public Socket getSessionSocket(){
    return null;
  }

  public void dispose(){}

}