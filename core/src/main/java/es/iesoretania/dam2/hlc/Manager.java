package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Manager extends Actor {
    Stage stage;
    DeepSpace game;
    Sound explosion;
    private BitmapFont font;
    private final Heroe heroe;
    Enemigo enemigo;
    EnemigoJefe enemigoJefe;
    Disparos disparos;
    Array<Disparos> lDisparos;
    Array<Enemigo> lEnemigo;
    Array<DisparosEnemigo> ldisparosEnemigos;
    OrthographicCamera camera;
    Array<PowerUp> lPowerUp = new Array<>();
    public int score;
    public Manager(Heroe heroe, DeepSpace game, Enemigo enemigo, Disparos disparos, Stage stage, Array<Enemigo> lEnemigo,
                   Array<Disparos> lDisparos, Array<DisparosEnemigo> ldisparosEnemigos, OrthographicCamera camera, EnemigoJefe enemigoJefe) {
        this.heroe = heroe;
        this.enemigo = enemigo;
        this.enemigoJefe = enemigoJefe;
        this.disparos = disparos;
        this.lDisparos= lDisparos;
        this.lEnemigo = lEnemigo;
        this.stage = stage;
        this.game = game;
        this.camera = camera;
        this.ldisparosEnemigos = ldisparosEnemigos;
        if (font == null) {
            font = new BitmapFont();
        }
        explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        score = 0;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Puntos: " + score + " vida: " + heroe.vida, heroe.getX(),  460);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(score == 1000){
            enemigoJefe = new EnemigoJefe(camera.position.x , camera.position.y, stage, ldisparosEnemigos, game,camera);
            enemigoJefe.setVisible(true);
            stage.addActor(enemigoJefe);

        }

        for (PowerUp up : lPowerUp) {
            if (up.isVisible() && Intersector.overlaps(heroe.getShape(), up.getShape())) {
                up.setVisible(false);
                heroe.vida++;
                score += 100;
            }
        }

        for (Enemigo value : lEnemigo) {
            if (!value.muerto && value.isVisible() && Intersector.overlaps(heroe.getShape(), value.getShape())) {
                explosion.play();
                value.explotar();
                heroe.setTocado();
                score -= 100;
            }
        }

        for (DisparosEnemigo ldisparosEnemigo : ldisparosEnemigos) {
            if (heroe.isVisible() && ldisparosEnemigo.isVisible() && Intersector.overlaps(heroe.getShape(), ldisparosEnemigo.getShape())) {
                ldisparosEnemigo.setVisible(false);
                heroe.setTocado();
                score -= 100;
            }
        }

        for (Enemigo value : lEnemigo) {
            for (Disparos lDisparo : lDisparos) {
                if (!value.muerto && value.isVisible() && lDisparo.isVisible() && Intersector.overlaps(lDisparo.getShape(), value.getShape())) {
                    value.explotar();
                    lDisparo.setVisible(false);
                    explosion.play();
                    score += 100;
                    if (heroe.vida < 3) {
                        if (MathUtils.randomBoolean()) {
                            PowerUp powerUp = new PowerUp();
                            powerUp.setPosition(value.getX(), value.getY());
                            powerUp.setVisible(true);
                            stage.addActor(powerUp);
                            lPowerUp.add(powerUp);
                        }
                    }
                }

            }

        }
        for (Disparos lDisparo : lDisparos) {
            for (DisparosEnemigo ldisparosEnemigo : ldisparosEnemigos) {
                if (ldisparosEnemigo.isVisible() && lDisparo.isVisible() &&
                        Intersector.overlaps(lDisparo.getShape(), ldisparosEnemigo.getShape())) {
                    lDisparo.setVisible(false);
                    ldisparosEnemigo.setVisible(false);
                    ldisparosEnemigo.remove();
                    lDisparo.remove();
                }

            }

        }

        for (Disparos lDisparo : lDisparos) {
            if (lDisparo.isVisible()&& enemigoJefe.isVisible() && Intersector.overlaps(lDisparo.getShape(), enemigoJefe.getShape())) {
                enemigoJefe.vida--;
                score += 100;
            }
        }

        if(enemigoJefe.isVisible() && Intersector.overlaps(enemigoJefe.getShape(), heroe.getShape())) {
            heroe.setTocado();
            score -= 100;
        }
    }
}
