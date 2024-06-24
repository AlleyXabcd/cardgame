import model.*;
import skills.*;
import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameActivity extends JFrame implements Runnable {

    private List<Card> cardPool;  // 卡牌池
    private Player player1;  // 玩家1
    private Player player2;  // 玩家2
    private JTextArea gameLog;  // 游戏日志文本区域
    private JButton endTurnButton;  // 结束回合按钮
    private JPanel player1Panel;  // 玩家1的卡牌面板
    private JPanel player2Panel;  // 玩家2的卡牌面板
    private JPanel controlPanel;  // 控制面板
    private JLabel player1EnergyLabel;  // 玩家1的能量标签
    private JLabel player2EnergyLabel;  // 玩家2的能量标签
    private Card selectedCard;  // 当前选中的卡牌
    private Skill selectedSkill;  // 当前选中的技能
    private Card selectedTargetCard;  // 当前选中的目标卡牌
    private Player currentPlayer;  // 当前玩家
    private Player opponentPlayer;  // 对手玩家
    private boolean isSelectingCards;  // 是否正在选择卡牌
    private JPanel cardSelectionPanel;  // 卡牌选择面板

    public GameActivity()  {
        initializeCardPool();  // 初始化卡牌池
        player1 = new Player("玩家1", new ArrayList<>());  // 初始化玩家1
        player2 = new Player("玩家2", new ArrayList<>());  // 初始化玩家2
        currentPlayer = player1;  // 设置当前玩家为玩家1
        opponentPlayer = player2;  // 设置对手玩家为玩家2
        isSelectingCards = true;  // 开始选择卡牌

        // Setup GUI
        setLayout(new BorderLayout());
        setTitle("CardGame");
        setSize(1400, 800);
        setLocation(70,0);;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        gameLog = new JTextArea("战斗日志:\n");
        gameLog.setEditable(false);
        gameLog.setFont(new Font("标楷体", Font.BOLD, 16)); 
        JScrollPane gameLogScrollPane = new JScrollPane(gameLog);
        add(gameLogScrollPane, BorderLayout.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        add(controlPanel, BorderLayout.SOUTH);

        player1Panel = new JPanel();
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
        add(player1Panel, BorderLayout.WEST);

        player2Panel = new JPanel();
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
        add(player2Panel, BorderLayout.EAST);

        endTurnButton = new JButton("结束回合");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurn();   // 结束回合按钮的事件处理
            }
        });
        controlPanel.add(endTurnButton);

        cardSelectionPanel = new JPanel();
        
        cardSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        add(cardSelectionPanel, BorderLayout.NORTH);

        startCardSelection();  // 开始卡牌选择
        
    }

    private void initializeCardPool() {
        cardPool = new ArrayList<>();  // 初始化卡牌池

        //实例化技能
        Skill normalAttack = new NormalAttack();
        Skill chainKick = new ChainKick();
        Skill fireBlade = new FireBlade();
        Skill shieldSpell = new ShieldSpell();
        Skill leechSeed = new LeechSeed();
        Skill shadowStrike = new ShadowStrike();
        Skill freezeSpell = new FreezeSpell();

        //向卡牌池添加实例的卡牌
        cardPool.add(new Card("呱呱", 10, normalAttack, chainKick,"images/guagua.png"));
        cardPool.add(new Card("火花", 12, normalAttack, fireBlade,"images/huohua.png"));
        cardPool.add(new Card("水蓝蓝", 12, normalAttack, shieldSpell,"images/shuilanlan.png"));
        cardPool.add(new Card("喵喵", 10, normalAttack, leechSeed,"images/miaomiao.png"));
        cardPool.add(new Card("迪莫", 11, normalAttack, shadowStrike,"images/dimo.png"));
        cardPool.add(new Card("冰龙宝宝", 11, normalAttack, freezeSpell,"images/binglongbaobao.png"));
    }
    


    //玩家交替选择卡牌
    private void startCardSelection() {
        Collections.shuffle(cardPool);  // 洗牌
        List<Card> selectedCards = new ArrayList<>(cardPool.subList(0, 6));  // 选择前6张卡牌作为本次游戏的待选择卡牌

        for (Card card : selectedCards) {
            JButton cardButton = createCardButton(card, 160, 90);
            cardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPlayer.getCards().size() < 3) {
                        //当选择了一张卡牌后，更新卡牌池，确保每张卡牌不重复选择
                        currentPlayer.getCards().add(card);
                        updateGameLog(currentPlayer.getName() + "选择了" + card.getName());
                        selectedCards.remove(card);
                        updateCardSelectionPanel(selectedCards); 
                        switchPlayer();
                        if (player1.getCards().size() == 3 && player2.getCards().size() == 3) {
                            isSelectingCards = false;
                            cardSelectionPanel.setVisible(true);
                            startGame();  // 如果两名玩家都选择了3张卡牌，开始游戏
                        }
                    }
                }
            });
            cardSelectionPanel.add(cardButton);
        }
        updateGameLog("玩家交替选择卡牌!!!");
    }

    //更新卡牌池
    private void updateCardSelectionPanel(List<Card> selectedCards) {
        cardSelectionPanel.removeAll();
        for (Card card : selectedCards) {
            //按钮按下触发卡牌被选择
            JButton cardButton = createCardButton(card,160,90);
            cardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPlayer.getCards().size() < 3) {
                        currentPlayer.getCards().add(card);
                        updateGameLog(currentPlayer.getName() + "选择了" + card.getName());
                        selectedCards.remove(card);
                        updateCardSelectionPanel(selectedCards);
                        switchPlayer();
                        if (player1.getCards().size() == 3 && player2.getCards().size() == 3) {
                            isSelectingCards = false;
                            cardSelectionPanel.setVisible(true);
                            startGame();
                        }
                    }
                }
            });
            cardSelectionPanel.add(cardButton);
        }
        revalidateAndRepaint(cardSelectionPanel);
    }
    //为每一个卡牌添加背景图标，w,h为按钮宽高
    private JButton createCardButton(Card card,int w,int h) {
        JButton cardButton = new JButton(card.getName());
        cardButton.setPreferredSize(new Dimension(w, h));
        //鼠标悬浮查看卡牌详情
        cardButton.setToolTipText(getCardToolTipText(card));
        // 加载图像并调整大小
        ImageIcon icon = new ImageIcon(card.getimagePath());
        // 确保图像正确加载
        if (icon.getIconWidth() == -1 || icon.getIconHeight() == -1) {
            System.err.println("Image not found: " +card.getimagePath());
        } else {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            //设定透明效果
            cardButton.setOpaque(false);  
            //去掉背景点击效果
            cardButton.setContentAreaFilled(false);  
            //去掉聚焦线
            cardButton.setFocusPainted(false);
            
            cardButton.setIcon(scaledIcon);
        }


        return cardButton;
    }

    //设置鼠标悬浮时的详情信息
    private String getCardToolTipText(Card card) {
        return String.format("<html> %s<br>HP: %d<br>普通攻击(Cost: %d)<br>%s<br><br>特殊技: %s (Cost: %d)<br>%s</html>",
                card.getName(), card.getHealth(), card.getNormalAttack().getEnergyCost(),
                card.getNormalAttack().getDescription(), card.getSpecialSkill().getName(), card.getSpecialSkill().getEnergyCost(),
                card.getSpecialSkill().getDescription());
    }

    //交换行动方
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        opponentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    //启动游戏
    private void startGame() {
        currentPlayer = player1;
        opponentPlayer = player2;
        updatePlayerPanels();
        updateGameLog("游戏开始!\n\n玩家1率先行动");
    }
    //更新每个玩家操作区的内容
    private void updatePlayerPanels() {
        player1Panel.removeAll();
        player2Panel.removeAll();
        player1Panel.add(new JLabel(player1.getName()+"操作区"));
        player2Panel.add(new JLabel(player2.getName()+"操作区"));

        for (Card card : player1.getCards()) {
            player1Panel.add(Box.createVerticalStrut(30));
            player1Panel.add(createCardPanel(card, player1));
            player1Panel.add(new JLabel("        HP: "+card.getHealth()+" "));
            player1Panel.add(Box.createVerticalStrut(30));
        }

        for (Card card : player2.getCards()) {
            player2Panel.add(Box.createVerticalStrut(30));
            player2Panel.add(createCardPanel(card, player2));
            player2Panel.add(new JLabel("        HP: "+card.getHealth()+" "));
            player2Panel.add(Box.createVerticalStrut(30));
        }

        revalidateAndRepaint(player1Panel, player2Panel);
        //更新剩余能量点
        player1EnergyLabel = new JLabel(player1.getName() + "  能量点："  + player1.getEnergy());
        player2EnergyLabel = new JLabel(player2.getName() + "  能量点： " + player2.getEnergy());

        player1Panel.add(player1EnergyLabel);
        player2Panel.add(player2EnergyLabel);
    }


    //设置卡牌按钮
    private JPanel createCardPanel(Card card, Player player) {
        JPanel cardPanel = new JPanel();

        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        JButton cardButton = createCardButton(card, 30,30);


        cardButton.setEnabled(card.isAlive());
        

        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player == currentPlayer && !isSelectingCards) {
                    //当选定攻击卡牌时，技能区域及时更新
                    selectedCard = card;
                    updateSkillButtons(card);
                }
            }
        });
        
        cardPanel.add(cardButton);

        return cardPanel;
    }
    //更新技能区域
    private void updateSkillButtons(Card card) {
        controlPanel.removeAll();
        
        addSkillButton(card.getNormalAttack(), card);
        
        addSkillButton(card.getSpecialSkill(), card);
        controlPanel.add(endTurnButton);
        revalidateAndRepaint(controlPanel);


    }
    //控制区根据选择的卡牌更新技能按钮
    private void addSkillButton(Skill skill, Card card) {
        JButton skillButton = new JButton(skill.getName() + " (Cost: " + skill.getEnergyCost() + ")");
        skillButton.setBackground(Color.orange);
        skillButton.setToolTipText(skill.getDescription());
        skillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSkill = skill;

                if(selectedSkill.getName()=="治疗术"){
                    updateTargetSelection(currentPlayer); //如果是治疗术，则释放的对象是自已的卡牌
                }

                else{
                    updateTargetSelection(opponentPlayer); //剩余情况 技能释放的对象是对手的卡牌
                }
                
            }
        });
        controlPanel.add(skillButton);
    }

    //控制区更新技能选择的对象并更新战斗日志
    private void updateTargetSelection(Player player) {
        
        controlPanel.removeAll();
        for (Card card : player.getCards()) {
            JButton targetButton = new JButton(card.getName() + " (HP: " + card.getHealth() + ")");
            targetButton.setToolTipText(getCardToolTipText(card));
            targetButton.setBackground(Color.cyan);
            targetButton.setEnabled(card.isAlive());
            targetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    selectedTargetCard = card;
                    if (currentPlayer.useSkill(selectedCard, selectedSkill, selectedTargetCard)) {
                        if(selectedSkill.getName()=="普通攻击"){
                            updateGameLog(selectedCard.getName() + " 对 " + selectedTargetCard.getName()+ " 使用了 " + selectedSkill.getName()+",造成"+ selectedSkill.damage +"点伤害");
                        }

                        if(selectedSkill.getName()=="火焰踢" || selectedSkill.getName()=="光影斩杀" || selectedSkill.getName()=="火之刃"){

                            updateGameLog(selectedCard.getName() + " 对 " + selectedTargetCard.getName()+ " 使用了 " + selectedSkill.getName() +",造成"+ selectedSkill.damage +"点伤害");
                        }
                        if(selectedSkill.getName()=="治疗术"){
                            updateGameLog(selectedCard.getName() + " 对 " + selectedTargetCard.getName()+ " 使用了 " + selectedSkill.getName() +","+ selectedTargetCard.getName() +"恢复了"+ -selectedSkill.damage +"点血量");
                        }
                        if(selectedSkill.getName()=="冰冻术"){
                            updateGameLog(selectedCard.getName() + " 对 " + selectedTargetCard.getName()+ " 使用了 " + selectedSkill.getName() +",造成"+ selectedSkill.damage +"点伤害并冰冻了对手");
                        }
                        if(selectedSkill.getName()=="寄生种子"){
                            updateGameLog(selectedCard.getName() + " 对 " + selectedTargetCard.getName()+ " 使用了 " + selectedSkill.getName() +",造成"+ selectedSkill.damage+"点伤害,"+selectedCard.getName()+"恢复了"+ selectedSkill.damage +"点血量");
                        }
                        if(selectedTargetCard.getHealth() == 0) {
                            updateGameLog(selectedTargetCard.getName() + " 战败");
                        }
                        updateControlPanel();
                        updatePlayerPanels();
                        checkGameOver();
                    } else if(selectedCard.isFrozen())
                    {
                        updateGameLog(selectedCard.getName()+"处于冰冻状态,无法攻击");;
                    
                    }
                    else
                    {
                        updateGameLog("能量不足");
                    }
                }
            });
            controlPanel.add(targetButton);
        }
        controlPanel.add(endTurnButton);
        
        revalidateAndRepaint(controlPanel);
    }

    private void updateControlPanel() {
        controlPanel.removeAll();
        updateSkillButtons(selectedCard);
    }
    //回合结束，恢复本次攻击者的能量点，交换攻击方，更新每个玩家的操作区和控制区
    private void endTurn() {
        currentPlayer.restoreEnergy();
        switchPlayer();
        updatePlayerPanels();
        updateGameLog(currentPlayer.getName() + "的回合(能量剩余 " + currentPlayer.getEnergy()+")");
        updateEnergyLabels();
        controlPanel.removeAll();
        controlPanel.add(endTurnButton);
        
        
    }
    //战斗日志内容添加
    private void updateGameLog(String message) {
        
        gameLog.append(message + "\n\n");
      
    }
    //更新能量Label
    private void updateEnergyLabels() {
        player1EnergyLabel.setText(player1.getName() + " Energy: " + player1.getEnergy());
        player2EnergyLabel.setText(player2.getName() + " Energy: " + player2.getEnergy());
    }

    private void revalidateAndRepaint(Component... components) {
        for (Component component : components) {
            component.revalidate();
            component.repaint();
        }
    }
    //判断游戏结束与否(弹窗提示)
    private void checkGameOver() {
        //检查玩家的牌库是否还有存活
        if (player1.getCards().stream().noneMatch(Card::isAlive)) {
            JOptionPane.showMessageDialog(this, "玩家2获胜!", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            //System.exit(0);
        } else if (player2.getCards().stream().noneMatch(Card::isAlive)) {
            JOptionPane.showMessageDialog(this, "玩家1获胜!", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            //System.exit(0);
        }
    }


    @Override
    public void run() {
        new GameActivity().setVisible(true);
    }


}
