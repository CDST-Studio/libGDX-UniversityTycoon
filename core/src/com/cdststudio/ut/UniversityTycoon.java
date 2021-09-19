package com.cdststudio.ut;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.cdststudio.ut.View.InputProcessor.ViewportInputProcessor;
import com.cdststudio.ut.Model.NPC;
import com.cdststudio.ut.Model.Tile;

import org.lwjgl.Sys;

import java.util.Iterator;

public class UniversityTycoon extends ApplicationAdapter{
	private SpriteBatch mainBatch;
	private OrthographicCamera camera;
	private ViewportInputProcessor vip;
	private ExtendViewport viewport;

	// 캐릭터 애니매이션 프레임 간격
	private float elapsedTime = 0F;
	private NPC npc;

	// 오브젝트
	private Texture it;
	private Texture art;

	// 타일
	private Tile tile;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;

	float viewportWidth, viewportHeight;
	int Size_Width = 256;
	int Size_Height = 256;

	@Override
	public void create () {
		// 초기화 단계

		// Viewport 크기 설정, Gdx.app.getGraphics().getWidth(); // 디바이스의 X 크기
		viewportWidth = Gdx.app.getGraphics().getWidth();
		viewportHeight = Gdx.app.getGraphics().getHeight();

		mainBatch = new SpriteBatch(); // ima를 Sprite 시키기 위한 객체
		// sprite = new Sprite(img, (int)0, (int)0, (int)Size_Width, (int)Size_Height); // 실제 이미지 Sprite할 크기 설정
		// sprite.setPosition(viewportWidth/2-Size_Width/2, viewportHeight/2-Size_Height/2); // 이미지 위치 값 중심에 맞추기

		// 오브젝트
		it = new Texture("Buildings/IT.png");
		art = new Texture("Buildings/Art.png");

		// 타일
		tile = new Tile(400, 400, 400, 500, new Texture("tile1.png"));
		tiledMap = new TmxMapLoader().load("tiledMap.tmx");
		// Layer count = 1, Object count = 0
		System.out.println(tiledMap.getLayers().get(0).getObjects().getCount());
		// Tilesets size = 1, Tileset size = 1
		System.out.println(tiledMap.getLayers().get(0));
		for (Iterator<String> iter = tiledMap.getLayers().get(0).getProperties().getKeys(); iter.hasNext(); ) {
			String key = iter.next();
			System.out.println(key);
		}

		renderer = new OrthogonalTiledMapRenderer(tiledMap);

		// NPC 설정
		npc = new NPC("w1", tile, (tile.getStartX() + tile.getEndX()) >> 1, (tile.getStartY() + tile.getEndX()) >> 1);

		// 카메라 초기화 (2D 게임에서 주로 쓰는 카메라 OrthographicCamera)
		camera = new OrthographicCamera();

		// Viewport(사용자에게 보여지는 영역) 크기 설정
		viewport = new ExtendViewport(1000,1000, camera);
		viewport.apply();

		/** 줌 배율 = 기본 0.5, 최대 축소 1 */
		camera.zoom = 0.5F; // 카메라 줌 설정 (1배 Ex. 2.0F == 1/2배, 0.5F == 2배 줌)

		// InputProcessor 설정
		vip = new ViewportInputProcessor(camera, viewportWidth, viewportHeight); // 카메라(viewport)를 위한 InputProcessor 초기화
		Gdx.input.setInputProcessor(vip);
	}

	@Override
	public void render () {
		// create에서 초기화 했던걸 그려주는 단계, 프로그램 종료시까지 반복 실행
		elapsedTime += Gdx.graphics.getDeltaTime(); // 애니메이션 시간 설정
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update(); // camera 정보 업데이트
		ScreenUtils.clear(0, 0, 0, 1); // 배경색 설정
		mainBatch.setProjectionMatrix(camera.combined); // 카메라 시점을 해당 객체에 고정

		mainBatch.begin(); // .begin == 해당 객체 그리기 시작
		// 타일맵 배치
		renderer.setView(camera);
		renderer.render();
		tile.drawTile(mainBatch); // 타일 배치
		npc.drawNPC(mainBatch, elapsedTime); // NPC 배치, 해당 타일 만큼만 걷도록
		mainBatch.draw(it, 700, 400); // 오브젝트 그리기
		mainBatch.draw(art, 900, 400);
		mainBatch.end(); // 객체 그리기 끝
	}

	@Override
	public void resize(int width, int height) {
		// 업데이트 사항을 실제로 반영해주는 단계, create() 메서드가 끝난 후 Viewport 크기가 확정된 후 1회 실행
		viewport.update(width,height); // viewport 업데이트
		camera.position.set(viewportWidth/2,viewportHeight/2,0); // camera 위치 업데이트
	}

	@Override
	public void dispose () {
		// 객체들의 메모리 해제 단계
		mainBatch.dispose(); // .dispose == 해당 객체의 메모리 해제
	}
}
