package com.tdt4240.game.net;

import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.net.Socket;

import io.reactivex.Completable;
import io.reactivex.Observable;


// interface for a network session
public interface NetSession{
  public UUID getSessionId();

  public NetUser getLocalUser();
  public List<NetUser> getConnectedUsers();
  public Observable<NetUser> onUserLeft();
  public Observable<NetUser> onUserJoin();
  public Completable onSessionStart();

  public void leaveSession();


  // get socket to user
  public Socket getSocket(NetUser user);

  // Socket that proxies all messages from and to all users
  public Socket getSessionSocket();

}