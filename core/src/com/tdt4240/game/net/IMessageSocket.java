package com.tdt4240.game.net;

import io.reactivex.Observable;

public interface IMessageSocket{
  public Observable<INetData> getMessages();
  public void sendMessage(INetData message);
}