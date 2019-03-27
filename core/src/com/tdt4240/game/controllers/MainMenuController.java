package com.tdt4240.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.tdt4240.game.views.MainMenuScreen;

public class MainMenuController extends InputAdapter {
    private MainMenuScreen screen;

    public MainMenuController(MainMenuScreen screen){
        this.screen = screen;
        Gdx.input.setInputProcessor(screen.getStage());
    }
}
