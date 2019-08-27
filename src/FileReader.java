import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileReader implements Serializable{
    private List<Joueur> joueurs = new ArrayList<>();

    FileReader() {
        ObjectInputStream ois = null;
        File file = new File("BestScores.pages");

        if ( file.length() > 0){
            try {
                ois = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)));
                joueurs = (ArrayList<Joueur>) ois.readObject();
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            } finally {
                try {
                    assert ois != null;
                    ois.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    List<Joueur> getJoueurs() {
        return joueurs;
    }
}