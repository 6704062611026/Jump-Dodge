import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class StartMenu extends JPanel {
    private BufferedImage backgroundImage;

    public StartMenu(ActionListener main) {
        this.setLayout(null);

        
        try {
            backgroundImage = ImageIO.read(new File("image/Menu.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        JButton playButton = new JButton("Play");
        playButton.setBounds(485, 150, 200, 60);
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        playButton.setActionCommand("GoToLevelSelect");
        playButton.addActionListener(main);
        this.add(playButton);

    }

    @Override // ภาพหน้าแรก Polymorphism (การพ้องรูป - Method Overriding) การกระทำต่าง
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
