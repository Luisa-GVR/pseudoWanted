import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Character {

    Image look;
    int xPosition;
    int yPosition;

    int xDirection; // 1 moving right, -1 moving left
    int yDirection; // 1 moving down, -1 moving up

    /*
    0=wario
    1=yoshi
    2=mario
    3=luigi
    */
    int type;

    public void move(Level level){

        xPosition += xDirection * level.getSpeed();
        yPosition += yDirection * level.getSpeed();

        checkCollision(level);
    }

    private void checkCollision(Level level) {
        boolean collision = false;

        // Check X boundaries
        if (xPosition <= level.getX1Corner()) {
            xPosition = level.getX1Corner(); // Adjust position to boundary
            xDirection *= -1;
            collision = true;
        } else if (xPosition >= level.getX2Corner() - 50) { // Assuming 50 is the character's width
            xPosition = level.getX2Corner() - 50; // Adjust position to boundary
            xDirection *= -1;
            collision = true;
        }

        // Check Y boundaries
        if (yPosition <= level.getY1Corner()) {
            yPosition = level.getY1Corner(); // Adjust position to boundary
            yDirection *= -1;
            collision = true;
        } else if (yPosition >= level.getY2Corner() - 50) { // Assuming 50 is the character's height
            yPosition = level.getY2Corner() - 50; // Adjust position to boundary
            yDirection *= -1;
            collision = true;
        }

        // Optional: Change to diagonal movement after collision
        if (collision) {
            adjustDirection();
        }
    }

    private void adjustDirection() {
        Random random = new Random();
        boolean diagonal = random.nextBoolean();

        if (diagonal){
            xDirection *= -1;
            yDirection *= -1;
        }

    }


    public void changeLook() {
        switch (type) {
            case 0 -> setLook(new ImageIcon(getClass().getResource("/pictures/wario.png")).getImage());
            case 1 -> setLook(new ImageIcon(getClass().getResource("/pictures/yoshi.png")).getImage());
            case 2 -> setLook(new ImageIcon(getClass().getResource("/pictures/mario.png")).getImage());
            case 3 -> setLook(new ImageIcon(getClass().getResource("/pictures/luigi.png")).getImage());
            default -> setLook(new ImageIcon(getClass().getResource("/pictures/wario.png")).getImage());
        }
    }

    //getter and setter
    public Image getLook() {
        return look;
    }

    public void setLook(Image look) {
        this.look = look;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }
}
