package com.tdt4240.game.mvc;
import com.tdt4240.game.assets.Assets;


import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance = new MusicManager();
    private Music music;

  
    public MusicManager(){
        music = Assets.getInstance().getAsset("music.backgroundMusic.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
  
    }

    public static MusicManager getInstance(){
      return instance;
    }

    public void playMusic(){
        music.play();
    }


    public void pauseMusic(){
        music.pause();
    }

    
    public Boolean musicOn(){
        if(music.isPlaying() == true){
          return true;
        }
        return false;
    }
      




}