package com.tdt4240.game.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.assets.loaders.AssetLoader;

public class AssetsPresets {
  public HashMap<String, String> aliases;
  public HashMap<String, List<String>> assetLoaders;
  public List<String> assets;
  public boolean preloadAssets;
  private HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
  private HashMap<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>> loaders;

  // returns null if alias or class doesn't exist
  public Class<?> resolveAlias(String alias){
    if(!classes.containsKey(alias)){
      String className = aliases.get(alias);
      if(className != null){
        try{
          Class<?> cls = Class.forName(className);
          classes.put(alias, cls);
          return cls;
        } catch (ClassNotFoundException e){
          return null;
        }
      }
      return null;
    }

    return classes.get(alias);
  }

  // return all classloaders the asset classes they load
  public HashMap<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>> getAssetLoaders(){
    loaders = new HashMap<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>>();
    HashMap<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>> loaders = new HashMap<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>>();
    System.out.println("Loading assetloaders");
    for(Entry<String, List<String>> loader : assetLoaders.entrySet()){
      String loaderName = loader.getKey();
      try{
        Class<?> loaderCls = resolveAlias(loaderName);
        if(loaderCls == null){
          loaderCls = Class.forName(loaderName);
        }
        ArrayList<Class<?>> assetClasses = new ArrayList<Class<?>>();
        for(String assetClassName : loader.getValue()){
          try{
            Class<?> assetClass = resolveAlias(assetClassName);
            if(assetClass == null){
              assetClass = Class.forName(assetClassName);
            }
            assetClasses.add(assetClass);
          } catch(ClassNotFoundException e){
            System.err.printf("Could not find the asset class [%s], ignoring.\n", assetClassName);
          }
        }
        loaders.put((Class<AssetLoader<?, ?>>) loaderCls, assetClasses);
      } catch (ClassNotFoundException e){
        System.err.printf("Could not find the class [%s] for asset loader, ignoring.\n", loaderName);
      } catch (ClassCastException e){
        System.err.printf("Class [%s] is not a asset loader!. Ignoring.\n", loaderName);
      }
    }
    return loaders;
  }

  public List<String> getAssetDirs(){
    return assets;
  }

}