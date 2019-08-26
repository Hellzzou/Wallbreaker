import java.awt.*;

public class Bloc {
    private int posX;
    private int posY;
    private int size;
    private Color color;

    Bloc(int posX, int posY, int size, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.color = color;
    }
    Bloc(){}

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
}
