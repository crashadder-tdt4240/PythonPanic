package com.tdt4240.game.mvc.models;

import com.badlogic.gdx.Gdx;
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
import com.tdt4240.game.assets.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class HelpModel extends GdxStageModel {


    private Stage stage;
    private TextButton musicButton, soundButton;
    private Slider soundSlider, musicSlider;
    private Actor actor;
    private TextButton.TextButtonStyle style;
    private Slider.SliderStyle sliderStyle;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Table table;
    private Skin skin;


    public HelpModel(){
        Assets assets = Assets.getInstance();
        skin = assets.getAsset("skin.uiskin.json");
        table = new Table();
        stage = new Stage();


        Label helpText = new Label("Manouver your snake and try to block the other players with your trail. Avoid walls and the trails of other players. Hit powerups to gain an advantage. Steer by pressing left or right on your device. Good luck!", skin);
        TextButton backButton = new TextButton("BACK", skin);

        backButton.setColor(Color.YELLOW);


        helpText.setWrap(true);
        table.setFillParent(true);
        table.setDebug(false); //debugger

        table.row();
        table.add(backButton);
        table.row();
        table.add(helpText).width(450f);

        table.setHeight(Gdx.graphics.getHeight());
        table.setWidth(Gdx.graphics.getWidth());
        stage.addActor(table);



        bindActor("BACK", backButton);



    }


    public void update(float dtime){
        stage.act(dtime);
    }

    public Stage getStage(){
        return stage;
    }
}