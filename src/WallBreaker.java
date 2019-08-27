import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class WallBreaker extends JPanel {
    private Window window;
        private Bloc[][] wall = new Bloc[10][25];
        private Bloc breaker = new Bloc(320,480,71, Color.darkGray);
        private List<Ball> balls = new ArrayList<>();
        private boolean lost = false;
        private List<Gun> guns = new LinkedList<>();
        private Bloc invicibleBreaker;
        private boolean okHelp = false;
        private boolean okGuns = false;
        private int extraPos, extraPos1;

    WallBreaker(Window window){
        this.window = window;
        this.setPreferredSize(new Dimension(710,500));
        this.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.WHITE));
        this.setBackground(Color.WHITE);
        this.addKeyListener(new KeyBoardListener(window));
        Ball ball = new Ball(345,460,20,true,false);
        balls.add(ball);
        initBlocs();
    }
    private void initBlocs(){
        for ( int i = 5 ; i < 10 ; i++){
            extraPos = new Random().nextInt(10);
            extraPos1 = new Random().nextInt(10);
            for ( int j = 0 ; j < 10 ; j++){
                randomExtras(i,j);
            }
        }
    }
    public void paintComponent(Graphics g){
        paintGunsAndInvincibility(g);
        paintBlocsAndBalls(g);
        paintLost(g);
    }
    private void paintLost(Graphics g){
        if (lost){
            Font font = new Font("arrial", Font.BOLD, 50);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("PERDU", 250, 300);
            writeNewHighScore();
        }
    }
    private void writeNewHighScore(){
        int counter = 1;
        boolean found = false;
        FileReader filereader = new FileReader();
        List<Joueur> joueurs = filereader.getJoueurs();
        if ( joueurs.size() == 0 ){
            System.out.println("1");
            String name = JOptionPane.showInputDialog(null,"Vous entrez dans le top 5, veuillez entrer votre nom : ", "Top 5 !",JOptionPane.QUESTION_MESSAGE);
            joueurs.add(new Joueur(name,window.getScore(),window.getLevel()));
            found = true;
        }
        else {
            System.out.println("2");
            for (int i = 0; i < joueurs.size(); i++) {
                if (!found) {
                    if (window.getScore() > joueurs.get(i).getScore()) {
                        String name = JOptionPane.showInputDialog(null, "Vous entrez dans le top 5, veuillez entrer votre nom : ", "Top 5 !", JOptionPane.QUESTION_MESSAGE);
                        joueurs.add(i, new Joueur(name, window.getScore(), window.getLevel()));
                        found = true;
                    }
                    counter++;
                }
                if (i >= 5) joueurs.remove(i);
            }
        }
        if ( counter < 5  && !found) {
            System.out.println("3");
            String name = JOptionPane.showInputDialog(null,"Vous entrez dans le top 5, veuillez entrer votre nom : ", "Top 5 !",JOptionPane.QUESTION_MESSAGE);
            joueurs.add(new Joueur(name,window.getScore(),window.getLevel()));
        }
        FileWriter fileWriter = new FileWriter(joueurs);
    }
    private void paintGunsAndInvincibility(Graphics g){
        if ( okHelp ){
            g.setColor(Color.CYAN);
            g.fillRect(invicibleBreaker.getPosX(),invicibleBreaker.getPosY(),invicibleBreaker.getSize(),20);
        }
        if ( okGuns ){
            g.setColor(Color.darkGray);
            for ( Gun gun : guns){
                g.fillRect(gun.getPosX(),gun.getPosY(),3,16);
            }
        }
    }
    private void paintBlocsAndBalls(Graphics g){
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
                        if ( wall[i][j].getColor() == Color.PINK ) str = "speed";
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
    }
    void lowerBlocs() {
        for (int i = 24; i >= 5 ; i--) {
            extraPos = new Random().nextInt(10);
            extraPos1 = new Random().nextInt(10);
            for (int j = 0; j < 10; j++) {
                if ( i > 5 && wall[j][i - 1] != null){
                    wall[j][i] = new Bloc(wall[j][i-1].getPosX(),wall[j][i-1].getPosY() + 20,71,wall[j][i-1].getColor());
                }
                else if ( i == 5 ) {
                    randomExtras(i,j);
                }
            }
        }
    }
    void randomExtras(int i, int j){
        if ( j == extraPos || j == extraPos1) {
            int extra = new Random().nextInt(5);
            Color color = Color.darkGray;
            if ( extra == 0 ) color = Color.ORANGE;
            if ( extra == 1 ) color = Color.RED;
            if ( extra == 2 ) color = Color.GREEN;
            if ( extra == 3 ) color = Color.CYAN;
            if ( extra == 4 ) color = Color.PINK;
            wall[j][i] = new Bloc(j * 71,i * 20,71,color);
        }
        else wall[j][i] = new Bloc(j * 71,i * 20,71,Color.darkGray);
    }

    Bloc getBreaker() { return breaker; }

    List<Ball> getBalls() { return balls; }

    Bloc[][] getWall() { return wall; }

    void setLost(boolean lost) { this.lost = lost; }

    List<Gun> getGuns() { return guns; }

    boolean isOkGuns() { return okGuns; }

    void setOkGuns(boolean okGuns) { this.okGuns = okGuns; }

    Bloc getInvicibleBreaker() { return invicibleBreaker; }

    void setInvicibleBreaker(Bloc invicibleBreaker) { this.invicibleBreaker = invicibleBreaker; }

    boolean isOkHelp() { return okHelp; }

    void setOkHelp(boolean okHelp) { this.okHelp = okHelp; }

    public void setBreaker(Bloc breaker) { this.breaker = breaker; }

    public int getExtraPos() {
        return extraPos;
    }

    public void setExtraPos(int extraPos) {
        this.extraPos = extraPos;
    }

    public int getExtraPos1() {
        return extraPos1;
    }

    public void setExtraPos1(int extraPos1) {
        this.extraPos1 = extraPos1;
    }

    public boolean isLost() {
        return lost;
    }
}

