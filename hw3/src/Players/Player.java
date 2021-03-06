package Players;

import Board.*;
import Enemys.Enemy;
import Enemys.Monster;
import Interfaces.HeroicUnit;
import Interfaces.ObserverPattern.Observer;
import Tiles.*;
import Interfaces.VisitorPattern.Visited;
import Interfaces.VisitorPattern.Visitor;

import java.util.List;

public abstract class Player extends Unit implements Observer, Visitor, HeroicUnit {
    //====================FIELDS==================
    private final int START_LEVEL=1;
    private final int START_EXP=0;
    protected int level;
    protected int exp;
    //=================CONSTRUCTOR=================
    public Player(String name, int attack, int defence,int health,Point point){
        super(point,'@',name, attack, defence, health);
        level=START_LEVEL;
        exp=START_EXP;
    }
    //================PUBLIC_METHODS===============
    public void levelUP() {
        exp = exp - (50 * level);
        level++;
        health.levelUP(level);
        attackPoint = attackPoint + 4 * level;
        defencePoint = defencePoint + level;
    }

    public void act(char action) {// get the action char from the gameController
        if (action == 'e') castSpecialAbility();
        if (action == 'w') moveUp();
        if (action == 'd') moveRight();
        if (action == 's') moveDown();
        if (action == 'a') moveLeft();

    }

    public void setLocation(int i, int j) {
        location.setX(i);
        location.setY(j);
    }

    public void killedEnemy(int monsterExp){
        exp = exp + monsterExp;
        while(exp >= level * 50){
            levelUP();
        }
    }

    protected void died(){
        this.character = 'X';
        Board.getBoard().playerDied();
    }

    public String toString(){
        String output="";
        output=getName() + "\t\t" + health.toString() + "\t\t" + "Attack: " + attackPoint + "\t\t" + "Defence: " + defencePoint+"\t\t"+"Level: "+ level+"\t\t"+"Experience: "+ exp+"\\"+50*level;
        return output;
    }

    public abstract List<Enemy> filter(List<Enemy> list);

    //================PRIVATE_METHODS==============
    private void moveTo(Point goTo){
        //Tile toVisit = Board.getBoard().getTile(goTo);
        int correntLvl = Board.getBoard().getLevel();
        if(visit(Board.getBoard().getTile(goTo)) && correntLvl == Board.getBoard().getLevel()){
            switchLocation(Board.getBoard().getTile(goTo));
            Board.getBoard().switchTile(location, Board.getBoard().getTile(goTo).getLocation());
        }
    }
    private void moveRight(){
        Point goTo = new Point(location.getX(), location.getY() + 1);
        moveTo(goTo);
    }
    private void moveLeft(){
        Point goTo  = new Point(location.getX() , location.getY()- 1);
        moveTo(goTo);
    }
    private void moveUp(){
        Point goTo  = new Point(location.getX() - 1, location.getY() );
        moveTo(goTo);
    }
    private void moveDown(){
        Point goTo  = new Point(location.getX() + 1, location.getY() );
        moveTo(goTo);
    }

    //==================INTERFACES===============
    @Override
    public boolean visit(Visited V){
        return V.accept(this);
    }

    @Override
    public boolean accept(Player p) {
        return false;
    }

    @Override
    public boolean accept(Monster m) {
        m.attack(this);
        return false;
    }

    public abstract void castSpecialAbility();

}
