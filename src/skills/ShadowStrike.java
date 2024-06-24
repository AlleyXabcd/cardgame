package skills;

import model.Card;

public class ShadowStrike extends Skill {
    public ShadowStrike() {
        super("光影斩杀", 5, "A shadowy strike causing significant damage.(造成重大伤害的暗影打击)");
    }

    @Override
    public void apply(Card user, Card target) {         //技能效果实例化
        damage=6;
        target.takeDamage(6);
    }
}
