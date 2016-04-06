package base;

/**
 * Classe ArestaDirigida
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class ArestaDirigida extends Aresta {

    /**
     * Construtor da classe ArestaDirigida
     * 
     * @param id
     *            Identificador único da aresta
     * @param vi
     *            Vertice de origem
     * @param vj
     *            Vertice de destino
     */
    public ArestaDirigida(int id, Vertice vi, Vertice vj) {
        super(id, vi, vj);
    }

    /**
     * Retorna o vértice de origem da aresta
     * 
     * @return vi
     */
    @Override
    public Vertice getVi() {
        return super.vi;
    }

    /**
     * Retorna o vértice de destino da aresta
     * 
     * @return vj
     */
    @Override
    public Vertice getVj() {
        return super.vj;
    }
}
