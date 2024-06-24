//卡牌类
package model;
import skills.Skill;

public class Card {
    private String name;  //卡牌名称
    private int health;   //血量
    private Skill normalAttack; //普通攻击技能类
    private Skill specialSkill;  //特殊技能类
    private String imagePath;    //图标路径
    private boolean frozen;      //冰冻转态

    public Card(String name, int health, Skill normalAttack, Skill specialSkill,String imagePath) {
        this.name = name;
        this.health = health;
        this.normalAttack = normalAttack;
        this.specialSkill = specialSkill;
        this.imagePath=imagePath;
        this.frozen = false;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Skill getNormalAttack() {
        return normalAttack;
    }

    public Skill getSpecialSkill() {
        return specialSkill;
    }

    public String getimagePath() {
        return imagePath;
    }

    public boolean isAlive() {        //判断是否存活
        return health > 0;
    }

    public boolean isFrozen() {       //判断是否被冰冻
        return frozen;
    }

    public void freeze() {             //施加冰冻
        frozen = true;
    }

    public void unfreeze() {           //解除冰冻
        frozen = false;
    }

    public void takeDamage(int damage) {     //卡牌承受伤害
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }
}
