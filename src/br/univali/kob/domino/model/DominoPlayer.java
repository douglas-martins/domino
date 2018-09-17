package br.univali.kob.domino.model;

public class DominoPlayer {
    private String name;
    private DominoRock[] playerRocks;
    private int rocksCounter = 0;
    private int numberOfRocksDrawerOnRound = 0;

    public DominoPlayer () {
        playerRocks = new DominoRock[21];
    }

    /**
     * Atribui valor para a variavel name
     * @param name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Retorna o valor da variavel name
     * @return variavel name
     */
    public String getName () {
        return name;
    }

    /**
     * Retorna o valor do contador de pedras
     * @return valor do contador de pedras
     */
    public int getRocksCounter () {
        return rocksCounter;
    }

    /**
     * Retorna o valor das pedras do jogador
     * @return playerRocks
     */
    public DominoRock[] getPlayerRocks() {
        return playerRocks;
    }

    /**
     *
     * @return
     */
    public int getNumberOfRocksDrawerOnRound() {
        return numberOfRocksDrawerOnRound;
    }

    /**
     *
     * @param numberOfRocksDrawerOnRound
     */
    public void setNumberOfRocksDrawerOnRound(int numberOfRocksDrawerOnRound) {
        this.numberOfRocksDrawerOnRound = numberOfRocksDrawerOnRound;
    }

    /**
     * Adiciona uma pedra a mão do jogador
     * @param rock
     */
    public void addPlayerRock (DominoRock rock) { playerRocks[rocksCounter++] = rock; }

    /**
     * Remove uma peça do array de peças que o jogador tem na mão
     * @param indexElement
     */
    public void removePlayerRock (int indexElement) {
        playerRocks = ArraysHelper.removeElement(playerRocks, indexElement, rocksCounter);
        rocksCounter--;
    }

    /**
     * Procura, na mao do jogador, e retorna uma pedra que possa ser colocada na mesa do jogo
     * @param dominoRound
     * @return retorna a pedra que pode ser jogada (null se não existir uma pedra)
     */
    public DominoRock findAndReturnDominoRock (DominoRound dominoRound) { // TODO: refactor
        DominoRock returnRock = null;
        if (dominoRound.getGameTable()[0].getRockNumbers()[0] > 6) {
            returnRock = playerRocks[getBiggerDominoRockIndex()];
            removePlayerRock(getBiggerDominoRockIndex());
            return returnRock;
        }
        for (int i = 0; i < rocksCounter; i++) {
            if (dominoRound.isDominoRockFitOnGameTable(playerRocks[i])) {
                returnRock = playerRocks[i];
                removePlayerRock(i);
                return returnRock;
            }
        }
        for (int i = 0; i < rocksCounter; i++) {
            playerRocks[i].invertDominoRockNumbers();
            if (dominoRound.isDominoRockFitOnGameTable(playerRocks[i])) {
                returnRock = playerRocks[i];
                removePlayerRock(i);
                return returnRock;
            } else {
                playerRocks[i].invertDominoRockNumbers();
            }
        }
        return null;
    }

    /**
     * Retorna a pedra de maior valor (conforme a regra do jogo Domino) do jogador
     * @return rockNumbers
     */
    public int getBiggerDominoRockIndex () { // TODO: refatorar esse código
        int rockIndex = 0;
        int rockIndexDif = 0;
        int rockNumber = -1;
        int rockNumberDif = -1;
        for (int i = 0; i < rocksCounter; i++) {
            if (playerRocks[i].getRockNumbers()[0] == playerRocks[i].getRockNumbers()[1]) {
                if (playerRocks[i].getRockNumbers()[0] > rockNumber) {
                    rockNumber = playerRocks[i].getRockNumbers()[0];
                    rockIndex = i;
                }
            } else {
                if (playerRocks[i].getRocksNumberSum() > rockNumberDif) {
                    rockNumberDif = playerRocks[i].getRockNumbers()[0];
                    rockIndexDif = i;
                }
            }
        }
        return rockNumber > 0 ? rockIndex : rockIndexDif;
    }
}
