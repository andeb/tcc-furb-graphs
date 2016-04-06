package base;

/**
 * Classe Aresta
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public abstract class Aresta implements Cloneable {

    private final int id;
    private Object dado;
    private double valor;
    private double capacidade;
    protected Vertice vi;
    protected Vertice vj;
    private Grafo g;

    /**
     * Construtor da classe Aresta
     * 
     * @param id
     *            Identificador único da aresta
     * @param vi
     *            Vértice de origem
     * @param vj
     *            Vértice de destino
     */
    public Aresta(int id, Vertice vi, Vertice vj) {
        this.id = id;
        dado = id;
        this.vi = vi;
        this.vj = vj;
    }

    /**
     * Retorna o identificador único da aresta
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o dado da aresta
     * 
     * @return dado
     */
    public Object getDado() {
        return dado;
    }

    /**
     * Atribui um dado qualquer para a aresta
     * 
     * @param dado
     */
    public void setDado(Object dado) {
        this.dado = dado;
    }

    /**
     * Retorna o valor/custo da aresta
     * 
     * @return valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * Atribui um valor/custo para a aresta
     * 
     * @param valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna a capacidade da aresta
     * 
     * @return capacidade
     */
    public double getCapacidade() {
        return capacidade;
    }

    /**
     * Atribui uma capacidade para a aresta
     * 
     * @param capacidade
     */
    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Atribui um grafo para a aresta
     * 
     * @param g
     *            Grafo
     */
    public void setGrafo(Grafo g) {
        this.g = g;
    }

    /**
     * Retorna o grafo da aresta
     * 
     * @return Grafo
     */
    public Grafo getGrafo() {
        return g;
    }

    /**
     * Compara a aresta
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return getId() == ((Aresta) obj).getId();
    }

    /**
     * Retorna uma String descrevendo o objeto aresta
     */
    @Override
    public String toString() {
        return vi.getId() + " -> " + vj.getId() + " D: " + dado + " V: " + valor + " C: " + capacidade;
    }

    public Vertice getVi() {
        return vi;
    }

    public Vertice getVj() {
        return vj;
    }

    @Override
    public int hashCode() {
        // FIXME
        return super.hashCode();
    }

}
