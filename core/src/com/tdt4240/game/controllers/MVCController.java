package com.tdt4240.game.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.tdt4240.game.views.MVCView;

public abstract class MVCController<T extends MVCView, M> implements InputProcessor {
    private T screen;
    private M model;
    public MVCController(T screen, M model){
        this.screen = screen;
        this.model = model;
    }

    public T getView(){
        return screen;
    }

    public M getModel(){
        return model;
    }
}
