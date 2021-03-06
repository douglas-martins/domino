package br.univali.kob.domino.model;

public class DominoRound {
    private DominoRock[] gameTable;
    private int gameTableCounter = 0;
    private int gameRound = 0;
    private int lastRockPlacedIndex = 0;

    public DominoRound () {
        gameTable = new DominoRock[56];
        for (int i = 0; i < gameTable.length; i++) {
            gameTable[i] = new DominoRock(10, 10);
        }
    }

    /**
     * Retorna o index da ultima pedra colocada na mesa
     * @return lastRockPlacedIndex: posição no array da mesa, da ultima pedra colocada
     */
    public int getLastRockPlacedIndex() {
        return lastRockPlacedIndex;
    }

    /**
     * Atribui o valor do index no array da mesa da ultima pedra colocada
     * @param lastRockPlacedIndex posição no array da mesa, da ultima pedra colocada
     */
    public void setLastRockPlacedIndex(int lastRockPlacedIndex) {
        this.lastRockPlacedIndex = lastRockPlacedIndex;
    }

    /**
     * Retorna o valor de gameRound
     * @return gameRound: valor que define em qual rodada o jogo esta
     */
    public int getGameRound() {
        return gameRound;
    }

    /**
     * Modifica o valor de gameRound
     * @param gameRound: atribui um valor para qual rodada o jogo esta
     */
    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    /**
     * Adiciona +1 em gameRound (gameRound++)
     */
    public void addOneGameRound () { ++this.gameRound; }

    /**
     * Retorna o valor da gameTableCounter
     * @return gameTableCounter
     */
    public int getGameTableCounter () { return gameTableCounter; }

    /**
     * Retorna a mesa do jogo
     * @return gameTable: retorna o array da mesa do jogo
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

    /**
     * Verifica se a pedra pode ser encaixa na mesa
     * @param rock: pedra que será verificada se encaixa
     * @return true or false: true se encaixa e false se não encaixa
     */
    public boolean isDominoRockFitOnGameTable (DominoRock rock) {
        return rock.getRockNumbers()[1] == gameTable[0].getRockNumbers()[0] ||
                rock.getRockNumbers()[0] == gameTable[gameTableCounter].getRockNumbers()[1];
    }
}
