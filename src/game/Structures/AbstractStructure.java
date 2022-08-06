package game.Structures;

import resource.Sprite;

public abstract class AbstractStructure extends Sprite {


    protected boolean isImmortal=true;
    protected int hp=1;
    protected int hpMAX=1;

    public AbstractStructure() {
        super();
        this.high=3;
        this.width=3;
        this.canmove=false;
    }


    //abstract void destroy()
}
