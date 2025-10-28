
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WinScreen extends JPanel {
    public WinScreen(ActionListener main, long score) {
        this.setLayout(null);
        this.setBackground(new Color(50, 150, 50));

        JLabel winLabel = new JLabel("You Win!");
        winLabel.setFont(new Font("Arial", Font.BOLD, 40));
        winLabel.setForeground(Color.WHITE);
        winLabel.setBounds(515, 100, 300, 50);
        this.add(winLabel);

        JButton homeButton = new JButton("Home");
        homeButton.setBounds(500, 200, 200, 60);
        homeButton.setFont(new Font("Arial", Font.BOLD, 24));
        homeButton.setActionCommand("Restart");
        homeButton.addActionListener(main);
        this.add(homeButton);

        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(520, 160, 300, 40);
        this.add(scoreLabel);

    }
}
