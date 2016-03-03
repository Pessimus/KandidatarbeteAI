package Controller;

import java.util.LinkedList;

/**
 * Created by Gustav on 2016-03-02.
 */
public class TestPlanner {

    // instance variables
    TestState startState;
    int hungerLoss;         // hunger lost per timestep
    int thirstLoss;         // thirst lost per timestep
    int energyLoss;         // energy lost per timestep
    LinkedList<TestAction> actionList;  // list of possible actions
    LinkedList<TestState> unexploredStates; // states found that have not yet been explored
    LinkedList<TestState> exploredStates; // states that have been explored

    // constructor
    public TestPlanner(int cH, int cT, int cE, int hL, int tL, int eL) {
        startState = new TestState(cH, cT, cE, 0, 0, 0, new LinkedList<TestAction>()); // zeroes for testing. Will be changed later
        hungerLoss = hL;
        thirstLoss = tL;
        energyLoss = eL;
        actionList = new LinkedList<TestAction>();
        exploredStates = new LinkedList<TestState>();
        unexploredStates = new LinkedList<TestState>();
        unexploredStates.add(startState);
    }

    // function for inserting new actions
    public void addActions (LinkedList<TestAction> newActions) {
        actionList.addAll(newActions);
    }

    public TestState plan (int searchDepth) {
        TestState current;

        while (unexploredStates.size() != 0) { // if list is empty return bestState()
            current = unexploredStates.getFirst(); // take the first unexplored State
            exploredStates.add(current); // put the state in explored list
            if (current.stateList.size() < searchDepth) { // If the state is as deep as searchDepth then dump it
                // explore the state with every possible action that doesn't give value() = -1 and put those states in unexplored
                for(TestAction a : actionList) {
                    if (a.reqFood <= current.stateFood && a.reqGold <= current.stateGold && a.reqWater <= current.stateWater) { //state can be explored
                        LinkedList<TestAction> newActionList = new LinkedList<TestAction>();
                        newActionList.addAll(current.stateList);
                        newActionList.add(a);
                        TestState newState = new TestState( current.stateHunger + a.hungerGain - hungerLoss*a.time,
                                                            current.stateThirst + a.thirstGain - thirstLoss*a.time,
                                                            current.stateEnergy + a.energyGain - energyLoss*a.time,
                                                            current.stateGold + a.goldGain - a.reqGold,
                                                            current.stateFood + a.foodGain - a.reqFood,
                                                            current.stateWater + a.waterGain - a.reqWater,
                                                            newActionList);
                        if (newState.value() > 0) { //if the state has any value keep it
                            unexploredStates.add(newState);
                        } // if the state has no value then dump it
                    }
                }
            }
        }
        return bestState();
    }

    private TestState bestState () { //finds the best state among the unexplored and explored states except the startState.
        TestState good;
        int goodValue = 0;
        for (TestState s : exploredStates) {
            if (s.value() > goodValue) {good = s;}
        }
        for (TestState s : unexploredStates) {
            if (s.value() > goodValue) {good = s;}
        }
    }

    private class TestState {
        int stateHunger;
        int stateThirst;
        int stateEnergy;
        int stateGold;
        int stateFood;
        int stateWater;
        LinkedList<TestAction> stateList;   // list of actions to perform to get to this state

        // constructor
        public TestState (int sH, int sT, int sE, int sG, int sF, int sW, LinkedList<TestAction> actions) {
            stateHunger = sH;
            stateThirst = sT;
            stateEnergy = sE;
            stateGold =  sG;
            stateFood = sF;
            stateWater = sW;
            stateList = actions;
        }

        // function for valuing different states
        private double value() {
            if (stateHunger < 1 || stateThirst < 1) {
                return -1; // this would kill the character so a state returning -1 is dumped
            } else {
                return stateEnergy * stateHunger * stateThirst + 0.1*stateGold + 0.1*stateFood + 0.1*stateWater;
            }
        }
    }

    class TestAction {
        int hungerGain;     // hunger gained at end of action.
        int thirstGain;     // thirst -||-
        int energyGain;     // energy -||-
        int foodGain;
        int waterGain;
        int goldGain;
        boolean affected;   // whether or not the characters needs will be affected by time when taking action.
        int time;           // how much time will it take to finish the action.
        int reqFood;        // how much food is needed to perform action;
        int reqWater;       // how much water is needed to perform action;
        int reqGold;        // how much gold is needed to perform action;
    }
}