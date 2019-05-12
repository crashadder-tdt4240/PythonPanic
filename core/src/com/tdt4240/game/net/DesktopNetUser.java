package com.tdt4240.game.net;

import java.util.UUID;

import com.tdt4240.game.net.session.NetUser;

public class DesktopNetUser implements NetUser{
  private UUID id;
  private String username;
  
  public DesktopNetUser(UUID id, String username){
    this.id = id;
    this.username = username;
  }


  public UUID getUserId(){
    return id;
  }

  public String getUserName(){
    return username;
  }

}