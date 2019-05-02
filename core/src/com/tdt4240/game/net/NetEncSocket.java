package com.tdt4240.game.net;

import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Completable;


public class NetEncSocket implements INetSocket{
  
  private INetSocket childSocket;
  private boolean isConnected = false;
  



  @Override
  public Completable connect() {
    return null;
  }
  @Override
  public void dispose() {
    
  }
  
  @Override
  public InputStream getInputStream() {
    return null;
  }

  @Override
  public OutputStream getOutputStream() {
    return null;
  }

  @Override
  public String getRemoteAddress() {
    return childSocket.getRemoteAddress();
  }

  @Override
  public Completable onConnected() {
    return null;
  }

  @Override
  public boolean isConnected() {
    return false;
  }



}