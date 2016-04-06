package algoritmos;

import java.util.HashMap;
import java.util.PriorityQueue;

import auxiliar.Constante;
import auxiliar.PairPriority;
import base.ArestaNaoDirigida;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoPrim<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar Ã¡rvores geradoras de custo
 * mínimo em grafos conexos.<br>
 * <br>
 * <b>Referências</b><br>
 * GOODRICH, Michael T.; TAMASSIA, Roberto. Projeto de algoritmos: fundamentos, anÃ¡lise e exemplos da Internet.
 * Tradução Bernardo Copstein. Porto Alegre: Bookman, 2004.
 * LAU, Hang T. A Java library of graph algorithms and optimization. Boca Raton: Chapman & Hall/CR, 2007. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoPrim {

    private AlgoritmoPrimResultado resultado;
    private HashMap<Vertice, Boolean> naArvore;
    private PriorityQueue<PairPriority<Vertice>> fila;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoPrimResultado
     */
    public AlgoritmoPrimResultado getResultado() {
        return resultado;
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(GrafoNaoDirigido g) {
        resultado = new AlgoritmoPrimResultado();
        naArvore = new HashMap<Vertice, Boolean>();
        fila = new PriorityQueue<PairPriority<Vertice>>();

        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            naArvore.put(v, false);
            resultado.setCusto(v, Constante.INF);
            resultado.setPredecessor(v, null);
        }

        Vertice x = g.getUmVertice();

        fila.offer(new PairPriority<Vertice>(x, 0));

        while (fila.size() > 0) {
            PairPriority<Vertice> p = fila.peek();
            fila.poll();

            Vertice u = p.getDado();

            if (naArvore.get(u)) {
                continue;
            }

            naArvore.put(u, true);
            resultado.addCustoTotal(p.getCusto());

            int qtdeArestas = u.getQtdeArestas();
            for (int i = 0; i < qtdeArestas; i++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) u.getAresta(i);

                Vertice v = a.getVj(u);
                if (naArvore.get(v) == false && a.getValor() < resultado.getCusto(v)) {
                    resultado.setCusto(v, a.getValor());
                    resultado.setPredecessor(v, u);

                    fila.offer(new PairPriority<Vertice>(v, a.getValor()));
                }
            }
        }

        //constroi os descendentes
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);
            Vertice pred = resultado.getPredecessor(v);

            if (pred != null) {
                resultado.addDescendente(pred, v);
                resultado.addAresta(g.getArestaCustoMinimo(pred, v));
            }
        }
    }
}
