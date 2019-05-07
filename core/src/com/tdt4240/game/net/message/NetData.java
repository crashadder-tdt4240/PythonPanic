package com.tdt4240.game.net.message;


public class NetData implements INetData{
  private byte[] data;
  public NetData(byte[] data){
    this.data = data;
  }

  @Override
  public byte[] getData() {
    return data;
  }
}