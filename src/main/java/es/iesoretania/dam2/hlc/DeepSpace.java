package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DeepSpace extends Game {

    public Skin gameSkin;
    BitmapFont font;
    SpriteBatch batch;

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        gameSkin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
        setScreen(new MenuScreen(this));
    }
    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
