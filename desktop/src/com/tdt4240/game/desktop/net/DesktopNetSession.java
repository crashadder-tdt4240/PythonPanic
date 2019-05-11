package com.tdt4240.game.desktop.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.tdt4240.game.net.session.NetSession;
import com.tdt4240.game.net.session.NetUser;


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

  public List<NetUser> getConnectedUsers(){
    return this.users;
  }

  public NetUser getLocalUser(){
    return localuser;
  }

  public void addRandomNumber(int val){
    accRandomNumber += val;
  }

  public long getRandomNumber(){
    return accRandomNumber;
  }

  public long getPersonalNumber(){
    return randomNumber;
  }

  public void dispose(){}

}