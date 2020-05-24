package Emenys;

import Board.*;
import Players.Player;
import Tiles.Tile;
import VisitorPattern.*;

public class Monster extends Enemy implements Visitor {

    private int vision;

    public Monster(Point point, char character, String name, int attack, int defence, int health, int vision,int expValue) {
        super(expValue,point, character, name, attack, defence, health);
        this.vision=vision;
    }


    @Override
    public void act(Board b){
        Player player = b.getPlayer();
        if(location.range(player.getLocation())< vision){
            //algorithm to go the best path to player / go to player way
        }
        else{
            int direction = (int) Math.random()*5;
            Point toGo;
            if(direction == 0){
                toGo = new Point(location.getX() + 1, location.getY());
                if(visit(b.getTile(toGo))){
                    // go right
                    Tile toSwitch = Board.getBoard().getTile(toGo);
                    switchLocation(toSwitch);
                    Board.getBoard().switchTile(location, toGo);
                }
            }
            if(direction == 1){
                toGo  = new Point(location.getX(), location.getY() + 1);
                if(visit(b.getTile(toGo))){
                    // go up
                    Tile toSwitch = Board.getBoard().getTile(toGo);
                    switchLocation(toSwitch);
                    Board.getBoard().switchTile(location, toGo);
                }
            }
            if(direction == 2){
                toGo  = new Point(location.getX() - 1, location.getY());
                if(visit(b.getTile(toGo))){
                    // go left
                    Tile toSwitch = Board.getBoard().getTile(toGo);
                    switchLocation(toSwitch);
                    Board.getBoard().switchTile(location, toGo);
                }
            }
            if(direction == 3){
                toGo  = new Point(location.getX(), location.getY() - 1);
                if(visit(b.getTile(toGo))){
                    // go down
                    Tile toSwitch = Board.getBoard().getTile(toGo);
                    switchLocation(toSwitch);
                    Board.getBoard().switchTile(location, toGo);
                }
            }
        }

    }

    @Override
    public boolean accept(Player p) {// if dead change all neccesery in board
        return p.attack(this);

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
