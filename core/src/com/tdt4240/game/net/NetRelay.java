package com.tdt4240.game.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NetRelay{

  private List<NetRelayThread> threads = new ArrayList<NetRelayThread>();

  public NetRelay(List<INetSocket> sockets){
    for(INetSocket s1 : sockets){
      threads.add(new NetRelayThread(s1, sockets.stream().filter((INetSocket s2) -> {
        return s1 != s2;
      }).toArray(INetSocket[]::new)));
    }
  }

  public void close(){
    for(NetRelayThread thread : threads){
      thread.run = false;
    }

  }

  private class NetRelayThread implements Runnable{
    public boolean run = true;
    private INetSocket from;
    private INetSocket[] to;
    private Thread thread;
    public NetRelayThread(INetSocket from, INetSocket[] to){
      this.from = from;
      this.to = to;
      thread = new Thread(this);
      thread.start();
    }
    
    public void run(){
      while(run){
        try{
          byte[] buffer = new byte[2048];
          int size = from.getInputStream().read(buffer);
          if(size > 0){
            for(INetSocket other : to){
              other.getOutputStream().write(buffer);
            }
          } else {
            run = false;
          }
        }catch(IOException e){
          e.printStackTrace();
          run = false;
        }
      }
      from.dispose();
    }
  }
}