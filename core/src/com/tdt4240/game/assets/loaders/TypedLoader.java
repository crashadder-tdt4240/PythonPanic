package com.tdt4240.game.assets.loaders;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
public abstract class TypedLoader<T, P extends AssetLoaderParameters<T>> extends AsynchronousAssetLoader<T, P>{
  
  private Class<T> typeClass;
  
  public TypedLoader(FileHandleResolver fileHandleResolver, Class<T> typeClass){
    super(fileHandleResolver);
    this.typeClass = typeClass;
  }

  public Class<T> getTypeClass(){
    return this.typeClass;
  }
}