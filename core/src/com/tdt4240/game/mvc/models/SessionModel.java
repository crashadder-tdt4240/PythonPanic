package com.tdt4240.game.mvc.models;

import com.tdt4240.game.net.session.NetSession;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

public class SessionModel extends MVCModel{

  private NetSession session;
  private boolean ready = false;

  private CompletableSubject readySubject = CompletableSubject.create();

  public SessionModel(NetSession session){
    this.session = session;
  }

  public Completable onReady(){
    return readySubject;
  }

  public NetSession getSession(){
    return session;
  }

  public void update(float dtime){
    if(!ready && this.session.getConnectedUsers().size() > 1){
      ready = true;
      readySubject.onComplete();
    }
  }
}