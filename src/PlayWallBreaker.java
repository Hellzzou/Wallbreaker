import java.awt.*;
import java.util.List;

public class PlayWallBreaker implements Runnable{
    private Bloc[][] wall;
    private Window window;
    private List<Ball> balls;
    private Bloc breaker;
    private Ball newBall;
    private int bigCounter = 1;
    private boolean okBig = false;
    private int helpCounter = 1;
    private boolean okHelp = false;
    private int removeBall = 0;
    private boolean remove = false;
    private boolean create = false;
    private int counter = 1;

    PlayWallBreaker(Window window) {
        this.wall = window.getWallBreaker().getWall();
        this.window = window;
        this.balls = window.getWallBreaker().getBalls();
        this.breaker = window.getWallBreaker().getBreaker();
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
                /*
                Si la balle touche un bloc en haut ou en bas : detruit le bloc et inverse FrontY
                Si la balle touche le haut de la fenetre ou le breaker : inverse FrontY
                 */
                if ((wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20] != null ||
                        ball.getPosY() < 460 && wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() + ball.getSize() + 1) / 20] != null) ||
                        (ball.getPosY() == 0 || (ball.getPosX() + ball.getSize() / 2 >= breaker.getPosX() && ball.getPosX() + ball.getSize() / 2 <= breaker.getPosX() + breaker.getSize() && ball.getPosY() == 460))) {
                    destroyBloc(ball,(ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() - 1) / 20);
                    destroyBloc(ball,(ball.getPosX() + (ball.getSize() / 2)) / 71,(ball.getPosY() + ball.getSize() + 1) / 20);
                    ball.setFrontY(!ball.isFrontY());
                }
                /*
                Si la balle touche un bloc a droite ou a gauche : detruit le bloc et inverse FrontY
                Si la balle touche un des bords droite ou gauche de la fenetre : Inverse FrontX
                 */
                if (((ball.getPosX() <= window.getWallBreaker().getWidth() - 71 && wall[(ball.getPosX() + ball.getSize() + 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null) ||
                        (ball.getPosX() >= 71 && wall[(ball.getPosX() - 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null)) ||
                        (ball.getPosX() == window.getWallBreaker().getWidth() - ball.getSize() || ball.getPosX() == 0)) {
                    if (ball.getPosX() <= window.getWallBreaker().getWidth() - 71) destroyBloc(ball,(ball.getPosX() + ball.getSize() + 1) / 71,(ball.getPosY() + (ball.getSize() / 2)) / 20);
                    if (ball.getPosX() >= 71) destroyBloc(ball,(ball.getPosX() - 1) / 71,(ball.getPosY() + (ball.getSize() / 2)) / 20);
                    ball.setFrontX(!ball.isFrontX());
                }
            }
            manageBlocs();
            manageExtraBalls();
            manageBig();
            manageHelp();

            counter ++;

            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            window.getWallBreaker().repaint();
        }
    }
    private void destroyBloc(Ball ball,int posX, int posY) {
        if (wall[posX][posY] != null){
            if (wall[posX][posY].getColor() == Color.ORANGE) {
                breaker.setPosX(breaker.getPosX() - 35);
                breaker.setSize(142);
                okBig = true;
                bigCounter = 1;
            }
            if (wall[posX][posY].getColor() == Color.GREEN) {
                newBall = new Ball(breaker.getPosX() + ( breaker.getSize() / 2 ) - ( ball.getSize() / 2 ), 480 - ball.getSize(), ball.getSize(), true, false, Color.darkGray);
                create = true;
            }
            if (wall[posX][posY].getColor() == Color.CYAN) {
                breaker.setPosX(0);
                breaker.setSize(710);
                okHelp = true;
                helpCounter = 1;
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
    private void manageHelp(){
        if (okHelp){
            helpCounter ++;
            if (helpCounter % 4000 == 0){
                breaker.setPosX(320);
                breaker.setSize(71);
                okHelp =false;
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
    }
}

