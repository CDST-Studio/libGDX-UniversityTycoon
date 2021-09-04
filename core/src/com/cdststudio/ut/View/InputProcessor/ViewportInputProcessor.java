package com.cdststudio.ut.View.InputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ViewportInputProcessor implements InputProcessor {
    private int DeviceX, DeviceY;

    public OrthographicCamera camera;
    public ViewportInputProcessor(OrthographicCamera camera, float x, float y) {
        this.camera = camera; // Reference(참조)를 통해 두 객체가 동일한 메모리 주소 참조하도록 생성자에서 처리
        this.DeviceX = (int)x;
        this.DeviceY = (int)y;
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
        /*
        System.out.println("##########################");
        System.out.println(DeviceX);
        System.out.println(DeviceY);
        System.out.println(screenX);
        System.out.println(screenY);
        System.out.println((int)camera.viewportWidth);
        System.out.println((int)camera.viewportHeight);
         */
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * 드래그 중에 발생하는 이벤트 처리 메서드
     * @param screenX X좌표
     * @param screenY Y좌표
     * @param pointer ?
     * @return
     */
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

    /**
     * Custom Method
     */
    
    // Viewport 이동 제어 메서드
    private boolean CanMove(int screenX, int screenY) {
        if (((int)camera.viewportWidth / 2) + screenX > DeviceX) return false;
        else if (((int)camera.viewportWidth / 2) - screenX < 0) return false;
        else if (((int)camera.viewportHeight / 2) + screenY > DeviceY) return false;
        else if (((int)camera.viewportHeight / 2) - screenY < 0) return false;

        return true;
    }
}
