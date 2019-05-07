package com.tdt4240.game.mvc.models;

import java.util.HashMap;
import java.util.concurrent.Callable;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public abstract class GdxStageModel extends MVCModel implements EventListener{

  private HashMap<String, Actor> actorNamingMap = new HashMap<>();
  private HashMap<Actor, Subject<Event>> subjectNameMap = new HashMap<>();
  

  public void bindActor(String name, Actor actor){
    actorNamingMap.put(name, actor);
    subjectNameMap.put(actor, PublishSubject.create());
    actor.addListener(this);
  }


  public Observable<Event> onEvent(String name){
    Actor actor = actorNamingMap.get(name);
    return subjectNameMap.get(actor);
  }
  
  public boolean handle(Event event){
    Actor target = event.getListenerActor();
    subjectNameMap.get(target).onNext(event);
    return true;
  }

  public abstract Stage getStage();
}