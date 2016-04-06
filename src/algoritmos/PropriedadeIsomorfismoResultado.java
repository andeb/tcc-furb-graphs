package algoritmos;

import java.util.Map;

import base.Aresta;
import base.Vertice;

/**
 * JAVADOC
 *
 * @author Anderson.Borba
 */
public class PropriedadeIsomorfismoResultado {

    private Map<Aresta, Aresta> relacao_a;
    private Map<Vertice, Vertice> relacao_v;

    /**
     * @param relacao_a
     * @param relacao_v
     */
    public PropriedadeIsomorfismoResultado(Map<Aresta, Aresta> relacao_a, Map<Vertice, Vertice> relacao_v) {
        this.relacao_a = relacao_a;
        this.relacao_v = relacao_v;
    }

    /**
     * @return the relacao_a
     */
    public Map<Aresta, Aresta> getRelacaoArestas() {
        return relacao_a;
    }

    /**
     * @return the relacao_v
     */
    public Map<Vertice, Vertice> getRelacaoVertices() {
        return relacao_v;
    }

}
