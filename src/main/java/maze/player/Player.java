package maze.player;

import Utils.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class Player implements PlayerInterface {

    private final Logger log = Logger.getInstance();
    protected boolean isHitWall;
    protected Map<Integer,Integer> bookMarks=new HashMap<>();
    protected int currentSequence=0;

    @Override
    public void hitWall() {
        log.info("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) {
        //TODO need to rethink the preset bookmark
        if(bookMarks.containsKey(seq)){
            int amountOfVisit = bookMarks.get(seq);
            amountOfVisit++;
            bookMarks.put(seq,amountOfVisit);
        }else {
            addBookmark(seq);
        }
    }
    protected void addBookmark(int seq) {
        bookMarks.put(seq,1);
        currentSequence++;
    }
    protected void removeBookmark(int seq) {
        bookMarks.remove(seq);
    }
    protected void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }
    protected boolean isHitWall() {
        return isHitWall;
    }
    protected Map<Integer, Integer> getBookMarks() {
        return bookMarks;
    }

    protected void setBookMarks(Map<Integer, Integer> bookMarks) {
        this.bookMarks = bookMarks;
    }
}

