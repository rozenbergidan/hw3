package Enemys;

import Board.*;
import Tiles.Unit;

public abstract class Enemy extends Unit {
    //====================FIELDS==================
    private int expValue;
    //=================CONSTRUCTOR=================
    public Enemy(int expValue, Point point, char character, String name, int attack, int defence, int health) {
        super(point, character, name, attack, defence, health);
        this.expValue = expValue;
    }
    //=================PROPERTIES================
    public int getExpValue(){
        return expValue;
    }

    //================PUBLIC_METHODS===============
    public String toString() {
        String output = "";
        output = getName() + "\t\t" + health.toString() + "\t\t" + "Attack: " + attackPoint + "\t\t" + "Defence: " + defencePoint + "\t\t" + "Experience Value: " + expValue;
        return output;

    }
    @Override
    protected void died() {
        Board.getBoard().unitDied(this);
    }

    public abstract void act();


}
