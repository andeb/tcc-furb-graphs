package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import auxiliar.Constante;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoBuscaLargura<br>
 * <br>
 * Este algoritmo executa uma sequ�ncia de visita�ões iniciando em um v�rtice qualquer <b>p</b>.<br>
 * O m�todo consiste em visitar todos os v�rtices, ainda n�o visitados, adjacentes a <b>p</b>
 * e colocá-los em uma fila.<br>
 * Depois de terminar de examinar <b>p</b> um novo v�rtice � retirado da fila e processado
 * da mesma forma.<br>
 * Este procedimento � feito at� que a fila se torne vazia.<br>
 * <br>
 * <b>Refer�ncias</b><br>
 * LAU, Hang T. A Java library of graph algorithms and optimization. Boca Raton: Chapman & Hall/CR, 2007. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoBuscaLargura {

    private AlgoritmoBuscaLarguraResultado resultado;
    private HashMap<Vertice, Boolean> branco;
    private int tempo;
    private Vertice verticeDestino;
    private Grafo g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoBuscaLarguraResultado
     */
    public AlgoritmoBuscaLarguraResultado getResultado() {
        return resultado;
    }

    private void bfs(Vertice pred, Vertice u) {
        ArrayList<Vertice> fila = new ArrayList<Vertice>();

        branco.put(u, false);
        fila.add(u);
        resultado.setPredecessor(u, null);

        while (fila.size() > 0) {
            Vertice v = fila.get(0);
            fila.remove(v);

            resultado.setTempoAbertura(v, tempo);
            resultado.setVisitado(v, true);
            resultado.incrementaAtingidos();
            tempo++;

            if (verticeDestino == null || (verticeDestino != null && !verticeDestino.equals(v))) {
                List<Vertice> adjV = g.getAdjacentes(v);
                for (Vertice w : adjV) {
                    if (branco.get(w)) {
                        branco.put(w, false);
                        fila.add(w);

                        resultado.addDescendente(v, w);
                        resultado.setPredecessor(w, v);
                    }
                }
            }

            resultado.setTempoFechamento(v, tempo);
            tempo++;

            if (verticeDestino != null && verticeDestino.equals(v)) {
                return; //encontrou o vertice de destino
            }
        }
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(Grafo g) {
        this.g = g;
        resultado = new AlgoritmoBuscaLarguraResultado();
        branco = new HashMap<Vertice, Boolean>();
        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setPredecessor(v, null);
            resultado.setVisitado(v, false);
            resultado.setTempoAbertura(v, Constante.INFTEMPONEGATIVO);
            resultado.setTempoFechamento(v, Constante.INFTEMPONEGATIVO);
        }

        tempo = 1;

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);
            if (branco.get(v)) {
                bfs(null, v);
            }
        }
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo a partir de um v�rtice de origem
     * 
     * @param g Grafo
     * @param s V�rtice de origem
     */
    public void executar(Grafo g, Vertice s) {
        this.g = g;
        resultado = new AlgoritmoBuscaLarguraResultado();
        branco = new HashMap<Vertice, Boolean>();
        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setPredecessor(v, null);
            resultado.setVisitado(v, false);
            resultado.setTempoAbertura(v, Constante.INFTEMPONEGATIVO);
            resultado.setTempoFechamento(v, Constante.INFTEMPONEGATIVO);
        }

        tempo = 1;

        bfs(null, s);
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo a partir de um v�rtice de origem<br>
     * at� um v�rtice de destino
     * 
     * @param g Grafo
     * @param s V�rtice de origem
     * @param d V�rtice de destino
     */
    public void executar(Grafo g, Vertice s, Vertice d) {
        this.g = g;
        verticeDestino = d;
        executar(g, s);
    }
}
