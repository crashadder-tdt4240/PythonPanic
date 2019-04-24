package com.tdt4240.game.mvc.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.tdt4240.game.mvc.models.MVCModel;
import com.tdt4240.game.mvc.views.MVCView;

public class MVCController<T extends MVCView<M>, M extends MVCModel> extends InputAdapter {
    private T view;
    private M model;

    public MVCController(T view, M model){
        this.view = view;
        this.model = model;
    }

    public T getView(){
        return view;
    }

    public M getModel(){
        return model;
    }

    public InputProcessor getInputManager(){
        return this;
    }


}
