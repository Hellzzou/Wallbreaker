import java.io.*;
import java.util.List;


class FileWriter {

    FileWriter(List<Joueur> joueurs) {
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("BestScores.pages"))));
            oos.writeObject(joueurs);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                assert oos!= null;
                oos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}