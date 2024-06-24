package skills;

import model.Card;

public class ShieldSpell extends Skill {
    public ShieldSpell() {
        super("治疗术", 3, "A protective spell that heals target.(一个治疗目标的保护法术)");
    }

    @Override
    public void apply(Card user, Card target) {       //技能效果实例化
        damage=-3;
        target.takeDamage(-3);
    }
}
