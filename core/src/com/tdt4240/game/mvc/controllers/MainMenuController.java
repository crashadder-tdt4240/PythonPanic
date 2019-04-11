package com.tdt4240.game.mvc.controllers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;

public class MainMenuController extends MVCController<GdxScreenView<MainMenuModel>, MainMenuModel> {
    
    private InputMultiplexer multiplexer;

    public MainMenuController(GdxScreenView<MainMenuModel> screen, MainMenuModel model){
        super(screen, model);
        multiplexer = new InputMultiplexer();
        
        multiplexer.addProcessor(model.getStage());
        model.onEvent("START").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    System.out.println("Button pressed");
                } 
            }
        });
    }

    public InputProcessor getInputManager(){
        return multiplexer;
    }
}
