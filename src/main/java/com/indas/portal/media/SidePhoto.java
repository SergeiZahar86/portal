package com.indas.portal.media;

public enum SidePhoto {
    LEFT(0, "left"), RIGHT(1, "right"), TOP(2, "top"),
    ;

    public final String text;
    public int num;

    SidePhoto(int numSide, String textSide) {
        this.num = numSide;
        this.text = textSide;
    }

    public static SidePhoto get(int numSide) throws Exception{
        switch (numSide){
            case 0: return LEFT;
            case 1: return RIGHT;
            case 2: return TOP;
            default: throw new Exception("Не поддерживаемая сторона изображения");
        }
    }
}
