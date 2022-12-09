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
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class EnemigoJefe extends Actor {
    Stage stage;
    List<DisparosEnemigo> ldisparosEnemigo;
    OrthographicCamera camera;
    Texture completoEneJefe;
    TextureRegion enemigoJefe;
    private Action currentAction;
    public EnemigoJefe( float x, float y, List<DisparosEnemigo> ldisparosEnemigo, OrthographicCamera camera) {
        this.stage = stage;
        this.camera = camera;
        this.ldisparosEnemigo = ldisparosEnemigo;
        completoEneJefe = new Texture(Gdx.files.internal("AlienJefe.png"));
        enemigoJefe = new TextureRegion(completoEneJefe,1,1,716,546);
        setSize(enemigoJefe.getRegionWidth(), enemigoJefe.getRegionHeight());
        setVisible(true);
        setPosition(camera.position.x + camera.viewportWidth / 2, camera.position.y);
        animaciones(x,y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
            batch.draw(enemigoJefe, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    Rectangle getShape() {
        return new Rectangle(getX(), getY(),getWidth(), getHeight());
    }
    private void animaciones(float x, float y) {
        removeAction(currentAction);
        MoveToAction movimiento1 = Actions.moveTo(MathUtils.random(x - 400), MathUtils.random(y + 400), 7f, Interpolation.sine);

        SequenceAction sequence = Actions.sequence(movimiento1);
        currentAction = Actions.repeat(RepeatAction.FOREVER, sequence);
        addAction(currentAction);
    }
}
