package com.cdststudio.ut.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    public int getWidth() { return tileImg.getWidth(); }
    public int getHeight() { return tileImg.getHeight(); }
    public Texture getTileImg() { return tileImg; }

    public boolean isHorizontal() {
        if (startY == endY) return true;
        else return false;
    }

    public void drawTile(SpriteBatch mainBatch) {
        if (this.isHorizontal()) {
            for (int x = this.startX; x < this.endX; x += this.getWidth()) {
                mainBatch.draw(this.tileImg, x, this.startY);
            }
        }else {
            for (int y = this.startY; y < this.endY; y += this.getHeight()) {
                mainBatch.draw(this.tileImg, this.startX, y);
            }
        }
    }
}
