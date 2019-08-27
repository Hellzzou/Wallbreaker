import java.awt.*;

class Bloc {
    private int posX;
    private int posY;
    private int size;
    private Color color;
    private int speed = 10;

    Bloc(int posX, int posY, int size, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.color = color;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    Color getColor() {
        return color;
    }

    int getSize() {
        return size;
    }

    void setPosX(int posX) {
        this.posX = posX;
    }

    void setSize(int size) { this.size = size; }

    int getSpeed() { return speed; }

    void setSpeed(int speed) { this.speed = speed; }
}
