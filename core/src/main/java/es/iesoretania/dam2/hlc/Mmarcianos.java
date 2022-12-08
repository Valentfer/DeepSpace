package es.iesoretania.dam2.hlc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class Mmarcianos extends ScreenAdapter {
	DeepSpace game;
	TiledMap map;
	static Stage stage;
	List<Enemigo> lEnemigo = new ArrayList<>();
	List<Disparos> lDisparos = new ArrayList<>();
	List<DisparosEnemigo> lDisparosEnemigo = new ArrayList<>();
	Heroe heroe;
	Enemigo enemigo;
	Disparos disparos;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer mapRenderer;
	private final int mapWidthInPixels;
	private final int mapHeightInPixels;
	private float offsetX, offsetY;
	int[] capanormal = {0,1,2};
	int[] capaAlta = {3};
	float tiempo;
	public Mmarcianos(DeepSpace game){
		this.game = game;
			map = new TmxMapLoader().load("espaciobueno.tmx");
			mapRenderer = new OrthogonalTiledMapRenderer(map);

			Music space = Gdx.audio.newMusic(Gdx.files.internal("space-asteroids.ogg"));
			space.setVolume(0);
			space.setLooping(true);
			space.play();

		MapProperties properties = map.getProperties();
		int tileWidth = properties.get("tilewidth", Integer.class);
		int tileHeight = properties.get("tileheight", Integer.class);
		int mapWidthInTiles = properties.get("width", Integer.class);
		int mapHeightInTiles = properties.get("height", Integer.class);
		mapWidthInPixels = mapWidthInTiles * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		camera.position.x = camera.viewportWidth / 2;
		camera.position.y = mapHeightInPixels - camera.viewportHeight / 2;
		offsetX = 0;
		offsetY = -mapHeightInPixels;

			stage = new Stage();
			heroe = new Heroe(400, 100, stage, game, lDisparos);
			PowerUp powerUp = new PowerUp();

			Actor score = new Manager(heroe, game, enemigo ,disparos, stage, lEnemigo, lDisparos, lDisparosEnemigo);
			stage.addActor(heroe);
			stage.addActor(powerUp);
			stage.addActor(score);
			stage.setKeyboardFocus(heroe);

			Viewport viewport = new ScreenViewport(camera);
			stage.setViewport(viewport);
			tiempo = (float) (2 + Math.random());
		}

		@Override
		public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			tiempo -= Gdx.graphics.getDeltaTime();
			if(tiempo < 0) {
				Enemigo enemigo = new Enemigo(400,100, heroe,camera, stage, lDisparosEnemigo);
				enemigo.setVisible(true);
				stage.addActor(enemigo);
				lEnemigo.add(enemigo);
				tiempo = (float) (2 + Math.random());
			}


			if (offsetX > mapWidthInPixels - camera.viewportWidth) offsetX = mapWidthInPixels - camera.viewportWidth;
			if (offsetY < -mapHeightInPixels + camera.viewportHeight) offsetY = -mapHeightInPixels + camera.viewportHeight;
			if(heroe.getX() <= (camera.position.x - camera.viewportWidth /2) + heroe.getWidth()){
				heroe.setX((camera.position.x - camera.viewportWidth /2)+ heroe.getWidth());
			}
			if(heroe.getX()>= (camera.position.x + camera.viewportWidth / 2) - heroe.getWidth()){
				heroe.setX((camera.position.x + camera.viewportWidth / 2) - heroe.getWidth());
			}
			for (Disparos lDisparo : lDisparos) {
				if (lDisparo.getX() >= (camera.position.x + camera.viewportWidth / 2)) {
					lDisparo.remove();
				}
			}


			camera.translate(50 * Gdx.graphics.getDeltaTime(),0);
			if(camera.position.x > mapWidthInPixels-camera.viewportWidth) {
				camera.position.x = camera.viewportWidth / 2 + offsetX;
				heroe.setX((int) camera.position.x);
			}

			camera.position.y =  camera.viewportHeight / 2 + offsetY;
			camera.update();


			stage.act(Gdx.graphics.getDeltaTime());
			mapRenderer.setView(camera);
			mapRenderer.render(capanormal);

			stage.draw();
			mapRenderer.render(capaAlta);
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