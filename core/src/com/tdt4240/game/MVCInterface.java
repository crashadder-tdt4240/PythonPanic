package com.tdt4240.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.tdt4240.game.controllers.MVCController;
import com.tdt4240.game.views.MVCView;

public interface MVCInterface {

    public MVCView getScreen();
    public void setScreen(MVCView screen);
    public MVCController getController();
    public void setController(MVCController controller);
}
