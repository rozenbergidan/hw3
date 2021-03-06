package Players;

import Board.*;
import Control.ScreenWriter;
import Enemys.Enemy;

import java.util.List;

public class Rogue extends Player{
    //====================FIELDS==================
    private Energy energy;
    private SpecialAbility specialAbility;
    //=================CONSTRUCTOR=================
    public Rogue(String name, int attack, int defence, int health,int energyCost, Point point) {
        super(name, attack, defence, health, point);
        energy=new Energy();
        specialAbility=new SpecialAbility(energyCost);
    }
    //================PUBLIC_METHODS===============
    @Override
    public void levelUP(){
        super.levelUP();
        energy.currentEnergy=100;
        attackPoint=attackPoint+3*level;
        String output = getName() + " reached level " + level +  ": +" + (level * 10) + " Health, +"+ (level * 7) + " Attack, +"+ (level * 1) + " Defense ";
        ScreenWriter.getScreenWriter().print(output);
    }

    public String toString(){
        return super.toString()+"\t\t"+energy.toString();
    }
    @Override
    public List<Enemy> filter(List<Enemy> list) {
        return null; //do nothing
    }
    //==================INTERFACES===============
    @Override
    public void onTickAct(Board board) {
        energy.currentEnergy=Math.min(energy.currentEnergy+10,100);
    }
    @Override
    public void castSpecialAbility() {
        String output="";
        if(energy.currentEnergy<specialAbility.energyCost){
            output=getName()+" tried to cast "+specialAbility.name+", but there was not enough "+energy.toString()+".\n";
            ScreenWriter.getScreenWriter().print(output);
        }
        else{
            energy.currentEnergy=energy.currentEnergy-specialAbility.energyCost;
            List<Enemy> inRangeEnemies=Board.getBoard().enemiesInRange(this,specialAbility.range);
            //List<Enemy> inRangeEnemies=Board.getBoard().enemiesInRangeRogue(this,specialAbility.range);
            output=getName()+" cast "+specialAbility.name+".\n";
            ScreenWriter.getScreenWriter().print(output);
            for(Enemy e: inRangeEnemies){
                e.attackMe(attackPoint,this);
            }
        }
    }

    //================NESTED_CLASSES===============
    private class Energy{
        private final int MAX_ENERGY=100;

        private int totalEnergy;
        private int currentEnergy;

        public Energy(){
            this.totalEnergy=MAX_ENERGY;
            this.currentEnergy=MAX_ENERGY;
        }

        public String toString(){
            String output="";
            output="Energy: "+currentEnergy+"/"+totalEnergy;
            return output;
        }
    }

    private class SpecialAbility{
        private final String NAME="Fan of Knives";
        private final String DESCRIPTION="hits everyone around the rogue for an amount equals to the\n" +
                "rogue’s attack points at the cost of energy.";
        private final int MAX_RANGE=2;

        private String name;
        private String desc;
        private int energyCost;
        private double range;

        public SpecialAbility(int energyCost){
            this.name=NAME;
            this.desc=DESCRIPTION;
            this.energyCost=energyCost;
            range=MAX_RANGE;
        }
    }
}
