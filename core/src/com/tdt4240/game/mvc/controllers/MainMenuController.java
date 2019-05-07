package com.tdt4240.game.mvc.controllers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.tdt4240.game.mvc.MVCManager;
import com.tdt4240.game.mvc.models.GdxStageModel;
import com.tdt4240.game.mvc.models.MainMenuModel;
import com.tdt4240.game.mvc.views.GdxScreenView;
import com.tdt4240.game.net.NetInst;
import com.tdt4240.game.net.session.NetSession;
import com.tdt4240.game.net.session.NetUser;

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
                    MVCManager.getInstance().createMVC("GAME");
                } 
            }
        });

        model.onEvent("EXIT").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    Gdx.app.exit();
                } 
            }
        });

        model.onEvent("HOST").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    NetInst.userService.signIn().subscribe((NetUser user) -> {
                        NetInst.sessionService.hostSession(user).subscribe((NetSession session) -> {
                            System.out.println("Host session created");
                        });
                    });
                } 
            }
        });

        model.onEvent("JOIN").subscribe((Event event) -> {
            if(event instanceof InputEvent){
                InputEvent ievent = (InputEvent)event;
                if(ievent.getType() == InputEvent.Type.touchUp){
                    NetInst.userService.signIn().subscribe((NetUser user) -> {
                        NetInst.sessionService.clientSession(user).subscribe((NetSession session) -> {
                            System.out.println("Client session created");
                        }, (Throwable t) -> {
                            System.err.println("Error happened!");
                            t.printStackTrace();
                        });
                    });
                } 
            }
        });
    }

    public InputProcessor getInputManager(){
        return multiplexer;
    }
}
