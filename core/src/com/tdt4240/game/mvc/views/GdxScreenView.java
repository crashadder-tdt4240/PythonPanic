package com.tdt4240.game.mvc.views;


import com.tdt4240.game.mvc.models.GdxStageModel;

public class GdxScreenView<M extends GdxStageModel> extends MVCView<M> {


    public GdxScreenView(M model){
        super(model);
        
    }

    public void render(float dtime){
        getModel().getStage().draw();;
    }

}
