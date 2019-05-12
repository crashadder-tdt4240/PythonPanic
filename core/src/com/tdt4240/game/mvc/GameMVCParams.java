package com.tdt4240.game.mvc;

import com.tdt4240.game.mvc.controllers.GameController;
import com.tdt4240.game.mvc.models.GameModel;
import com.tdt4240.game.mvc.views.GameView;
import com.tdt4240.game.net.session.NetSession;

public class GameMVCParams extends MVCParams<GameModel, GameView, GameController>{
  public boolean isMultiplayer = false;
  public NetSession session;
  public int playerIndex = 0;
}