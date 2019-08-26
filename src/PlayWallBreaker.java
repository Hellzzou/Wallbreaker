import java.awt.*;
import java.util.List;

public class PlayWallBreaker implements Runnable{
    private Bloc[][] wall;
    private Window window;
    private List<Ball> balls;
    private Bloc breaker;
    private Ball newBall;
    private boolean create = false;

    PlayWallBreaker(Window window) {
        this.wall = window.getWallBreaker().getWall();
        this.window = window;
        this.balls = window.getWallBreaker().getBalls();
        this.breaker = window.getWallBreaker().getBreaker();
    }

    public void run(){
        int counter = 1;
        while(window.isGo()){
            for (Ball ball : balls) {
                if (ball.isFrontX()) ball.setPosX(ball.getPosX() + 1);
                else ball.setPosX(ball.getPosX() - 1);
                if (ball.isFrontY()) ball.setPosY(ball.getPosY() + 1);
                else ball.setPosY(ball.getPosY() - 1);
                if (ball.getPosX() == window.getWallBreaker().getWidth() - ball.getSize()) ball.setFrontX(false);
                if (ball.getPosY() == window.getWallBreaker().getHeight() - ball.getSize()) {
                    window.setGo(false);
                    window.getWallBreaker().setLost(true);
                }
                if (ball.getPosX() == 0) ball.setFrontX(true);
                if (ball.getPosY() == 0) ball.setFrontY(true);
                if (wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20] != null) {
                    ball.setFrontY(true);
                    if (wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20].getColor() == Color.ORANGE ){
                        breaker.setPosX(breaker.getPosX() - 35);
                        breaker.setSize(142);
                    }
                    if (wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20].getColor() == Color.GREEN ){
                        newBall = new Ball(wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20].getPosX() + 25,
                                wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20].getPosY(), 20, !ball.isFrontX(), ball.isFrontY(), Color.darkGray);
                        create = true;
                    }

                    wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() - 1) / 20] = null;
                    window.setScore(window.getScore() + 5);
                }
                if (ball.getPosY() < 460) {
                    if (wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() + ball.getSize() + 1) / 20] != null) {
                        ball.setFrontY(false);
                        wall[(ball.getPosX() + (ball.getSize() / 2)) / 71][(ball.getPosY() + ball.getSize() + 1) / 20] = null;
                        window.setScore(window.getScore() + 5);
                    }
                }
                if (ball.getPosX() <= window.getWallBreaker().getWidth() - 71) {
                    if (wall[(ball.getPosX() + ball.getSize() + 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null) {
                        ball.setFrontX(false);
                        wall[(ball.getPosX() + ball.getSize() + 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] = null;
                        window.setScore(window.getScore() + 5);
                    }
                }
                if (ball.getPosX() >= 71) {
                    if (wall[(ball.getPosX() - 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] != null) {
                        ball.setFrontX(true);
                        wall[(ball.getPosX() - 1) / 71][(ball.getPosY() + (ball.getSize() / 2)) / 20] = null;
                        window.setScore(window.getScore() + 5);
                    }
                }
                if (ball.getPosX() + ball.getSize() / 2 >= breaker.getPosX() && ball.getPosX() + ball.getSize() / 2 <= breaker.getPosX() + breaker.getSize() && ball.getPosY() == 460)
                    ball.setFrontY(false);
            }
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (create) {
                balls.add(newBall);
                create = false;
            }
            if( counter % 4000 == 0){
                window.getWallBreaker().lowerBlocs();
            }
            counter ++;
            window.getWallBreaker().repaint();
        }
    }
}
