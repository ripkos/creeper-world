package game.Grids;

import game.lib.Point;
import game.lib.Ser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreepLayout extends Ser {
    double[][] grid;
    transient Rectangle[][]  rectLis;

    public CreepLayout(int x, int y) {
        this.grid=new double[y][x];
        fill();
    }



    public synchronized void  addCreep(double amount, int posX, int posY,int sizeW,int sizeH) {
        for (int y = 0; y < sizeH; y++) {
            for (int x = 0; x < sizeW; x++) {
                if(x+posX<0||y+posY<0||x+posX>grid[0].length||y+posY>grid.length){
                    //Array out of bounds
                }
                else
                {
                grid[y + posY][x + posX] += amount;
                if (grid[y + posY][x + posX] < 0)
                    grid[y + posY][x + posX] = 0;
            }
            }
        }
    //updateRect();
    }

    private void fill() {
        for (int y=0; y<grid.length;y++) {
            for (int x=0; x<grid[0].length;x++) {
                grid[y][x]=0;
            }
        }
    }
    public static Color c = new Color(0, 0, 1, 0);
    public void fillRect(AnchorPane bgL) {
        if (rectLis == null)
            this.rectLis = new Rectangle[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                rectLis[y][x] = new Rectangle(64 + x * 16, 64 + y * 16, 16, 16);
                rectLis[y][x].setStroke(c);
                rectLis[y][x].setFill(c);

                bgL.getChildren().add(rectLis[y][x]);
                //

                }
            }

        }


        public void updateRect() {

                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[0].length; x++) {
                        double c = grid[y][x]>2500 ? 1 : (double)(grid[y][x])/2500;
                        c= c < 0.4 ? 0.4 : c;
                        c = c > 1 ? 1 : c;
                        rectLis[y][x].setFill(new Color(0, 0, 1, grid[y][x]==0 ? 0 : c));
                        //

                    }
                }


        }

    public synchronized void spreadCreep(int[][] lgrid) {
        double rate=0.2;
        for (int y = 0; y< this.grid.length; y++) {
            for (int x = 0; x< this.grid[0].length; x++) {
                double d=0;
                //checking left
                if(x>1) {
                    d=grid[y][x]-grid[y][x-1]-(lgrid[y][x-1]-lgrid[y][x]>0 ? (lgrid[y][x-1]-lgrid[y][x])*2000 : 0);
                    if(d>250) {
                        addCreep(d*rate,x-1,y,1,1);
                        addCreep(-d*rate,x,y,1,1);
                    }
                }
                //checking up
                if(y>1) {
                    d=grid[y][x]-grid[y-1][x]-(lgrid[y-1][x]-lgrid[y][x]>0 ? (lgrid[y-1][x]-lgrid[y][x])*2000 : 0);
                    if(d>250) {
                        addCreep(d*rate,x,y-1,1,1);
                        addCreep(-d*rate,x,y,1,1);
                    }

                }
                //checking right
                if(x<grid[0].length-1){
                    d=grid[y][x]-grid[y][x+1]-(lgrid[y][x+1]-lgrid[y][x]>0 ? (lgrid[y][x+1]-lgrid[y][x])*2000 : 0);
                    if(d>250) {
                        addCreep(d*rate,x+1,y,1,1);
                        addCreep(-d*rate,x,y,1,1);
                    }
                }
                //checking down
                if(y<grid.length-1) {
                    d=grid[y][x]-grid[y+1][x]-(lgrid[y+1][x]-lgrid[y][x]>0 ? (lgrid[y+1][x]-lgrid[y][x])*2000 : 0);
                    if(d>250) {
                        addCreep(d*rate,x,y+1,1,1);
                        addCreep(-d*rate,x,y,1,1);
                    }
                }

            }

        }
        updateRect();
    }


    public Point getTarget(Point pos, int area, boolean canFireUp, LevelLayout lvl,int width, int high) {
        int y2=(int)(pos.getPosY())/16-4;
        int x2=(int)(pos.getPosX())/16-4;
        int clevel=lvl.grid[y2][x2];
        int X=-1;
        int Y=-1;
        boolean created=false;
        double lowestAm=1500000;
        for (int y=y2-area; y<y2+high+area;y++) {
            for (int x=x2-area; x<x2+width+area;x++) {
                if(x>0&&y>0&&x<grid[0].length&&y<grid.length)
                if((x-x2)*(x-x2)+(y-y2)*(y-y2)<=area*area){
                //begin

                if (grid[y][x]>0&&grid[y][x] < lowestAm && (canFireUp || (clevel > lvl.grid[y][x]))
                ) {
                    lowestAm = grid[y][x];
                    X = x;
                    Y = y;
                    created=true;
                }
                else{
                    //System.out.println("here");
                }
                //end
            }
            }
        }
        Point p=null;
        if(created) {
            p=new Point(
                    (X+4)*16,(Y+4)*16);
           // System.out.println("Created point");

        }
        return p;
    }
}


