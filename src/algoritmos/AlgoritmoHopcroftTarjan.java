package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import auxiliar.Constante;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoHopcroftTarjan<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar as componentes fortemente
 * conexas de um grafo dirigido.<br>
 * <br>
 * <b>Referências</b><br>
 * ALSUWAIYEL, Muhammad H. Algorithms: design techniques and analysis. Singapore: World Scientific Publishing, 2003.
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoHopcroftTarjan {

    private AlgoritmoHopcroftTarjanResultado resultado;
    private HashMap<Vertice, Integer> num;
    private HashMap<Vertice, Integer> low;
    private ArrayList<Vertice> pilha;
    private int niv;
    private Grafo g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoHopcroftTarjanResultado
     */
    public AlgoritmoHopcroftTarjanResultado getResultado() {
        return resultado;
    }

    private void tarjan(Vertice u) {
        num.put(u, niv);
        low.put(u, niv);
        niv++;

        pilha.add(u);

        List<Vertice> adjU = g.getAdjacentes(u);
        for (Vertice v : adjU) {
            if (num.get(v) == Constante.INFTEMPOPOSITIVO) {
                tarjan(v);
                if (low.get(v) < low.get(u)) {
                    low.put(u, low.get(v));
                }
            } else if (pilha.contains(v)) {
                if (num.get(v) < low.get(u)) {
                    low.put(u, num.get(v));
                }
            }
        }

        if (low.get(u) == num.get(u)) {
            ArrayList<Vertice> componente = new ArrayList<Vertice>();
            Vertice v;
            do {
                v = pilha.get(pilha.size() - 1);

                componente.add(v);

                pilha.remove(pilha.size() - 1);
            } while (!u.equals(v));

            if (componente.size() > 0) {
                resultado.addComponente(componente);
            }
        }
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(Grafo g) {
        this.g = g;
        resultado = new AlgoritmoHopcroftTarjanResultado();
        num = new HashMap<Vertice, Integer>();
        low = new HashMap<Vertice, Integer>();
        niv = 0;

        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            num.put(v, Constante.INFTEMPOPOSITIVO);
            low.put(v, Constante.INFTEMPOPOSITIVO);
        }

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);
            if (num.get(v) == Constante.INFTEMPOPOSITIVO) {
                pilha = new ArrayList<Vertice>();

                tarjan(v);
            }
        }
    }
}
