package com.tdt4240.game.mvc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.tdt4240.game.mvc.controllers.MVCController;
import com.tdt4240.game.mvc.views.MVCView;

import java.util.HashMap;

public class MVCManager implements MVCInterface {
    private MVCView<?> currentView;
    private MVCController currentController;
    private InputMultiplexer multiplexer;
    private HashMap<String, MVC> mvcMap = new HashMap<String, MVC>();

    public MVCManager(){
        multiplexer = new InputMultiplexer();
    }

    @Override
    public MVCView getView() {
        return this.currentView;
    }

    @Override
    public MVCController getController() {
        return this.currentController;
    }

    public void registerMVC(String name, MVC mvc){
        mvcMap.put(name,mvc);
    }

    public void createMVC(String name){
        if(this.currentController != null){
            multiplexer.removeProcessor(this.currentController);
        }
        MVC<?,?,?> mvc = mvcMap.get(name);
        mvc.create();
        this.currentController = mvc.getController();
        this.currentView = mvc.getView();
        multiplexer.addProcessor(this.currentController.getInputManager());
    }

    public void render(float delta){
        this.currentView.render(delta);
    }

    public void update(float delta){

    }

    public InputProcessor getInputProcessor(){
        return multiplexer;
    }
}
