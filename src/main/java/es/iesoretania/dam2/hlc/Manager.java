package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Actor {
    Stage stage;
    DeepSpace game;
    Sound explosion;
    private BitmapFont font;
    private final Heroe heroe;
   Enemigo enemigo;
    Disparos disparos;
    List<Disparos> lDisparos;
    List<Enemigo> lEnemigo;
    List<DisparosEnemigo> ldisparosEnemigos;
    List<PowerUp> lPowerUp = new ArrayList<>();

    public int score;
    public Manager(Heroe heroe, DeepSpace game, Enemigo enemigo, Disparos disparos, Stage stage, List<Enemigo> lEnemigo, List<Disparos> lDisparos, List<DisparosEnemigo> ldisparosEnemigos) {
        this.heroe = heroe;
        this.enemigo = enemigo;
        this.disparos = disparos;
        this.lDisparos= lDisparos;
        this.lEnemigo = lEnemigo;
        this.stage = stage;
        this.game = game;
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
        if(score == 5000){
            heroe.setX(100 * Gdx.graphics.getDeltaTime());
            game.setScreen(new PantallaJefe(game));
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
                value.muerto = true;
                value.setVisible(false);
                heroe.tocado = true;
                score -= 100;
            }
        }

        for (DisparosEnemigo ldisparosEnemigo : ldisparosEnemigos) {
            if (heroe.isVisible() && ldisparosEnemigo.isVisible() && Intersector.overlaps(heroe.getShape(), ldisparosEnemigo.getShape())) {
                ldisparosEnemigo.setVisible(false);
                heroe.tocado = true;
                score -= 100;
            }
        }

        for (Enemigo value : lEnemigo) {
            for (Disparos lDisparo : lDisparos) {
                if (!value.muerto && value.isVisible() && lDisparo.isVisible() && Intersector.overlaps(lDisparo.getShape(), value.getShape())) {
                    value.muerto = true;
                    value.setVisible(false);
                    lDisparo.remove();
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
        for (int i = 0; i < lDisparos.size(); i++) {
            for (int j = 0; j < ldisparosEnemigos.size(); j++) {
                if(ldisparosEnemigos.get(j).isVisible() && lDisparos.get(i).isVisible() &&
                        Intersector.overlaps(lDisparos.get(i).getShape(), ldisparosEnemigos.get(j).getShape())){
                    lDisparos.get(i).setVisible(false);
                    ldisparosEnemigos.get(j).setVisible(false);
                    ldisparosEnemigos.get(j).remove();
                    lDisparos.get(i).remove();
                }

            }

        }
    }
}
