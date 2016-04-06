package auxiliar;

/**
 * Classe Pair<br>
 * Classe auxiliar para controlar conexÃµes entre vértices
 *
 * @author Maicon Rafael Zatelli
 */
public final class Pair<A, B> {

    private final A a;
    private final B b;
    private final int hash;

    /**
     * Construtor da classe Pair
     *
     * @param a
     * @param b
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;

        hash = (a.hashCode() ^ (b.hashCode() >> 1));
    }

    /**
     * Compara a Pair
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>) obj;
        if (other.a == null && a != null) {
            return false;
        }
        if (other.b == null && b != null) {
            return false;
        }
        return other.a.equals(a) && other.b.equals(b);
    }

    /**
     * Retorna um código hash para a Pair
     */
    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * Retorna o objeto A
     *
     * @return a
     */
    public A getA() {
        return a;
    }

    /**
     * Retorna o objeto B
     *
     * @return b
     */
    public B getB() {
        return b;
    }
}
