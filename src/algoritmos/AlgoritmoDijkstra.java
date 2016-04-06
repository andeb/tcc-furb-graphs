package algoritmos;

import java.util.HashMap;
import java.util.PriorityQueue;

import auxiliar.Constante;
import auxiliar.PairPriority;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoDijkstra<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar o caminho m�nimo entre dois v�rtices em grafos que
 * contenham arestas com apenas pesos positivos.<br>
 * <br>
 * <b>Refer�ncias</b><br>
 * GOODRICH, Michael T.; TAMASSIA, Roberto. Projeto de algoritmos: fundamentos, análise e exemplos da Internet.
 * Tradu��o Bernardo Copstein. Porto Alegre: Bookman, 2004. KOCAY, William; KREHER, Donald L. Graphs, algorithms, and
 * optimization. Boca Raton: Chapman & Hall/CRC, 2005. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoDijkstra {

    private AlgoritmoDijkstraResultado resultado;
    private HashMap<Vertice, Boolean> branco;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoDijkstraResultado
     */
    public AlgoritmoDijkstraResultado getResultado() {
        return resultado;
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo
     * 
     * @param g
     *            Grafo
     * @param s
     *            V�rtice de origem
     * @param d
     *            V�rtice de destino
     */
    public void executar(Grafo g, Vertice s, Vertice d) {
        executar(g, s);
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo
     * 
     * @param g
     *            Grafo
     * @param s
     *            V�rtice de origem
     */
    public void executar(Grafo g, Vertice s) {
        PriorityQueue<PairPriority<Vertice>> fila = new PriorityQueue<PairPriority<Vertice>>();
        Vertice current;
        int tamanhoGrafo = g.getTamanho();
        double dc;
        double novaDist;

        resultado = new AlgoritmoDijkstraResultado();
        branco = new HashMap<Vertice, Boolean>();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setCusto(v, Constante.INF);
            resultado.setPredecessor(v, null);
        }

        branco.put(s, false);
        resultado.setCusto(s, 0.0);

        fila.offer(new PairPriority<Vertice>(s, 0));

        while (fila.size() > 0) {
            PairPriority<Vertice> p = fila.peek();
            fila.poll();

            current = p.getDado();
            dc = p.getCusto();

            for (int i = 0; i < tamanhoGrafo; i++) {
                Vertice verticeAtual = g.getVertice(i);
                if (branco.get(verticeAtual)) {
                    if (g.ehAdjacente(current, verticeAtual)) {
                        novaDist = dc + g.getCustoMinimo(current, verticeAtual);

                        if (novaDist < resultado.getCusto(verticeAtual)) {
                            resultado.setCusto(verticeAtual, novaDist);
                            resultado.setPredecessor(verticeAtual, current);

                            fila.offer(new PairPriority<Vertice>(verticeAtual, novaDist));
                        }
                    }
                }
            }

            branco.put(current, false);
        }
    }
}
