import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
        private WallBreaker wallBreaker;

    KeyBoardListener(WallBreaker wallbreaker){
        this.wallBreaker = wallbreaker;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_LEFT){
            if(wallBreaker.getBreaker().getPosX() > 0 ){
                wallBreaker.getBreaker().setPosX(wallBreaker.getBreaker().getPosX() - wallBreaker.getBreaker().getSpeed());
                wallBreaker.repaint();
            }
        }
        if ( e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(wallBreaker.getBreaker().getPosX() + wallBreaker.getBreaker().getSize() < wallBreaker.getWidth() ){
                wallBreaker.getBreaker().setPosX(wallBreaker.getBreaker().getPosX() + wallBreaker.getBreaker().getSpeed());
                wallBreaker.repaint();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        if ( e.getKeyChar() == 'g' && wallBreaker.isOkGuns() && wallBreaker.getGuns().size() < 2){
                wallBreaker.getGuns().add(0,new Gun(wallBreaker.getBreaker().getPosX() + wallBreaker.getBreaker().getSize() / 3, 460));
                wallBreaker.getGuns().add(0,new Gun(wallBreaker.getBreaker().getPosX() + wallBreaker.getBreaker().getSize() * 2 / 3, 463));

        }
    }
}