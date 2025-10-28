

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelectMenu extends JPanel {
    public LevelSelectMenu(ActionListener main){
        this.setLayout(null);
        this.setBackground(new Color(30, 30, 30));

        String[] waves = {"Easy", "Normal", "Hard"};
        for(int i = 0; i < waves.length; i++){
            JButton button = new JButton(waves[i]);
            button.setBounds(500, 80 + i * 80, 200, 60);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setActionCommand("StartLevel" + (i + 1));
            button.addActionListener(main);
            this.add(button);
        }
    }
}
