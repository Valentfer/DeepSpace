package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DisparosEnemigo extends Actor {
    Texture disparo;
    TextureRegion laser;
    public DisparosEnemigo(float x, float y) {
        disparo = new Texture("beams.png");
        laser = new TextureRegion(disparo, 88,118,12,20);
        setPosition(x + 20, y - 20);
        setRotation(90);
        setSize(laser.getRegionWidth(), laser.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(laser, getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(-250 * Gdx.graphics.getDeltaTime(),0);
        if(getX() <= -1000){
            remove();
        }
    }
    Rectangle getShape() {
        return new Rectangle(getX(), getY(),getWidth(), getHeight());
    }
}
