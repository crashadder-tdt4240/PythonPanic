package com.tdt4240.game.net.message;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NetMessage implements INetData{
  
  private ByteBuffer buffer;
  private int channel = -1;

  public NetMessage(INetData data){
    byte[] bufferData = data.getData();
    buffer = ByteBuffer.wrap(bufferData).asReadOnlyBuffer();
    
    channel = buffer.getInt();
  }

  public NetMessage(int channel){
    this.channel = channel;
    buffer = ByteBuffer.allocate(2048);
    buffer.putInt(channel);
  }

  public void putString(String str){
    buffer.put((byte)str.toCharArray().length);
    for(char c : str.toCharArray()){
      buffer.putChar(c);
    }
  }

  public String getString(){
    byte len = buffer.get();
    char[] arr = new char[len];
    for(int i=0; i < len;i++){
      arr[i] = buffer.getChar();
    }
    return new String(arr);
  }

  public int getChannel() {
    return channel;
  }

  public ByteBuffer getBuffer(){
    return buffer;
  }

  @Override
  public byte[] getData() {
    return buffer.array();
  }



}