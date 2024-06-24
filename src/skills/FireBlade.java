package skills;

import model.Card;

public class FireBlade extends Skill {
    public FireBlade() {
        super("火之刃", 4, "A blazing sword attack causing significant damage.(火之刃攻击,造成重大伤害)");
    }

    @Override
    public void apply(Card user, Card target) {      //技能效果实例化
        damage=5;
        target.takeDamage(5);
    }
}
