package br.univali.kob.domino.model;

public class DominoMount {
    private DominoRock[] gameRocks;
    private int dominoRocksCounter = 0;

    public DominoMount () {
        gameRocks = new DominoRock[28];
        makeDominoRocks();
    }

    /**
     * Retorna o valor de gameRocks
     * @return gameRocks: array de pedras que ainda estão no monte
     */
    public DominoRock[] getDominoRocks () {
        return gameRocks;
    }

    /**
     * Retorna o valor de dominoRocksCounter
     * @return dominoRocksCounter: retorna a quantidade real de pedras que ainda tem no monte
     */
    public int getDominoRocksCounter () {
        return dominoRocksCounter;
    }

    /**
     * Remove um elemento do array de peças a comprar
     * @param elementIndex: index no array de pedras do monte que sera removido.
     */
    public void removeDominoRock (int elementIndex) {
        gameRocks = ArraysHelper.removeElement(gameRocks, elementIndex, dominoRocksCounter);
        dominoRocksCounter--;
    }

    /**
     * Compra uma pedra do monte
     * @return DominoRock: retorna a pedra comprada (null se não exister mais pedras no monte)
     */
    public DominoRock drawDominoRock () {
        return dominoRocksCounter > 0 ? gameRocks[dominoRocksCounter--] : null;
    }

    private void addDominoRock (DominoRock rock) {
        gameRocks[dominoRocksCounter++] = rock;
    }

    private void makeDominoRocks () {
        for (int i = 0; i < 7; i++) {
            DominoRock rock = new DominoRock(i, i);
            addDominoRock(rock);
            for (int j = (i + 1); j < 7; j++) {
                rock = new DominoRock(i, j);
                addDominoRock(rock);
            }
        }
        ArraysHelper.shuffleElements(gameRocks);
    }
}
