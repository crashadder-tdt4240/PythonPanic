package com.tdt4240.game.ecs.systems;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tdt4240.game.ecs.components.Box2dComponent;

public class ContactSystem extends IteratingSystem implements ContactListener{
  
  ComponentMapper<Box2dComponent> box2dMapper;
  HashMap<Body, Integer> bodyEntityMap = new HashMap<Body, Integer>(256);
  
  public ContactSystem(){
    super(Aspect.all(Box2dComponent.class));
  }
  // inserted might not be safe if body is not set for the component!
  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Box2dComponent box2dComponent = box2dMapper.get(entityId);
    bodyEntityMap.put(box2dComponent.body, entityId);
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    Box2dComponent box2dComponent = box2dMapper.get(entityId);
    bodyEntityMap.remove(box2dComponent.body);
  }
  
  @Override
  protected void process(int entityId) {
    // do nothing
  }

  // we never want to process the system
  @Override
  public boolean isEnabled() {
    return false;
  }

  private void updateContact(int entity1, int entity2, boolean contact){
    if(entity1 > 0){
      Box2dComponent box2dComponent1 = box2dMapper.get(entity1);
      box2dComponent1.contact = true;
      box2dComponent1.contactWith = entity2;
    }
  }
  // move common code to updateContact
  @Override
  public void beginContact(Contact contact) {
    Body body1 = contact.getFixtureA().getBody();
    Body body2 = contact.getFixtureB().getBody();
    int entity1 = -1;
    int entity2 = -1;
    if (bodyEntityMap.containsKey(body1) ){
      entity1 = bodyEntityMap.get(body1);
    }
    if (bodyEntityMap.containsKey(body2) ){
      entity2 = bodyEntityMap.get(body2);
    }
    updateContact(entity1, entity2, true);
    updateContact(entity2, entity1, true);
  }

  @Override
  public void endContact(Contact contact) {
    Body body1 = contact.getFixtureA().getBody();
    Body body2 = contact.getFixtureB().getBody();
    int entity1 = -1;
    int entity2 = -1;
    if (bodyEntityMap.containsKey(body1) ){
      entity1 = bodyEntityMap.get(body1);
    }
    if (bodyEntityMap.containsKey(body2) ){
      entity2 = bodyEntityMap.get(body2);
    }
    updateContact(entity1, entity2, false);
    updateContact(entity2, entity1, false);

  }
  
  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {
  }
}