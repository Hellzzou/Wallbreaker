import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WallBreaker extends JPanel {
        private Bloc[][] wall = new Bloc[10][25];
        private Bloc breaker = new Bloc(320,480,71, Color.darkGray);
        private List<Ball> balls = new ArrayList<>();
        private boolean lost = false;

    WallBreaker(){
        this.setPreferredSize(new Dimension(710,500));
        this.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.WHITE));
        this.setBackground(Color.WHITE);
        this.addKeyListener(new KeyBoardListener(this));
        Ball ball = new Ball(345,460,20,true,false, Color.darkGray);
        balls.add(ball);
        initBlocs();
    }
    private void initBlocs(){
        for ( int i = 5 ; i < 10 ; i++){
            int extraPos = new Random().nextInt(10);
            int extraPos1 = new Random().nextInt(10);
            for ( int j = 0 ; j < 10 ; j++){
                if ( j == extraPos || j == extraPos1) {
                    int extra = new Random().nextInt(4);
                    Color color;
                    switch (extra) {
                        case 0:
                            color = Color.ORANGE;
                            break;
                        case 1:
                            color = Color.RED;
                            break;
                        case 2:
                            color = Color.GREEN;
                            break;
                        case 3 :
                            color = Color.CYAN;
                            break;
                        default :
                            color = Color.darkGray;
                    }
                    wall[j][i] = new Bloc(j * 71,i * 20,71,color);
                }
                else wall[j][i] = new Bloc(j * 71,i * 20, 71,Color.darkGray);
            }
        }
    }
    public void paintComponent(Graphics g){
        for ( int i = 0 ; i < 10 ; i++){
            for ( int j = 0 ; j < 25 ; j++){
                if(wall[i][j] != null) {
                    g.setColor(wall[i][j].getColor());
                    g.fillRoundRect(wall[i][j].getPosX(),wall[i][j].getPosY(),wall[i][j].getSize() - 2,18,10,10);
                    if ( wall[i][j].getColor() != Color.darkGray){
                        String str = "";
                        if ( wall[i][j].getColor() == Color.ORANGE ) str = "bigger";
                        if ( wall[i][j].getColor() == Color.GREEN ) str = "ball";
                        if ( wall[i][j].getColor() == Color.RED ) str = "guns";
                        if ( wall[i][j].getColor() == Color.CYAN ) str = "help";
                        g.setColor(Color.darkGray);
                        g.drawString(str,wall[i][j].getPosX() + 35 - (str.length() / 2 * 6),wall[i][j].getPosY() + 13);
                    }
                }
            }
        }
        g.setColor(Color.darkGray);
        g.fillRoundRect(breaker.getPosX(),breaker.getPosY(),breaker.getSize() - 2,18,10,10);
        for (Ball ball : balls) {
            g.fillOval(ball.getPosX(), ball.getPosY(), ball.getSize(), ball.getSize());
        }
        if (lost){
            Font font = new Font("arrial", Font.BOLD, 50);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("PERDU", 250, 300);
        }
    }
    void lowerBlocs() {
        for (int i = 24; i >= 5 ; i--) {
            int extraPos = new Random().nextInt(10);
            int extraPos1 = new Random().nextInt(10);
            for (int j = 0; j < 10; j++) {
                if ( i > 5 && wall[j][i - 1] != null){
                    wall[j][i] = new Bloc(wall[j][i-1].getPosX(),wall[j][i-1].getPosY() + 20,71,wall[j][i-1].getColor());
                }
                else if ( i == 5 ) {
                    if ( j == extraPos || j == extraPos1) {
                        int extra = new Random().nextInt(4);
                        Color color;
                        switch (extra) {
                            case 0:
                                color = Color.ORANGE;
                                break;
                            case 1:
                                color = Color.RED;
                                break;
                            case 2:
                                color = Color.GREEN;
                                break;
                            case 3:
                                color = Color.CYAN;
                                break;
                            default:
                                color = Color.darkGray;
                        }
                        wall[j][i] = new Bloc(j * 71,100,71,color);
                    }
                    else wall[j][i] = new Bloc(j * 71,100,71,Color.darkGray);
                }
            }
        }
    }

    Bloc getBreaker() { return breaker; }

    List<Ball> getBalls() { return balls; }

    Bloc[][] getWall() { return wall; }

    void setLost(boolean lost) { this.lost = lost; }
}

