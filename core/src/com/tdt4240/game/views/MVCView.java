package com.tdt4240.game.views;

public abstract class MVCView<M> {
    private M model;
    public MVCView(M model){
        this.model = model;
    }

    public M getModel() {
        return model;
    }
}
