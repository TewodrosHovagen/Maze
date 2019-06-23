package maze.player;

import Utils.directionEnum.Enums.MainDirectionsEnum;

public class PlayerRandom extends Player {

    private MainDirectionsEnum newRandomDirection;
    private int counter;
    private boolean isBookMark;
    private boolean addBookMark;
    public PlayerRandom() {
        addBookMark=true;
        this.newRandomDirection = MainDirectionsEnum.randomMainDirection();
        mainDirection = MainDirectionsEnum.randomMainDirection();
    }

    @Override
    public MainDirectionsEnum move() {
        if (isHitWall) {
            setNewRandomDirection();
            isHitWall = false;
            counter=0;
            return mainDirection;
        }
        counter++;
        if(counter>=11){
            counter=0;
            setNewRandomDirection();
            return mainDirection;
        }
        return mainDirection;
    }

//    private Map<MainDirectionsEnum, MainDirectionsEnum> backStep = new HashMap<>() {{
//        put(MainDirectionsEnum.UP, MainDirectionsEnum.DOWN);
//        put(MainDirectionsEnum.DOWN, MainDirectionsEnum.UP);
//        put(MainDirectionsEnum.RIGHT, MainDirectionsEnum.LEFT);
//        put(MainDirectionsEnum.LEFT, MainDirectionsEnum.RIGHT);
//    }};

    private void setNewRandomDirection(){
        do {
            newRandomDirection = MainDirectionsEnum.randomMainDirection();
        } while (newRandomDirection.equals(mainDirection));
        mainDirection = newRandomDirection;
    }
    @Override
    public void hitBookmark(int seq) {
        isBookMark = true;
    }

}
