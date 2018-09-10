package br.univali.kob.domino.model;

import java.util.Random;

public class ArraysHelper {
    /**
     * Embaralha os elementos dentro array
     * @param array
     * @param <T>
     */
    public static <T> void shuffleElements(T[] array) {
        int n = array.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swapElements(array, i, change);
        }
    }

    /**
     * Troca um elemento do array de posição com o outro
     * @param array
     * @param i
     * @param change
     * @param <T>
     */
    public static <T> void swapElements(T[] array, int i, int change) {
        T helper = array[i];
        array[i] = array[change];
        array[change] = helper;
    }

    /**
     * Remove um elemento de um array, colocando este elemento como null e diminuindo a referencia do tamanho do array
     * @param array
     * @param elementIndex
     * @param arrayLength
     * @param <T>
     * @return um novo array sem o elemento que deseja ser removido
     */
    public static <T> T[] removeElement (T[] array, int elementIndex, int arrayLength) {
        T[] newArray = array;
        for (int i = elementIndex; i < arrayLength - 1; i++) {
            newArray[i] = newArray[i + 1];
        }
        newArray[arrayLength - 1] = null;
        return newArray;
    }
}
