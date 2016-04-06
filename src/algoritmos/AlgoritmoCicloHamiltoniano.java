package algoritmos;

import java.util.Arrays;

import base.Grafo;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * JAVADOC
 *
 * @author Anderson.Borba
 */
public class AlgoritmoCicloHamiltoniano {

    public AlgoritmoCicloHamiltonianoResultado backtracking(GrafoNaoDirigido g) {
        if (g.getTamanho() == 0) {
            return null;
        }

        Vertice[] caminho = new Vertice[g.getTamanho()];

        caminho[0] = g.getVertice(0);
        if (!backtracking(g, caminho, 1)) {
            return null;
        }
        return new AlgoritmoCicloHamiltonianoResultado(Arrays.asList(caminho));
    }

    private boolean backtracking(GrafoNaoDirigido g, Vertice[] caminho, int i) {
        if (i == g.getTamanho()) { // se todos os vértices foram encontrados
            // verifica se existe aresta entre o último e o primeiro (fechar o ciclo)
            return g.ehAdjacente(caminho[i - 1], caminho[0]);
        }

        for (int v = 1; v < g.getTamanho(); v++) {
            Vertice vertice = g.getVertice(v);
            if (ehVerticeValido(vertice, g, caminho, i)) {
                caminho[i] = vertice;

                if (backtracking(g, caminho, i + 1)) {
                    return true;
                }
                caminho[i] = null;
            }
        }

        // caso nenhum vértice seja passível de entrar no ciclo, retorna false
        return false;
    }

    private static boolean ehVerticeValido(Vertice vertice, Grafo grafo, Vertice[] caminho, int i) {
        if (!grafo.ehAdjacente(vertice, caminho[i - 1])) {
            return false;
        }

        // verifica se o vértice já não está incluso
        for (int j = 0; j < caminho.length; j++) {
            if (caminho[j] == vertice) {
                return false;
            }
        }
        return true;
    }

}
