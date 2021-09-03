package com.cdststudio.ut.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Total {
    Texture one;

    public Total(){

    }
    public Sprite drawInterface(){
        Sprite sprite = new Sprite(new Texture("Interface/all.png"));
        return sprite;
    }
}
