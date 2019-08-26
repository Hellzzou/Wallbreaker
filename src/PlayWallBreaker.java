import java.awt.*;
import java.util.List;
import java.util.ListIterator;

public class PlayWallBreaker implements Runnable{
    private Bloc[][] wall;
    private Window window;
    private List<Ball> balls;
    private List<Gun> guns;
    private Bloc breaker;
    private Ball newBall;
    private int bigCounter = 1;
    private boolean okBig = false;
    private int helpCounter = 1;
    private int gunsCounter = 1;
    private int speedCounter = 1;
    private boolean okSpeed = false;
    private int removeBall = 0;
    private boolean remove = false;
    private boolean create = false;
    private int counter = 1;

    PlayWallBreaker(Window window) {
        this.wall = window.getWallBreaker().getWall();
        this.window = window;
        this.balls = window.getWallBreaker().getBalls();
        this.breaker = window.getWallBreaker().getBreaker();
        this.guns = window.getWallBreaker().getGuns();
    }

    public void run(){
        while(window.isGo()){
            for (Ball ball : balls) {
                if (ball.isFrontX()) ball.setPosX(ball.getPosX() + 1);
                else ball.setPosX(ball.getPosX() - 1);
                if (ball.isFrontY()) ball.setPosY(ball.getPosY() + 1);
                else ball.setPosY(ball.getPosY() - 1);
                /*
                S'il ne reste plus qu'une balle et qu'elle touche le bas : PERDU !!!
                Sinon je retire la balle qui a touche le bas
                 */
                if (ball.getPosY() == window.getWallBreaker().getHeight() - ball.getSize()) {
                    if ( balls.size() > 1){
                        remove = true;
                        removeBall = balls.indexOf(ball);
                    }
                    else {
                        window.setGo(false);
                        window.getWallBreaker().setLost(true);
                    }
                }
                if ( window.getWallBreaker().getInvicibleBreaker() != null && ((wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20] != null ||
                        ball.getPosY() < 460 && wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() + ball.getSize() + 1) / 20] != null) || (ball.getPosY() == 0 || ball.getPosY() == 420))){
                    destroyBloc((ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() - 1) / 20);
                    destroyBloc((ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() + ball.getSize() + 1) / 20);
                    ball.setFrontY(!ball.isFrontY());
                }
                /*
                Si la balle touche un bloc en haut ou en bas : detruit le bloc et inverse FrontY
                Si la balle touche le haut de la fenetre ou le breaker : inverse FrontY
                 */
                else if ((wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20] != null || ball.getPosY() < 460 && wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() + ball.getSize() + 1) /
                        20] != null) || (ball.getPosY() == 0 || (ball.getPosX() + ball.getSize() / 2 >= breaker.getPosX() && ball.getPosX() + ball.getSize() / 2 <= breaker.getPosX() + breaker.getSize() && ball.getPosY() == 460))) {
                    destroyBloc((ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() - 1) / 20);
                    destroyBloc((ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() + ball.getSize() + 1) / 20);
                    ball.setFrontY(!ball.isFrontY());
                }
                /*
                Si la balle touche un bloc a droite ou a gauche : detruit le bloc et inverse FrontY
                Si la balle touche un des bords droite ou gauche de la fenetre : Inverse FrontX
                 */
                if (((ball.getPosX() <= window.getWallBreaker().getWidth() - 71 && wall[(ball.getPosX() + ball.getSize() + 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null) ||
                        (ball.getPosX() >= 71 && wall[(ball.getPosX() - 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null)) ||
                        (ball.getPosX() == window.getWallBreaker().getWidth() - ball.getSize() || ball.getPosX() == 0)) {
                    if (ball.getPosX() <= window.getWallBreaker().getWidth() - 71) destroyBloc((ball.getPosX() + ball.getSize() + 1) / 71,(ball.getPosY() + (ball.getSize() / 2)) / 20);
                    if (ball.getPosX() >= 71) destroyBloc((ball.getPosX() - 1) / 71,(ball.getPosY() + (ball.getSize() / 2)) / 20);
                    ball.setFrontX(!ball.isFrontX());
                }
            }
            manageSpeed();
            manageGuns();
            manageBlocs();
            manageExtraBalls();
            manageBig();
            manageHelp();

            counter ++;

            try {
                Thread.sleep(7 - (window.getLevel() - 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            window.getWallBreaker().repaint();
        }
    }
    private void destroyBloc(int posX, int posY) {
        if (wall[posX][posY] != null){
            if (wall[posX][posY].getColor() == Color.ORANGE) {
                if ( breaker.getSize() != 142 ) breaker.setPosX(breaker.getPosX() - 35);
                breaker.setSize(142);
                okBig = true;
                bigCounter = 1;
            }
            if (wall[posX][posY].getColor() == Color.GREEN) {
                newBall = new Ball(breaker.getPosX() + ( breaker.getSize() / 2 ) - 10, 460, 20, true, false, Color.darkGray);
                create = true;
            }
            if (wall[posX][posY].getColor() == Color.CYAN) {
                window.getWallBreaker().setInvicibleBreaker(new Bloc(0,440,710,Color.CYAN));
                window.getWallBreaker().setOkHelp(true);
                helpCounter = 1;
            }
            if (wall[posX][posY].getColor() == Color.RED) {
                window.getWallBreaker().setOkGuns(true);
                gunsCounter = 1;
            }
            if (wall[posX][posY].getColor() == Color.PINK) {
                breaker.setSpeed(40);
                okSpeed = true;
                speedCounter = 1;
            }

            wall[posX][posY] = null;
            window.setScore(window.getScore() + 5);
        }
    }
    private void manageBig(){
        if (okBig){
            bigCounter ++;
            if (bigCounter % 4000 == 0){
                breaker.setPosX(breaker.getPosX() + 35);
                breaker.setSize(71);
                okBig =false;
                bigCounter = 1;
            }
        }
    }
    private void manageSpeed(){
        if (okSpeed){
            speedCounter ++;
            if (speedCounter % 4000 == 0){
                breaker.setSpeed(20);
                okSpeed =false;
                speedCounter = 1;
            }
        }
    }
    private void manageHelp(){
        if (window.getWallBreaker().isOkHelp()){
            helpCounter ++;
            if (helpCounter % 4000 == 0){
                window.getWallBreaker().setInvicibleBreaker(null);
                window.getWallBreaker().setOkHelp(false);
                helpCounter = 1;
            }
        }
    }
    private void manageExtraBalls(){
        if (create) {
            balls.add(newBall);
            create = false;
        }
        if (remove){
            balls.remove(removeBall);
            remove = false;
        }
    }
    private void manageBlocs(){
        if( counter % 4000 == 0){
            window.getWallBreaker().lowerBlocs();
        }
        if(counter % 20000 == 0 ){
            window.setLevel(window.getLevel() + 1);
        }
    }
    private void manageGuns(){
        if ( window.getWallBreaker().isOkGuns()) {
            gunsCounter ++;
            ListIterator<Gun> li = guns.listIterator();
            while (li.hasNext()) {
                Gun gun = li.next();
                gun.setPosY(gun.getPosY() - 1);
                if (gun.getPosY() == 0) guns.remove(gun);
                else if (wall[(gun.getPosX() + 1) / 71][(gun.getPosY() + 1) / 20] != null) {
                    guns.remove(gun);
                    destroyBloc((gun.getPosX() + 1) / 71, (gun.getPosY() + 1) / 20);
                }
            }
            if ( gunsCounter % 4000 == 0) {
                window.getWallBreaker().setOkGuns(false);
                guns.clear();
                gunsCounter = 1;
            }
        }
    }
}

