package skills;

import model.Card;

public class FreezeSpell extends Skill {
    public FreezeSpell() {
        super("冰冻术", 4, "A spell that freezes the target, preventing actions.(冻结目标，阻止行动的法术)");
    }

    @Override
    public void apply(Card user, Card target) {          //技能效果实例化
        damage=2;
        target.takeDamage(2);
        target.freeze();
    }
}
