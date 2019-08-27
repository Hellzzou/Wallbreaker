import java.io.Serializable;

class Joueur implements Serializable {
    private String name;
    private int score;
    private int level;

    Joueur(String name, int score, int level) {
        this.name = name;
        this.score = score;
        this.level = level;
    }
    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    int getLevel() {
        return level;
    }
}
