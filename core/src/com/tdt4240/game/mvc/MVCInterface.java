package com.tdt4240.game.mvc;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.tdt4240.game.mvc.controllers.MVCController;
import com.tdt4240.game.mvc.views.MVCView;

public interface MVCInterface {
    public MVCView<?> getView();
    public MVCController<?, ?> getController();
    public InputProcessor getInputProcessor();
    
}
