package com.cdststudio.ut.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;

public class NPC {
    private static ArrayList<String> atlasPaths = new ArrayList<>(Arrays.asList("backWalkSheet", "frontWalkSheet", "leftWalkSheet", "rightWalkSheet"));

    private String charaName;
    private ArrayList<TextureAtlas> charaAtlas;
    private ArrayList<Animation<TextureRegion>> charaAnima;

    private Tile belongedTile;
    private float sinceWalking;
    private int direction;
    private float x;
    private float y;

    public NPC(String charaName, Tile belongedTile, float x, float y) {
        this.belongedTile = belongedTile;
        this.x = x;
        this.y = y;
        this.sinceWalking = 0F;
        this.charaName = charaName;
        this.charaAtlas = new ArrayList<>();
        this.charaAnima = new ArrayList<>();

        if (belongedTile.isHorizontal()) {
            this.direction = 2 + ((int)(Math.random()*10000) % 2);
        }else {
            this.direction = (int)(Math.random()*10000) % 2;
        }

        for(int i = 0; i < atlasPaths.size(); i++) {
            charaAtlas.add(new TextureAtlas(Gdx.files.internal("character/"+ this.charaName +"/" + atlasPaths.get(i) + "/pack.atlas")));
            charaAnima.add(new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.5f, charaAtlas.get(i).getRegions()));
        }
    }

    public Animation<TextureRegion> getBackMove() {
        return charaAnima.get(0);
    }
    public Animation<TextureRegion> getFrontMove() {
        return charaAnima.get(1);
    }
    public Animation<TextureRegion> getLeftMove() {
        return charaAnima.get(2);
    }
    public Animation<TextureRegion> getRightMove() {
        return charaAnima.get(3);
    }
    public int getDirection() { return direction; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public void drawNPC(SpriteBatch mainBatch, float elapsedTime) {
        switch (this.direction) {
            case 0:
                if (this.getY() + ((elapsedTime - sinceWalking) * 1.75F) < belongedTile.getEndY()) {
                    mainBatch.draw(this.getBackMove().getKeyFrame((elapsedTime - sinceWalking), true), this.getX(), this.getY() + ((elapsedTime - sinceWalking) * 1.75F));
                }
                else {
                    sinceWalking = elapsedTime;
                    direction = 1;
                    y = belongedTile.getEndY();
                }
                break;
            case 1:
                if (this.getY() - ((elapsedTime - sinceWalking) * 1.75F) > belongedTile.getStartY()) {
                    mainBatch.draw(this.getFrontMove().getKeyFrame((elapsedTime - sinceWalking), true), this.getX(), this.getY() - ((elapsedTime - sinceWalking) * 1.75F));
                }
                else {
                    sinceWalking = elapsedTime;
                    direction = 0;
                    y = belongedTile.getStartY();
                }
                break;
            case 2:
                if (this.getX() - ((elapsedTime - sinceWalking) * 1.75F) > belongedTile.getStartX()) {
                    mainBatch.draw(this.getLeftMove().getKeyFrame((elapsedTime - sinceWalking), true), this.getX() - ((elapsedTime - sinceWalking) * 1.75F), this.getY());
                }
                else {
                    sinceWalking = elapsedTime;
                    direction = 3;
                    x = belongedTile.getEndX();
                }
                break;
            case 3:
                if (this.getX() + ((elapsedTime - sinceWalking) * 1.75F) < belongedTile.getEndX()) {
                    mainBatch.draw(this.getRightMove().getKeyFrame((elapsedTime - sinceWalking), true), this.getX() + ((elapsedTime - sinceWalking) * 1.75F), this.getY());
                }
                else {
                    sinceWalking = elapsedTime;
                    direction = 2;
                    x = belongedTile.getStartX();
                }
                break;
            default:
                System.out.println("ERROR 01. Direction Error");
                break;
        }
    }
}
