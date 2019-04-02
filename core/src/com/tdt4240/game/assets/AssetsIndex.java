package com.tdt4240.game.assets;

public class AssetsIndex{
  public String defaultClass;
  public String prefix;


  public String getPrefix(){
    return prefix;
  }

  public String resolvePrefix(String name){
    return String.format("%s.%s", getPrefix(), name);
  }

  public String getClassName(){
    return defaultClass;
  }
}