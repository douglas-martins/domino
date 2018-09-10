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
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new DominoPlayer();
            players[i].setName(" ");
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
     * Função para simluar o loop do game
     */
    public void gameLoop () {
        int i = startPlayerIndex;
        int numberOfRocksDrawer = 0;
        int gameTableIndex = 0;
        while (!isGameOver()) {
            numberOfRocksDrawer = 0;
            if (i == startPlayerIndex) numberOfPlayersPass = 0;
            DominoRock rock = players[i].findAndReturnDominoRock(dominoRound);
            if (rock == null) {
                do {
                    rock = dominoMount.drawDominoRock();
                    if (rock != null) {
                        numberOfRocksDrawer++;
                        players[i].addPlayerRock(rock);
                        rock = players[i].findAndReturnDominoRock(dominoRound);
                    }
                } while (dominoMount.getDominoRocksCounter() > 0 && rock == null);
                if (rock == null) {
                    numberOfPlayersPass++;
                } else {
                    gameTableIndex = dominoRound.addRockToGameTable(rock);
                }
            } else {
                gameTableIndex = dominoRound.addRockToGameTable(rock);
                if (players[i].getRocksCounter() < 1) {
                    playerWinner = players[i];
                }
            }
            int gameRound = dominoRound.getGameRound();
            gameRound++;
            dominoRound.setGameRound(gameRound);
            debugDominoSimulate(players[i], rock, numberOfRocksDrawer, gameTableIndex);
            if (i < numberOfPlayers - 1) {
                i++;
            } else {
                i = 0;
            }
        }
    }

    /**
     * Retorna o estado atual do jogo
     * @return boolean com estado do jogo
     */
    public boolean isGameOver () {
        return numberOfPlayersPass >= numberOfPlayers || playerWinner != null;
    }

    private int getWinner () {
        if (playerWinner != null) {
            return 0;
        }

        int minPoints = 100;
        for (DominoPlayer player : players) {
            int sum = 0;
            for (int i = 0; i < player.getRocksCounter(); i++) {
                sum += player.getPlayerRocks()[i].getRocksNumberSum();
            }
            if (minPoints > sum) {
                minPoints = sum;
                playerWinner = player;
            }
        }
        return minPoints;
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
    }

    private void setPlayerIndexStart () {
        DominoPlayer bigger = players[0];
        for (int i = 1; i < numberOfPlayers; i++) {
            if (players[i].getPlayerRocks()[players[i].getBiggerDominoRockIndex()].getRockNumbers()[0] >
                    bigger.getPlayerRocks()[bigger.getBiggerDominoRockIndex()].getRockNumbers()[0]) {
                bigger = players[i];
                startPlayerIndex = i;
            }
        }
    }

    private void debugDominoSimulate (DominoPlayer player, DominoRock rock, int numberOfRocksDrawer, int gameTableIndex) {
        System.out.println("Rodada: " + dominoRound.getGameRound());
        System.out.println("Jogador: " + player.getName());
        System.out.print("Mão: ");
        for (int i = 0; i < player.getRocksCounter(); i++) {
            System.out.print("[" + player.getPlayerRocks()[i].getRockNumbers()[0] + ", " + player.getPlayerRocks()[i].getRockNumbers()[1] + "] ");
        }
        System.out.println();
        System.out.println("Compradas: " + numberOfRocksDrawer);
        System.out.print("Pedra usada: ");
        if (rock != null){
            System.out.print("[" + rock.getRockNumbers()[0] + ", " + rock.getRockNumbers()[1] + "] ");
            if (gameTableIndex > 0) {
                System.out.println("(lado 1)");
            } else {
                System.out.println("(lado 0)");
            }
        } else {
            System.out.println();
        }
        System.out.print("Tabuleiro: ");
        for (int i = 0; i < dominoRound.getGameTableCounter(); i++) {
            if (dominoRound.getGameTable()[i].getRockNumbers()[0] > 6) continue;
            System.out.print("[" + dominoRound.getGameTable()[i].getRockNumbers()[0]
                    + ", " + dominoRound.getGameTable()[i].getRockNumbers()[1] + "] ");
        }
        if (rock != null) System.out.println(" --> " + rock.getRockNumbers()[0] + ", " + rock.getRockNumbers()[1] + " ");
        System.out.println();
        System.out.println("###################################################");
        if (playerWinner != null) {
            System.out.println("Jogo acabou! " + playerWinner.getName() + " jogou todas suas pedras!");
        } else if (numberOfPlayersPass >= numberOfPlayers) {
            int points = getWinner();
            System.out.println("Ninguém acabou com suas peças, porém não existe mais possibilidade de jogadas ou compra de pedras.\n" +
                    "O jogador " + playerWinner.getName() + " ganhou com a menor pontuação entre os outros jogadores.\n" +
                    "Sua pontuação foi de " + points + " pontos!");
        }
    }
}
