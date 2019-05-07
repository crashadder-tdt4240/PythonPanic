package com.tdt4240.game.net;

import com.badlogic.gdx.net.Socket;

import io.reactivex.Completable;

public interface INetSocket extends Socket{
  public void bind(String address, int port);
  public Completable connect();
  public Completable onConnected();
}