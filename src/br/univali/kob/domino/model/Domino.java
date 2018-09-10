package br.univali.kob.domino.model;

import java.util.Arrays;

public class Domino {
    private DominoMount dominoMount;
    private DominoRound dominoRound;
    private DominoPlayer[] players;
    private DominoPlayer playerWinner;
    private final int numberOfPlayers;
    private int startPlayerIndex = 0;
    private int numberOfPlayersPass = 0;

    public Domino (int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        dominoMount = new DominoMount();
        dominoRound = new DominoRound();
        players = new DominoPlayer[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) { // TODO: substituir por foreach
            players[i] = new DominoPlayer();
            players[i].setName("Jogador " + String.valueOf(i));
        }
        distributePlayersDominoRocks();
        setPlayerIndexStart();
    }

    /**
     * Retorna o valor de players
     * @return players
     */
    public DominoPlayer[] getPlayers () {
        return players;
    }

    /**
     * Retorna o valor do index que começa o jogo
     * @return startPlayersIndex
     */
    public int getStartPlayerIndex () { return startPlayerIndex; }


    /**
     * Função para simluar o loop do game
     */
    public void gameLoop () {
        int i = startPlayerIndex;
        int numberOfRocksDrawed = 0;
        int gameTableIndex = 0;
        while (!isGameOver() && i <= numberOfPlayers) {
            numberOfRocksDrawed = 0;
            numberOfPlayersPass = 0;
            DominoRock rock = players[i].findAndReturnDominoRock(0, dominoRound.getGameTableCounter());
            if (rock == null) {
                do {
                    rock = dominoMount.drawDominoRock();
                    if (rock != null) {
                        numberOfRocksDrawed++;
                        players[i].addPlayerRock(rock);
                        rock = players[i].findAndReturnDominoRock(0, dominoRound.getGameTableCounter());
                    }
                } while (dominoMount.getDominoRocksCounter() > 0 && rock == null);
                if (rock == null) {
                    numberOfPlayersPass++;
                } else {
                    gameTableIndex = dominoRound.addRockToGameTable(rock);
                    players[i].removePlayerRock(players[i].getRocksCounter());
                }
            } else {
                gameTableIndex = dominoRound.addRockToGameTable(rock);
                if (players[i].getRocksCounter() < 1) {
                    //TODO: acabar jogo com players[i] como vencedor
                    playerWinner = players[i];
                }
            }
            int gameRound = dominoRound.getGameRound();
            dominoRound.setGameRound(gameRound++);
            if (i >= numberOfPlayers) {
                i = 0;
            } else {
                i++;
            }
            debugDominoSimulate(players[i], rock, numberOfRocksDrawed, gameTableIndex);
        }
    }

    public boolean isGameOver () {
        return numberOfPlayersPass >= numberOfPlayers || playerWinner != null;
    }

    private void distributePlayersDominoRocks () {
        int indexRocks = 0;
        DominoRock[] rockCopy = Arrays.copyOf(dominoMount.getDominoRocks(), dominoMount.getDominoRocksCounter());

        for (DominoPlayer player : players) {
            for (int i = 0; i < 7; i++) {
                player.addPlayerRock(rockCopy[indexRocks]);
                dominoMount.removeDominoRock(indexRocks);
                indexRocks++;
            }
        }

//        // Debug jogadores e suas pedras
//        for (DominoPlayer player : players) {
//            System.out.println(player.getName());
//            for (int i = 0; i < player.getPlayerRocks().length; i++) {
//                System.out.print("Peça " + i + ":");
//                System.out.print("[" + player.getPlayerRocks()[i].getRockNumbers()[0] + ", " +
//                        player.getPlayerRocks()[i].getRockNumbers()[1] + "]");
//                System.out.println();
//            }
//        }
//
//        // Debug pedras que restaram no monte
//        System.out.println("TAMANHO: " + dominoMount.getDominoRocksCounter());
//        System.out.println("{ SOBRAM AS PEDRAS PARA COMPRA: ");
//        for (int i = 0; i < dominoMount.getDominoRocksCounter(); i++) {
//            System.out.print("[" + dominoMount.getDominoRocks()[i].getRockNumbers()[0] + ", " +
//                    dominoMount.getDominoRocks()[i].getRockNumbers()[1] + "] / ");
//        }
//        System.out.println();
//        System.out.println("}");
    }

    private void setPlayerIndexStart () {
        DominoPlayer bigger = players[0];
        for (int i = 1; i < numberOfPlayers; i++) {
            if (players[i].getBiggerDominoRock()[0] > bigger.getBiggerDominoRock()[0]) {
                bigger = players[i];
                startPlayerIndex = i;
            }
        }
        // System.out.println("Started index: " + startPlayerIndex);
    }

    private void debugDominoSimulate (DominoPlayer player, DominoRock rock, int numberOfRocksDrawed, int gameTableIndex) {
        System.out.println("Rodada: " + dominoRound.getGameRound());
        System.out.println("Jogador: " + player.getName());
        System.out.println("Compradas: " + numberOfRocksDrawed);
        System.out.print("Pedra usada: ");
        if (rock != null){
            System.out.print("[" + rock.getRockNumbers()[0] + ", " + rock.getRockNumbers()[1] + "]");
            if (gameTableIndex > 0) {
                System.out.println("(lado 1)");
            } else {
                System.out.println("(lado 0)");
            }
        }
        System.out.print("Tabuleiro: ");
        for (int i = 0; i < dominoRound.getGameTableCounter(); i++) {
            System.out.print("[" + dominoRound.getGameTable()[i].getRockNumbers()[0]
                    + ", " + dominoRound.getGameTable()[i].getRockNumbers()[1] + "] ");
        }
        if (rock != null) System.out.println("--> " + "[" + rock.getRockNumbers()[0] + ", " + rock.getRockNumbers()[1] + "]");
        System.out.println("###################################################");
    }
}
