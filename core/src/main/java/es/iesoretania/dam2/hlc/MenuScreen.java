package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter {
    DeepSpace game;
    Stage stage;
    public MenuScreen(DeepSpace game) {


        this.game = game;
        stage = new Stage(new ScreenViewport());

        Label titulo = new Label("Deep Space", game.gameSkin, "default");
        titulo.setAlignment(Align.center);
        titulo.setY(Gdx.graphics.getHeight() / 1.5f);
        titulo.setFontScale(5f);
       // titulo.setStyle(S);
        titulo.setWidth(Gdx.graphics.getWidth());
        titulo.setColor(Color.BLUE);
        stage.addActor(titulo);

        TextButton empezar = new TextButton("EMPEZAR", game.gameSkin);
        empezar.setWidth(Gdx.graphics.getWidth() / 2f);
        empezar.setPosition(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() /2f);
        empezar.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {game.setScreen( new Mmarcianos(game));}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(empezar);

        TextButton exitButton = new TextButton("SALIR", game.gameSkin);
        exitButton.setWidth(Gdx.graphics.getWidth() / 2f);
        exitButton.setPosition(
                Gdx.graphics.getWidth() / 4f,
                Gdx.graphics.getHeight() / 3f);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(exitButton);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
