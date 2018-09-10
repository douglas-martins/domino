package br.univali.kob.domino.model;

import java.util.Scanner;

public final class Console {
    /**
     * Arquivo para leitura diretamente do console (System.in).
     */
    private final static Scanner in = new Scanner(System.in);

    /**
     * Lê uma linha do console.
     *
     * @param caption Texto que identifica o que está sendo solicitado (rótulo)
     * @return uma string com a linha lida
     */
    private static String readLine(String caption) {
        System.out.printf("%s ", caption);
        return in.nextLine();
    }

    /**
     * Lê uma string do console.
     *
     * @param caption Texto que identifica o que está sendo solicitado (rótulo)
     * @return uma string com a linha lida
     */
    public static String readlnString(String caption) {
        return readLine(caption);
    }

    /**
     * Lê um int do console.
     *
     * @param caption Texto que identifica o que está sendo solicitado (rótulo)
     * @return um int a partir da conversão da linha lida
     * @exception NumberFormatException lança se não for uma valor numérico.
     */
    public static int readlnInt(String caption) {
        String errorMsg = "O valor informado deve ser um número inteiro.";
        // Você pode melhorar o tratamento aqui ...
        // Experimente tratar todas as entradas de dados...
        while (true) {
            try {
                int n = Integer.parseInt(readLine(caption));
                return n;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg + "\n");
            }
        }
    }
     /**
     * Lê um float do console.
     *
     * @param caption Texto que identifica o que está sendo solicitado (rótulo)
     * @return um float a partir da conversão da linha lida
     */
     public static float readlnFloat(String caption) {
     return Float.parseFloat(readLine(caption));
     }

     /**
     * Aguarda até que um ENTER seja teclado no console.
     */
     public static void waitEnter() {
     readLine("\n\n pressione [ENTER] para continuar\n");
     }

     /**
     * Faz uma pergunta tipo s/n e retorna a resposta.
     *
     * @param question o texto com a pergunta a ser feita
     * @return true se a resposta for sim, false caso contrário
     */
     public static boolean ask(String question) {
        return readlnString("\n" + question + " ").equals("s");
     }
}