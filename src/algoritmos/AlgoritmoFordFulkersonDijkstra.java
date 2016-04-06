package algoritmos;

import java.util.HashMap;
import java.util.PriorityQueue;

import auxiliar.Constante;
import auxiliar.PairPriority;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoFordFulkersonDijkstra<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar o fluxo máximo em uma
 * rede de fluxo utilizando um m�todo guloso.<br>
 * A id�ia principal � aumentar o valor de um fluxo em estágios, onde para cada etapa
 * uma quantidade � colocada ao longo de um caminho entre a origem e o destino.<br>
 * O algoritmo encerra quando n�o existe mais um caminho para utilizar um fluxo.<br>
 * A diferen�a deste algoritmo que utiliza Dijkstra para o outro, que utiliza busca<br>
 * em profundidade � que este aumenta o valor do fluxo sempre sobre o menor caminho<br>
 * restante.<br>
 * <br>
 * <b>Refer�ncias</b><br>
 * FLEMING, Kermin; CRUTCHFIELD, Chris. Minimum cost maximum flow, Minimum cost circulation, Cost/Capacity scaling.
 * [Cambridge], 2006. Dispon�vel em: <http://courses.csail.mit.edu/6.854/06/scribe/s12-minCostFlowAlg.pdf>. Acesso em:
 * 10 ago. 2010.
 * GOODRICH, Michael T.; TAMASSIA, Roberto. Projeto de algoritmos: fundamentos, análise e exemplos da Internet.
 * Tradu��o Bernardo Copstein. Porto Alegre: Bookman, 2004.
 * GROSS, Jonathan L.; YELLEN, Jay. Graph theory and its applications. 2nd ed. Boca Raton: CRC, 2006. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoFordFulkersonDijkstra {

    private AlgoritmoFordFulkersonResultado resultado;
    private HashMap<Vertice, Vertice> predecessor;
    private HashMap<Vertice, Boolean> branco;
    private HashMap<Vertice, Double> dist;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoFordFulkersonResultado
     */
    public AlgoritmoFordFulkersonResultado getResultado() {
        return resultado;
    }

    private boolean dijkstra(Grafo g, Vertice s, Vertice t) {
        PriorityQueue<PairPriority<Vertice>> fila = new PriorityQueue<PairPriority<Vertice>>();
        Vertice current;
        int tamanhoGrafo = g.getTamanho();
        double dc;
        double novaDist;

        branco = new HashMap<Vertice, Boolean>();
        dist = new HashMap<Vertice, Double>();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            dist.put(v, Constante.INF);
            predecessor.put(v, null);
        }

        branco.put(s, false);
        dist.put(s, 0.0);

        fila.offer(new PairPriority<Vertice>(s, 0));

        while (fila.size() > 0) {
            PairPriority<Vertice> p = fila.peek();
            fila.poll();

            current = p.getDado();
            dc = p.getCusto();

            for (int i = 0; i < tamanhoGrafo; i++) {
                Vertice verticeAtual = g.getVertice(i);
                if (branco.get(verticeAtual)) {
                    if (g.ehAdjacente(current, verticeAtual) && resultado.getCapacidade(current, verticeAtual) - resultado.getFluxo(current, verticeAtual) > 0) {
                        novaDist = dc + g.getCustoMinimo(current, verticeAtual);

                        if (novaDist < dist.get(verticeAtual)) {
                            dist.put(verticeAtual, novaDist);
                            predecessor.put(verticeAtual, current);

                            fila.offer(new PairPriority<Vertice>(verticeAtual, novaDist));
                        }

                    }
                }
            }

            branco.put(current, false);
        }

        if (branco.get(t)) {
            return false;
        }
        return true;
    }

    private void inicializaFluxo(GrafoDirigido g) {
        int qtdeArestas = g.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida a = g.getAresta(i);
            resultado.addCapacidade(a.getVi(), a.getVj(), a.getCapacidade());
        }
    }

    private void inicializaFluxo(GrafoNaoDirigido g) {
        int qtdeArestas = g.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaNaoDirigida a = g.getAresta(i);
            resultado.addCapacidade(a.getVi(), a.getVj(a.getVi()), a.getCapacidade());
            resultado.addCapacidade(a.getVj(a.getVi()), a.getVi(), a.getCapacidade());
        }
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     * @param s V�rtice de origem
     * @param t V�rtice de destino
     */
    public void executar(Grafo g, Vertice s, Vertice t) {
        resultado = new AlgoritmoFordFulkersonResultado();
        predecessor = new HashMap<Vertice, Vertice>();
        branco = new HashMap<Vertice, Boolean>();
        dist = new HashMap<Vertice, Double>();

        resultado.setGrafo(g);

        double fluxoMaximo = 0;
        double incrementa;

        if (g.ehDirigido()) {
            inicializaFluxo((GrafoDirigido) g);
        } else {
            inicializaFluxo((GrafoNaoDirigido) g);
        }

        while (dijkstra(g, s, t)) {
            incrementa = Constante.INF;

            for (Vertice u = t; predecessor.get(u) != null; u = predecessor.get(u)) {
                if (resultado.getCapacidade(predecessor.get(u), u) - resultado.getFluxo(predecessor.get(u), u) < incrementa) {
                    incrementa = resultado.getCapacidade(predecessor.get(u), u) - resultado.getFluxo(predecessor.get(u), u);
                }
            }

            for (Vertice u = t; predecessor.get(u) != null; u = predecessor.get(u)) {
                resultado.addFluxo(predecessor.get(u), u, incrementa);
                resultado.addFluxo(u, predecessor.get(u), -incrementa);
            }

            fluxoMaximo += incrementa;
        }

        resultado.setFluxoMaximo(fluxoMaximo);
    }
}
