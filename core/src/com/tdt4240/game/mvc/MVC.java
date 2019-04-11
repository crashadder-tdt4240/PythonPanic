package com.tdt4240.game.mvc;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.tdt4240.game.mvc.controllers.MVCController;
import com.tdt4240.game.mvc.models.MVCModel;
import com.tdt4240.game.mvc.views.MVCView;

public abstract class MVC<M extends MVCModel, V extends MVCView<M>, C extends MVCController<V, M>> {
    private M model;
    private V view;
    private C controller;



    public abstract void create();

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    public void setView(V view){
        this.view = view;
    }
    
    public void setModel(M model){
        this.model = model;
    }

    public void setController(C controller){
        this.controller = controller;
    }

    public C getController() {
        return controller;
    }
}


