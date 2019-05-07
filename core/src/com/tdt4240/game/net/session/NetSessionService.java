package com.tdt4240.game.net.session;



import io.reactivex.Single;

public interface NetSessionService{
  public Single<NetSession> createSession(NetUser user);
  public void closeSession(NetSession session);


  public Single<NetSession> hostSession(NetUser user);
  public Single<NetSession> clientSession(NetUser user);
}

