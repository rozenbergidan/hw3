package Players;

import Board.*;
import Control.ScreenWriter;
import Enemys.Enemy;

import java.util.List;

public class Hunter extends Player{
    //====================FIELDS==================
    private final int MIN_ARROWS=10;
    private int arrows;
    private SpecialAbility specialAbility;
    //=================CONSTRUCTOR=================
    public Hunter(String name, int attack, int defence, int health, int range, Point point) {
        super(name, attack, defence, health, point);
        specialAbility=new SpecialAbility(range);
        this.arrows=MIN_ARROWS;
    }
    //================PUBLIC_METHODS===============

    public void levelUP(){
        super.levelUP();
        arrows = arrows + 10*level;
        attackPoint = attackPoint + 2*level;
        defencePoint = defencePoint + level;
        String output = getName() + " reached level " + level +  ": +" + (level * 10) + " Health, +"+ (level * 6) + " Attack, +"+ (level * 2) + " Defense ";
        ScreenWriter.getScreenWriter().print(output);
    }

    public String toString(){
        return super.toString() + "\t\t" + specialAbility.toString();
    }
    @Override
    public List<Enemy> filter(List<Enemy> list) {
        return null;
    }
    //==================INTERFACES===============
    @Override
    public void onTickAct(Board board) {

        if (specialAbility.tickCount == 10) {
            arrows = arrows + level;
            specialAbility.tickCount = 0;
        }
        else specialAbility.tickCount++;
    }
    @Override
    public void castSpecialAbility() {
        String output="";
        if(arrows ==0){//print error message
            output=getName()+" tried to cast "+specialAbility.name+", but there are no arrows left.\n";
            ScreenWriter.getScreenWriter().print(output);
        }
        else{
            List<Enemy> inRangeEnemies =Board.getBoard().enemiesInRange(this,specialAbility.range);
            Enemy toAttack = null;
            for (Enemy enemy: inRangeEnemies) {
                if (toAttack == null) toAttack = enemy;
                else {
                    if (this.location.range(enemy.getLocation()) < this.location.range(toAttack.getLocation()))
                        toAttack = enemy;
                }
            }
            if (toAttack != null) {
                output = getName() + " fired an arrow at " + toAttack.getName();
                ScreenWriter.getScreenWriter().print(output);
                toAttack.attackMe(attackPoint, this);
                arrows = arrows - 1;
            }
            else{
                output = getName()+" tried to shoot an arrow but there were no enemies in range.";
                ScreenWriter.getScreenWriter().print(output);
            }
        }
    }

    //================NESTED_CLASSES===============
    private class SpecialAbility{ //nested class
        private final String NAME="Shoot";
        private final String DESCRIPTION="add hunter ability description here...";

        private int tickCount;
        private String name;
        private String desc;
        private double range;


        public SpecialAbility(int range){
            this.name=NAME;
            this.desc=DESCRIPTION;
            this.range=range;
            this.tickCount = 0;
        }

        @Override
        public String toString() {
            return "Arrows: "+ arrows +"\t\t Range: " + (int)range;
        }
    }

}
