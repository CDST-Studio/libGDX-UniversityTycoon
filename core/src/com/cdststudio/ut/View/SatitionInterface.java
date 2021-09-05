package com.cdststudio.ut.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SatitionInterface {
    public SatitionInterface(){

    }
    public Sprite drawInterface(){
        Sprite sprite = new Sprite(new Texture("Interface/schoolsati.png"));
        return sprite;
    }
}
