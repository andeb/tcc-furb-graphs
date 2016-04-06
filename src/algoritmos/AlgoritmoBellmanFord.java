package algoritmos;

import auxiliar.Constante;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoBellmanFord<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar um caminho mínimo
 * de um vértice de origem a todos os demais.<br>
 * Um diferencial do algoritmo é que o grafo pode conter arestas com peso negativo.<br>
 * Entretanto, o algoritmo não consegue chegar a um resultado quando o grafo
 * originar um ciclo de custo negativo.<br>
 * <br>
 * <b>Referências</b><br>
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001.
 * GOODRICH, Michael T.; TAMASSIA, Roberto. Estrutura de dados e algoritmos em Java. 4. ed. Porto Alegre: Bookman, 2007. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoBellmanFord {

    private AlgoritmoBellmanFordResultado resultado;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoBellmanFordResultado
     */
    public AlgoritmoBellmanFordResultado getResultado() {
        return resultado;
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     * @param s Vértice de origem
     */
    public void executar(Grafo g, Vertice s) {
        int tamanhoGrafo = g.getTamanho();
        int qtdeArestas = g.getQtdeArestas();

        resultado = new AlgoritmoBellmanFordResultado();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            resultado.setCusto(v, Constante.INF);
            resultado.setPredecessor(v, null);
        }
        resultado.setCusto(s, 0);

        if (g.ehDirigido()) {
            for (int i = 0; i < tamanhoGrafo; i++) {
                for (int uv = 0; uv < qtdeArestas; uv++) {
                    ArestaDirigida a = (ArestaDirigida) g.getAresta(uv);

                    Vertice u = a.getVi();
                    Vertice v = a.getVj();

                    if (resultado.getCusto(v) > resultado.getCusto(u) + a.getValor()) {
                        resultado.setCusto(v, resultado.getCusto(u) + a.getValor());
                        resultado.setPredecessor(v, u);
                    }
                }
            }
        } else {
            for (int i = 0; i < tamanhoGrafo; i++) {
                for (int uv = 0; uv < qtdeArestas; uv++) {
                    ArestaNaoDirigida a = (ArestaNaoDirigida) g.getAresta(uv);

                    Vertice u = a.getVi();
                    Vertice v = a.getVj(u);

                    if (resultado.getCusto(v) > resultado.getCusto(u) + a.getValor()) {
                        resultado.setCusto(v, resultado.getCusto(u) + a.getValor());
                        resultado.setPredecessor(v, u);
                    }
                }
            }
        }
    }

}
