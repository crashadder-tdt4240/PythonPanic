package com.tdt4240.game.mvc.controllers;

import com.tdt4240.game.mvc.models.SessionModel;
import com.tdt4240.game.mvc.views.SessionView;

public class SessionController extends MVCController<SessionView, SessionModel>{
  public SessionController(SessionView view, SessionModel model){
    super(view, model);
  }
}