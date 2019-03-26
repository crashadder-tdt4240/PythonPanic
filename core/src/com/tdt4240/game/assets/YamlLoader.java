package com.tdt4240.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import org.yaml.snakeyaml.Yaml;

public class YamlLoader extends AssetLoader<Yaml, YamlLoader.YamlLoaderParams>{
  
  public YamlLoader(YamlLoaderParams params){
    super(null);
  }
  
  
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, YamlLoaderParams parameter) {
    return null;
  }

  public class YamlLoaderParams extends AssetLoaderParameters<Yaml>{
    public YamlLoaderParams(){

    }
  }
}