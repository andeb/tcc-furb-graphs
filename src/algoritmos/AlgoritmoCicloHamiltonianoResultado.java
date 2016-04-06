package algoritmos;

import java.util.List;

import base.Vertice;

/**
 * @author Anderson.Borba
 */
public class AlgoritmoCicloHamiltonianoResultado {

    private List<Vertice> vertices;

    public AlgoritmoCicloHamiltonianoResultado(List<Vertice> vertices) {
        this.vertices = vertices;
    }

    /**
     * @return the vertices
     */
    public List<Vertice> getCaminho() {
        return vertices;
    }

    @Override
    public String toString() {
        String s = "Ciclo: \n";
        for (Vertice vertice : vertices) {
            s += vertice.getDado() == null ? "(vértice não nomeado)" : vertice.getDado();
            s += "\n";
        }
        if (!vertices.isEmpty()) {
            s += vertices.get(0).getDado() == null ? "(vértice não nomeado)" : vertices.get(0).getDado();
            s += "\n";
        }
        return s;
    }

}
