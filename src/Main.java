public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.addKeyListener(new KeyBoardListener(window));
    }
}
