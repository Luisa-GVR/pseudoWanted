import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Character {

    Image look;
    int xPosition;
    int yPosition;

    int xDirection; // 1 moving right, -1 moving left
    int yDirection; // 1 moving down, -1 moving up

    final int pxLook = 60;
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
        Random random = new Random();
        boolean collisionChange = random.nextBoolean();

        if (xPosition <= level.getX1Corner()) {
            xPosition = level.getX1Corner(); // stops clipping
            xDirection *= -1;
            collision = true;
            if (collisionChange) {
                yDirection = random.nextInt(3) - 1;
            }
        } else if (xPosition >= level.getX2Corner() - pxLook) {
            xPosition = level.getX2Corner() - pxLook; // stops clipping
            xDirection *= -1;
            collision = true;
            if (collisionChange) {
                yDirection = random.nextInt(3) - 1;
            }
        }


        if (yPosition <= level.getY1Corner()) {
            yPosition = level.getY1Corner(); // stops clipping
            yDirection *= -1;
            collision = true;
            if (collisionChange) {
                xDirection = random.nextInt(3) - 1;
            }
        } else if (yPosition >= level.getY2Corner() - pxLook) {
            yPosition = level.getY2Corner() - pxLook;  // stops clipping
            yDirection *= -1;
            collision = true;
            if (collisionChange) {
                xDirection = random.nextInt(3) - 1;
            }
        }
        if (collision) {
            adjustDirection();
        }
    }

    private void adjustDirection() {
        Random random = new Random();
        boolean diagonal = random.nextBoolean();

        if (diagonal) {
            // diagonal
            if (xDirection == 0) {
                xDirection = random.nextBoolean() ? 1 : -1;
            }
            if (yDirection == 0) {
                yDirection = random.nextBoolean() ? 1 : -1;
            }
        } else {
            // straight mov
            if (random.nextBoolean()) {
                xDirection = 0;
            } else {
                yDirection = 0;
            }
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
