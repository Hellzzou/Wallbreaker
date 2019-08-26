import javax.swing.*;
import java.awt.*;

class Window extends JFrame{
    private JPanel labelPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel gamePanel = new JPanel();
    private JPanel animationPanel = new JPanel();
    private WallBreaker wallBreaker = new WallBreaker();
    private Button newGame = new Button("Nouveau", Color.GREEN);
    private Button pause = new Button("Pause", Color.LIGHT_GRAY);
    private Button highScores = new Button("View high scores", Color.LIGHT_GRAY);
    private JLabel levelLabel = new JLabel("Level : 1");
    private JLabel scoreLabel = new JLabel("Score : 0");
    private boolean go = true;
    private int score = 0;
    private int level = 1;

    Window(){
        this.setSize(720,610);
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
        gamePanel.addKeyListener(new KeyBoardListener(wallBreaker));
        animationPanel.add(wallBreaker);
        animationPanel.addKeyListener(new KeyBoardListener(wallBreaker));
        gamePanel.add(animationPanel, BorderLayout.CENTER);
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void initButtonPanel(){
        newGame.setPreferredSize(new Dimension(120,40));
        newGame.addKeyListener(new KeyBoardListener(wallBreaker));
        newGame.addActionListener(e-> {
                    Thread t = new Thread(new PlayWallBreaker(this));
                    t.start();
                });
        pause.setPreferredSize(new Dimension(120,40));
        pause.addKeyListener(new KeyBoardListener(wallBreaker));
        highScores.setPreferredSize(new Dimension(150,40));
        buttonPanel.add(newGame);
        buttonPanel.add(pause);
        buttonPanel.add(highScores);
        buttonPanel.addKeyListener(new KeyBoardListener(wallBreaker));
    }
    private void initLabelPanel(){
        Font font = new Font("arrial", Font.BOLD, 20);
        levelLabel.setFont(font);
        levelLabel.addKeyListener(new KeyBoardListener(wallBreaker));
        scoreLabel.setFont(font);
        scoreLabel.addKeyListener(new KeyBoardListener(wallBreaker));
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(levelLabel, BorderLayout.WEST);
        labelPanel.add(scoreLabel, BorderLayout.EAST);
        labelPanel.addKeyListener(new KeyBoardListener(wallBreaker));
    }

    WallBreaker getWallBreaker() {
        return wallBreaker;
    }

    boolean isGo() { return go; }

    int getScore() { return score; }

    void setGo(boolean go) { this.go = go; }

    void setScore(int score) {
        this.score = score;
        this.scoreLabel.setText("Score : "+ this.score);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.levelLabel.setText("Level : " + this.level);
    }
}
