package br.univali.kob.domino.model;

public class DominoRound {
    private DominoRock[] gameTable;
    private int gameTableCounter = 0;
    private int gameRound = 0;

    public DominoRound () {
        gameTable = new DominoRock[28];
        for (int i = 0; i < gameTable.length; i++) {
            gameTable[i] = new DominoRock(0, 0);
        }
    }

    /**
     * Retorna o valor de gameRound
     * @return gameRound
     */
    public int getGameRound() {
        return gameRound;
    }

    /**
     * Modifica o valor de gameRound
     * @param gameRound
     */
    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    /**
     * Retorna o valor da gameTableCounter
     * @return gameTableCounter
     */
    public int getGameTableCounter () { return gameTableCounter; }

    /**
     * Retorna a mesa do jogo
     * @return
     */
    public DominoRock[] getGameTable() {
        return gameTable;
    }

    public int addRockToGameTable (DominoRock rock) {
        if (gameTableCounter == 0) {
            gameTable[gameTableCounter++] = rock;
            return gameTableCounter;
        }

        for (int i = 0; i < 2; i++) {
            if (rock.getRockNumbers()[i] == gameTable[0].getRockNumbers()[0]) {
                gameTableCounter++;
                for (int j = 0; j < gameTableCounter; j++) {
                    ArraysHelper.swapElements(gameTable, j + 1, j);
                }
                gameTable[0] = rock;
                return 0;
            } else if (rock.getRockNumbers()[i] == gameTable[gameTableCounter].getRockNumbers()[1]) {
                gameTable[gameTableCounter++] = rock;
                return gameTableCounter;
            }
        }
        return 0;
    }
}
