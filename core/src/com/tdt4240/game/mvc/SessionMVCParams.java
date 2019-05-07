package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.SessionController;
import com.tdt4240.game.mvc.models.SessionModel;
import com.tdt4240.game.mvc.views.SessionView;
import com.tdt4240.game.net.session.NetSession;

public class SessionMVCParams extends MVCParams<SessionModel, SessionView, SessionController>{
  public NetSession session;
}