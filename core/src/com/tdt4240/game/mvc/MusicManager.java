package com.tdt4240.game.mvc;
import com.tdt4240.game.assets.Assets;


import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance = new MusicManager();
    private Music music;
    private boolean musicOn = false;

  
    public MusicManager(){
        music = Assets.getInstance().getAsset("music.backgroundMusic.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
  
    }

    public boolean getMusicOn() {
        return musicOn;   
    }

    public static MusicManager getInstance(){
      return instance;
    }

    public void playMusic(){
        this.musicOn = true;
        music.play();
    }

    public void pauseMusic(){
        this.musicOn = false;
        music.pause();
    
    }

    




}