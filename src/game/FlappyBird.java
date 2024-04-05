package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyBird extends JFrame {

    protected static final int WIDTH = 800;
    protected static final int HEIGHT = 600;
    protected static final int PIPE_WIDTH = 100;
    protected static final int PIPE_HEIGHT = 600;
    protected static final int PIPE_GAP = 200;
    protected static final int BIRD_SIZE = 40;
    protected static final int BIRD_START_X = 200;
    protected static final int BIRD_START_Y = HEIGHT / 2;
    protected static final int GRAVITY = 2;
    protected static final int FLAP = -20;

    private Bird bird;
    private List<Pipe> pipes;
    private Timer timer;
    private boolean gameOver;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        bird = new Bird(BIRD_START_X, BIRD_START_Y);
        pipes = new ArrayList<>();
        pipes.add(new Pipe(WIDTH, PIPE_GAP));

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
                    bird.flap();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
                    resetGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw pipes
        g2d.setColor(Color.GREEN);
        for (Pipe pipe : pipes) {
            g2d.fillRect(pipe.getX(), 0, PIPE_WIDTH, pipe.getTopHeight());
            g2d.fillRect(pipe.getX(), pipe.getBottomY(), PIPE_WIDTH, pipe.getBottomHeight());
        }

        // Draw bird
        g2d.setColor(Color.RED);
        g2d.fillRect(bird.getX(), bird.getY(), BIRD_SIZE, BIRD_SIZE);

        if (gameOver) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
            g2d.drawString("Game Over! Press Enter to Restart", 200, HEIGHT / 2);
        }
    }

    private void update() {
        if (!gameOver) {
            bird.update();

            for (Pipe pipe : pipes) {
                pipe.update();
                if (pipe.collidesWith(bird)) {
                    gameOver = true;
                    break;
                }
            }

            if (bird.getY() >= HEIGHT || bird.getY() + BIRD_SIZE <= 0) {
                gameOver = true;
            }

            if (pipes.get(pipes.size() - 1).getX() < WIDTH - WIDTH / 2) {
                pipes.add(new Pipe(WIDTH, PIPE_GAP));
            }
        }
    }

    private void resetGame() {
        bird = new Bird(BIRD_START_X, BIRD_START_Y);
        pipes.clear();
        pipes.add(new Pipe(WIDTH, PIPE_GAP));
        gameOver = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlappyBird().setVisible(true);
            }
        });
    }
}
