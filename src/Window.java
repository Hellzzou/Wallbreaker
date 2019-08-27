import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class Window extends JFrame{
    private JPanel labelPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel gamePanel = new JPanel();
    private JPanel progressPanel = new JPanel();
    private JProgressBar levelBar = new JProgressBar();
    private JProgressBar biggerBar = new JProgressBar();
    private JProgressBar gunsBar = new JProgressBar();
    private JProgressBar helpBar = new JProgressBar();
    private JProgressBar speedBar = new JProgressBar();
    private JPanel animationPanel = new JPanel();
    private WallBreaker wallBreaker = new WallBreaker(this);
    private Button newGame = new Button("Nouveau", Color.GREEN,this);
    private Button pause = new Button("Pause", Color.LIGHT_GRAY,this);
    private Button highScores = new Button("View high scores", Color.LIGHT_GRAY,this);
    private JLabel levelLabel = new JLabel("Level : 1");
    private JLabel scoreLabel = new JLabel("Score : 0");
    private List<Joueur> joueurs = new ArrayList<>();
    private boolean go = false;
    private boolean first = false;
    private int score = 0;
    private int level = 1;

    Window(){
        this.setSize(720,660);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGamePanel();
        this.setContentPane(gamePanel);
        this.setVisible(true);
    }
    private void initGamePanel(){
        initButtonPanel();
        initLabelPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(labelPanel,BorderLayout.NORTH);
        gamePanel.addKeyListener(new KeyBoardListener(this));
        animationPanel.add(wallBreaker);
        animationPanel.addKeyListener(new KeyBoardListener(this));
        gamePanel.add(animationPanel, BorderLayout.CENTER);
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void initButtonPanel(){
        newGame.setPreferredSize(new Dimension(120,40));
        newGame.addKeyListener(new KeyBoardListener(this));
        newGame.addActionListener(e-> {
            int option = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir recommencer?"
                    , "Recommencer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( option == JOptionPane.OK_OPTION) {
                this.setScore(0);
                this.setLevel(1);
                this.setFirst(true);
                this.getWallBreaker().setLost(false);
                this.setGo(true);
                disableButton(newGame);
                enableButton(pause, "Pause", Color.orange);
                disableButton(highScores);
                Thread t = new Thread(new PlayWallBreaker(this));
                t.start();
            }
        });
        pause.setPreferredSize(new Dimension(120,40));
        pause.addKeyListener(new KeyBoardListener(this));
        pause.addActionListener(e->{
                if(this.isGo()) {
                    this.setGo(false);
                    enableButton(newGame, "Nouveau", Color.GREEN);
                    enableButton(pause, "Start", Color.GREEN);
                    enableButton(highScores, "HighScores", Color.LIGHT_GRAY);
                }
                else {
                    this.setGo(true);
                    disableButton(newGame);
                    enableButton(pause, "Pause", Color.ORANGE);
                    disableButton(highScores);
                    Thread t = new Thread(new PlayWallBreaker(this));
                    t.start();
                }
                });
        disableButton(pause);
        highScores.setPreferredSize(new Dimension(150,40));
        highScores.addActionListener(e->{
                    FileReader filereader = new FileReader();
                    joueurs = filereader.getJoueurs();
                    String top5 = "\t" + "\tTOP5\n";
                    for (int i = 0 ; i < joueurs.size() ; i++){
                        top5 += i + 1 + "\t" + joueurs.get(i).toString();
                    }
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, top5, "TOP5",JOptionPane.INFORMATION_MESSAGE);
                });
        buttonPanel.add(newGame);
        buttonPanel.add(pause);
        buttonPanel.add(highScores);
        buttonPanel.addKeyListener(new KeyBoardListener(this));
    }
    void disableButton(Button b){
        b.setName("");
        b.setColor(Color.LIGHT_GRAY);
        b.setEnabled(false);
    }
    void enableButton(Button b, String str, Color color){
        b.setEnabled(true);
        b.setName(str);
        b.setColor(color);
    }
    private void initLabelPanel(){
        initProgressBar();
        Font font1 = new Font("arrial", Font.BOLD, 20);
        levelLabel.setFont(font1);
        levelLabel.addKeyListener(new KeyBoardListener(this));
        scoreLabel.setFont(font1);
            scoreLabel.addKeyListener(new KeyBoardListener(this));
        levelBar.setMaximum(100);
        levelBar.setMinimum(0);
        levelBar.setStringPainted(true);
        labelPanel.setPreferredSize(new Dimension( 710,70));
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(levelLabel, BorderLayout.WEST);
        labelPanel.add(scoreLabel, BorderLayout.EAST);
        labelPanel.add(levelBar, BorderLayout.CENTER);
        labelPanel.add(progressPanel,BorderLayout.SOUTH);
        labelPanel.addKeyListener(new KeyBoardListener(this));
    }
    private void initProgressBar(){
        Font font = new Font("arrial", Font.BOLD, 12);
        JPanel progress1 = new JPanel();
        JPanel progress2 = new JPanel();
        String[] barlabels = {"Bigger : ","Guns : ","Help : ","Speed : "};
        JLabel[] labels = new JLabel[4];
        for ( int j = 0 ; j < 4 ; j++){
            labels[j] = new JLabel(barlabels[j]);
            labels[j].setFont(font);
        }
        JProgressBar[] progressBars = {biggerBar,gunsBar,helpBar,speedBar};
        for ( JProgressBar bar : progressBars){
            bar.setMaximum(100);
            bar.setMinimum(0);
            bar.setStringPainted(true);
            bar.setPreferredSize(new Dimension(300,15));
        }
        progressPanel.setLayout(new BorderLayout());
        for ( int i = 0 ; i < 4 ; i++ ){
            if ( i < 2 ){
                progress1.add(labels[i]);
                progress1.add(progressBars[i]);
            }
            else {
                progress2.add(labels[i]);
                progress2.add(progressBars[i]);
            }
        }
        progressPanel.add(progress1,BorderLayout.NORTH);
        progressPanel.add(progress2,BorderLayout.SOUTH);
    }

    WallBreaker getWallBreaker() { return wallBreaker; }

    boolean isGo() { return go; }

    int getScore() { return score; }

    void setGo(boolean go) { this.go = go; }

    void setScore(int score) {
        this.score = score;
        this.scoreLabel.setText("Score : "+ this.score);
    }

    int getLevel() { return level; }

    void setLevel(int level) {
        this.level = level;
        this.levelLabel.setText("Level : " + this.level);
    }

    JProgressBar getLevelBar() { return levelBar; }

    JProgressBar getBiggerBar() { return biggerBar; }

    JProgressBar getGunsBar() { return gunsBar; }

    JProgressBar getHelpBar() { return helpBar; }

    JProgressBar getSpeedBar() { return speedBar; }

    void setWallBreaker(WallBreaker wallBreaker) { this.wallBreaker = wallBreaker; }

    boolean isFirst() { return first; }

    void setFirst(boolean first) { this.first = first; }

    public Button getNewGame() {
        return newGame;
    }

    public void setNewGame(Button newGame) {
        this.newGame = newGame;
    }

    public Button getPause() {
        return pause;
    }

    public void setPause(Button pause) {
        this.pause = pause;
    }

    public Button getHighScores() {
        return highScores;
    }

    public void setHighScores(Button highScores) {
        this.highScores = highScores;
    }
}
