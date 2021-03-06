package Enemys;

import Board.*;
import Players.Player;
import Tiles.Tile;
import Interfaces.VisitorPattern.*;

public class Monster extends Enemy implements Visitor {
    //====================FIELDS==================
    protected int vision;
    //=================CONSTRUCTOR=================
    public Monster(Point point, char character, String name, int attack, int defence,
                   int health, int vision,int expValue) {
        super(expValue,point, character, name, attack, defence, health);
        this.vision=vision;
    }
    //================PUBLIC_METHODS===============
    @Override
    public void act(){
        Player player = Board.getBoard().getPlayer();
        if(location.range(player.getLocation())< vision){
            //algorithm to go the best path to player / go to player way
            int deltaX = location.getX() - player.getLocation().getX();
            int deltaY = location.getY() - player.getLocation().getY();
            if (Math.abs(deltaX) > Math.abs(deltaY)){
                if(deltaX > 0) moveUp();
                else moveDown();
            }
            else {
                if (deltaY > 0) moveLeft();
                else moveRight();
            }
        }
        else{
            int direction = (int) (Math.random()*5);
            if(direction == 0){
                moveRight();
            }
            if(direction == 1){
                moveUp();
            }
            if(direction == 2){
                moveLeft();
            }
            if(direction == 3){
                moveDown();
            }
        }

    }
    @Override
    public String toString(){
        return super.toString()+"\t\tVision Range: "+vision;
    }
    //================PRIVATE_METHODS==============
    private void moveTo(Point goTo){
        Tile toVisit = Board.getBoard().getTile(goTo);
        if(visit(toVisit)){
            switchLocation(toVisit);
            Board.getBoard().switchTile(location, toVisit.getLocation());
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
    public boolean accept(Player p) {
        return p.attack(this); // return true if the munster died.

    }
    @Override
    public boolean accept(Monster m) {
        return false;
    }
    @Override
    public boolean visit(Visited v) {
        return v.accept(this);
    }



}
