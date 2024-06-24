
//技能类
package skills;

import model.Card;

public abstract class Skill {
    private String name;        //技能名称
    private int energyCost;     //能量消耗
    private String description; //技能描述
    public int damage;          //伤害

    public Skill(String name, int energyCost, String description) {
        this.name = name;
        this.energyCost = energyCost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public String getDescription() {
        return description;
    }

    public abstract void apply(Card user, Card target);    //抽象方法，等待具体化
}
