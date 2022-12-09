package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class PowerUp extends Actor {
    Animation<TextureRegion> movimiento;
    TextureRegion[] vidaGira;
    TextureRegion region,region2;
    float stateTime;

    public PowerUp() {
            Texture completo = new Texture(Gdx.files.internal("vida.png"));

            vidaGira = new TextureRegion[10];
            vidaGira[0] = new TextureRegion(completo,51,66,102,88);
            vidaGira[1] = new TextureRegion(completo,186,66,100,88);
            vidaGira[2] = new TextureRegion(completo,318,66,90,88);
            vidaGira[3] = new TextureRegion(completo,452,66,75,88);
            vidaGira[4] = new TextureRegion(completo,599,66,52,88);
            vidaGira[5] = new TextureRegion(completo,82,195,40,88);
            vidaGira[6] = new TextureRegion(completo,208,195,51,88);
            vidaGira[7] = new TextureRegion(completo,324,195,76,88);
            vidaGira[8] = new TextureRegion(completo,446,195,92,88);
            vidaGira[9] = new TextureRegion(completo,573,195,98,88);

            movimiento = new Animation<>(0.15f, vidaGira);
            region = vidaGira[0];
            stateTime = 0f;

        setSize(region.getRegionWidth()/3f, region.getRegionHeight()/3f);
        setOrigin(Align.center);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        region2 = movimiento.getKeyFrame(stateTime,true);
        batch.draw(region2, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        stateTime += Gdx.graphics.getDeltaTime();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(-5,0);
    }
    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
