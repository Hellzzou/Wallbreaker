import java.awt.*;

public class Ball {
    private int posX;
    private int posY;
    private int size;
    private boolean frontX = true;
    private boolean frontY = false;
    private Color color;

    public Ball(int posX, int posY, int size, boolean frontX, boolean frontY, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.frontX = frontX;
        this.frontY = frontY;
        this.color = color;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public boolean isFrontX() {
        return frontX;
    }

    public boolean isFrontY() {
        return frontY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFrontX(boolean frontX) {
        this.frontX = frontX;
    }

    public void setFrontY(boolean frontY) {
        this.frontY = frontY;
    }
}
