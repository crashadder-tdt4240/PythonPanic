package com.tdt4240.game.net;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MessageSocket implements IMessageSocket, Runnable{
  private int nextId = 1;
  private boolean disposed = false;
  private Subject<INetData> messageSubject = PublishSubject.create();
  
  private INetSocket socket;
  
  public MessageSocket(INetSocket socket){
    this.socket = socket;
    Thread t = new Thread(this);
    t.start();
  }

  public void run(){
    while(socket.isConnected()){
      try{
        byte[] buffer = new byte[2048];
        int size = socket.getInputStream().read(buffer);
        if(size == -1){
          throw new IOException("Too much data");
        }
        // construct a data object, notify listeners

      } catch(IOException err) {
        System.err.println(err);
        socket.dispose();
      }
    }
  }


  @Override
  public void sendMessage(INetData message) {
    nextId++;
    if(disposed) { throw new RuntimeException("Socket is disposed"); }
    try{
      socket.getOutputStream().write(message.getData());
    } catch (IOException e) {
      System.err.println(e);
      dispose();
    }
  }

  @Override
  public Observable<INetData> getMessages() {
    return messageSubject;
  }

  private void dispose(){
    socket.dispose();
    this.disposed = true;
    messageSubject.onComplete();
  }
}