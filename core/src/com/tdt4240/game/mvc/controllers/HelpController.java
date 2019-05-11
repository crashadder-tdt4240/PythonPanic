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
import com.tdt4240.game.mvc.models.HelpModel;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;


public class HelpController extends MVCController<GdxScreenView<HelpModel>, HelpModel> {
    private InputMultiplexer multiplexer;

    public HelpController(GdxScreenView<HelpModel> view, HelpModel model){
        super(view, model); 
        multiplexer = new InputMultiplexer();




        multiplexer.addProcessor(model.getStage());
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