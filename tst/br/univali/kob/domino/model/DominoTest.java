package br.univali.kob.domino.model;

public class DominoTest {
    private void runDominoTestCase () {
        System.out.println("[runDominoTestCase]");
        Domino domino = new Domino(4);
        domino.getPlayers()[0].setName("Douglas");
        domino.getPlayers()[1].setName("Gabriel");
        domino.getPlayers()[2].setName("Dong");
        domino.getPlayers()[3].setName("Enio");
        //TODO: missing implement
        domino.gameLoop();
        System.out.println("[runDominoTestCase]");
    }

    public static void main (String[] args) {
        DominoTest test = new DominoTest();
        test.runDominoTestCase();
    }
}
