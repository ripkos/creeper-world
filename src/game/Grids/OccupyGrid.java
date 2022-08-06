package game.Grids;

import game.lib.Point;
import game.lib.Ser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resource.Sprite;

public class OccupyGrid extends Ser {
    int grid[][];
    transient Rectangle[][]  rectLis;


    public OccupyGrid(int x, int y) {
        this.grid = new int[y][x];
    }

    public void addToGrid(int x1,int y1, int sizeX, int sizeY) {

        for (int y=0; y<sizeY;y++) {
            for(int x=0;x<sizeX;x++) {
                grid[y+y1][x+x1]=1;
            }
        }
    }

    public void removeFromGrid(int x1,int y1, int sizeX, int sizeY) {

        for (int y=0; y<sizeY;y++) {
            for(int x=0;x<sizeX;x++) {
                grid[y+y1][x+x1]=0;
            }
        }
    }
    public boolean isOccupied(int x1,int y1, int sizeX, int sizeY) {
        if (x1 < 0 || y1 < 0 || y1+sizeY > grid.length || x1+sizeX > grid[0].length) {
            System.out.println("Array out of bounds at x=" + x1 + " y=" + y1);
            return true;
        }
        for (int y=0; y<sizeY;y++) {
            for(int x=0;x<sizeX;x++) {
                if(grid[y+y1][x+x1]!=0)
                    return true;
            }
        }
        return false;
    }

    public void removeFromGrid(Point p, int sizeX, int sizeY) {
        int x2 = (int) (p.getPosX()) - ((int) (p.getPosX()) % 16);
        int y2 = (int) (p.getPosY()) - ((int) (p.getPosY()) % 16);
        removeFromGrid(x2 / 16 - 4, y2 / 16 - 4, sizeX, sizeY);
    }

    public void removeFromGrid(Sprite current) {
        int x2 = (int) (current.pos.getPosX()) - ((int) (current.pos.getPosX()) % 16);
        int y2 = (int) (current.pos.getPosY()) - ((int) (current.pos.getPosY()) % 16);
        removeFromGrid(x2 / 16 - 4, y2 / 16 - 4, current.getWidth(), current.getHigh());

    }


    public void fillRect(AnchorPane bgL) {
        if (rectLis == null)
            this.rectLis = new Rectangle[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                rectLis[y][x] = new Rectangle(64 + x * 16, 64 + y * 16, 16, 16);
                rectLis[y][x].setStroke(new Color(1, 0, 1, 0));
                rectLis[y][x].setFill(new Color(1, 0, 1, 0));

                bgL.getChildren().add(rectLis[y][x]);
                //

            }
        }
        updateRect();
    }


    public void updateRect() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x]==0||grid[y][x]==1)
                    rectLis[y][x].setFill(new Color(1, 1, 1, grid[y][x] > 0 ? 0.25 : 0));
                //

            }
        }
    }

    public void addToGrid(Sprite current) {
        int x2 = (int) (current.pos.getPosX()) - ((int) (current.pos.getPosX()) % 16);
        int y2 = (int) (current.pos.getPosY()) - ((int) (current.pos.getPosY()) % 16);
        addToGrid(x2 / 16 - 4, y2 / 16 - 4, current.getWidth(), current.getHigh());
    }
}
