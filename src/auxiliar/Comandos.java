package auxiliar;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Classe Comandos<br>
 * Cont�m diversos comandos auxiliares do framework
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class Comandos {

    /**
     * Retorna um n�mero inteiro aleat�rio para o intervalo
     * 
     * @param min M�nimo
     * @param max Máximo
     * @return n�mero aleat�rio
     */
    public static int getIntNumeroAleatorio(int min, int max) {
        Random random = new Random();

        int num = 0;
        do {
            num = min + random.nextInt((max + 1) - min);
        } while (num > max);
        return num;
    }

    /**
     * Retorna um n�mero decimal aleat�rio para o intervalo
     * 
     * @param min M�nimo
     * @param max Máximo
     * @return n�mero aleat�rio
     */
    public static double getDoubleNumeroAleatorio(double min, double max) {
        Random random = new Random();

        double num = 0.0;
        do {
            num = random.nextDouble();

            num = min + ((max + 1) - min) * num;

        } while (num > max);

        return num;
    }

    /**
     * Salva um texto em um arquivo
     * 
     * @param arquivo Arquivo de destino
     * @param conteudo Conte�do a ser salvo
     * @param adicionar Está adicionando?
     * @throws IOException
     */
    public static void salvar(String arquivo, String conteudo, boolean adicionar) throws IOException {

        FileWriter fw = new FileWriter(arquivo, adicionar);

        fw.write(conteudo);
        fw.close();
    }
}
