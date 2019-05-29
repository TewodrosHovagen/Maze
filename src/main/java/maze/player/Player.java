package maze.player;

import Utils.logging.Logger;

import java.util.Set;

public abstract class Player implements PlayerInterface {

    private final Logger log = Logger.getInstance();
    protected boolean isHitWall;
    protected Set<Integer> bookMarks;

    @Override
    public void hitWall() {
        log.info("You hit the wall");
        isHitWall = true;
    }


    @Override
    public void hitBookmark(int seq) { log.info("You hit bookmark: "+ seq); }
    public void addBookamrk(int seq) {
        bookMarks.add(seq);
    }
    public void removeBookamrk(int seq) {
        bookMarks.remove(seq);
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

