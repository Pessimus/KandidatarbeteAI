package Controller;

import Controller.AIStates.IState;
import Model.ICharacterHandle;
import java.util.LinkedList;

import Model.ICollidable;
import Model.IItem;
import Model.ResourcePoint;
import Utility.Constants;


/**
 * Created by Gustav on 2016-04-18.
 */
public class PlannerBrain implements AbstractBrain {

    ICharacterHandle body;
    private static final int maxDepth = 10;
    private static final int criticalAt = 15;
    private static final int criticalIf = 40;
    private int waiting;
    private boolean idle;
    private boolean moving;
    private boolean reached;
    private ResourcePoint goal;
    private LinkedList<PathStep> path;

    @Override
    public ICharacterHandle getBody() {
        return body;
    }

    @Override
    public void setBody(ICharacterHandle body) {
        this.body = body;
    }

    @Override
    public void update() {
        if (moving) {
            if (path.isEmpty()) {
                moving = false;
                idle = true;
                if (goal != null) {
                    reached = true;
                }
            } else {
                if (path.peek().reached(body)) {
                    path.poll();
                } else {
                    if(path.peek().stepTowards(body)) {
                        path.poll();
                    }
                }
            }
        } else if (reached) {
            if (goal != null) {
                int index = body.getInteractables().indexOf(goal);
                if (index != -1) {
                    idle = false;
                    reached = false;
                    idle = false;
                    body.interactObject(index);
                    waiting = 90; //sets time to complete task
                } else {
                    reached = false;
                    moving = false;
                    idle = true;
                }
            } else {
                reached = false;
                moving = false;
                idle = true;
            }

        } else if (idle) {
            plan();
        } else {
            waiting--;
            if (waiting <= 0) {
                idle = true;
            }
        }

    }

    private void plan() {
        LinkedList<ICollidable> surroundings = new LinkedList<>(body.getSurroundings());
        LinkedList<IItem> inventory = new LinkedList<>(body.getInventory());
        if (new PlanState().critical()) {

        } else {
            
        }


    }


    //returns the value of the current state
    private double value() {return new PlanState().value();}

    //class for handling states
    private class PlanState {

        int[] needs;
        LinkedList<IItem> inventory;
        LinkedList<ResourcePoint> surroundings;
        PlanState previous;
        ResourcePoint actionPoint;
        IItem actionItem;

        public PlanState (int[] need, LinkedList<IItem> inv) {
            needs = need;
            inventory = inv;
            previous = null;
            actionPoint = null;
            actionItem = null;
            surroundings = null;

        }

        public PlanState () {
            this(body.getNeeds(), new LinkedList<>(body.getInventory()));
            LinkedList<ICollidable> list = new LinkedList<>(body.getSurroundings());
            LinkedList<ResourcePoint> list2 = new LinkedList<>();
            for (ICollidable c : list) {
                if (c.getClass().equals(ResourcePoint.class)) {
                    list2.add((ResourcePoint) c);
                }
            }
            surroundings = list2;
        }

        public PlanState (PlanState previous, IItem item) {
            this.previous = previous;
            actionPoint = null;
            actionItem = item;
        }

        public PlanState (PlanState previous, ResourcePoint point) {
            this.previous = previous;
            actionItem = null;
            actionPoint = point;
        }

        public boolean critical() {
            return needs[0] < criticalAt || needs[1] < criticalAt || needs[2] < criticalAt || (needs[0] < criticalIf && needs[1] < criticalIf && needs[2] < criticalIf);
        }

        public double value() {
            double ret = 0;
            for (int i = 0; i < needs.length; i++) {
                ret += (double) needs[i];
            }
            for (IItem i : inventory) {
                switch (i.getType()) {
                    case GOLD_ITEM: ret += Math.min(Constants.GOLD_ENERGY_CHANGE_CONSUME + Constants.GOLD_HUNGER_CHANGE_CONSUME + Constants.GOLD_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case WATER_ITEM: ret += Math.min(Constants.WATER_ENERGY_CHANGE_CONSUME + Constants.WATER_HUNGER_CHANGE_CONSUME + Constants.WATER_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case WOOD_ITEM: ret += Math.min(Constants.WOOD_ENERGY_CHANGE_CONSUME + Constants.WOOD_HUNGER_CHANGE_CONSUME + Constants.WOOD_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case CROPS_ITEM: ret += Math.min(Constants.CROP_ENERGY_CHANGE_CONSUME + Constants.CROP_HUNGER_CHANGE_CONSUME + Constants.CROP_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case MEAT_ITEM: ret += Math.min(Constants.MEAT_ENERGY_CHANGE_CONSUME + Constants.MEAT_HUNGER_CHANGE_CONSUME + Constants.MEAT_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case FISH_ITEM: ret += Math.min(Constants.FISH_ENERGY_CHANGE_CONSUME + Constants.FISH_HUNGER_CHANGE_CONSUME + Constants.FISH_THIRST_CHANGE_CONSUME - 1, 100); break;
                    case STONE_ITEM: ret += Math.min(Constants.STONE_ENERGY_CHANGE_CONSUME + Constants.STONE_HUNGER_CHANGE_CONSUME + Constants.STONE_THIRST_CHANGE_CONSUME - 1, 100); break;
                }
            }
            return ret;
        }

    }

    //methods demanded by AbstractBrain. Not utilized
    @Override
    public IState getState() {return null;}
    @Override
    public void setState(IState state) {}
}
