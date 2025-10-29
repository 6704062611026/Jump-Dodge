import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import character.Cat;
import character.LowObstacle;
import character.Wave;
import event.Event;
import java.awt.BasicStroke;  
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements KeyListener{
    private List<Rectangle> occupiedRects = new ArrayList<>();
    private Timer gameTimer; 
    private double timeLeft = 60.0;
    private int score = 0; 
    private boolean isInvincible = false; 
    private BufferedImage backgroundImage;
    private boolean isColliding = false; 

    private int backgroundX = 0;
    private int backgroundSpeed = 5;  

    private long lastInvincibleTime = 0;
    private final int invincibleDuration = 2000; 
    private final int invincibleCooldown = 30_000; 
    
   
    private boolean isHitInvincible = false; 
    private long lastHitTime = 0;
    private final int hitCooldown = 1000;
    

    Display display;
    int level;
    int gameSpeed=30;
    long lastPress=0;
    Cat cat = new Cat(150,290,50,100);
    long lastJumpTime = 0; 
    int jumpCooldown = 500;
 
    Wave[] wave;
    LowObstacle[] lowObstacles;
    
    public Game(Display display, int level) {
      
        startGameTimer();
        this.display = display;
        this.level = level;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.occupiedRects.clear();
        int obstacleSpeed = 12 + level * 10; 
        occupiedRects.clear();
        lowObstacles = makeLowObstacleSet(1 + level, obstacleSpeed);
        wave = makeWaveSet(level + 2, obstacleSpeed);
        try {
            backgroundImage = ImageIO.read(new File("image/background.jpg"));  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        isColliding = false;

        if (isHitInvincible) {
            long now = System.currentTimeMillis();
            if (now - lastHitTime > hitCooldown) {
                isHitInvincible = false; 
            }
        }
        
       
        backgroundX -= backgroundSpeed;
        if (backgroundX <= -getWidth()) {
            backgroundX = 0;
        }
        g2.drawImage(backgroundImage, backgroundX, 0, getWidth(), getHeight(), null);
        g2.drawImage(backgroundImage, backgroundX + getWidth(), 0, getWidth(), getHeight(), null);
       
        int maxHealth = 100; 
        int barWidth = 200; 
        int barHeight = 20;  
        int barX = 30; 
        int barY = 30; 
        float healthPercent = Math.max(cat.health, 0) / (float) maxHealth;
        int currentBarWidth = (int) (barWidth * healthPercent);
        g2.setColor(Color.GRAY);
        g2.fillRect(barX, barY, barWidth, barHeight);
        g2.setColor(Color.RED);
        g2.fillRect(barX, barY, currentBarWidth, barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);
        g2.setColor(Color.WHITE);
        g2.drawString("Time Left: " + (int)Math.ceil(timeLeft) + "s", 1050, 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + score, 1050, 60); 

       
        g2.setColor(Color.BLACK);
        
       
        g2.setColor(Color.BLACK);

        if (!isInvincible) { 
            
       
            if (isHitInvincible) {
                
                if ((System.currentTimeMillis() / 100) % 2 == 0) { 
                    g2.drawImage(cat.getImage(),cat.x,cat.y ,cat.catSize,cat.catSize,null);
                }
            } else {
               
                g2.drawImage(cat.getImage(),cat.x,cat.y ,cat.catSize,cat.catSize,null);
            }
        }
       
        for (Wave w : wave) {
            if (w.getImage() != null) {
                g2.drawImage(w.getImage(), w.x, w.y, w.width, w.height, null);
            } else {
                g2.setColor(Color.BLACK);
                g2.drawRect(w.x, w.y, w.width, w.height); 
            }

            if (!isInvincible && !isHitInvincible && Event.checkHit(cat, w)) {
                g2.setStroke(new BasicStroke(10.0f));
                g2.setColor(Color.RED);
                g2.drawRect(0, 0, 1180, 360);
                
                cat.health -= 15; 
                score = Math.max(0, score - 50); 
                isColliding = true; 
               
                isHitInvincible = true; 
                lastHitTime = System.currentTimeMillis();
            }
        }

        for(LowObstacle obs : lowObstacles){
            if (obs.getImage() != null) {
                g2.drawImage(obs.getImage(), obs.x, obs.y, obs.width, obs.height, null);
            } else {
                g2.setColor(Color.BLACK);
                g2.drawRect(obs.x, obs.y, obs.width, obs.height); 
            }

            if (!isInvincible && !isHitInvincible && Event.checkHit(cat, obs)) {
                if (!cat.isCrouching()) { 
                    g2.setStroke(new BasicStroke(8.0f));
                    g2.setColor(Color.RED);
                    g2.drawRect(0, 0, 1180, 360);
                    
                    cat.health -= 10; 
                    score = Math.max(0, score - 30); 
                    isColliding = true; 
                    
                
                    isHitInvincible = true;
                    lastHitTime = System.currentTimeMillis();
                }
            }
        }

      
        long now = System.currentTimeMillis();
        long timeSinceLastUse = now - lastInvincibleTime;
        long cooldownRemaining = invincibleCooldown - timeSinceLastUse;
        if (cooldownRemaining <= 0 && !isInvincible) {
            g2.setColor(Color.white);
            g2.drawString("Invisible(Spacebar)", cat.x-25, cat.y - 1);
        } else {
            if (!isInvincible && cooldownRemaining > 0) {
                float percent = cooldownRemaining / (float) invincibleCooldown;
                int barWidth1 = cat.catSize; 
                int barHeight1 = 8;
                int filled = (int) (barWidth1 * percent);
                int barX1 = cat.x;
                int barY1 = cat.y - 15; 
                g2.setColor(Color.GRAY);
                g2.fillRect(barX1, barY1, barWidth1, barHeight1);
                g2.setColor(Color.ORANGE);
                g2.fillRect(barX1, barY1, filled, barHeight1);
                g2.setColor(Color.BLACK);
                g2.drawRect(barX1, barY1, barWidth1, barHeight1);
            }
        }

        if (cat.health <= 0) {
            if (gameTimer != null) gameTimer.stop();
            display.gameOver((int)Math.ceil(timeLeft), level, score);
        }
    }
    
    List<Integer> occupiedX = new ArrayList<>();
    int baseXWave = 1000;
    int spacingWaveMin = 800;
    int spacingWaveMax = 1000;
    int baseXLow = 1500;
    int spacingLowMin = 700;
    int spacingLowMax = 900;
    private LowObstacle[] makeLowObstacleSet(int count, int speed) {
        LowObstacle[] set = new LowObstacle[count];
        int PADDING = 150; 
        int MAX_X = 6500; 
        for (int i = 0; i < count; i++) {
            int attempts = 0;
            int x=1500;
            Rectangle newRect;
            do {
                newRect = new Rectangle(x, 240, 30, 70);
                attempts++;
                if (attempts > 300) break;
            } while (isOverlapping(newRect, occupiedRects, PADDING));
            if (attempts > 300) {
                x = MAX_X + (attempts - 300) * 50;
                newRect = new Rectangle(x, 240, 30, 70);
            }
            set[i] = new LowObstacle(x, 225, 100, 100, speed, this);
            occupiedRects.add(newRect);
        }
        return set;
    }
    private Wave[] makeWaveSet(int waveNumber, int speed) {
        Wave[] waveSet = new Wave[waveNumber];
        int PADDING = 600; 
        int MIN_X = 1000;
        int MAX_X = 2000;
        for (int i = 0; i < waveNumber; i++) {
            int attempts = 0;
            int x;
            Rectangle newRect;
            do {
                x = MIN_X + (int) (Math.random() * (MAX_X - MIN_X));
                newRect = new Rectangle(x, 300, 30, 40);
                attempts++;
                if (attempts > 300) break;
            } while (isOverlapping(newRect, occupiedRects, PADDING));
            if (attempts > 300) {
                x = MAX_X + (attempts - 300) * 50;
                newRect = new Rectangle(x, 300, 30, 40);
            }
            waveSet[i] = new Wave(x, 250, 100, 90, speed, this);
            occupiedRects.add(newRect);
        }
        return waveSet;
    }
    private boolean isOverlapping(Rectangle newRect, List<Rectangle> rects, int padding) {
        for (Rectangle r : rects) {
            if (Math.abs(newRect.y - r.y) > 200) continue;
            int leftA = newRect.x - padding;
            int rightA = newRect.x + newRect.width + padding;
            int leftB = r.x;
            int rightB = r.x + r.width;
            if (rightA >= leftB && leftA <= rightB) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public void keyTyped (KeyEvent e){
    }
    
  
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (cat.isJumping()) {
                cat.cancelJumpAndCrouch(this);
            } else {
                cat.crouchDown();
                repaint();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            long now = System.currentTimeMillis();
            if (now - lastJumpTime > jumpCooldown) {
                cat.jump(this);
                lastJumpTime = now;
                repaint();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            long now = System.currentTimeMillis();
            if (!isInvincible && now - lastInvincibleTime > invincibleCooldown) {
                isInvincible = true;
                lastInvincibleTime = now;
                Timer invincibleTimer = new Timer(invincibleDuration, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        isInvincible = false;
                    }
                });
                invincibleTimer.setRepeats(false);
                invincibleTimer.start();
                repaint(); 
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            cat.standUp();  
            repaint();
        }
    }
    private void startGameTimer() {
        gameTimer = new Timer(130, e -> { 
            timeLeft -= 0.1;
            if (!isColliding) {
                score += 1; 
            }
            repaint();
            if (timeLeft <= 0) {
                gameTimer.stop();
                display.endgame(score);
            }
        });
        gameTimer.start();
    }
}