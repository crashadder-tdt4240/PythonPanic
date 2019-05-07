package com.tdt4240.game.net;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;

public class QuickGame {

    private RealTimeMultiplayer multiplayerClient = Games.RealTimeMultiplayer;

    private static final long PLAYER_ONE = 0x0;
    private static final long PLAYER_TWO = 0x1;
    private static final long PLAYER_THREE = 0x2;
    private static final long PLAYER_FOUR = 0x3;

    private Window window;
    private RoomConfig roomConfig;

   private void startQuickGame(long role){
       Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(2,4,role);
       RoomUpdateListener listener = new RoomListener();
       MessageListener messageListener = new MessageListener();
       RoomStatus roomStatus = new RoomStatus();

       roomConfig = RoomConfig.builder(listener)
               .setMessageReceivedListener(messageListener)
               .setRoomStatusUpdateListener(roomStatus)
               .setAutoMatchCriteria(autoMatchCriteria)
               .build();

       getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

       Games.RealTimeMultiplayer(this,GoogleSignIn.getLastSignedInAccount(this)).create(roomConfig);

   }

   private Window getWindow(){
       return window;
   }
}
