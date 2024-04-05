package game;

public class Bird {

    private int x;
    private int y;
    private int velocity;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        velocity = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update() {
        velocity += FlappyBird.GRAVITY;
        y += velocity;
    }

    public void flap() {
        velocity = FlappyBird.FLAP;
    }
}
