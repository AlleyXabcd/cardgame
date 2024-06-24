package skills;

import model.Card;

public class ChainKick extends Skill {
    public ChainKick() {
        super("火焰踢", 5, "A powerful kick that causes severe damage.(能造成严重伤害的强力一脚)");
    }

    @Override
    public void apply(Card user, Card target) {      //技能效果实例化
        damage=6; 
        target.takeDamage(6);
    }
}
