package com.tdt4240.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tdt4240.game.Game;
import com.tdt4240.game.net.DesktopNetSessionService;
import com.tdt4240.game.net.DesktopNetUserService;
import com.tdt4240.game.net.session.NetSessionService;
import com.tdt4240.game.net.session.NetUserService;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		NetSessionService sessionService = new DesktopNetSessionService();
		NetUserService userService = new DesktopNetUserService();

		Game game = new Game(sessionService, userService);


		initialize(game, config);
	}
}
