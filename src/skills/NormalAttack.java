package skills;

import model.Card;

public class NormalAttack extends Skill {
    public NormalAttack() {
        super("普通攻击", 2, "A basic attack causing moderate damage.");
    }

    @Override
    public void apply(Card user, Card target) {            //技能效果实例化
        damage=2;
        target.takeDamage(2);
    }
}
