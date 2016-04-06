package teste;

public class Cronometro {

    private static long tempoAbertura;
    private static long tempoFechamento;
    private static long difTempos;

    public static void start() {
        tempoAbertura = System.currentTimeMillis();
    }

    public static void stop() {
        tempoFechamento = System.currentTimeMillis();
        difTempos = tempoFechamento - tempoAbertura;
    }

    public static long getDiferenca() {
        return difTempos;
    }
}
