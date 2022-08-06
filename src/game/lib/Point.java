package game.lib;

public class Point extends Ser {
    double posX;
    double posY;

    public Point(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Point(Point pos) {
        this.posX=pos.getPosX();
        this.posY=pos.getPosY();
    }

    public static double getDistance(Point p1, Point p2) {
        if (p1==null||p2==null)
        {
            System.out.println("Mistake");
            return 6000000;
        }
        else
        return (
                Math.sqrt((p1.posX-p2.posX)*(p1.posX-p2.posX)+(p1.posY-p2.posY)*(p1.posY-p2.posY))
                );
    }
    public static double getDistance(double x, double y, double x2, double y2) {
        return getDistance(new Point(x,y),new Point(x2,y2));
    }
    public static double getDistance(Point p, double x2, double y2) {
        return getDistance(p,new Point(x2,y2));
    }

    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
