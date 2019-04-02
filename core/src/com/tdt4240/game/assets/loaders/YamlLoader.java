package com.tdt4240.game.assets.loaders;

import java.util.Arrays;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlLoader<T> extends TypedLoader<T, YamlLoader<T>.YamlLoaderParams<T>>{
  
  Yaml yaml;
  T currentResource = null;
  public YamlLoader(FileHandleResolver resolver, Class<T> typeClass){
    super(resolver, typeClass);
    // not ideal, but it works
    yaml = new Yaml(new Constructor(typeClass));
  }
  
  
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, YamlLoaderParams<T> params) {
    return null;
  }

  public void loadAsync(AssetManager manager, String fileName, FileHandle handle, YamlLoaderParams<T> params){
    currentResource = yaml.load(handle.read());
 
  }

  public T loadSync(AssetManager manager, String fileName, FileHandle handle, YamlLoaderParams<T> params){
    return currentResource;
  }

  public class YamlLoaderParams<T> extends AssetLoaderParameters<T>{
    public YamlLoaderParams(){
      
    }
  }
}