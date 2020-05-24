package Players;

import Board.*;
import Emenys.Monster;
import VisitorPattern.Visited;

public class Mage extends Player{
    private Mana mana;
    private SpecialAbility specialAbility;

    public Mage(String name, int attack, int defence, int health,int mana, int manaCost,int spellPower, int hitsCount, int range, Point point) {
        super(name, attack, defence, health, point);
        this.mana = new Mana(mana);
        specialAbility=new SpecialAbility(manaCost,spellPower,hitsCount,range);
    }

    @Override
    public void onTickAct(Board board) {

    }

    @Override
    public void act(Board b) {

    }

    @Override
    public boolean accept(Player p) {
        return false;
    }

    @Override
    public boolean accept(Monster m) {
        return false;
    }

    @Override
    public boolean visit(Visited V) {
        return false;
    }


    private class Mana{// nested class
        private int manaPool;
        private int currentMana;

        public Mana(int manaPool){
            this.manaPool=manaPool;
            this.currentMana=manaPool/4;
        }
    }

    private class SpecialAbility{ //nested class
        private final String NAME="Blizzard";
        private final String DESCRIPTION="randomly hit enemies within range for an amount equals to the mage’s\n" +
                "spell power at the cost of mana.";

        private String name;
        private String desc;
        private int range;
        private int manaCost;
        private int spellPower;
        private int hitsCount;

        public SpecialAbility(int manaCost, int spellPower, int hitsCount,int range){
            this.name=NAME;
            this.desc=DESCRIPTION;
            this.manaCost=manaCost;
            this.spellPower=spellPower;
            this.hitsCount=hitsCount;
            this.range=range;
        }

    }
}