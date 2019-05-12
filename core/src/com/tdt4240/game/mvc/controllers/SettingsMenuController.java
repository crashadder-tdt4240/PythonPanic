package com.tdt4240.game.mvc.controllers;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.MusicManager;
import com.tdt4240.game.mvc.models.SettingsMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;



public class SettingsMenuController extends MVCController<GdxScreenView<SettingsMenuModel>, SettingsMenuModel> {
    private InputMultiplexer multiplexer;
    private MusicManager music;

    public SettingsMenuController(GdxScreenView<SettingsMenuModel> view, SettingsMenuModel model){
        super(view, model); 
        multiplexer = new InputMultiplexer();
        MusicManager music = MusicManager.getInstance();

        
        
        multiplexer.addProcessor(model.getStage());
        model.onEvent("ENABLE MUSIC").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    if(music.getMusicOn()){
                        music.pauseMusic();
                    }  
                    else{
                        music.playMusic();
                    } 
                    
                } 
            }
        });


        model.onEvent("BACK").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    MVCManager.getInstance().createMVC("MAIN_MENU");
                } 
            }
        });

    }


    


    public InputProcessor getInputManager(){
        return multiplexer;
    }




}