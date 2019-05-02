package com.tdt4240.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tdt4240.game.Game;
import com.tdt4240.game.desktop.net.DesktopNetSessionService;
import com.tdt4240.game.desktop.net.DesktopNetUserService;
import com.tdt4240.game.net.NetSessionService;
import com.tdt4240.game.net.NetUserService;

public class DesktopLauncher {
  public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    

    NetSessionService sessionService = new DesktopNetSessionService();
    NetUserService userService = new DesktopNetUserService();

    Game game = new Game(sessionService, userService);
    
    new LwjglApplication(game, config);
  }
}
