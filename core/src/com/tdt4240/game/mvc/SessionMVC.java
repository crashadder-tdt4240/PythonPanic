package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.SessionController;
import com.tdt4240.game.mvc.models.SessionModel;
import com.tdt4240.game.mvc.views.SessionView;
import com.tdt4240.game.net.session.NetSession;

// waiting room for players when hosting/joining
public class SessionMVC extends MVC<SessionModel, SessionView, SessionController, SessionMVCParams>{
  public void create(){
    setModel(new SessionModel(null));
    setView(new SessionView(getModel()));
    setController(new SessionController(getView(), getModel()));
  }

  public void create(SessionMVCParams param){
    setModel(new SessionModel(param.session));
    setView(new SessionView(getModel()));
    setController(new SessionController(getView(), getModel()));
  }

}