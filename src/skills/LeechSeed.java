package skills;

import model.Card;

public class LeechSeed extends Skill {
    public LeechSeed() {
        super("寄生种子", 4, "A spell that drains health from the target.(吸取目标生命值的法术)");
    }

    @Override
    public void apply(Card user, Card target) {         //技能效果实例化
        damage = 2; 
        target.takeDamage(damage);
        user.takeDamage(-damage); // 恢复等量的生命值
    }
}
