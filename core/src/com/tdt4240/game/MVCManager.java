package com.tdt4240.game;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.tdt4240.game.controllers.MVCController;
import com.tdt4240.game.views.MVCView;

import java.util.HashMap;

public class MVCManager extends Game implements MVCInterface {
    private MVCView currentScreen;
    private MVCController controller;
    private InputProcessor multiplexer;
    private HashMap<String, MVC> mvcMap = new HashMap<String, MVC>();

    public MVCManager(){
        multiplexer = new InputMultiplexer();
    }

    @Override
    public MVCView getScreen() {
        return this.currentScreen;
    }

    @Override
    public void setScreen(MVCView screen) {
        currentScreen = screen;
    }

    @Override
    public MVCController getController() {
        return this.controller;
    }

    @Override
    public void setController(MVCController controller) {
        this.controller = controller;
    }

    public void registerMVC(String name, MVC mvc){
        mvcMap.put(name,mvc);
    }

    public void createMVC(String name){
        MVC mvc = mvcMap.get(name);
        mvc.create();
        setScreen(mvc.getView());
        setController(mvc.getController());
    }

    public void render(float delta){

    }
}
