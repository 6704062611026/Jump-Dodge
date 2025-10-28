package character;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Wave {
    public int x,y,width,height,speed;
    private int xStart;
    private BufferedImage image;
    public Wave(int x,int y,int w,int h,int speed,JPanel game){
        this.x=x;
        this.xStart=x;
        this.y=y;
        this.width=w;
        this.height=h;
        this.speed=speed;
        try {
            image = ImageIO.read(new File("image/tree.png")); 
        } catch (Exception e) {
            e.printStackTrace();
}

        move(game);
    }
    public void move(JPanel game){
        Timer timer = new Timer(50, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e){
        x -= speed;
        game.repaint();
        if(x < 0){
            x = xStart;
        }
    }
});

        timer.start();
    }
    public BufferedImage getImage() {
    return image;
}

    
}


