
import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class FirstActivity extends JFrame {
    private JButton startButton;//开始按钮

    public FirstActivity(){
    
        //初始GUI设计
        this.setLayout(null);
        this.setTitle("CardGame");
        this.setVisible(true);
        setSize(1400, 800);
        setLocation(70,0);;
        startButton=new JButton("开始游戏");

        //text=new TextField("设置昵称");
        //text.setBounds(595,200,200,25);

        startButton.setBackground(Color.yellow);
        startButton.setBounds(595,500,200,50);
        this.add(startButton);
        //this.add(text);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
  
        // 加载图像并调整大小
        ImageIcon icon = new ImageIcon("images/start.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(200,50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        //设定透明效果
        startButton.setOpaque(false);  
        //去掉背景点击效果
        startButton.setContentAreaFilled(false);  
        //去掉聚焦线
        startButton.setFocusPainted(false);
            
        startButton.setIcon(scaledIcon);
        
        //按钮事件监听
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if("设置昵称".equals(text.getText()))
                //{
                    //JOptionPane.showMessageDialog(new TextField(),"请设置昵称", "提示", JOptionPane.INFORMATION_MESSAGE);
                //}
                
                //else
                //{
                    //按钮按下启动游戏
                    new Thread(new GameActivity()).start();
                    dispose();             
                //}
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //设置背景图片
        String path = "images/b.jpg";
        // 背景图片
        ImageIcon background = new ImageIcon(path);
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(background);
        // 把标签的大小位置设置为图片刚好填充整个面板
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

    }
}


