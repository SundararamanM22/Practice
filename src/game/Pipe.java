package game;

public class Pipe {

    private int x;
    private int topHeight;
    private int bottomHeight;

    public Pipe(int x, int gap) {
        this.x = x;
        int gapPosition = (int) (Math.random() * (FlappyBird.HEIGHT - gap));
        topHeight = gapPosition;
        bottomHeight = FlappyBird.HEIGHT - (gapPosition + gap);
    }

    public int getX() {
        return x;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public int getBottomY() {
        return topHeight + FlappyBird.PIPE_GAP;
    }

    public void update() {
        x -= 5;
    }

    public boolean collidesWith(Bird bird) {
        return bird.getX() + FlappyBird.BIRD_SIZE > x
                && bird.getX() < x + FlappyBird.PIPE_WIDTH
                && (bird.getY() < topHeight || bird.getY() + FlappyBird.BIRD_SIZE > getBottomY());
    }
}
