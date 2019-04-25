package com.tdt4240.game.net;

import io.reactivex.Observable;

public interface NetSessionService{
  public Observable<NetSession> createSession(NetUser user);
  public void closeSession(NetSession session);
}

