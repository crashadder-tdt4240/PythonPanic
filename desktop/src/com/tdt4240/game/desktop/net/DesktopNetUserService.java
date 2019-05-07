package com.tdt4240.game.desktop.net;

import java.util.UUID;

import com.tdt4240.game.net.session.NetUser;
import com.tdt4240.game.net.session.NetUserService;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class DesktopNetUserService implements NetUserService{
  
  private NetUser currentUser = null;
  private BehaviorSubject<NetUser> userSubject = BehaviorSubject.create();
  public Observable<NetUser> signIn(){
    this.currentUser = new DesktopNetUser(UUID.randomUUID(), "Player");
    this.userSubject.onNext(this.currentUser);
    
    return Observable.just(this.currentUser);
  }

  public void signOut(){
    this.currentUser = null;
    this.userSubject.onNext(null);
  }

  public boolean isSignedIn(){
    return this.currentUser != null;
  }

  public NetUser getSignedInUser(){
    return this.currentUser;
  }
  
  public Observable<NetUser> onLogin(){
    return userSubject;
  }
}