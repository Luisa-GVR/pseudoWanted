import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JFrame implements ActionListener {

    private List<Character> characters;
    private Level level;
    private GamePanel gamePanel;
    private Timer timer;
    private Timer countdownTimer;
    private int timerRemaining = 60; //start w 60 sec
    private int score = 100; // score
    private Clip backgroundMusic;


    private Random random;
    private boolean gameRunning = true;


    public Game() {

        random = new Random();

        // initialize level
        level = new Level();
        level.setX1Corner(0);
        level.setY1Corner(200);
        level.setX2Corner(800);
        level.setY2Corner(800);
        level.setSpeed(1);
        level.setLevelDifficulty(1);
        level.setNumberOfCharacters(5);

        characters = new ArrayList<>();
        addCharacters(level.getNumberOfCharacters());

        gamePanel = new GamePanel();
        this.add(gamePanel);

        setTitle("Wanted!");
        setSize(819, 841);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // initialize timer
        timer = new Timer(16, this); // 60fps?
        timer.start();

        countdownTimer = new Timer(1000, e -> {
            if (timerRemaining > 0) {
                timerRemaining--;
            } else {
                gameOver();
            }
        });
        countdownTimer.start();

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        playBackgroundMusic();

    }


    private void playBackgroundMusic() {
        try {
            InputStream audioInputStream = getClass().getResourceAsStream("/sfx/wantedBGMusic.wav");
            if (audioInputStream == null) {
                throw new IOException("Music not found");
            }
            backgroundMusic = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioInputStream);
            backgroundMusic.open(ais);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void gameOver() {
        gameRunning = false;
        String message = "GAME OVER \n your score was: " + score + " and you reached level: " + level.getLevelDifficulty() + "\n wanna restart!??!";

        int response = JOptionPane.showConfirmDialog(this, message, "game over", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() {
        //reset params
        score = 100;
        timerRemaining = 60;
        level.setLevelDifficulty(1);
        level.setSpeed(1);
        characters.clear();
        level.setNumberOfCharacters(5);
        addCharacters(level.getNumberOfCharacters());

        //restart game
        gameRunning = true;
        countdownTimer.restart();
        timer.start();
    }

    private void handleMouseClick(int mouseX, int mouseY) {

        Character characterToFind = characters.get(0);

        // check if click inside area
        int targetX = characterToFind.getxPosition();
        int targetY = characterToFind.getyPosition();
        //characters are 60x60px
        int targetWidth = 60;
        int targetHeight = 60;

        if (mouseX >= targetX && mouseX <= targetX + targetWidth &&
                mouseY >= targetY && mouseY <= targetY + targetHeight) {
            // time to level up bb
            levelUp();
        } else{
            //misclick, less points!
            score -= 100;
        }
    }



    private void addCharacters(int numberOfNewCharacters) {
        level.setCharacterToFind(random.nextInt(4));

        for (int i = 0; i < numberOfNewCharacters; i++) {
            Character character = new Character();
            character.setxPosition(random.nextInt(level.getX2Corner() - 50));
            character.setyPosition(random.nextInt(level.getY2Corner() - 50));
            character.setxDirection(random.nextBoolean() ? 1 : -1);
            character.setyDirection(random.nextBoolean() ? 1 : -1);

            // first one is the one to find
            if (i == 0) {
                character.setType(level.getCharacterToFind());
            } else {
                int randomType;
                do {
                    randomType = random.nextInt(4);
                } while (randomType == level.getCharacterToFind()); //make sure it wont be the same as the one to find!

                character.setType(randomType);
            }

            character.changeLook();

            characters.add(character);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // move characters
        for (Character character : characters) {
            character.move(level);
        }

        gamePanel.repaint();
    }

    private void levelUp() {
        level.levelUp();
        level.speedUp();
        level.charactersUp();
        characters.clear();
        addCharacters(level.getNumberOfCharacters());
        score+= 100 + (level.getLevelDifficulty()*10);
    }

    //drawing
    private class GamePanel extends JPanel {
        private Image backgroundImage;
        private static final int OUTSIDE_X = 350;
        private static final int OUTSIDE_Y = 50;
        private static final int CHARACTER_WIDTH = 60;
        private static final int CHARACTER_HEIGHT = 60;
        private static final int CHARACTER_TO_FIND_WIDTH = 60;
        private static final int CHARACTER_TO_FIND_HEIGHT = 60;

        public GamePanel() {
            try {
                backgroundImage = ImageIO.read(getClass().getResource("/pictures/wanted_poster.png"));
            } catch (IOException e) {
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBackground(g);
            //game boundaries
            drawGameBoundaries(g);
            // level, timer, score
            drawHUD(g);
            drawCharacterToFind(g);
            drawCharacters(g);
        }

        private void drawBackground(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), 200, this);
            }
        }

        private void drawGameBoundaries(Graphics g) {
            g.setColor(Color.WHITE);
            g.drawRect(
                    level.getX1Corner(),
                    level.getY1Corner(),
                    level.getX2Corner() - level.getX1Corner(),
                    level.getY2Corner() - level.getY1Corner()
            );
        }

        private void drawHUD(Graphics g) {
            g.setColor(Color.red);
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Mario64.ttf")).deriveFont(Font.BOLD, 24f);
                g.setFont(customFont);
            } catch (Exception e) {
                g.setFont(new Font("Arial", Font.PLAIN, 18));
            }

            // level
            g.drawString("LEVEL:", OUTSIDE_X - 100, OUTSIDE_Y);
            g.drawString(String.valueOf(level.getLevelDifficulty()), OUTSIDE_X - 100, OUTSIDE_Y + 30);

            // timer
            g.drawString("TIME:", OUTSIDE_X + 150, OUTSIDE_Y);
            g.drawString(timerRemaining + "s", OUTSIDE_X + 150, OUTSIDE_Y + 30);

            // score
            g.drawString("SCORE:", OUTSIDE_X - 100, OUTSIDE_Y + 100);
            g.drawString(String.valueOf(score), OUTSIDE_X - 100, OUTSIDE_Y + 120);
        }

        private void drawCharacterToFind(Graphics g) {
            Character characterToFind = characters.get(0);
            g.drawImage(
                    characterToFind.getLook(),
                    OUTSIDE_X + 25,
                    OUTSIDE_Y + 12,
                    CHARACTER_TO_FIND_WIDTH,
                    CHARACTER_TO_FIND_HEIGHT,
                    this
            );
        }

        private void drawCharacters(Graphics g) {
            for (Character character : characters) {
                g.drawImage(
                        character.getLook(),
                        character.getxPosition(),
                        character.getyPosition(),
                        CHARACTER_WIDTH,
                        CHARACTER_HEIGHT,
                        this
                );
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}
