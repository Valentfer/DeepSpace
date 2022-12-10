package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class TheEndScreen extends ScreenAdapter {
        DeepSpace game;
        boolean ganado;

    public TheEndScreen(DeepSpace game, boolean ganado) {

        this.game = game;
        this.ganado = ganado;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        float x;
        int height = Gdx.graphics.getHeight();

        if(ganado){
            x = Gdx.graphics.getWidth() * .25f;
            game.font.draw(game.batch, "¡Has ganado!", x, height * .75f);
        }else{
            x = Gdx.graphics.getWidth() * .25f;
            game.font.draw(game.batch, "¡Game over!", x, height * .75f);
        }
        //game.font.draw(game.batch, "¡Puntos: " + Manager.sco, x, height * .75f);
        game.font.draw(game.batch, "Presiona Intro para volver a empezar.", x, height * .25f);
        game.batch.end();

    }
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
