package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;


import static com.badlogic.gdx.Input.Keys.SPACE;

public class Heroe extends Actor {
    Stage stage;
    Sound shot;
    DeepSpace game;
    public int vida = 3;
    int score = 0;
    Array<Disparos> lDisparos;
    enum VerticalMovement { UP, NONE, DOWN }
    enum HorizontalMovement { LEFT, NONE, RIGHT }
    HorizontalMovement horizontalMovement;
    VerticalMovement verticalMovement;
    private static Animation<TextureRegion> explosion;
    float stateTime;
    boolean ganado = false;
    TextureRegion regionActual, naveReposo, naveArriba, naveAbajo;
    Texture completoExplosion, completoNave;
    public Heroe(float x, float y, Stage stage, DeepSpace game, Array<Disparos> lDisparos) {
        this.stage = stage;
        this.game = game;
        this.lDisparos = lDisparos;
         completoNave = new Texture(Gdx.files.internal("player1.png"));
         naveReposo = new TextureRegion(completoNave,0,3,26,12);
         naveArriba = new TextureRegion(completoNave,1,20,25,20);
         naveAbajo = new TextureRegion(completoNave,26,0,26,20);
        regionActual = naveReposo;
        horizontalMovement = HorizontalMovement.NONE;
        verticalMovement = VerticalMovement.NONE;
        setSize(regionActual.getRegionWidth(), regionActual.getRegionHeight() );
        setPosition(x - getWidth() / 2, y - getHeight() / 2);


        completoExplosion = new Texture(Gdx.files.internal("explosionHeroe.png"));
        TextureRegion[] explosiones = new TextureRegion[5];
        explosiones[0] = new TextureRegion(completoExplosion, 2, 5, 25, 26);
        explosiones[1] = new TextureRegion(completoExplosion, 35, 5, 25, 26);
        explosiones[2] = new TextureRegion(completoExplosion, 2, 36, 25, 26);
        explosiones[3] = new TextureRegion(completoExplosion, 35, 36, 25, 26);
        explosiones[4] = new TextureRegion(completoExplosion, 2, 68, 25, 26);
        explosion = new Animation<>(0.5f, explosiones);

        stateTime = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(regionActual, getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        processKeyboard();

        if(vida == 0){
            stateTime += Gdx.graphics.getDeltaTime();
            regionActual = explosion.getKeyFrame(stateTime,false);
            if(explosion.isAnimationFinished(stateTime)){
                this.setVisible(false);
                game.setScreen(new TheEndScreen(game, ganado, score));
            }
        }


        if (verticalMovement == VerticalMovement.UP) this.moveBy(0, 200 * delta);
        if (verticalMovement == VerticalMovement.DOWN) this.moveBy(0, -200 * delta);
        if (horizontalMovement == HorizontalMovement.LEFT) this.moveBy(-200 * delta, 0);
        if (horizontalMovement == HorizontalMovement.RIGHT) this.moveBy(200 * delta, 0);
        if (horizontalMovement == HorizontalMovement.NONE) this.moveBy(50 * delta, 0);
        if(Gdx.input.isKeyPressed(SPACE)){
            shot = Gdx.audio.newSound(Gdx.files.internal("shot 1.wav"));
            shot.play();
            Disparos disparos = new Disparos(getX(), getY());
            stage.addActor(disparos);
            lDisparos.add(disparos);
        }
        if(getY() <= 0){
            setY(0);
        }
        if(getY() >= 480 - getHeight()) setY(480 - getHeight());
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveArriba;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveArriba;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveArriba;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveReposo;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveReposo;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveReposo;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveAbajo;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveAbajo;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveAbajo;
    }
    public void processKeyboard() {
        verticalMovement = VerticalMovement.NONE;
        horizontalMovement = HorizontalMovement.NONE;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            verticalMovement = VerticalMovement.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            verticalMovement = VerticalMovement.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalMovement = HorizontalMovement.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalMovement = HorizontalMovement.RIGHT;
    }
    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void setpuntosNeg(){
        score -=100;
    }
    public void setpuntosPos(){
        score +=100;
    }

}

