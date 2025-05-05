import java.io.*;
import java.util.*;

public class Versao_3_Threads {
    public static void main(String[] args) throws InterruptedException {
        File pasta = new File("arquivos");
        File[] todosArquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));
        Arrays.sort(todosArquivos);

        int numThreads = 3;
        int[] resultado = new int[2];

        long inicio = System.currentTimeMillis();

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            List<File> arquivosPorThread = new ArrayList<>();
            for (int j = i; j < todosArquivos.length; j += numThreads) {
                arquivosPorThread.add(todosArquivos[j]);
            }
            threads[i] = new Thread(new CPFProcessor(arquivosPorThread, resultado));
            threads[i].start();
        }

        for (Thread t : threads) t.join();

        long fim = System.currentTimeMillis();
        long duracao = fim - inicio;

        System.out.println("CPFs válidos: " + resultado[0]);
        System.out.println("CPFs inválidos: " + resultado[1]);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resultados/versao_3_threads.txt"))) {
            bw.write("Tempo de execução: " + duracao + " ms");
        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo de resultado.");
        }
    }
}