package game.Grids;

import game.lib.Ser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnergyLayout extends Ser {
    int[][] grid;
    transient Rectangle[][]  rectLis;

    public EnergyLayout(int mapW, int mapH) {
        grid=new int[mapH][mapW];
        fill();
    }


    public void addEnergy(int amount, int r, int posX, int posY, int[][] gridL) {
        int h=gridL[posY][posX];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if(x+posX<0||y+posY<0||x+posX>grid[0].length||y+posY>grid.length){
                    //Array out of bounds
                }
                else
                {
                    if((y+posY)*(y+posY)+(x+posX)*(x+posX)<=r*r&&gridL[y+posY][x+posX]==h)
                    {
                        grid[y + posY][x + posX] += amount;
                        if (grid[y + posY][x + posX] < 0)
                            grid[y + posY][x + posX] = 0;
                    }
                }
            }
        }
        updateRect();
    }

    private void fill() {
        for (int y=0; y<grid.length;y++) {
            for (int x=0; x<grid[0].length;x++) {
                grid[y][x]=0;
            }
        }
    }

    public void fillRect(AnchorPane bgL) {
        if (rectLis == null)
            this.rectLis = new Rectangle[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                rectLis[y][x] = new Rectangle(64 + x * 16, 64 + y * 16, 16, 16);
                rectLis[y][x].setStroke(new Color(0, 0.25, 0, 0.20));
                rectLis[y][x].setFill(new Color(0, 0.25, 0, 0.20));

                bgL.getChildren().add(rectLis[y][x]);
                //

            }
        }
        updateRect();
    }


    public void updateRect() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                rectLis[y][x].setFill(new Color(0, 0.25, 0, grid[y][x]>0 ? 0.20 : 0 ));
                //

            }
        }
    }

    public double calcEnergy() {
        int d =0;
        for (int y=0; y<grid.length;y++) {
            for (int x=0; x<grid[0].length;x++) {
                if(grid[y][x]>0)
                    d+=0.03;
            }
        }
        return d;
    }
}
