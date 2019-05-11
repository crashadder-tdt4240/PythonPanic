package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.badlogic.gdx.net.Socket;
import com.tdt4240.game.net.session.NetSession;
import com.tdt4240.game.net.session.NetUser;

import io.reactivex.Observable;
import io.reactivex.Single;

public abstract class DesktopNetSession implements NetSession{
  
  private UUID id;
  private NetUser localuser;
  private ArrayList<NetUser> users = new ArrayList<>();
  private long randomNumber = new Random().nextLong();

  private long accRandomNumber = randomNumber;

  public DesktopNetSession(UUID id, NetUser localuser){
    this.id = id;
    this.localuser = localuser;
    this.users.add(localuser);
  }
  
  public UUID getSessionId(){
    return this.id;
  }

  protected void addUser(NetUser user){
    this.users.add(user);
  }
  public List<NetUser> getConnectedUsers(){
    return this.users;
  }

  public NetUser getLocalUser(){
    return localuser;
  }

  public void addRandomNumber(long val){
    accRandomNumber += val;
  }

  public long getSeed(){
    return accRandomNumber;
  }

  public long getLocalSeed(){
    return randomNumber;
  }

  public void dispose(){}

}