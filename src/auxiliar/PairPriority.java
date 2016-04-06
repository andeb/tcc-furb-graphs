package auxiliar;

/**
 * Classe PairPriority<br>
 * Classe auxiliar para trabalhar com fila de prioridades
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class PairPriority<T> implements Comparable<T> {

    private final T dado;
    private final double custo;

    /**
     * Construtor da classe PairPriority
     * 
     * @param dado
     * @param custo
     */
    public PairPriority(T dado, double custo) {
        this.dado = dado;
        this.custo = custo;
    }

    /**
     * Retorna o dado
     * 
     * @return dado
     */
    public T getDado() {
        return dado;
    }

    /**
     * Retorna o custo
     * 
     * @return custo
     */
    public double getCusto() {
        return custo;
    }

    /**
     * Compara a PairPriority
     */
    @Override
    public int compareTo(Object o) {
        @SuppressWarnings("unchecked")
        PairPriority<T> v1 = (PairPriority<T>) o;
        PairPriority<T> v2 = this;

        if (v1.getCusto() > v2.getCusto()) {
            return -1;
        } else {
            return 1;
        }
    }
}
