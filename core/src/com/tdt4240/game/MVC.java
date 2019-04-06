package com.tdt4240.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.tdt4240.game.controllers.MVCController;
import com.tdt4240.game.views.MVCView;

public abstract class MVC<M, V extends MVCView, C extends MVCController> {
    private Class<M> modelClass;
    private Class<V> viewClass;
    private Class<C> controllerClass;
    private M model;
    private V view;
    private C controller;


    public MVC(Class<M> model, Class<V> view, Class<C> controller){
        modelClass = model;
        viewClass = view;
        controllerClass = controller;
    }

    public Class<M> getModelClass() {
        return modelClass;
    }

    public Class<V> getViewClass() {
        return viewClass;
    }

    public Class<C> getControllerClass() {
        return controllerClass;
    }

    public abstract M createModel();

    public abstract void create();

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    public C getController() {
        return controller;
    }
}


