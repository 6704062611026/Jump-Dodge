

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoseScreen extends JPanel {
    public LoseScreen(ActionListener main, int timeLeft, int level, long score){
        this.setLayout(null);
        this.setBackground(new Color(150, 50, 50));

        JLabel loseLabel = new JLabel("You Lose");
        loseLabel.setFont(new Font("Arial", Font.BOLD, 40));
        loseLabel.setForeground(Color.WHITE);
        loseLabel.setBounds(500, 80, 300, 50);
        this.add(loseLabel);

        JLabel timeLabel = new JLabel("Time remaining: " + timeLeft + "s");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(505, 140, 300, 30);
        this.add(timeLabel);

        
        JButton homeButton = new JButton("Home");
        homeButton.setBounds(440, 250, 120, 50);
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        homeButton.setActionCommand("Restart");
        homeButton.addActionListener(main);
        this.add(homeButton);

        
        JButton retryButton = new JButton("Retry");
        retryButton.setBounds(620, 250, 120, 50);
        retryButton.setFont(new Font("Arial", Font.BOLD, 18));
        retryButton.setActionCommand("RetryLevel" + level);
        retryButton.addActionListener(main);
        this.add(retryButton);

        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(505, 170, 300, 30);
        this.add(scoreLabel);

    }
}
