package algoritmos;

import auxiliar.Constante;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoFloydWarshall<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar o caminho mínimo entre
 * todos os pares de vértices de um grafo.<br>
 * Este algoritmo pode ser executado sobre um grafo que contém arestas com pesos negativos,
 * mas que não possua ciclos negativos.<br>
 * <br>
 * <b>Referências</b><br>
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoFloydWarshall {

    private AlgoritmoFloydWarshallResultado resultado;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoFloydWarshallResultado
     */
    public AlgoritmoFloydWarshallResultado getResultado() {
        return resultado;
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(Grafo g) {
        int tamanhoGrafo = g.getTamanho();

        resultado = new AlgoritmoFloydWarshallResultado();

        //inicializa
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);

            resultado.setCusto(u, u, 0);
            resultado.setPredecessor(u, u, null);

            for (int j = i + 1; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);

                double custoTemp = g.getCustoMinimo(u, v);
                resultado.setCusto(u, v, custoTemp);
                if (custoTemp == Constante.INF) {
                    resultado.setPredecessor(u, v, null);
                } else {
                    resultado.setPredecessor(u, v, u);
                }

                custoTemp = g.getCustoMinimo(v, u);
                resultado.setCusto(v, u, custoTemp);
                if (custoTemp == Constante.INF) {
                    resultado.setPredecessor(v, u, null);
                } else {
                    resultado.setPredecessor(v, u, v);
                }
            }
        }

        for (int k = 0; k < tamanhoGrafo; k++) {
            Vertice vk = g.getVertice(k);
            for (int i = 0; i < tamanhoGrafo; i++) {
                Vertice vi = g.getVertice(i);
                for (int j = 0; j < tamanhoGrafo; j++) {
                    Vertice vj = g.getVertice(j);
                    if (resultado.getCusto(vi, vj) > (resultado.getCusto(vi, vk) + resultado.getCusto(vk, vj))) {
                        resultado.setCusto(vi, vj, (resultado.getCusto(vi, vk) + resultado.getCusto(vk, vj)));
                        resultado.setPredecessor(vi, vj, resultado.getPredecessor(vk, vj));
                    }
                }
            }
        }

    }
}
