package com.tdt4240.game.net.message;

import java.io.IOException;
import java.util.HashMap;

import com.tdt4240.game.net.INetSocket;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;


public class MessageSocket implements IMessageSocket, Runnable{
  //private int nextId = 1;
  private boolean disposed = false;
  private Subject<INetData> messageSubject = ReplaySubject.create(4);
  private HashMap<Integer, Subject<INetData>> channelSubjects = new HashMap<>();
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
          throw new IOException("End of stream");
        }
        // construct a data object, notify listeners
        NetMessage message = new NetMessage(new NetData(buffer));
        int channel = message.getChannel();
        if(channel > 0){
          if(!channelSubjects.containsKey(channel)){
            channelSubjects.put(channel, ReplaySubject.create(4));
          }      
          channelSubjects.get(channel).onNext(message);
        }
        messageSubject.onNext(message);

      } catch(IOException err) {
        System.err.println(err);
        socket.dispose();
      }
    }
  }


  @Override
  public void sendMessage(INetData message) {
    if(!socket.isConnected()){return;}
    if(disposed) { throw new RuntimeException("Socket is disposed"); }
    try{
      byte[] data = message.getData();
      System.out.printf("Sending message of %d bytes\n", data.length);
      socket.getOutputStream().write(data);
    } catch (IOException e) {
      System.err.println(e);
      dispose();
    }
  }

  @Override
  public Observable<INetData> getMessages() {
    return messageSubject;
  }

  @Override
  public Observable<INetData> getMessages(int channel) {
    if(!channelSubjects.containsKey(channel)){
      channelSubjects.put(channel, ReplaySubject.create(4));
    }
    return channelSubjects.get(channel);
  }


  private void dispose(){
    socket.dispose();
    this.disposed = true;
    messageSubject.onComplete();
  }
}