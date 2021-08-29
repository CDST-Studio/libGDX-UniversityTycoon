package com.cdststudio.ut;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.cdststudio.ut.ViewModel.InputProcessor.ViewportInputProcessor;

import java.util.ArrayList;
import java.util.Arrays;

public class UniversityTycoon extends ApplicationAdapter{
	private SpriteBatch mainBatch;
	private Sprite sprite;
	private OrthographicCamera camera;
	private ViewportInputProcessor vip;
	private ExtendViewport viewport;

	private static ArrayList<String> atlasPaths = new ArrayList<>(Arrays.asList("backWalkSheet", "frontWalkSheet", "leftWalkSheet", "rightWalkSheet"));
	private ArrayList<TextureAtlas> characterAtlases;
	private ArrayList<Animation<TextureRegion>> characterAnima;
	private float elapsedTime = 0f;

	Texture img;
	float viewportWidth, viewportHeight;
	int Size_Width = 256;
	int Size_Height = 256;

	Texture backgroundTexture;
	Sprite backgroundSprite;

	@Override
	public void create () {
		// 초기화 단계

		// 디바이스 크기 가져오기
		viewportWidth = Gdx.app.getGraphics().getWidth(); // 디바이스의 X 크기
		viewportHeight = Gdx.app.getGraphics().getHeight(); // 디바이스의 Y 크기

		mainBatch = new SpriteBatch(); // ima를 Sprite 시키기 위한 객체
		img = new Texture("badlogic.jpg"); // Sprite할 이미지를 가져오는 객체
		sprite = new Sprite(img, (int)0, (int)0, (int)Size_Width, (int)Size_Height); // 실제 이미지 Sprite할 크기 설정
		sprite.setPosition(viewportWidth/2-Size_Width/2, viewportHeight/2-Size_Height/2); // 이미지 위치 값 중심에 맞추기

		// 캐릭터 설정
		characterAtlases = new ArrayList<>();
		characterAnima = new ArrayList<>();
		for(int i = 0; i < atlasPaths.size(); i++) {
			characterAtlases.add(new TextureAtlas(Gdx.files.internal("character/w1/" + atlasPaths.get(i) + "/pack.atlas")));
			characterAnima.add(new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.5f, characterAtlases.get(i).getRegions()));
		}

		// 배경 설정
		backgroundTexture = new Texture("test.jpg");
		backgroundSprite = new Sprite(backgroundTexture);

		camera = new OrthographicCamera(); // 카메라 초기화 (2D 게임에서 주로 쓰는 카메라 OrthographicCamera)

		//Viewport(사용자에게 보여지는 영역) 크기 설정 (시점 이동까지 설정된 것은 X)
		viewport = new ExtendViewport(1000,1000, camera);
		viewport.apply();

		/** 줌 배율 = 기본 0.4, 최대 축소 1 */
		camera.zoom = 0.4F; // 카메라 줌 설정 (1배 Ex. 2.0F == 1/2배, 0.5F == 2배 줌)

		// InputProcessor 설정
		vip = new ViewportInputProcessor(camera, viewportWidth, viewportHeight); // 카메라(viewport)를 위한 InputProcessor 초기화
		Gdx.input.setInputProcessor(vip);
	}

	@Override
	public void render () {
		// create에서 초기화 했던걸 그려주는 단계, 프로그램 종료시까지 반복 실행
		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update(); // camera 정보 업데이트

		ScreenUtils.clear(0, 0, 0, 1); // 배경색 설정

		mainBatch.setProjectionMatrix(camera.combined); // 카메라 시점을 해당 객체에 고정
		mainBatch.begin(); // .begin == 해당 객체 그리기 시작
		backgroundSprite.draw(mainBatch); // 배경 그리기
		for(int i = 0; i < atlasPaths.size(); i++) {
			mainBatch.draw(characterAnima.get(i).getKeyFrame(elapsedTime, true), 400 + (i * 50), 400);
		}
		mainBatch.draw(img, 0, 0); // 객체의 이미지 및 (x, y)위치 설정
		mainBatch.end(); // 객체 그리기 끝
	}

	@Override
	public void resize(int width, int height) {
		// 업데이트 사항을 실제로 반영해주는 단계, create() 메서드가 끝난 후 Viewport 크기가 확정된 후 1회 실행
		viewport.update(width,height); // viewport 업데이트
		camera.position.set(viewportWidth/2 - img.getWidth()/2,viewportHeight/2 - img.getHeight()/2,0); // camera 위치 업데이트
	}

	@Override
	public void dispose () {
		// 객체들의 메모리 해제 단계
		mainBatch.dispose(); // .dispose == 해당 객체의 메모리 해제
		img.dispose();
		for(int i = 0; i < atlasPaths.size(); i++) {
			characterAtlases.get(i).dispose();
		}
	}
}
