package algoritmos;

import java.util.ArrayList;
import java.util.List;

import base.Aresta;
import base.Grafo;
import base.Vertice;

/**
 * JAVADOC
 *
 * @author Anderson.Borba
 */
public class AlgoritmoCaminhoEuleriano {

    public AlgoritmoCaminhoEulerianoResultado fleury(Grafo g, Vertice verticeInicial) {
        g = g.clone(); // clona para não alterar o original
        if (g.getTamanho() == 0) {
            return null;
        }

        // escolha do vértice inicial
        int quantidadeVerticesImpares = 0;
        for (int i = g.getTamanho() - 1; i >= 0; i--) {
            Vertice vertice = g.getVertice(i);
            if (vertice.getQtdeArestas() % 2 == 1) {
                quantidadeVerticesImpares++;
            }
        }
        if (quantidadeVerticesImpares != 0 && quantidadeVerticesImpares != 2) {
            return null;
        }

        List<Aresta> result = new ArrayList<>();

        Vertice verticeAtual = verticeInicial;
        while (g.getQtdeArestas() != 0) {
            Aresta aresta = null;

            if (verticeAtual.getQtdeArestas() == 1) {
                aresta = verticeAtual.getAresta(0);
            } else {
                for (int i = 0; i < verticeAtual.getQtdeArestas(); i++) {
                    Aresta outraAresta = verticeAtual.getAresta(i);
                    if (!ehPonte(g.clone(), outraAresta)) {
                        aresta = outraAresta;
                        break;
                    }
                }
                if (aresta == null) {
                    break;
                }
            }

            result.add(aresta);

            g.delAresta(aresta.getId());

            if (aresta.getVi().getQtdeArestas() == 0) {
                g.delVertice(aresta.getVi().getId());
            }
            if (aresta.getVj().getQtdeArestas() == 0) {
                g.delVertice(aresta.getVj().getId());
            }

            verticeAtual = verticeOposto(verticeAtual, aresta);
        }

        if (g.getQtdeArestas() > 0 || g.getTamanho() > 0) {
            return null;
        }
        return new AlgoritmoCaminhoEulerianoResultado(result);
    }

    public AlgoritmoCaminhoEulerianoResultado fleury(Grafo g) { // escolha do vértice inicial
        for (int i = g.getTamanho() - 1; i >= 0; i--) {
            Vertice vertice = g.getVertice(i);
            if (vertice.getQtdeArestas() % 2 == 1) {
                return fleury(g, vertice);
            }
        }

        if (g.getTamanho() == 0) {
            return null;
        }
        return fleury(g, g.getUmVertice());
    }

    private static boolean ehPonte(Grafo gr, Aresta a) {
        AlgoritmoPontes pontes = new AlgoritmoPontes();
        pontes.executar(gr);

        AlgoritmoPontesResultado resultado = pontes.getResultado();
        for (Aresta aresta : resultado.getPontes()) {
            if (aresta.getId() == a.getId()) {
                return true;
            }
        }
        return false;
    }

    private static Vertice verticeOposto(Vertice v, Aresta a) {
        if (a.getVj().getId() == v.getId()) {
            return a.getVi();
        }

        if (a.getVi().getId() == v.getId()) {
            return a.getVj();
        }

        throw new UnsupportedOperationException();
    }

}
