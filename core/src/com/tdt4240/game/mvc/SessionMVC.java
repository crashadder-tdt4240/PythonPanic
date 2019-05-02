package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.SessionController;
import com.tdt4240.game.mvc.models.SessionModel;
import com.tdt4240.game.mvc.views.SessionView;
import com.tdt4240.game.net.NetSession;

// waiting room for players when hosting/joining
public class SessionMVC extends MVC<SessionModel, SessionView, SessionController, SessionMVC.SessionMVCParams>{
  public void create(){
    setModel(new SessionModel());
    setView(new SessionView(getModel()));
    setController(new SessionController(getView(), getModel()));
  }

  public void create(SessionMVCParams param){

  }

  public class SessionMVCParams extends MVCParams<SessionModel, SessionView, SessionController>{
    public NetSession session;
  }
}