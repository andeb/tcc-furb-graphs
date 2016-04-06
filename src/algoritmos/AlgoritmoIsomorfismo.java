package algoritmos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import base.Aresta;
import base.ArestaNaoDirigida;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * @author Anderson.Borba
 */
public class AlgoritmoIsomorfismo {

    private Comparator<Vertice> ARESTAS_COMPARATOR = new Comparator<Vertice>() {

        @Override
        public int compare(Vertice v1, Vertice v2) {
            return v2.getQtdeArestas() - v1.getQtdeArestas();
        }

    };

    public static void main(String[] args) throws Exception {
        //        Vertice g1v1 = new Vertice(1);
        //        Vertice g1v2 = new Vertice(2);
        //        Vertice g1v3 = new Vertice(3);
        //        Vertice g1v4 = new Vertice(4);
        //
        //        GrafoNaoDirigido g1 = new GrafoNaoDirigido();
        //        g1.addVertice(g1v1);
        //        g1.addVertice(g1v2);
        //        g1.addVertice(g1v3);
        //        g1.addVertice(g1v4);
        //
        //        g1.addAresta(new ArestaNaoDirigida(1, g1v1, g1v2));
        //        g1.addAresta(new ArestaNaoDirigida(2, g1v1, g1v3));
        //        g1.addAresta(new ArestaNaoDirigida(3, g1v2, g1v4));
        //        g1.addAresta(new ArestaNaoDirigida(4, g1v3, g1v4));
        //        g1.addAresta(new ArestaNaoDirigida(5, g1v3, g1v4));

        Vertice g1v1 = new Vertice(1);
        Vertice g1v2 = new Vertice(2);
        Vertice g1v3 = new Vertice(3);
        Vertice g1v4 = new Vertice(4);

        GrafoNaoDirigido g1 = new GrafoNaoDirigido();
        g1.addVertice(g1v1);
        g1.addVertice(g1v2);
        g1.addVertice(g1v3);
        g1.addVertice(g1v4);

        g1.addAresta(new ArestaNaoDirigida(1, g1v1, g1v2));
        g1.addAresta(new ArestaNaoDirigida(2, g1v1, g1v4));
        g1.addAresta(new ArestaNaoDirigida(3, g1v2, g1v3));
        g1.addAresta(new ArestaNaoDirigida(4, g1v4, g1v3));
        g1.addAresta(new ArestaNaoDirigida(5, g1v2, g1v4));

        Vertice g2v1 = new Vertice(1);
        Vertice g2v2 = new Vertice(2);
        Vertice g2v3 = new Vertice(3);
        Vertice g2v4 = new Vertice(4);

        GrafoNaoDirigido g2 = new GrafoNaoDirigido();
        g2.addVertice(g2v4);
        g2.addVertice(g2v3);
        g2.addVertice(g2v2);
        g2.addVertice(g2v1);

        g2.addAresta(new ArestaNaoDirigida(2, g2v1, g2v4));
        g2.addAresta(new ArestaNaoDirigida(4, g2v4, g2v3));
        g2.addAresta(new ArestaNaoDirigida(5, g2v2, g2v4));
        g2.addAresta(new ArestaNaoDirigida(3, g2v2, g2v3));
        g2.addAresta(new ArestaNaoDirigida(1, g2v1, g2v2));

        System.out.println(g1.ehIsomorfico(g2));
        System.out.println(new AlgoritmoIsomorfismo().ehIsomorfico(g1, g2));
    }

