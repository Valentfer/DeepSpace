package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;


public class EnemigoJefe extends Actor {
    Stage stage;
    DeepSpace game;
    Array<DisparosEnemigo> ldisparosEnemigos;
    Texture completoEneJefe;
    OrthographicCamera camera;
    TextureRegion enemigoJefe;
    private Action currentAction;
    float tiempo;
    int vida;

    public EnemigoJefe( float x, float y, Stage stage, Array<DisparosEnemigo> ldisparosEnemigos, DeepSpace game, OrthographicCamera camera){
        this.stage = stage;
        this.game = game;
        this.camera = camera;
        this.ldisparosEnemigos = ldisparosEnemigos;
        completoEneJefe = new Texture(Gdx.files.internal("AlienJefe.png"));
        enemigoJefe = new TextureRegion(completoEneJefe,1,1,716,546);
        setSize((float) enemigoJefe.getRegionWidth() / 5, (float) enemigoJefe.getRegionHeight() / 5);
        setPosition(camera.position.x + camera.viewportWidth /2, camera.position.y - camera.viewportHeight /2);
        animacion(x, y);

        vida = 10;
        tiempo = (float) (2 + Math.random());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemigoJefe, getX(), getY(),  getWidth(),  getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(50 * delta, 0);

        tiempo += Gdx.graphics.getDeltaTime();
        if(tiempo < 0 && isVisible()) {
            DisparosEnemigo disparosEnemigo = new DisparosEnemigo(getX(), getY());
            disparosEnemigo.setVisible(true);
            stage.addActor(disparosEnemigo);
            ldisparosEnemigos.add(disparosEnemigo);

            tiempo = (float) (2 + Math.random());
        }

    }

    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    private void animacion(float x, float y) {
        removeAction(currentAction);
        MoveToAction mov1 = Actions.moveTo(MathUtils.random(x - 400), MathUtils.random(y - 400), 7, Interpolation.linear);
        MoveToAction mov2 = Actions.moveTo(MathUtils.random(x + 400), MathUtils.random(y + 400), 7, Interpolation.linear);
        SequenceAction seq = Actions.sequence(mov1, mov2);
        currentAction = Actions.repeat(RepeatAction.FOREVER, seq);
        addAction(currentAction);
    }
}
