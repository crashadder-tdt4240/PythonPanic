package com.tdt4240.game.net.session;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.tdt4240.game.net.message.IMessageSocket;

import io.reactivex.Observable;
import io.reactivex.Single;


// interface for a network session
public interface NetSession{
  public UUID getSessionId();
  public NetUser getLocalUser();
  public List<NetUser> getConnectedUsers();
  public Observable<NetUser> onUserLeft();
  public Observable<NetUser> onUserJoin();
  
  // returns the seed
  public long getSeed();
  public long getLocalSeed();
  public void leaveSession();

  public void dispose();

  // Socket that proxies all messages from and to all users
  public IMessageSocket getSessionSocket();

}