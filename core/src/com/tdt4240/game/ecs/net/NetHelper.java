package com.tdt4240.game.ecs.net;

import java.nio.ByteBuffer;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.utils.reflect.Field;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.ecs.components.Box2dComponent;
import com.tdt4240.game.ecs.components.TransformComponent;


public class NetHelper{
  
  private World world;

  

  public NetHelper(World world){
    this.world = world;
  }


  public static void writeComponent(Component component){
  }

  public static void writeField(Field field){

  }

  public static void writeVector2(ByteBuffer buffer, Vector2 vec){
    buffer.putFloat(vec.x);
    buffer.putFloat(vec.y);
  }

  public static void writeVector3(ByteBuffer buffer, Vector3 vec){
    buffer.putFloat(vec.x);
    buffer.putFloat(vec.y);
    buffer.putFloat(vec.z);
  }

  public static Vector2 readVector2(ByteBuffer buffer, Vector2 vec){
    return vec.set(buffer.getFloat(), buffer.getFloat());
  }

  public static Vector3 readVector3(ByteBuffer buffer, Vector3 vec){
    return vec.set(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
  }

  public static void writeTransformComponent(ByteBuffer buffer, TransformComponent component){
    float values[] = component.transform.getValues();
    buffer.putInt(values.length);
    for(float value : values){
      buffer.putFloat(value);
    }
  }

  public static void readTransformComponent(ByteBuffer buffer, TransformComponent component){
    int length = buffer.getInt();
    float values[] = new float[length];
    for(int i = 0; i < length; i++){
      values[i] = buffer.getFloat();
    }
    component.transform.set(values);
  }


  
  public static void writeBox2dComponent(ByteBuffer buffer, Box2dComponent component){
    writeVector2(buffer, component.body.getPosition());
    buffer.putFloat(component.body.getAngle());
    buffer.putFloat(component.body.getAngularVelocity());
  }

  public static Box2dComponent readBox2dComponent(ByteBuffer buffer, Box2dComponent component){
    Vector2 pos = readVector2(buffer, new Vector2(0, 0));
    float angle = buffer.getFloat();
    float angularVel = buffer.getFloat();
    component.interpolate = true;
    component.ticksToInterpolate = 64;
    component.intAngle = angle;
    component.intVector = pos;
    component.intOmega = angularVel;
    return component;
  }



}