package algoritmos;

import java.util.HashSet;
import java.util.Set;

import base.Aresta;
import base.ArestaNaoDirigida;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * @author Anderson.Borba
 */
public class AlgoritmoHipercubo {

    public static void main(String[] args) throws Exception {
        {
            Vertice v0 = new Vertice(0);
            Vertice v1 = new Vertice(1);
            Vertice v2 = new Vertice(2);
            Vertice v3 = new Vertice(3);

            GrafoNaoDirigido g1 = new GrafoNaoDirigido();
            g1.addVertice(v0);
            g1.addVertice(v1);
            g1.addVertice(v2);
            g1.addVertice(v3);

            g1.addAresta(new ArestaNaoDirigida(1, v0, v1));
            g1.addAresta(new ArestaNaoDirigida(2, v1, v2));
            g1.addAresta(new ArestaNaoDirigida(3, v2, v3));
            g1.addAresta(new ArestaNaoDirigida(4, v3, v0));

            System.out.println(ehHipercubo(g1).isEhHipercubo());
        }
        {
            Vertice v0 = new Vertice(0);
            Vertice v1 = new Vertice(1);
            Vertice v2 = new Vertice(2);
            Vertice v3 = new Vertice(3);
            Vertice v4 = new Vertice(4);
            Vertice v5 = new Vertice(5);
            Vertice v6 = new Vertice(6);
            Vertice v7 = new Vertice(7);

            GrafoNaoDirigido g1 = new GrafoNaoDirigido();
            g1.addVertice(v0);
            g1.addVertice(v1);
            g1.addVertice(v2);
            g1.addVertice(v3);
            g1.addVertice(v4);
            g1.addVertice(v5);
            g1.addVertice(v6);
            g1.addVertice(v7);

            g1.addAresta(new ArestaNaoDirigida(1, v0, v1));
            g1.addAresta(new ArestaNaoDirigida(2, v0, v2));
            g1.addAresta(new ArestaNaoDirigida(3, v0, v4));
            g1.addAresta(new ArestaNaoDirigida(4, v1, v3));
            g1.addAresta(new ArestaNaoDirigida(5, v1, v5));
            g1.addAresta(new ArestaNaoDirigida(6, v2, v3));
            g1.addAresta(new ArestaNaoDirigida(7, v2, v6));
            g1.addAresta(new ArestaNaoDirigida(8, v3, v7));
            g1.addAresta(new ArestaNaoDirigida(9, v4, v5));
            g1.addAresta(new ArestaNaoDirigida(10, v4, v6));
            g1.addAresta(new ArestaNaoDirigida(11, v5, v7));
            g1.addAresta(new ArestaNaoDirigida(12, v6, v7));

            System.out.println(ehHipercubo(g1).isEhHipercubo());
        }
    }

    public static AlgoritmoHipercuboResultado ehHipercubo(GrafoNaoDirigido grafo) {
        int vertices = grafo.getTamanho();
        int n;
        for (n = 0;; n++) {
            double temp = Math.pow(2, n);
            if (temp == vertices) {
                break;
            }
            if (temp > vertices) { // não é uma potência de n.
                return AlgoritmoHipercuboResultado.FALSE;
            }
        }

        for (int i = 0; i < vertices; i++) {
            Vertice vertice = grafo.getVertice(i);
            if (vertice.getQtdeArestas() == n) {
                // verifica se existe todas as arestas apontam para vértices diferente e diferente de si.
                Set<Integer> set = new HashSet<Integer>();
                for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                    Aresta aresta = vertice.getAresta(j);
                    Vertice verticeOposto = aresta.getVi().getId() == vertice.getId() ? aresta.getVj() : aresta.getVi();

                    if (verticeOposto.getId() == vertice.getId()) {
                        return AlgoritmoHipercuboResultado.FALSE;
                    }
                    if (set.contains(aresta.getVj().getId())) {
                        return AlgoritmoHipercuboResultado.FALSE;
                    }
                }
            } else {
                return AlgoritmoHipercuboResultado.FALSE;
            }
        }

        return AlgoritmoHipercuboResultado.TRUE;
    }

}
