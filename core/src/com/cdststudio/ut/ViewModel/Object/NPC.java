package com.cdststudio.ut.ViewModel.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;

public class NPC {
    private static ArrayList<String> atlasPaths = new ArrayList<>(Arrays.asList("backWalkSheet", "frontWalkSheet", "leftWalkSheet", "rightWalkSheet"));

    private String charaName;
    private ArrayList<TextureAtlas> charaAtlas;
    private ArrayList<Animation<TextureRegion>> charaAnima;

    public NPC(String charaName) {
        this.charaName = charaName;
        this.charaAtlas = new ArrayList<>();
        this.charaAnima = new ArrayList<>();

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

}
