package com.tdt4240.game.assets;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.tdt4240.game.assets.loaders.YamlLoader;


import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class Assets {
  
  private AssetManager assetManager;
  private AssetsPresets presets;
  private FileHandleResolver fileResolver;

  private HashMap<String, AsyncSubject<?>> assetsBeingLoaded = new HashMap<String, AsyncSubject<?>>();

  private HashMap<String, Object> assetMap = new HashMap<String, Object>();
  
  
  private Subject<Integer> progressSubject = BehaviorSubject.createDefault(0);
  
  private static Assets assets = new Assets();

  public Assets(){
    assetManager = new AssetManager();
    this.fileResolver = assetManager.getFileHandleResolver();
  }

  public static Assets getInstance(){
    return assets;
  }

  // loads and sets assetloaders
  public <T> void setup(){
    
    assetManager.setLoader(AssetsPresets.class, new YamlLoader<AssetsPresets>(fileResolver, AssetsPresets.class));
    assetManager.setLoader(AssetsIndex.class, new YamlLoader<AssetsIndex>(fileResolver, AssetsIndex.class));

    presets = getSync("./assets.yml", AssetsPresets.class);

    // set up loaders

    for(Entry<Class<AssetLoader<?, ?>>, ArrayList<Class<?>>> kp : presets.getAssetLoaders().entrySet()) {
      Class<AssetLoader<?, ?>> loader = kp.getKey();
      System.out.printf("Loader: [%s] = %s\n",loader.toString(), loader.getTypeName());
      for(Class<?> assetClass : kp.getValue()){
        System.out.printf("  Asset: [%s] = %s\n",assetClass.toString(), assetClass.getTypeName());
        AssetLoader<T, ?> loaderInstance = null;
        try{
          loaderInstance = (AssetLoader<T, ?>)loader.getConstructor(FileHandleResolver.class).newInstance(fileResolver);
        } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | IllegalArgumentException e){
          System.err.printf("Could not create instance of [%s], trying different constructor.\n", loader);
          try{
            loaderInstance = (AssetLoader<T, ?>)loader.getConstructor(FileHandleResolver.class, ((Class<T>) assetClass).getClass()).newInstance(fileResolver, (Class<T>) assetClass);
          } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | IllegalArgumentException e2){
            System.err.printf("Could not create instance of [%s] using alternate constructor.\n", loader);
          }
        }

        if(loaderInstance != null){
          assetManager.setLoader((Class<T>)assetClass, (AssetLoader<T, ?>) loaderInstance);
        }
      }
    
    }
  }

  // starts preloading assets, has to be run after setup
  // should return async subject that complets once all assets are preloaded
  public <T> Observable<List<?>> preload(){

    ArrayList<Observable<?>> awaitList = new ArrayList<Observable<?>>();

    for(String dir : presets.getAssetDirs()){
      FileHandle indexHandle = fileResolver.resolve(String.format("%s/index.yml", dir));
      AssetsIndex index = getSync(new AssetDescriptor<>(indexHandle, AssetsIndex.class));
      Class<T> defaultClass = (Class<T>)presets.resolveAlias(index.getClassName());
      for(FileHandle assetFile : fileResolver.resolve(dir).list()){
        Class<T> assetClass = defaultClass;
        String ext = assetFile.extension();
        if(index.hasClassFor(ext)){
          assetClass = (Class<T>)presets.resolveAlias(index.getClassFor(ext));
        }
        if (!assetFile.equals(indexHandle)) {
          // assign name to asset after loading
          awaitList.add(getAsync(new AssetDescriptor<T>(assetFile, assetClass)).map((T asset) -> {
            String assetName = String.format(index.resolvePrefix(assetFile.name()));
            System.out.printf("Asset loaded %s\n", assetName);
            assetMap.put(assetName, asset);
            return asset;
          }));
        }
      }
    }
    return Observable.zip(awaitList, (res) -> Arrays.asList(res) );
  }

  public <T> T getSync(AssetDescriptor<T> assetDescriptor){
    if(!assetManager.isLoaded(assetDescriptor.fileName)){
      System.out.printf("loading %s\n", assetDescriptor.fileName);
      assetManager.load(assetDescriptor);
      assetManager.finishLoadingAsset(assetDescriptor.fileName);
    }
    System.out.println(assetManager.isLoaded(assetDescriptor.fileName));
    return assetManager.get(assetDescriptor);
  }

  @SuppressWarnings("unchecked")
  public <T> Observable<T> getAsync(AssetDescriptor<T> assetDescriptor){
    // Check if asset has been loaded
    if(assetManager.isLoaded(assetDescriptor.fileName)){
      return Observable.just(assetManager.get(assetDescriptor));
    }

    if(!assetsBeingLoaded.containsKey(assetDescriptor.fileName)){
      AsyncSubject<T> subject = AsyncSubject.create();
      assetsBeingLoaded.put(assetDescriptor.fileName, subject);
      assetManager.load(assetDescriptor);
    }

    //This should be a safe cast
    return (AsyncSubject<T>) assetsBeingLoaded.get(assetDescriptor.fileName);
  }
  
  public <T> Observable<T> getAsync(String fileName, Class<T> type){
    return getAsync(new AssetDescriptor<T>(fileName, type));
  }

  public <T> T getSync(String fileName, Class<T> type){
    return getSync(new AssetDescriptor<T>(fileName, type));
  }

  // get asset by name
  public <T> T getAsset(String assetname){
    return (T)assetMap.get(assetname);
  }

  
  public Observable<Integer> getProgress(){
    return progressSubject;
  }

  public void loadUpdate(){
    if(!assetManager.update()){
      float progress = assetManager.getProgress();
      progressSubject.onNext((int) (progress*100));
    }

    if(assetsBeingLoaded.size() > 0){
      float progress = assetManager.getProgress();
      progressSubject.onNext((int) (progress*100));
      ArrayList<String> keysToRemove = new ArrayList<String>(assetsBeingLoaded.size());
      for(Entry<String, AsyncSubject<?>> kp : assetsBeingLoaded.entrySet()){
        String key = kp.getKey();
        if(assetManager.isLoaded(key)){
          AsyncSubject<?> subject = kp.getValue();
          subject.onNext(assetManager.get(key));
          subject.onComplete();
          keysToRemove.add(key);
        }
      }

      for(String key : keysToRemove){
        assetsBeingLoaded.remove(key);
      }
    }
  }
  

}