package com.tdt4240.game.assets;

import java.util.HashMap;
import java.util.UUID;

public class AssetsIndex{
  public String defaultClass;
  public HashMap<String, String> classes = new HashMap<>();
  public String prefix = UUID.randomUUID().toString();
  

  public String getPrefix(){
    return prefix;
  }

  public String resolvePrefix(String name){
    return String.format("%s.%s", getPrefix(), name);
  }

  public String getClassName(){
    return defaultClass;
  }

  public boolean hasClassFor(String ext){
    return classes.containsKey(ext);
  }

  public String getClassFor(String ext){
    return classes.get(ext);
  }
}