package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HudEnemigo extends Actor {
    Stage stage;
    DeepSpace game;
    Texture hud;
    OrthographicCamera camera;
    TextureRegion hudvida0, hudvida1, hudvida2, hudvida3, hudvida4, hudvida5, hudvida6, hudvida7, hudvida8, hudvida9,
            hudvida10;
    int vida;

    public HudEnemigo(Stage stage, DeepSpace game, OrthographicCamera camera){
        this.stage = stage;
        this.game = game;
        this.camera = camera;

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

        setSize(hudvida10.getRegionWidth(), hudvida10.getRegionHeight());
        setPosition( camera.position.x + 50, camera.position.y + 190);
        vida = 10;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(hudvida10, getX(), getY() , getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(50 * delta, 0);

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
    public void quitarVida(){
        vida--;
    }
}
