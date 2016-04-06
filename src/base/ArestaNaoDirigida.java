package base;

/**
 * Classe ArestaNaoDirigida
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class ArestaNaoDirigida extends Aresta {

    /**
     * Construtor da classe ArestaNaoDirigida
     * 
     * @param id Identificador único da aresta
     * @param vi Vertice de origem
     * @param vj Vertice de destino
     */
    public ArestaNaoDirigida(int id, Vertice vi, Vertice vj) {
        super(id, vi, vj);
    }

    /**
     * Retorna o vértice da outra ponta da aresta
     * 
     * @param u Um vértice
     * @return Outro vertice
     */
    public Vertice getVj(Vertice u) {
        if (vi.equals(u)) {
            return vj;
        }
        return vi;
    }

    /**
     * Retorna um vértice da aresta
     * 
     * @return Um vertice
     */
    @Override
    public Vertice getVi() {
        return vi;
    }
}
