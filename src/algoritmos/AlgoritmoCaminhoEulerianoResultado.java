package algoritmos;

import java.util.List;

import base.Aresta;
import base.Vertice;

/**
 * @author Anderson.Borba
 */
public class AlgoritmoCaminhoEulerianoResultado {

    private final List<Aresta> result;

    public AlgoritmoCaminhoEulerianoResultado(List<Aresta> result) {
        this.result = result;
    }

    public List<Aresta> getCaminho() {
        return result;
    }

    @Override
    public String toString() {
        String s = "Caminho: \n";
        Vertice anterior = null;
        for (Aresta aresta : result) {
            Vertice vi = aresta.getVi();
            Vertice vj = aresta.getVj();
            if (anterior != null & anterior == vj) {
                Vertice tmp = vj;
                vj = vi;
                vi = tmp;
            }
            anterior = vj;

            s += "Aresta " + (aresta.getDado() == null ? "(n�o nomeada)" : aresta.getDado());
            s += ": ";
            s += vi.getDado() == null ? "(v�rtice n�o nomeado)" : vi.getDado();
            s += " -> ";
            s += vj.getDado() == null ? "(v�rtice n�o nomeado)" : vj.getDado();
            s += "\n";
        }
        return s;
    }

}
