package com.tdt4240.game.mvc.views;

import com.badlogic.gdx.ScreenAdapter;
import com.tdt4240.game.mvc.models.MVCModel;

public abstract class MVCView<M extends MVCModel> extends ScreenAdapter {
    private M model;
    public MVCView(M model){
        this.model = model;
    }

    public M getModel() {
        return model;
    }
}
