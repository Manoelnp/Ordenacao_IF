import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Ordenacao implements Ordenacao_IF {

    @Override
    public boolean checaVetorOrdenado(int[] numeros) {
        for (int i = 0; i < numeros.length - 1; i++) {
            if (numeros[i] > numeros[i + 1]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public long bubbleSort(int[] numeros) {
        long tempoInicial = System.nanoTime();
        int tamanho = numeros.length;
        boolean trocou;
        do {
            trocou = false;
            for (int i = 0; i < tamanho - 1; i++) {
                if (numeros[i] > numeros[i + 1]) {
                    int temp = numeros[i];
                    numeros[i] = numeros[i + 1];
                    numeros[i + 1] = temp;
                    trocou = true;
                }
            }
        } while (trocou);
        return System.nanoTime() - tempoInicial;
    }

    @Override
    public long selectionSort(int[] numeros) {
        return -1;
    }

    @Override
    public long insertionSort(int[] numeros) {
        return -1;
    }

    @Override
    public long mergeSort(int[] numeros) {
        long tempoInicial = System.nanoTime();
        mergeSortAux(numeros, 0, numeros.length - 1);
        return System.nanoTime() - tempoInicial;
    }

    private void mergeSortAux(int[] numeros, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            mergeSortAux(numeros, esquerda, meio);
            mergeSortAux(numeros, meio + 1, direita);
            mesclar(numeros, esquerda, meio, direita);
        }
    }

    private void mesclar(int[] numeros, int esquerda, int meio, int direita) {
        int[] vetorEsquerdo = Arrays.copyOfRange(numeros, esquerda, meio + 1);
        int[] vetorDireito = Arrays.copyOfRange(numeros, meio + 1, direita + 1);
        int i = 0, j = 0, k = esquerda;
        while (i < vetorEsquerdo.length && j < vetorDireito.length) {
            if (vetorEsquerdo[i] <= vetorDireito[j]) {
                numeros[k++] = vetorEsquerdo[i++];
            } else {
                numeros[k++] = vetorDireito[j++];
            }
        }
        while (i < vetorEsquerdo.length) numeros[k++] = vetorEsquerdo[i++];
        while (j < vetorDireito.length) numeros[k++] = vetorDireito[j++];
    }

    @Override
    public long quickSort(int[] numeros) {
        long tempoInicial = System.nanoTime();
        quickSortAux(numeros, 0, numeros.length - 1);
        return System.nanoTime() - tempoInicial;
    }

    private void quickSortAux(int[] numeros, int esquerda, int direita) {
        if (esquerda < direita) {
            int indicePivo = particionar(numeros, esquerda, direita);
            quickSortAux(numeros, esquerda, indicePivo - 1);
            quickSortAux(numeros, indicePivo + 1, direita);
        }
    }

    private int particionar(int[] numeros, int esquerda, int direita) {
        int pivo = numeros[esquerda];
        int i = esquerda + 1, j = direita;
        while (i <= j) {
            while (i <= j && numeros[i] <= pivo) i++;
            while (i <= j && numeros[j] > pivo) j--;
            if (i < j) {
                trocar(numeros, i, j);
            }
        }
        trocar(numeros, esquerda, j);
        return j;
    }

    @Override
    public long random_quickSort(int[] numeros) {
        long tempoInicial = System.nanoTime();
        embaralhar(numeros);
        quickSortAux(numeros, 0, numeros.length - 1);
        return System.nanoTime() - tempoInicial;
    }

    private void embaralhar(int[] numeros) {
        Random aleatorio = new Random();
        for (int i = numeros.length - 1; i > 0; i--) {
            int j = aleatorio.nextInt(i + 1);
            trocar(numeros, i, j);
        }
    }

    @Override
    public long quickSort_Java(int[] numeros) {
        long tempoInicial = System.nanoTime();
        Arrays.sort(numeros);
        return System.nanoTime() - tempoInicial;
    }

    @Override
    public long countingSort(int[] numeros) {
        long tempoInicial = System.nanoTime();
        int maximo = Arrays.stream(numeros).max().orElse(Integer.MIN_VALUE);
        int minimo = Arrays.stream(numeros).min().orElse(Integer.MAX_VALUE);
        int intervalo = maximo - minimo + 1;
        int[] contagem = new int[intervalo];
        int[] resultado = new int[numeros.length];
        for (int num : numeros) {
            contagem[num - minimo]++;
        }
        for (int i = 1; i < contagem.length; i++) {
            contagem[i] += contagem[i - 1];
        }
        for (int i = numeros.length - 1; i >= 0; i--) {
            resultado[contagem[numeros[i] - minimo] - 1] = numeros[i];
            contagem[numeros[i] - minimo]--;
        }
        System.arraycopy(resultado, 0, numeros, 0, numeros.length);
        return System.nanoTime() - tempoInicial;
    }

    private void trocar(int[] vetor, int i, int j) {
        int temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o tamanho do array: ");
        int tamanho = scanner.nextInt();
        int[] numeros = new int[tamanho];
        System.out.println("Digite os elementos do array: ");
        for (int i = 0; i < tamanho; i++) {
            numeros[i] = scanner.nextInt();
        }

        Ordenacao ordenacao = new Ordenacao();

        System.out.println("Escolha o método de ordenação: 1 - QuickSort, 2 - MergeSort, 3 - BubbleSort");
        int escolha = scanner.nextInt();
        long tempo = 0;

        if (escolha == 1) tempo = ordenacao.quickSort(numeros);
        else if (escolha == 2) tempo = ordenacao.mergeSort(numeros);
        else if (escolha == 3) tempo = ordenacao.bubbleSort(numeros);

        System.out.println("Array ordenado: " + Arrays.toString(numeros));
        System.out.println("Tempo de execução: " + tempo + " ns");
        scanner.close();
    }
}
