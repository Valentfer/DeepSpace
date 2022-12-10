package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;


public class Enemigo extends Actor {
    Stage stage;
    Texture enemImag = new Texture(Gdx.files.internal("enemy3.png"));
    TextureRegion enemigoReposo = new TextureRegion(enemImag,4,10,23,10);
    Texture explosionCompleto = new Texture(Gdx.files.internal("explosion.png"));
    private static final TextureRegion[] explosiones = new TextureRegion[16];
    public static Animation<TextureRegion> explosion;
    TextureRegion regionActual;
    private float stateTime;
    boolean muerto = false;
    private Action currentAction;
    float tiempo;
    Array<DisparosEnemigo> ldisparosEnemigos;

    public Enemigo (float x, float y, Heroe heroe, OrthographicCamera camera, Stage stage, Array<DisparosEnemigo> ldisparosEnemigos) {
        this.stage = stage;
        this.ldisparosEnemigos = ldisparosEnemigos;
        explosiones[0] = new TextureRegion(explosionCompleto, 13, 12, 3, 3);
        explosiones[1] = new TextureRegion(explosionCompleto, 41, 10, 12, 14);
        explosiones[2] = new TextureRegion(explosionCompleto, 70, 8, 16, 15);
        explosiones[3] = new TextureRegion(explosionCompleto, 102, 6, 20, 19);
        explosiones[4] = new TextureRegion(explosionCompleto, 5, 36, 22, 22);
        explosiones[5] = new TextureRegion(explosionCompleto, 37, 36, 25, 24);
        explosiones[6] = new TextureRegion(explosionCompleto, 68, 33, 23, 27);
        explosiones[7] = new TextureRegion(explosionCompleto, 99, 33, 23, 27);
        explosiones[8] = new TextureRegion(explosionCompleto, 1, 65, 23, 27);
        explosiones[9] = new TextureRegion(explosionCompleto, 33, 65, 23, 27);
        explosiones[10] = new TextureRegion(explosionCompleto, 65, 65, 23, 27);
        explosiones[11] = new TextureRegion(explosionCompleto, 95, 65, 23, 27);
        explosiones[12] = new TextureRegion(explosionCompleto, 1, 95, 23, 27);
        explosiones[13] = new TextureRegion(explosionCompleto, 33, 95, 23, 27);
        explosiones[14] = new TextureRegion(explosionCompleto, 65, 95, 23, 27);
        explosiones[15] = new TextureRegion(explosionCompleto, 95, 95, 23, 27);
        explosion = new Animation<>(0.05f, explosiones);

        regionActual = enemigoReposo;
        setSize(regionActual.getRegionWidth(), regionActual.getRegionHeight());
        setPosition(MathUtils.random(heroe.getX() + camera.viewportWidth, (camera.position.x + camera.viewportWidth) - regionActual.getRegionWidth()),
                MathUtils.random(0, camera.viewportHeight));
        animaciones(x, y);
        stateTime = 0;
        tiempo = (float) (2 + Math.random());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(regionActual, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        tiempo -= Gdx.graphics.getDeltaTime();
        if(tiempo < 0 && this.isVisible()) {
            DisparosEnemigo disparosEnemigo = new DisparosEnemigo(getX(), getY());
            disparosEnemigo.setVisible(true);
            stage.addActor(disparosEnemigo);
            ldisparosEnemigos.add(disparosEnemigo);

            tiempo = (float) (2 + Math.random());
        }

        if(muerto){
            stateTime += Gdx.graphics.getDeltaTime();
            regionActual =  explosion.getKeyFrame(stateTime,false);
            if(explosion.isAnimationFinished(stateTime)){
                muerto = false;
                this.setVisible(false);
                remove();
            }
        }

        if (getY() < 0) setY(0);
        if (getY() >= 479 - getHeight()) setY(479 - getHeight());
        if(getX() < 0 + getWidth()){
          remove();
        }

    }

    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void animaciones(float x, float y){
        removeAction(currentAction);
        MoveToAction movimiento1 = Actions.moveTo(MathUtils.random(x - 400), MathUtils.random(y + 400), 7f, Interpolation.sine);
        MoveToAction movimiento2 = Actions.moveTo( MathUtils.random(x + 400), MathUtils.random(y - 400), 7f, Interpolation.sine);
        MoveToAction movimiento3 = Actions.moveTo( MathUtils.random(x - 700), MathUtils.random(y + 327), 7f, Interpolation.sineOut);
        MoveToAction movimiento4 = Actions.moveTo(MathUtils.random(x - 800), MathUtils.random(y + 434), 7f, Interpolation.sineIn);

        SequenceAction sequence = Actions.sequence(movimiento1, movimiento2, movimiento3, movimiento4);
        currentAction = Actions.repeat(RepeatAction.FOREVER, sequence);
        addAction(currentAction);
    }

    public void explotar(){
        muerto = true;
        stateTime = 0;
    }
}
