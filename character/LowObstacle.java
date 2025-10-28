package character;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

public class LowObstacle {
    public int x, y, width, height, speed;
    private int xStart;

    
    private BufferedImage[] frames; 
    private int currentFrame = 0;
    private int totalFrames = 2; 
    
   
    private Timer animationTimer;
    
    private final int ANIMATION_DELAY = 150; 
    

    public LowObstacle(int x, int y, int w, int h, int speed, JPanel game){
        this.x = x;
        this.xStart = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.speed = speed;
        
        
        try {
            frames = new BufferedImage[totalFrames];
            for (int i = 0; i < totalFrames; i++) {
                frames[i] = ImageIO.read(new File("image/Bird" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            try {
                BufferedImage fallback = ImageIO.read(new File("image/Bird.png"));
                frames = new BufferedImage[1];
                frames[0] = fallback;
                totalFrames = 1; 
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
       

        move(game); 
        
     
        if (totalFrames > 1) {
            animationTimer = new Timer(ANIMATION_DELAY, e -> {
               
                currentFrame = (currentFrame + 1) % totalFrames;
                
                
            });
            animationTimer.start();
        }
    }
    
    public void move(JPanel game){
        
        Timer timer = new Timer(50, e -> { 
            x -= speed;
            game.repaint(); 
            if(x < 0){
                x = xStart;
            }
        });
        timer.start();
    }

    
    public BufferedImage getImage() {
        
        return frames[currentFrame];
    }
}