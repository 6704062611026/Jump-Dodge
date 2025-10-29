import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
public class Display extends JFrame implements ActionListener{  //การสร้างหน้าต่าง Inheritance(การสืบทอด) ยืม 
    public Display(){
        super("Jump Dodge");
        this.setSize(1200,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        showStartMenu();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }
    private void removeContent(){
        this.getContentPane().removeAll();
        this.getContentPane().repaint();
    }
    

     public void showStartMenu(){  // add()หน้าPolymorphism (การพ้องรูป - Subtyping)
        removeContent();
        this.getContentPane().add(new StartMenu(this));
        this.revalidate();
        this.repaint();
    }

     public void showLevelSelect(){
        removeContent();
        this.getContentPane().add(new LevelSelectMenu(this));
        this.revalidate();
        this.repaint();
    }

    public void startGame(int level){
        removeContent();
        Game game = new Game(this, level);
        this.getContentPane().add(game);
        game.requestFocus();
        this.revalidate();
        this.repaint();
    }

    public void endgame(long score) {
    removeContent();
    this.getContentPane().add(new WinScreen(this, score)); 
    this.revalidate(); 
    this.repaint();
}


public void gameOver(int timeLeft, int level, long score) {
    removeContent();
    this.getContentPane().add(new LoseScreen(this, timeLeft, level, score));
    this.revalidate();
    this.repaint();
}



    public static void main(String[] args){
        new Display();
    }
  
    
    @Override // คุมเมนู Event Handling (การจัดการเหตุการณ์) ดักจับ
public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals("GoToLevelSelect")) {
        showLevelSelect();
    } else if (cmd.equals("StartLevel1")) {
        startGame(1);
    } else if (cmd.equals("StartLevel2")) {
        startGame(2);
    } else if (cmd.equals("StartLevel3")) {
        startGame(3);
    } else if (cmd.equals("Restart")) {
        showStartMenu();
    } else if (cmd.startsWith("RetryLevel")) {
        int level = Integer.parseInt(cmd.replace("RetryLevel", ""));
        startGame(level);
    }
}

       
    }



