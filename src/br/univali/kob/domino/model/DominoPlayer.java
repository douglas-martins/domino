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
     * @param name: nome que será atribuido ao jogador
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Retorna o valor da variavel name
     * @return name: nome do jogador
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
     * @return playerRocks: array de pedras do jogador.
     */
    public DominoRock[] getPlayerRocks() {
        return playerRocks;
    }

    /**
     * Retorna o valor de pedras compras pelo jogador (no ultimo round).
     * @return numberOfRocksDrawerOnRound: valor da quantidade de pedras compradas no ultimo round
     */
    public int getNumberOfRocksDrawerOnRound() {
        return numberOfRocksDrawerOnRound;
    }

    /**
     *  Altera o valor de pedras compra pelo jogador
     * @param numberOfRocksDrawerOnRound: o valor que sera atribuido para o numero de compras no monte feito pelo jogador
     *                                  (em um round).
     */
    public void setNumberOfRocksDrawerOnRound(int numberOfRocksDrawerOnRound) {
        this.numberOfRocksDrawerOnRound = numberOfRocksDrawerOnRound;
    }

    /**
     * Adiciona uma pedra a mão do jogador
     * @param rock: pedra que sera adicionada no array de pedras do jogador
     */
    public void addPlayerRock (DominoRock rock) { playerRocks[rocksCounter++] = rock; }

    /**
     * Remove uma peça do array de peças que o jogador tem na mão
     * @param indexElement: index do elemento que sera removido do array de pedras
     */
    public void removePlayerRock (int indexElement) {
        playerRocks = ArraysHelper.removeElement(playerRocks, indexElement, rocksCounter);
        rocksCounter--;
    }

    /**
     * Procura, na mao do jogador, e retorna uma pedra que possa ser colocada na mesa do jogo
     * @param dominoRound: rodada em questão que está acontecendo
     * @return DominoRock: retorna a pedra que pode ser jogada (null se não existir uma pedra)
     */
    public DominoRock findAndReturnDominoRock (DominoRound dominoRound) {
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
     * Retorna o index da pedra de maior valor (conforme a regra do jogo Domino) do jogador
     * @return rockNumbers
     */
    public int getBiggerDominoRockIndex () {
        int rockIndex = 0;
        int rockNumber = -1;

        for (int i = 0; i < rocksCounter; i++) {
            if (playerRocks[i].getRockNumbers()[0] == playerRocks[i].getRockNumbers()[1]) {
                if (playerRocks[i].getRockNumbers()[0] > rockNumber) {
                    rockNumber = playerRocks[i].getRockNumbers()[0];
                    rockIndex = i;
                }
            }
        }
        if (rockNumber >= 0) return rockIndex;

        int lastBiggerNumber = 0;
        for (int i = 0; i < rocksCounter; i++) {
            if (playerRocks[i].getRocksNumberSum() > rockNumber && playerRocks[i].getRockNumbers()[0] > lastBiggerNumber) {
                rockNumber = playerRocks[i].getRockNumbers()[0];
                lastBiggerNumber = playerRocks[i].getRockNumbers()[0];
                rockIndex = i;
            }
        }
        return rockIndex;
    }
}
