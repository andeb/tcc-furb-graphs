package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import auxiliar.Constante;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoFordFulkerson<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar o fluxo mÃ¡ximo em uma
 * rede de fluxo utilizando um método guloso.<br>
 * A idéia principal é aumentar o valor de um fluxo em estÃ¡gios, onde para cada etapa
 * uma quantidade é colocada ao longo de um caminho entre a origem e o destino.<br>
 * O algoritmo encerra quando não existe mais um caminho para utilizar um fluxo.<br>
 * <br>
 * <b>Referências</b><br>
 * FLEMING, Kermin; CRUTCHFIELD, Chris. Minimum cost maximum flow, Minimum cost circulation, Cost/Capacity scaling.
 * [Cambridge], 2006. Disponível em: <http://courses.csail.mit.edu/6.854/06/scribe/s12-minCostFlowAlg.pdf>. Acesso em:
 * 10 ago. 2010.
 * GOODRICH, Michael T.; TAMASSIA, Roberto. Projeto de algoritmos: fundamentos, anÃ¡lise e exemplos da Internet.
 * Tradução Bernardo Copstein. Porto Alegre: Bookman, 2004.
 * GROSS, Jonathan L.; YELLEN, Jay. Graph theory and its applications. 2nd ed. Boca Raton: CRC, 2006. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoFordFulkerson {

    private AlgoritmoFordFulkersonResultado resultado;
    private HashMap<Vertice, Vertice> predecessor;
    private HashMap<Vertice, Boolean> branco;
    private final ArrayList<Vertice> pilha = new ArrayList<Vertice>();
    private int tamanhoGrafo;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoFordFulkersonResultado
     */
    public AlgoritmoFordFulkersonResultado getResultado() {
        return resultado;
    }

    private boolean dfs(Grafo g, Vertice s, Vertice t) {
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            predecessor.put(v, null);
        }
        pilha.clear();

        pilha.add(s);
        branco.put(s, false);

        while (pilha.size() > 0) {
            Vertice u = pilha.get(pilha.size() - 1);
            pilha.remove(pilha.size() - 1);

            for (int i = 0; i < tamanhoGrafo; i++) {
                Vertice v = g.getVertice(i);
                if (branco.get(v) && resultado.getCapacidade(u, v) - resultado.getFluxo(u, v) > 0) {
                    predecessor.put(v, u);

                    if (v.equals(t)) {
                        return true;
                    }

                    branco.put(v, false);
                    pilha.add(v);
                }
            }
        }
        return false;
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
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     * @param s Vértice de origem
     * @param t Vértice de destino
     */
    public void executar(Grafo g, Vertice s, Vertice t) {
        resultado = new AlgoritmoFordFulkersonResultado();
        predecessor = new HashMap<Vertice, Vertice>();
        branco = new HashMap<Vertice, Boolean>();

        resultado.setGrafo(g);

        double fluxoMaximo = 0;
        double incrementa;

        tamanhoGrafo = g.getTamanho();
        if (g.ehDirigido()) {
            inicializaFluxo((GrafoDirigido) g);
        } else {
            inicializaFluxo((GrafoNaoDirigido) g);
        }

        while (dfs(g, s, t)) {
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
