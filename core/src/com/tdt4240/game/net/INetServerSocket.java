package com.tdt4240.game.net;

import io.reactivex.Observable;

public interface INetServerSocket{

  public void bind(String address, int port);
  
  // how many connections to accept
  public Observable<INetSocket> accept(int count);
  
  // accept connections until disposed
  public Observable<INetSocket> accept();
  
  public void dispose();
}