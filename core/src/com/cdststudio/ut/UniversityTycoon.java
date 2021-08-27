package com.cdststudio.ut;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class UniversityTycoon extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private Sprite sprite;
	private ExtendViewport viewport;
	private float viewportWidth, viewportHeight;

	Texture img;
	OrthographicCamera camera;
	int Size_Width = 256;
	int Size_Height=256;
	
	@Override
	public void create () {
		// 초기화 단계

		// 디바이스 크기 가져오기
		viewportWidth = Gdx.app.getGraphics().getWidth(); // 디바이스의 X 크기
		viewportHeight = Gdx.app.getGraphics().getHeight(); // 디바이스의 Y 크기

		batch = new SpriteBatch(); // ima를 Sprite 시키기 위한 객체
		img = new Texture("badlogic.jpg"); // Sprite할 이미지를 가져오는 객체
		sprite = new Sprite(img, (int)0, (int)0, (int)Size_Width, (int)Size_Height); // 실제 이미지 Sprite할 크기 설정
		sprite.setPosition(viewportWidth/2-Size_Width/2, viewportHeight/2-Size_Height/2); // 이미지 위치 값 중심에 맞추기

		camera = new OrthographicCamera(); // 카메라 초기화 (2D 게임에서 주로 쓰는 카메라 OrthographicCamera)

		//Viewport(사용자에게 보여지는 영역) 크기 설정 (시점 이동까지 설정된 것은 X)
		viewport = new ExtendViewport(1000,1000, camera);
		viewport.apply();
		camera.zoom = 1.0F; // 카메라 줌 설정 (1배 Ex. 2.0F == 1/2배, 0.5F == 2배 줌)

		// InputProcessor 설정
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		// create에서 초기화 했던걸 그려주는 단계, 프로그램 종료시까지 반복 실행

		camera.update(); // camera 정보 업데이트

		ScreenUtils.clear(1, 0, 0, 1); // 배경색 설정

		batch.setProjectionMatrix(camera.combined); // 카메라 시점을 해당 객체에 고정
		batch.begin(); // .begin == 해당 객체 그리기 시작
		batch.draw(img, 0, 0); // 객체의 이미지 및 (x, y)위치 설정
		batch.end(); // 객체 그리기 끝
	}

	@Override
	public void resize(int width, int height) {
		// 업데이트 사항을 실제로 반영해주는 단계, create() 메서드가 끝난 후 Viewport 크기가 확정된 후 1회 실행
		viewport.update(width,height); // viewport 업데이트
		camera.position.set(viewportWidth/2 - 100,viewportHeight/2 - 100,0); // camera 위치 업데이트
	}

	@Override
	public void dispose () {
		// 객체들의 메모리 해제 단계
		batch.dispose(); // .dispose == 해당 객체의 메모리 해제
		img.dispose();
	}

	/**
	 * Must be override method for InputProcessor
	 * return false == 사용 안 하는 메서드, true 사용하는 메서드
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float x = Gdx.input.getDeltaX();
		float y = Gdx.input.getDeltaY();

		camera.translate(-x,y);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
