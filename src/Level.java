public class Level {
    int x1Corner, y1Corner, x2Corner, y2Corner;
    int levelDifficulty;
    int speed;
    int numberOfCharacters;

    int characterToFind;

    public void levelUp(){
        levelDifficulty++;
    }

    public void speedUp() {
        //only speeds up if divisible by 5
        if (levelDifficulty % 5 == 0) {
            speed += 1;
        }
    }


    public void charactersUp(){
        int multiplier = (int) Math.floor(levelDifficulty * 0.1);
        if (multiplier==0){
            numberOfCharacters += 3;
        } else{
            numberOfCharacters += 3*multiplier;
        }

    }


    //getters and setters


    public int getCharacterToFind() {
        return characterToFind;
    }

    public void setCharacterToFind(int characterToFind) {
        this.characterToFind = characterToFind;
    }

    public int getX1Corner() {
        return x1Corner;
    }

    public void setX1Corner(int x1Corner) {
        this.x1Corner = x1Corner;
    }

    public int getY1Corner() {
        return y1Corner;
    }

    public void setY1Corner(int y1Corner) {
        this.y1Corner = y1Corner;
    }

    public int getX2Corner() {
        return x2Corner;
    }

    public void setX2Corner(int x2Corner) {
        this.x2Corner = x2Corner;
    }

    public int getY2Corner() {
        return y2Corner;
    }

    public void setY2Corner(int y2Corner) {
        this.y2Corner = y2Corner;
    }

    public int getLevelDifficulty() {
        return levelDifficulty;
    }

    public void setLevelDifficulty(int levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public void setNumberOfCharacters(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }
}
