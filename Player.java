import java.awt.Color;

public class Player {
    private char key;
    private int positionX;
    private int positionY;
    private Color color;

    public Player(char key, int positionY, Color color) {
        this.key = key;
        this.positionX = 50; // Initial starting position
        this.positionY = positionY;
        this.color = color;
    }

    public char getKey() {
        return key;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Color getColor() {
        return color;
    }

    public void move(int distance) {
        positionX += distance;
    }

    public boolean hasWon(int finishLineX) {
        return positionX >= finishLineX;
    }
}
