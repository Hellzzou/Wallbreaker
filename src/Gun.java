class Gun {
    private int posX;
    private int posY;

    Gun(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    void setPosY(int posY) {
        this.posY = posY;
    }
}
