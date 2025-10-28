
package character;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;
import javax.imageio.ImageIO;

public class Cat {
    public int x, y, catSize, health;
    private int jumpHigh = 100;
    private boolean crouching = false;
    private int originalHeight;
    private boolean isJumping = false;
    private int baseY;
    private int jumpStartY;

    
    private BufferedImage[] runFrames;
    private BufferedImage jumpImage;
    private BufferedImage crouchImage;
    private int currentRunFrame = 0;
    private int totalRunFrames = 2; 
    

    public boolean isJumping() {
        return isJumping;
    }

    public Cat(int x, int y, int catSize, int health) {
        this.x = x;
        this.y = y;
        this.baseY = y;
        this.catSize = catSize;
        this.health = health;
        this.originalHeight = catSize;

        
        try {
            
            jumpImage = ImageIO.read(new File("image/cat_jump.png"));
            crouchImage = ImageIO.read(new File("image/cat_crouch.png")); 

           
            runFrames = new BufferedImage[totalRunFrames];
            for (int i = 0; i < totalRunFrames; i++) {
                
                runFrames[i] = ImageIO.read(new File("image/cat_run" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            try {
                BufferedImage fallback = ImageIO.read(new File("image/Cat.png")); 
                jumpImage = fallback;
                crouchImage = fallback;
                runFrames = new BufferedImage[1];
                runFrames[0] = fallback;
                totalRunFrames = 1;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
    }

    
    public void jump(JPanel game) {
        if (crouching || isJumping) return;
        isJumping = true;
        jumpStartY = y;
        y = baseY - jumpHigh;

        game.repaint();
        
        Timer timer = new Timer(450, e -> {
            y = crouching ? baseY + originalHeight / 2 : baseY; 
            isJumping = false;
            game.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void cancelJumpAndCrouch(JPanel game) {
        if (isJumping) {
            isJumping = false;
            y = baseY;
        }
        crouchDown();
        game.repaint();
    }

    
    public void crouchDown() {
        if (!crouching) {
            crouching = true;
            y = baseY + originalHeight / 2; 
            catSize = originalHeight / 2; 
        }
    }

    
    public void standUp() {
        if (crouching) {
            crouching = false;
            y = baseY; 
            catSize = originalHeight; 
        }
    }

    public boolean isCrouching() {
        return crouching;
    }

    
    public BufferedImage getImage() {
        BufferedImage imageToReturn;

        if (isJumping()) { 
            imageToReturn = jumpImage;
        } else if (isCrouching()) { 
            imageToReturn = crouchImage; 
        } else {
            
            imageToReturn = runFrames[currentRunFrame];
            currentRunFrame = (currentRunFrame + 1) % totalRunFrames; 
        }

        if (imageToReturn == null) {
            try {
                return ImageIO.read(new File("image/Cat.png"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return imageToReturn;
    }
}