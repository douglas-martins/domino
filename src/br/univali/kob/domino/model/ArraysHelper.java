package br.univali.kob.domino.model;

import java.util.Random;

public class ArraysHelper {
    /**
     * Embaralha os elementos dentro array
     * @param array: o array que será embaralhado
     * @param <T>: tipo genérico do array
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
     * @param array: array que será usado para troca de elementos
     * @param i: index do elemento que sera mudado
     * @param change: index do elemento que será alterado com i
     * @param <T>: tipo genérico do array
     */
    public static <T> void swapElements(T[] array, int i, int change) {
        T helper = array[i];
        array[i] = array[change];
        array[change] = helper;
    }

    /**
     * Remove um elemento de um array, colocando este elemento como null e diminuindo a referencia do tamanho do array
     * @param array: o array que precisa ser removido um elemento
     * @param elementIndex: index do elemento a ser removido
     * @param arrayLength: tamanho do array
     * @param <T>: um tipo genérico do array
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
