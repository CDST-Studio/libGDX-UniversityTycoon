package com.cdststudio.ut.Model;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
    private int startX, endX;
    private int startY, endY;
    private Texture tileImg;

    public Tile(int startX, int endX, int startY, int endY, Texture tileImg) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.tileImg = tileImg;
    }

    public int getStartX() { return startX; }
    public int getEndX() { return endX; }
    public int getStartY() { return startY; }
    public int getEndY() { return endY; }
    public Texture getTileImg() { return tileImg; }
    public int getWidth() { return tileImg.getWidth(); }
    public int getHeight() { return tileImg.getHeight(); }

    public boolean isHorizontal() {
        if (startY == endY) return true;
        else return false;
    }
}
