package com.tdt4240.game.mvc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.tdt4240.game.mvc.controllers.MVCController;
import com.tdt4240.game.mvc.views.MVCView;

import java.util.HashMap;

public class MVCManager {
    private MVC<?, ?, ?> currentMVC;
    private InputMultiplexer multiplexer;
    private static MVCManager instance = new MVCManager();
    private HashMap<String, MVC> mvcMap = new HashMap<String, MVC>();

    private MVCManager(){
        multiplexer = new InputMultiplexer();
    }

    public static MVCManager getInstance(){
        return instance;
    }

    public MVC getMVC(){
        return currentMVC;
    }
    public void registerMVC(String name, MVC mvc){
        mvcMap.put(name,mvc);
    }

    public void createMVC(String name){
        if(currentMVC != null){
            multiplexer.removeProcessor(this.currentMVC.getController().getInputManager());
        }
        currentMVC = mvcMap.get(name);
        currentMVC.create();
        multiplexer.addProcessor(currentMVC.getController().getInputManager());
    }

    public void render(float delta){
        currentMVC.getView().render(delta);
    }

    public void update(float delta){
        currentMVC.getModel().update(delta);
    }

    public InputProcessor getInputProcessor(){
        return multiplexer;
    }
}
