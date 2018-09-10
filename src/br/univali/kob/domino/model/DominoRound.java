package br.univali.kob.domino.model;

public class DominoRound {
    private DominoRock[] gameTable;
    private int gameTableCounter = 0;
    private int gameRound = 0;

    public DominoRound () {
        gameTable = new DominoRock[56];
        for (int i = 0; i < gameTable.length; i++) {
            gameTable[i] = new DominoRock(10, 10);
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

    /**
     * Adiciona uma pedra para a mesa do jogo
     * @param rock
     * @return retorna index do array da mesa, onde a pedra foi colocada (-1 caso não tenha sido colocado uma pedra)
     */
    public int addRockToGameTable (DominoRock rock) {
        if (gameTableCounter == 0) {
            gameTable[gameTableCounter++] = rock;
            return gameTableCounter;
        }

        if (rock.getRockNumbers()[1] == gameTable[0].getRockNumbers()[0]) {
            gameTableCounter++;
            for (int j = gameTableCounter; j > 0; j--) {
                ArraysHelper.swapElements(gameTable, j - 1, j);
            }
            gameTable[0] = rock;
            System.out.println();
            return 0;
        } else if (rock.getRockNumbers()[0] == gameTable[gameTableCounter].getRockNumbers()[1]) {
            gameTable[gameTableCounter++] = rock;
            return gameTableCounter;
        }
        return -1;
    }

    public boolean isDominoRockFitOnGameTable (DominoRock rock) {
        return rock.getRockNumbers()[1] == gameTable[0].getRockNumbers()[0] ||
                rock.getRockNumbers()[0] == gameTable[gameTableCounter].getRockNumbers()[1];
    }
}
