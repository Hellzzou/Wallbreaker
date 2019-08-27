import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private String name;
    private Color color;

    Button(String name, Color color, Window window) {
        this.name = name;
        this.color = color;
        this.setPreferredSize(new Dimension(120,40));
        this.addKeyListener(new KeyBoardListener(window));
    }
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int[] triangle1x = {0,this.getWidth(),this.getWidth() / 2};
        int[] triangle1y = {0,0,this.getHeight() / 2};
        int[] triangle2x = {this.getWidth(),this.getWidth(),this.getWidth() / 2};
        int[] triangle2y = {0,this.getHeight(),this.getHeight() / 2};
        int[] triangle3y = {this.getHeight(),this.getHeight(),this.getHeight() / 2};
        int[] triangle4x = {0,0,this.getWidth() / 2};
        GradientPaint gp = new GradientPaint(0,0,color,0,10,Color.WHITE,false);
        g2d.setPaint(gp);
        g2d.fillPolygon(triangle1x,triangle1y,3);
        GradientPaint gp1 = new GradientPaint(this.getWidth(),this.getHeight(),color,this.getWidth() - 28,this.getHeight(),Color.WHITE,false);
        g2d.setPaint(gp1);
        g2d.fillPolygon(triangle2x,triangle2y,3);
        GradientPaint gp2 = new GradientPaint(this.getWidth(),this.getHeight(),color,this.getWidth(),this.getHeight() - 10,Color.WHITE,false);
        g2d.setPaint(gp2);
        g2d.fillPolygon(triangle1x,triangle3y,3);
        GradientPaint gp3 = new GradientPaint(0,0,color,28,0,Color.WHITE,false);
        g2d.setPaint(gp3);
        g2d.fillPolygon(triangle4x,triangle2y,3);
        g2d.setColor(Color.BLACK);
        g2d.drawString(this.name,this.getWidth() / 2 - ( this.name.length() * 7 / 2 ), this.getHeight() / 2 + 5);

    }

    @Override
    public void setName(String name) { this.name = name; }

    void setColor(Color color) { this.color = color; }
}