import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
        private Window window;

    KeyBoardListener(Window window){
            this.window = window;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_LEFT && window.isGo()){
            if(window.getWallBreaker().getBreaker().getPosX() > 0 ){
                window.getWallBreaker().getBreaker().setPosX(window.getWallBreaker().getBreaker().getPosX() - window.getWallBreaker().getBreaker().getSpeed());
                window.getWallBreaker().repaint();
            }
        }
        if ( e.getKeyCode() == KeyEvent.VK_RIGHT && window.isGo()){
            if(window.getWallBreaker().getBreaker().getPosX() + window.getWallBreaker().getBreaker().getSize() < window.getWallBreaker().getWidth() ){
                window.getWallBreaker().getBreaker().setPosX(window.getWallBreaker().getBreaker().getPosX() + window.getWallBreaker().getBreaker().getSpeed());
                window.getWallBreaker().repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if ( e.getKeyChar() == 'g' && window.getWallBreaker().isOkGuns() && window.getWallBreaker().getGuns().size() < 2 && window.isGo()){
            window.getWallBreaker().getGuns().add(0,new Gun(window.getWallBreaker().getBreaker().getPosX() + window.getWallBreaker().getBreaker().getSize() / 3, 460));
            window.getWallBreaker().getGuns().add(0,new Gun(window.getWallBreaker().getBreaker().getPosX() + window.getWallBreaker().getBreaker().getSize() * 2 / 3, 463));

        }
    }
}