package com.tdt4240.game.net;

import io.reactivex.Observable;

public interface NetUserService{
  public Observable<NetUser> signIn();
  public void signOut();
  public boolean isSignedIn();
  public NetUser getSignedInUser();
  
  // subscibable observable for when the user loginprocess is finished
  public Observable<NetUser> onLogin();
}