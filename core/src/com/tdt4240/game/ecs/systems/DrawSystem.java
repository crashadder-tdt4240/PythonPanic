package com.tdt4240.game.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.tdt4240.game.ecs.components.DrawComponent;
import com.tdt4240.game.ecs.components.PixmapComponent;
import com.tdt4240.game.ecs.components.SpriteComponent;
import com.tdt4240.game.ecs.components.TransformComponent;

public class DrawSystem extends IteratingSystem{
  
  private ComponentMapper<TransformComponent> tComponentMapper;
  private ComponentMapper<DrawComponent> dComponentMapper;
  private ComponentMapper<SpriteComponent> sComponentMapper;
  private ComponentMapper<PixmapComponent> pComponentMapper;

  public DrawSystem(){
    super(Aspect.all(TransformComponent.class, DrawComponent.class));
  }

  public void process(int entity){
    TransformComponent transformComponent = tComponentMapper.get(entity);
    DrawComponent drawComponent = dComponentMapper.get(entity);
    if(drawComponent.drawTo > 0){
      Vector3 pos = transformComponent.transform.getTranslation(new Vector3());
      //Pixmap surface = this.surface;
      
      pos.y = -pos.y;
      pos.y += Gdx.graphics.getHeight()/2;
      pos.x += Gdx.graphics.getWidth()/2;

      Pixmap surface = pComponentMapper.get(drawComponent.drawTo).pixmap;
      SpriteComponent surfaceSprite = sComponentMapper.get(drawComponent.drawTo);

      surface.setColor(drawComponent.color);
      surface.fillCircle((int)pos.x, (int)pos.y, drawComponent.radius);

      // should do this post update for better speed
      surfaceSprite.sprite.getTextureRegion().getTexture().draw(surface, 0, 0);
      
    }
    
  }
}