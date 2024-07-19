import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GamePanel extends JPanel {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 200;
    private static final int FINISH_LINE_X = 750;
    private static final int MOVE_DISTANCE = 10;

    private Player player1;
    private Player player2;
    private boolean running;
    private ExecutorService executor;

    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.WHITE);
        this.running = true;

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();

        selectKeys();
        startGame();
    }

    private void selectKeys() {
        char key1 = promptForKey("Player 1");
        char key2 = promptForKey("Player 2");

        player1 = new Player(key1, 100, Color.BLUE);
        player2 = new Player(key2, 150, Color.GREEN);
    }

    private char promptForKey(String player) {
        String input;
        do {
            input = JOptionPane.showInputDialog(this, player + ": Pick a key to start the race:", "Select Key", JOptionPane.PLAIN_MESSAGE);
        } while (input == null || input.length() != 1);

        return input.charAt(0);
    }

    private void handleKeyPress(KeyEvent e) {
        if (e.getKeyChar() == player1.getKey()) {
            movePlayer(player1);
        } else if (e.getKeyChar() == player2.getKey()) {
            movePlayer(player2);
        }
    }

    private void movePlayer(Player player) {
        player.move(MOVE_DISTANCE);
        if (player.hasWon(FINISH_LINE_X)) {
            JOptionPane.showMessageDialog(this, (player == player1 ? "Player 1" : "Player 2") + " Wins!");
            running = false;
        }
        repaint();
    }

    private void startGame() {
        executor = Executors.newFixedThreadPool(2);
        executor.submit(this::playerTimer);
        executor.submit(this::playerTimer);
    }

    private void playerTimer() {
        while (running) {
            try {
                Thread.sleep(1000); // simulate some background task
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Finish Line", FINISH_LINE_X - 30, 90);
        g.drawLine(FINISH_LINE_X, 0, FINISH_LINE_X, PANEL_HEIGHT);

        drawPlayer(g, player1);
        drawPlayer(g, player2);
    }

    private void drawPlayer(Graphics g, Player player) {
        g.setColor(player.getColor());
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(Character.toString(player.getKey()), player.getPositionX(), player.getPositionY());
    }
}
