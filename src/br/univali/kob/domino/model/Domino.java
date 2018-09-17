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
        validateNumberOfPlayers(numberOfPlayers);
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
        int gameRound = 0;
        while (!isGameOver()) {
            players[i].setNumberOfRocksDrawerOnRound(0);
            if (i == startPlayerIndex) numberOfPlayersPass = 0;
            DominoRock rock = players[i].findAndReturnDominoRock(dominoRound);
            dominoRound.setLastRockPlacedIndex(gameRoundCheckResults(rock, players[i]));
            gameRound = dominoRound.getGameRound();
            dominoRound.setGameRound(++gameRound);
            showDominoSimulateRound(players[i], rock, players[i].getNumberOfRocksDrawerOnRound());
            if (i < numberOfPlayers - 1) {
                i++;
            } else {
                i = 0;
            }
        }
    }

    private int gameRoundCheckResults (DominoRock rock, DominoPlayer player) {
        int gameTableIndex = 0;
        int numberOfRocksDrawer = 0;
        if (rock == null) {
            do {
                rock = dominoMount.drawDominoRock();
                if (rock != null) {
                    numberOfRocksDrawer++;
                    player.setNumberOfRocksDrawerOnRound(numberOfRocksDrawer);
                    player.addPlayerRock(rock);
                    rock = player.findAndReturnDominoRock(dominoRound);
                }
            } while (dominoMount.getDominoRocksCounter() > 0 && rock == null);
            if (rock == null) {
                numberOfPlayersPass++;
            } else {
                gameTableIndex = dominoRound.addRockToGameTable(rock);
            }
        } else {
            gameTableIndex = dominoRound.addRockToGameTable(rock);
            if (player.getRocksCounter() < 1) {
                playerWinner = player;
            }
        }
        return gameTableIndex;
    }

    /**
     * Retorna o estado atual do jogo
     * @return boolean com estado do jogo
     */
    public boolean isGameOver () {
        return numberOfPlayersPass >= numberOfPlayers || playerWinner != null;
    }

    private int getWinnerPoints () {
        if (playerWinner != null) return 0;

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

    private void validateNumberOfPlayers (int players) {
        if (players < 2 || players > 4) {
            throw new IllegalArgumentException("O numero de jogadores pode ser 2, 3 ou 4");
        }
    }

    private void showDominoSimulateRound (DominoPlayer player, DominoRock rock, int numberOfRocksDrawer) {
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
            if (dominoRound.getLastRockPlacedIndex() > 0) {
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
            System.out.println("\nJogo acabou! " + playerWinner.getName() + " jogou todas suas pedras!\n");
        } else if (numberOfPlayersPass >= numberOfPlayers) {
            int points = getWinnerPoints();
            System.out.println("\nNinguém acabou com suas peças, porém não existe mais possibilidade de jogadas ou compra de pedras.\n" +
                    "O jogador " + playerWinner.getName() + " ganhou com a menor pontuação entre os outros jogadores.\n" +
                    "Sua pontuação foi de " + points + " pontos!\n");
        }
    }
}
