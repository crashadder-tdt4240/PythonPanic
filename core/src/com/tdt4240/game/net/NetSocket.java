package com.tdt4240.game.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.NetJavaSocketImpl;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

public class NetSocket implements INetSocket{
  
  private String boundAddress = null;
  private int boundPort;
  private Socket socket = null;

  private CompletableSubject connectionSubject = CompletableSubject.create();


  private boolean disposed = false;
  private boolean bound = false;

  public NetSocket(Socket socket){
    this.socket = socket;
    bound = true;
    if(socket.isConnected()){
      boundAddress = socket.getRemoteAddress();
      connectionSubject.onComplete();
    } else {
      _waitForConnection();
    }
  }

  public NetSocket(){}
  
  public void bind(String address, int port){
    boundAddress = address;
    boundPort = port;
    bound = true;
  }

  private void _waitForConnection(){
    Thread t = new Thread(){
      public void run(){
        int timeout = 60;
        while(!socket.isConnected()){
          try{
            sleep(100);
            timeout--;
            if(timeout <= 0){
              interrupt();
            }
          } catch(InterruptedException interrupt){
            connectionSubject.onError(interrupt);
          }
        }
        connectionSubject.onComplete();
      }
    };
    t.start();
  }

  public Completable connect(){
    if(socket != null){
      throw new RuntimeException("Socket is already connecting");
    }
    if(!bound){
      throw new RuntimeException("Socket not bound before connecting");
    }
    SocketHints hints = new SocketHints();
    socket = new NetJavaSocketImpl(Protocol.TCP, boundAddress, boundPort, hints);
    _waitForConnection();

    return connectionSubject;
  }

  @Override
  public Completable onConnected() {
    return connectionSubject;
  }

  @Override
  public InputStream getInputStream() {
    return socket.getInputStream();
  }

  @Override
  public OutputStream getOutputStream() {
    return socket.getOutputStream();
  }

  @Override
  public boolean isConnected() {
    if(socket == null){
      return false;
    }
    return socket.isConnected();
  }

  @Override
  public String getRemoteAddress() {
    return boundAddress;
  }

  @Override
  public void dispose() {
    if(!disposed){
      socket.dispose();
      disposed = true;
      socket = null;
    }
  }



}