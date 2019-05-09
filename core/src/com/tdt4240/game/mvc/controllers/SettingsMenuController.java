package com.tdt4240.game.mvc.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.MusicManager;
import com.tdt4240.game.mvc.models.MVCModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.models.SettingsMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.audio.Music;


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
                    if(music.musicOn()){
                        music.pauseMusic();
                    }  
                    else{
                        music.playMusic();
                    } 
                    
                } 
            }
        });
    }


    public InputProcessor getInputManager(){
        return multiplexer;
    }




}