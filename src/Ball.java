class Ball {
    private int posX;
    private int posY;
    private int size;
    private boolean frontX;
    private boolean frontY;

    Ball(int posX, int posY, int size, boolean frontX, boolean frontY) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.frontX = frontX;
        this.frontY = frontY;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    int getSize() {
        return size;
    }

    boolean isFrontX() {
        return frontX;
    }

    boolean isFrontY() {
        return frontY;
    }

    void setPosX(int posX) {
        this.posX = posX;
    }

    void setPosY(int posY) {
        this.posY = posY;
    }

    void setFrontX(boolean frontX) {
        this.frontX = frontX;
    }

    void setFrontY(boolean frontY) {
        this.frontY = frontY;
    }
}
