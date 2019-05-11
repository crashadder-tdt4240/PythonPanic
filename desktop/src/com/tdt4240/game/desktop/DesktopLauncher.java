package com.tdt4240.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tdt4240.game.Game;
import com.tdt4240.game.desktop.net.DesktopNetSessionService;
import com.tdt4240.game.desktop.net.DesktopNetUserService;
import com.tdt4240.game.desktop.net.GameServer;
import com.tdt4240.game.net.session.NetSessionService;
import com.tdt4240.game.net.session.NetUserService;

public class DesktopLauncher {
  public static void main (String[] arg) {
    // start headless server instead of running the game
    for(String a : arg){
      System.out.println(a);
    }
    if(arg.length >= 1 && arg[0].equals("headless")) {
      new GameServer();
    } else {
    
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      

      NetSessionService sessionService = new DesktopNetSessionService();
      NetUserService userService = new DesktopNetUserService();

      Game game = new Game(sessionService, userService);
      
      new LwjglApplication(game, config);
    }
  }
}
