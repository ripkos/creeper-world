package game.lib;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Pair extends Ser {
    Connectable a;
    Connectable b;
    Point pos1;
    Point pos2;
    transient public boolean alreadyPainted=false;
    transient public Line l;
    public Pair(Connectable a, Connectable b) {
        Point pos1=a.getPos();
        Point pos2=b.getPos();
        this.alreadyPainted=false;
        if (pos1.getPosX()<=pos2.getPosX()){
            if(pos1.getPosY()<=pos2.getPosY()){
            this.pos1=pos1;
            this.pos2=pos2;
            this.a=a;
            this.b=b;
            }
            else{
                this.pos1=pos2;
                this.pos2=pos1;
                this.b=a;
                this.a=b;
            }
        }
        else{
            this.pos1=pos2;
            this.pos2=pos1;
            this.a=b;
            this.b=a;
        }

    }
    public void setLine(){
        //System.out.println("IM HERE");
        this.alreadyPainted=true;
        l=new Line(
                pos1.getPosX()+a.getSizeW()*8,  pos1.getPosY()+a.getSizeH()*8,pos2.getPosX()+b.getSizeW()*8,pos2.getPosY()+b.getSizeH()*8
        );
        l.setStroke(Color.WHITESMOKE);
        l.setFill(Color.ANTIQUEWHITE);
        l.setStrokeWidth(2);
    }
    public void update(){
        if(l==null){
            setLine();
        }
        else{
            l.setStartX(pos1.getPosX()+a.getSizeW()*8);
            l.setStartY(pos1.getPosY()+a.getSizeH()*8);
            l.setEndX(pos2.getPosX()+b.getSizeW()*8);
            l.setEndY(pos2.getPosY()+b.getSizeH()*8);
        }
    }

    public boolean equals(Pair p2){
    return this.a==p2.a&&this.b==p2.b ||
            this.b==p2.a&&this.a==p2.b;
    }
}
