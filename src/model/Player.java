
//玩家类，游戏主体

package model;
import java.util.List;

import skills.Skill;

public class Player {

    private String name; //玩家姓名
    private List<Card> cards; //玩家的卡牌
    private int energy;       //能量点
    private int maxenergy=10;//最大储存能量点


    public Player(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
        this.energy = 10; // 初始化能量
    }

   
    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getEnergy() {
        return energy;
    }

    public void restoreEnergy() {
        this.energy += 5; // 每回合恢复能量
        if(this.energy>maxenergy) //判断是否超过最大存储能量点
        {
            this.energy=maxenergy;
        }
    }

    public boolean useSkill(Card card, Skill skill, Card targetCard) {     //判断是否有资格使用技能
        if (energy >= skill.getEnergyCost() && !card.isFrozen()) {         // 能量点不足或者卡牌被冰冻无法释放技能
            skill.apply(card, targetCard);
            energy -= skill.getEnergyCost();
            return true;
        }
        return false;
    }
}
