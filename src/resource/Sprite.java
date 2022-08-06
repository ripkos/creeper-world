package resource;

import game.Structures.FriendlyWeapon;
import game.lib.Point;
import game.lib.Ser;
import javafx.scene.image.ImageView;

public abstract class Sprite extends Ser {
    public boolean floating=false;
    public boolean reachedPosition=false;
    public Sprite target;
    public Point targetPos;
    public Point pos;
    public transient ImageView texture;
    protected int width;
    protected int high;
    protected double speed=1;
    public boolean canmove=true;
    public String[] text = new String[]{
            "Name",
            "HP",
            "Cost",
            "Extra",
            "Desc"
    };


    public Sprite() {
        //pos=new Point(-400,-400);
       // this.texture=new ImageView();
    }

    public void setPos(Point pos) {
        this.pos = pos;
        texture.setX(pos.getPosX());
        texture.setY(pos.getPosY());
    }

    public int getWidth() {
        return width;
    }

    public int getHigh() {
        return high;
    }

    public void move(){
        if(target!=null){
            targetPos=target.pos;
        }
       // System.out.println("HERE");
        if(!(checkReached())) {
            floating=true;
            double deltaX = targetPos.getPosX() - this.pos.getPosX();
            double deltaY = targetPos.getPosY() - this.pos.getPosY();
            double angle = Math.atan2(deltaY, deltaX);
            this.pos.setPosX(pos.getPosX() + (speed * Math.cos(angle)));
            this.pos.setPosY(pos.getPosY() + (speed * Math.sin(angle)));
            texture.setX(pos.getPosX());
            texture.setY(pos.getPosY());
        }
        else{
            reachedPosition=true;
        }
    }
    private boolean checkReached(){
        return
                Point.getDistance(pos,targetPos)<5;
    }

    public void setTarget(Point p) {
    targetPos=p;
    }
    public void setTarget(Sprite t) {
    target=t;
    targetPos=t.pos;
    }
    public void updateTexture(ResourceLoader r){
        texture=new ImageView(r.getImage(this));
    }

    protected void updateTexture(FriendlyWeapon wpn,boolean b) {
        ResourceLoader r=new ResourceLoader();
        texture=new ImageView(r.getProjectile(wpn));
    }
}
