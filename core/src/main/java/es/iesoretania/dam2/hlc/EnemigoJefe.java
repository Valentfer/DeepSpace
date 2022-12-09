package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
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

import java.util.List;


public class EnemigoJefe extends Actor {
    Stage stage;
    List<DisparosEnemigo> ldisparosEnemigos;
    Texture completoEneJefe;
    Texture hud;
    TextureRegion enemigoJefe;
    TextureRegion hudvida0, hudvida1, hudvida2, hudvida3, hudvida4, hudvida5, hudvida6, hudvida7, hudvida8, hudvida9,
            hudvida10;
    private Action currentAction;
    float tiempo;
    int vida = 11;
    public EnemigoJefe( float x, float y, Stage stage, List<DisparosEnemigo> ldisparosEnemigos){
        this.stage = stage;
         this.ldisparosEnemigos = ldisparosEnemigos;
        completoEneJefe = new Texture(Gdx.files.internal("AlienJefe.png"));
        enemigoJefe = new TextureRegion(completoEneJefe,1,1,716,546);
        setSize(enemigoJefe.getRegionWidth() / 5, enemigoJefe.getRegionHeight() / 5);
        setVisible(true);
        setPosition(x, y);
        //    animaciones(x,y);

        hud = new Texture(Gdx.files.internal("hud.png"));
        hudvida0 = new TextureRegion(hud,7,455, 254,45);
        hudvida1 = new TextureRegion(hud,7,408, 254,45);
        hudvida2 = new TextureRegion(hud,7,362, 254,45);
        hudvida3 = new TextureRegion(hud,7,317, 254,45);
        hudvida4 = new TextureRegion(hud,7,272, 254,45);
        hudvida5 = new TextureRegion(hud,7,227, 254,45);
        hudvida6 = new TextureRegion(hud,7,181, 254,45);
        hudvida7 = new TextureRegion(hud,7,137, 254,45);
        hudvida8 = new TextureRegion(hud,7,89, 254,45);
        hudvida9 = new TextureRegion(hud,7,44, 254,45);
        hudvida10 = new TextureRegion(hud,7,4, 254,45);

        setSize(hudvida0.getRegionWidth(), hudvida0.getRegionHeight());
        setPosition(x, y + 100);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemigoJefe, getX(), getY(), getWidth(), getHeight());
        batch.draw(hudvida10, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(50 * delta, 0);

        tiempo -= Gdx.graphics.getDeltaTime();
        if(tiempo < 0) {
            DisparosEnemigo disparosEnemigo = new DisparosEnemigo(getX(), getY());
            disparosEnemigo.setVisible(true);
            stage.addActor(disparosEnemigo);
            ldisparosEnemigos.add(disparosEnemigo);

            tiempo = (float) (2 + Math.random());
        }

        switch (vida){
            case 1:
                hudvida10 = hudvida1;
                break;
            case 2:
                hudvida10 = hudvida2;
                break;
            case 3:
                hudvida10 = hudvida3;
                break;
            case 4:
                hudvida10 = hudvida4;
                break;
            case 5:
                hudvida10 = hudvida5;
                break;
            case 6:
                hudvida10 = hudvida6;
                break;
            case 7:
                hudvida10 = hudvida7;
                break;
            case 8:
                hudvida10 = hudvida8;
                break;
            case 9:
                hudvida10 = hudvida9;
                break;
            case 0:
                hudvida10 = hudvida0;
                break;

        }
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
