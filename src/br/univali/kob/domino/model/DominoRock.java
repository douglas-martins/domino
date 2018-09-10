package br.univali.kob.domino.model;

public class DominoRock {
    private int[] rockNumbers = new int[2];

    public DominoRock (int end1, int end2) {
        this.rockNumbers[0] = end1;
        this.rockNumbers[1] = end2;
    }

    /**
     * Retorna o valor da pedra
     * @return valor da pedra
     */
    public int[] getRockNumbers () {
        return rockNumbers;
    }

    /**
     * Retorna o valor da soma dos números que estão na pedra
     * @return soma dos valores da pedra
     */
    public int getRocksNumberSum () { return getRockNumbers()[0] + getRockNumbers()[1]; }
}
