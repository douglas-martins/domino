package br.univali.kob.domino.model;

public class DominoPlayer {
    private String name;
    private DominoRock[] playerRocks;
    private int rocksCounter = 0;

    public DominoPlayer () {
        playerRocks = new DominoRock[7];
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
     * Adiciona uma pedra a mão do jogador
     * @param rock
     */
    public void addPlayerRock (DominoRock rock) {
        playerRocks[rocksCounter++] = rock;
    }

    /**
     * Remove uma peça do array de peças que o jogador tem na mão
     * @param indexElement
     */
    public void removePlayerRock (int indexElement) {
        playerRocks = ArraysHelper.removeElement(playerRocks, indexElement, rocksCounter);
        rocksCounter--;
    }

    public DominoRock findAndReturnDominoRock (int beginTable, int endTable) {
        for (DominoRock rock : playerRocks) {
            for (int i = 0; i < 2; i++) {
                if (rock.getRockNumbers()[i] == beginTable ||
                    rock.getRockNumbers()[i] == endTable) {
                    return rock;
                }
            }
        }
        return null;
    }

    /**
     * Retorna a pedra de maior valor (conforme a regra do jogo Domino) do jogador
     * @return rockNumbers
     */
    public int[] getBiggerDominoRock () { // TODO: refatorar esse código
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
        return rockNumber > 0 ? playerRocks[rockIndex].getRockNumbers() : playerRocks[rockIndexDif].getRockNumbers();
    }
}
