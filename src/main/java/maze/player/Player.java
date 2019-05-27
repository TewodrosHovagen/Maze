package maze.player;

import Utils.logging.Logger;

import java.util.Set;

public abstract class Player implements PlayerInterface {

    protected boolean isHitWall;
    protected Set<Integer> bookMarks;

    @Override
    public void hitWall() {
        Logger.info("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) {
        bookMarks.add(seq);
    }
    public void setHitWall(boolean hitWall) {
        isHitWall = hitWall;
    }
    public boolean isHitWall() {
        return isHitWall;
    }
    public Set<Integer> getBookMarks() {
        return bookMarks;
    }

    public void setBookMarks(Set<Integer> bookMarks) {
        this.bookMarks = bookMarks;
    }
}

