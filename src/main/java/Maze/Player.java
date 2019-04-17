package Maze;

public interface Player {

    public Directions move(Directions direction);

    public void hitWall();

    public void hitBookmark(int seq);
}
