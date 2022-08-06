package game.Grids;

import game.lib.Ser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelLayout extends Ser {
    public int[][] grid;
    public static int maxLVL=5;
    transient Rectangle[][][]  rectLis;
    //public AnchorPane gridL;
    public LevelLayout() {
    //this.gridL=gridL;
    }

    public LevelLayout(int x, int y) {

        setGrid(x, y);
    }

    public void reset(int x, int y) {

        setGrid(x, y);
    }

    private void fill() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = 3;

            }
        }
    }


    private void setGrid(int x, int y) {
        this.grid = new int[y][x];
        fill();
    }



    //Visual
    public void fillRect(AnchorPane bgL) {
        if(rectLis==null)
            this.rectLis=new Rectangle[grid.length][grid[0].length][5];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {

                rectLis[y][x][0]=new Rectangle(64+x*16,64+y*16,16,16);
                rectLis[y][x][0].setStroke(new Color(0,0,0,0.15));
                rectLis[y][x][0].setFill(new Color(0,0,0,0));
                bgL.getChildren().add(rectLis[y][x][0]);
                //rectLis[y][x][0].setVisible(false);
                //
                for(int i=1; i<5;i++){
                    rectLis[y][x][i]=new Rectangle(64+x*16,64+y*16,2,2);
                    rectLis[y][x][i].setFill(Color.BLACK);
                    if(i%2==0) {
                        rectLis[y][x][i].setWidth(16);
                        if(i==4) {
                            rectLis[y][x][i].setY(64+y*16+14);
                        }
                    }
                    else {
                        rectLis[y][x][i].setHeight(16);
                        if(i==3) {
                            rectLis[y][x][i].setX(64+x*16+14);
                        }
                    }
                    bgL.getChildren().add(rectLis[y][x][i]);
                    rectLis[y][x][i].setVisible(false);
                }
            }
        }

    }




    public void editLevel(int x, int y,int v, int hw) {

            for (int y2=0; y2<hw;y2++) {
                for (int x2=0; x2<hw;x2++) {
                    if (!(x+x2 < 0 || y +y2 < 0 || y+y2 > grid.length -1|| x+x2 > grid[0].length -1)) {
                        grid[y2 + y][x2 + x] += v;
                        if (grid[y2 + y][x2 + x] < 0)
                            grid[y2 + y][x2 + x] = 0;
                        if (grid[y2 + y][x2 + x] > maxLVL)
                            grid[y2 + y][x2 + x] = maxLVL;
                        //System.out.println(grid[y2+y][x2+x]);
                    }
                }
            }

    }

    public int getLevel(int x, int y) {
        if (x < 0 || y < 0 || y > grid.length || x > grid[0].length) {
            System.out.println("Array out of bounds at x=" + x + " y=" + y);
            return 0;
        } else {
            return grid[y][x];
        }
    }
    public boolean canPlace(int x1,int y1, int sizeX, int sizeY) {
        if (x1 < 0 || y1 < 0 || y1+sizeY > grid.length || x1+sizeX > grid[0].length) {
            System.out.println("Array out of bounds at x=" + x1 + " y=" + y1);
            return false;
        }
        int b=grid[y1][x1];
        for (int y=0; y<sizeY;y++) {
            for(int x=0;x<sizeX;x++) {
                if(!(x==0&&y==0))
                if(grid[y+y1][x+x1]!=b)
                    return false;
            }
        }
        return true;
    }

    public void update() {
        for(int y=0;y<grid.length;y++) {
            for(int x=0; x<grid[0].length;x++) {
                //checkCurrent
                rectLis[y][x][0].setFill(new Color(0,0,0,(5-grid[y][x])*0.05));
                //CheckLeft
                rectLis[y][x][1].setVisible(x > 0 && grid[y][x - 1] - grid[y][x] > 0);
                //CheckRight
                rectLis[y][x][3].setVisible(x < grid[0].length - 1 && grid[y][x + 1] - grid[y][x] > 0);
                //CheckUp
                rectLis[y][x][2].setVisible(y > 0 && grid[y-1][x] - grid[y][x] > 0);
                //CheckDown
                rectLis[y][x][4].setVisible(y < grid.length - 1 && grid[y+1][x] - grid[y][x] > 0);
            }
        }
    }
}
