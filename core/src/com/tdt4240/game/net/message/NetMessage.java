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

  public NetMessage(){
    this(0);
  }
  
  public int getChannel() {
    return channel;
  }

  public ByteBuffer getBuffer(){
    return buffer;
  }

  @Override
  public byte[] getData() {
    return buffer.compact().array();
  }



}