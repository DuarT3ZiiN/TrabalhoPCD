import java.io.*;
import java.util.List;

public class CPFProcessor implements Runnable {
    private List<File> arquivos;
    private int[] resultado;

    public CPFProcessor(List<File> arquivos, int[] resultado) {
        this.arquivos = arquivos;
        this.resultado = resultado;
    }

    @Override
    public void run() {
        for (File arquivo : arquivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (CPFValidator.validaCPF(linha)) {
                        synchronized (resultado) { resultado[0]++; }
                    } else {
                        synchronized (resultado) { resultado[1]++; }
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler arquivo: " + arquivo.getName());
            }
        }
    }
}
