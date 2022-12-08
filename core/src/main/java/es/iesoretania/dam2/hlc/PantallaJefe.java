package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public class PantallaJefe extends ScreenAdapter {
    DeepSpace game;
    TiledMap map;
    Stage stage;
    Heroe heroe;
    EnemigoJefe enemigoJefe;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;
    private final float offsetX;
    private final float offsetY;
    List<Disparos> lDisparos;

    public PantallaJefe(DeepSpace game) {
        this.game = game;
        map = new TmxMapLoader().load("PantallaJefe.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        MapProperties properties = map.getProperties();
        int tileWidth = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);
        int mapWidthInTiles = properties.get("width", Integer.class);
        int mapHeightInTiles = properties.get("height", Integer.class);
        int mapWidthInPixels = mapWidthInTiles * tileWidth;
        int mapHeightInPixels = mapHeightInTiles * tileHeight;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        camera.position.x = camera.viewportWidth / 2;
        camera.position.y = mapHeightInPixels - camera.viewportHeight / 2;
        offsetX = 0;
        offsetY = -mapHeightInPixels;

        stage = new Stage();
        heroe = new Heroe(50, 100, stage, game , lDisparos);
        enemigoJefe = new EnemigoJefe();
        stage.addActor(heroe);
        stage.addActor(enemigoJefe);
        Viewport viewport = new ScreenViewport(camera);
        stage.setViewport(viewport);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        mapRenderer.setView(camera);
        mapRenderer.render();
        stage.draw();
    }
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
        camera.position.x = camera.viewportWidth / 2 + offsetX;
        camera.position.y = camera.viewportHeight / 2 + offsetY;
        camera.update();
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
        map.dispose();
        stage.dispose();
    }
}