    public PropriedadeIsomorfismoResultado ehIsomorfico(GrafoNaoDirigido g1, GrafoNaoDirigido g2) {
        // a) compara o tamanho
        if (g1.getTamanho() != g2.getTamanho()) {
            return null;
        }

        // b) compara as arestas
        if (g1.getQtdeArestas() != g2.getQtdeArestas()) {
            return null;
        }

        // c) compara o grau de cada aresta
        Vertice[] grau1 = new Vertice[g1.getTamanho()];
        for (int i = 0; i < grau1.length; i++) {
            grau1[i] = g1.getVertice(i);
        }
        Arrays.sort(grau1, ARESTAS_COMPARATOR);

        Vertice[] grau2 = new Vertice[g2.getTamanho()];
        for (int i = 0; i < grau2.length; i++) {
            grau2[i] = g2.getVertice(i);
        }
        Arrays.sort(grau2, ARESTAS_COMPARATOR);

        for (int i = 0; i < grau1.length; i++) {
            if (grau1[i].getQtdeArestas() != grau2[i].getQtdeArestas()) {
                return null;
            }
        }

        // d) backtracking
        // para todos que tem o mesmo grau, verifica isomorfismo
        Vertice exemplo = grau1[0];
        for (int i = 0; exemplo.getQtdeArestas() == grau2[i].getQtdeArestas(); i++) {
            if (backtracking(g1, g2, exemplo, grau2[i])) {
                return new PropriedadeIsomorfismoResultado(relacao_a, relacao_v);
            }
        }
        return null;
    }

    // nota: se estiver no mapa, significa que já visitou ela

    // relação do vertice do grafo 1 para o vertice do grafo 2
    private Map<Vertice, Vertice> relacao_v = new HashMap<Vertice, Vertice>();

    // relação do vertice do grafo 1 para o vertice do grafo 2
    private Map<Aresta, Aresta> relacao_a = new HashMap<Aresta, Aresta>();

    private boolean backtracking(GrafoNaoDirigido g1, GrafoNaoDirigido g2, Vertice v1, Vertice v2) {
        // se já foi visitado, a relação deve ser a mesma
        if (relacao_v.containsKey(v1)) {
            return relacao_v.get(v1) == v2;
        }
        if (relacao_v.containsValue(v2)) {
            return false; // já existe relação para tal vértice
        }
        if (v1.getQtdeArestas() != v2.getQtdeArestas()) {
            return false;
        }

        // adicionamos ambos na mesma relação
        relacao_v.put(v1, v2);

        // comparamos todas as arestas agora que ainda não foram visitadas
        out: for (int i = 0; i < v1.getQtdeArestas(); i++) {
            Aresta aresta1 = v1.getAresta(i);
            if (relacao_a.containsKey(aresta1)) {
                continue; // aresta já visitada, então continua
            }

            // procura por todas as arestas não visitadas
            for (int j = 0; j < v2.getQtdeArestas(); j++) {
                Aresta aresta2 = v2.getAresta(j);
                if (relacao_a.containsValue(aresta2)) {
                    continue; // aresta já visitada, então continua
                }

                // se ambos possuem o mesmo grau, continua a busca
                Vertice v1_j = otherside(aresta1, v1);
                Vertice v2_j = otherside(aresta2, v2);
                if (v1_j.getQtdeArestas() != v2_j.getQtdeArestas()) {
                    continue;
                }

                relacao_a.put(aresta1, aresta2);
                if (backtracking(g1, g2, v1_j, v2_j)) {
                    continue out;
                }
                relacao_v.remove(v1_j);
                relacao_a.remove(aresta1);
            }
        }

        // se exauriu suas possiblidades, retorna true
        for (int i = 0; i < v1.getQtdeArestas(); i++) {
            Aresta a1 = v1.getAresta(i);
            if (!relacao_a.containsKey(a1)) {
                return false;
            }
            Aresta a2 = v2.getAresta(i);
            if (!relacao_a.containsValue(a2)) {
                return false;
            }
        }

        // fim da execução. Todos as relações preenchidas
        //    if (relacao_v.size() == g1.getTamanho() && relacao_a.size() == g1.getQtdeArestas()) {
        //        return true;
        //    }
        return true;
    }

    private static Vertice otherside(Aresta aresta, Vertice base) {
        return (aresta.getVi() == base) ? aresta.getVj() : aresta.getVi();
    }

}
