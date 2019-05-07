package com.tdt4240.game.net.message;

import io.reactivex.Observable;

public interface IMessageSocket{
  public Observable<INetData> getMessages();
  public Observable<INetData> getMessages(int channel);
  public void sendMessage(INetData message);
}