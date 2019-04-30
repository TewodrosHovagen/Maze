package maze;

public interface PlayerInterface {


    public DirectionsEnum move();

    public void hitWall();

    public void hitBookmark(int seq);
}
