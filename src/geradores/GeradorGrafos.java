package geradores;

import java.util.ArrayList;

import auxiliar.Comandos;
import auxiliar.Pair;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe GeradorGrafos
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class GeradorGrafos {

    /**
     * Gera um grafo n�o dirigido completo.<br>
     * Um grafo � completo se ele � simples e existe uma aresta ligando cada par de v�rtices distintos.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido completo
     */
    public static GrafoNaoDirigido getGrafoCompletoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int cArestas = 1;
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i + 1; j < qtdeVertices; j++) {
                Vertice v = g.getVertice(j);

                ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}

                cArestas++;
            }
        }

        return g;
    }

    /**
     * Gera um grafo dirigido completo.<br>
     * Um grafo � completo se ele � simples e existe uma aresta ligando cada par de v�rtices distintos.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido completo
     */
    public static GrafoDirigido getGrafoCompletoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int cArestas = 1;
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                if (i == j) {
                    continue;
                }

                Vertice v = g.getVertice(j);

                ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}

                cArestas++;
            }
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido simples.<br>
     * Um grafo � simples se n�o possui arestas paralelas e la�os.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido simples
     */
    public static GrafoNaoDirigido getGrafoSimplesNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if (qtdeVertices == 1) {
            return g;
        }

        int maxArestas = (qtdeVertices * (qtdeVertices - 1)) / 2;

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i + 1; j < qtdeVertices; j++) {
                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(0, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido simples.<br>
     * Um grafo � simples se n�o possui arestas paralelas e la�os.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido simples
     */
    public static GrafoDirigido getGrafoSimplesDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if (qtdeVertices == 1) {
            return g;
        }

        int maxArestas = qtdeVertices * (qtdeVertices - 1);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                if (i == j) {
                    continue;
                }

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(0, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido esparso.<br>
     * Um grafo � esparso se o total de arestas � menor que o quadrado da quantidade de v�rtices.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido esparso
     */
    public static GrafoNaoDirigido getGrafoEsparsoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices - 1;

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido esparso.<br>
     * Um grafo � esparso se o total de arestas � menor que o quadrado da quantidade de v�rtices.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido esparso
     */
    public static GrafoDirigido getGrafoEsparsoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices - 1;

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido multigrafo.<br>
     * Um grafo � multigrafo se possui arestas paralelas ou la�os.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido multigrafo
     */
    public static GrafoNaoDirigido getGrafoMultigrafoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 1;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        while (!g.ehMultigrafo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido multigrafo.<br>
     * Um grafo � multigrafo se possui arestas paralelas ou la�os.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido multigrafo
     */
    public static GrafoDirigido getGrafoMultigrafoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 1;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        while (!g.ehMultigrafo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido denso.<br>
     * Um grafo � denso se o total de arestas � maior ou igual ao quadrado da quantidade de v�rtices.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido denso
     */
    public static GrafoNaoDirigido getGrafoDensoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = qtdeVertices * qtdeVertices;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido denso.<br>
     * Um grafo � denso se o total de arestas � maior ou igual ao quadrado da quantidade de v�rtices.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido denso
     */
    public static GrafoDirigido getGrafoDensoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = qtdeVertices * qtdeVertices;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido conexo.<br>
     * Um grafo � conexo se for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido conexo
     */
    public static GrafoNaoDirigido getGrafoConexoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = qtdeVertices - 1;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        while (!g.ehConexo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido conexo.<br>
     * Um grafo � conexo se for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido conexo
     */
    public static GrafoDirigido getGrafoConexoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = qtdeVertices;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        while (!g.ehConexo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido desconexo.<br>
     * Um grafo � desconexo se n�o for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido desconexo
     * @throws Exception caso a quantidade de v�rtices seja 1
     */
    public static GrafoNaoDirigido getGrafoDesconexoNaoDirigido(int qtdeVertices) throws Exception {
        if (qtdeVertices == 1) {
            throw new Exception("Nao e possivel gerar um grafo dessa configuracao");
        }

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        while (g.ehConexo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, g.getQtdeArestas() - 1);

            ArestaNaoDirigida a = g.getAresta(pos);

            g.delAresta(a.getId());
        }

        return g;
    }

    /**
     * Gera um grafo dirigido desconexo.<br>
     * Um grafo � desconexo se n�o for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido desconexo
     * @throws Exception caso a quantidade de v�rtices seja 1
     */
    public static GrafoDirigido getGrafoDesconexoDirigido(int qtdeVertices) throws Exception {
        if (qtdeVertices == 1) {
            throw new Exception("Nao e possivel gerar um grafo dessa configuracao");
        }

        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //if (i == j) continue;

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        if (qtdeVertices == 1) {
            return g;
        }

        while (g.ehConexo()) {
            int pos = Comandos.getIntNumeroAleatorio(0, g.getQtdeArestas() - 1);

            ArestaDirigida a = g.getAresta(pos);

            g.delAresta(a.getId());
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido regular.<br>
     * Um grafo � regular se todos os v�rtices possuem o mesmo grau.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @param grau Grau dos v�rtices
     * @return GrafoNaoDirigido regular
     * @throws Exception caso a quantidade de v�rtices e o grau seja �mpar
     */
    public static GrafoNaoDirigido getGrafoRegularNaoDirigido(int qtdeVertices, int grau) throws Exception {

        if (qtdeVertices % 2 != 0 && grau % 2 != 0) {
            throw new Exception("Nao e possivel gerar um grafo dessa configuracao");
        }

        GrafoNaoDirigido g = new GrafoNaoDirigido();
        ArrayList<Vertice> possiveis = new ArrayList<Vertice>();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                possiveis.add(v);
            } catch (Exception e) {}
        }

        int i, j;
        int cArestas = 1;
        while (possiveis.size() > 1 || (possiveis.size() == 1 && (qtdeVertices == 1 || (grau - g.getGrau(possiveis.get(0))) > 1))) {
            i = Comandos.getIntNumeroAleatorio(0, possiveis.size() - 1);
            Vertice u = possiveis.get(i);

            j = Comandos.getIntNumeroAleatorio(0, possiveis.size() - 1);
            Vertice v = possiveis.get(j);

            if (u.equals(v) && g.getGrau(u) > grau - 2) {
                continue;
            }

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas++);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}

            if (g.getGrau(a.getVi()) == grau) {
                possiveis.remove(a.getVi());
            }
            if (g.getGrau(a.getVj(a.getVi())) == grau) {
                possiveis.remove(a.getVj(a.getVi()));
            }
        }

        return g;
    }

    /**
     * Gera um grafo dirigido regular.<br>
     * Um grafo � regular se todos os v�rtices possuem o mesmo grau.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @param grau Grau dos v�rtices
     * @return GrafoDirigido regular
     */
    public static GrafoDirigido getGrafoRegularDirigido(int qtdeVertices, int grau) {
        GrafoDirigido g = new GrafoDirigido();
        ArrayList<Vertice> possiveisU = new ArrayList<Vertice>();
        ArrayList<Vertice> possiveisV = new ArrayList<Vertice>();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                possiveisU.add(v);
                possiveisV.add(v);
            } catch (Exception e) {}
        }

        int i, j;
        int cArestas = 1;
        while (possiveisU.size() > 0 || possiveisV.size() > 0) {
            i = Comandos.getIntNumeroAleatorio(0, possiveisU.size() - 1);
            Vertice u = possiveisU.get(i);

            j = Comandos.getIntNumeroAleatorio(0, possiveisV.size() - 1);
            Vertice v = possiveisV.get(j);

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas++);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}

            if (g.getGrauSaida(a.getVi()) == grau) {
                possiveisU.remove(a.getVi());
            }
            if (g.getGrauEntrada(a.getVj()) == grau) {
                possiveisV.remove(a.getVj());
            }
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido bipartido.<br>
     * Um grafo � bipartido se seu conjunto de v�rtices pode ser dividido em dois conjunetos <b>X</b> e <b>Y</b> tal que
     * cada aresta tem uma ponta em <b>X</b> e outra em <b>Y</b> e/ou vice-versa.
     * 
     * @param x Quantidade de v�rtices do conjunto X
     * @param y Quantidade de v�rtices do conjunto Y
     * @return GrafoNaoDirigido bipartido
     */
    public static GrafoNaoDirigido getGrafoBipartidoNaoDirigido(int x, int y) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        ArrayList<Vertice> conjuntoX = new ArrayList<Vertice>();
        ArrayList<Vertice> conjuntoY = new ArrayList<Vertice>();

        int qtdeVertices = x + y;

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                if (i <= x) {
                    conjuntoX.add(v);
                } else {
                    conjuntoY.add(v);
                }
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < x; i++) {
            Vertice u = conjuntoX.get(i);
            for (int j = 0; j < y; j++) {
                Vertice v = conjuntoY.get(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido bipartido.<br>
     * Um grafo � bipartido se seu conjunto de v�rtices pode ser dividido em dois conjunetos <b>X</b> e <b>Y</b> tal que
     * cada aresta tem uma ponta em <b>X</b> e outra em <b>Y</b> e/ou vice-versa.
     * 
     * @param x Quantidade de v�rtices do conjunto X
     * @param y Quantidade de v�rtices do conjunto Y
     * @return GrafoDirigido bipartido
     */
    public static GrafoDirigido getGrafoBipartidoDirigido(int x, int y) {
        GrafoDirigido g = new GrafoDirigido();

        ArrayList<Vertice> conjuntoX = new ArrayList<Vertice>();
        ArrayList<Vertice> conjuntoY = new ArrayList<Vertice>();

        int qtdeVertices = x + y;

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                if (i <= x) {
                    conjuntoX.add(v);
                } else {
                    conjuntoY.add(v);
                }
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < x; i++) {
            Vertice u = conjuntoX.get(i);
            for (int j = 0; j < y; j++) {
                Vertice v = conjuntoY.get(j);

                Pair par = new Pair(u, v);
                arestas.add(par);

                par = new Pair(v, u);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido bipartido completo.<br>
     * Um grafo � bipartido completo se para cada v�rtice <b>u</b> pertencente a <b>X</b> e para todo <b>v</b>
     * pertencente a <b>Y</b> existe uma aresta.
     * 
     * @param x Quantidade de v�rtices do conjunto X
     * @param y Quantidade de v�rtices do conjunto Y
     * @return GrafoNaoDirigido bipartido completo
     */
    public static GrafoNaoDirigido getGrafoBipartidoCompletoNaoDirigido(int x, int y) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        ArrayList<Vertice> conjuntoX = new ArrayList<Vertice>();
        ArrayList<Vertice> conjuntoY = new ArrayList<Vertice>();

        int qtdeVertices = x + y;
        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                if (i <= x) {
                    conjuntoX.add(v);
                } else {
                    conjuntoY.add(v);
                }
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        int cArestas = 1;
        for (int i = 0; i < x; i++) {
            Vertice u = conjuntoX.get(i);
            for (int j = 0; j < y; j++) {
                Vertice v = conjuntoY.get(j);

                ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}
                cArestas++;

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        qtdeArestas -= x * y;

        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido bipartido completo.<br>
     * Um grafo � bipartido completo se para cada v�rtice <b>u</b> pertencente a <b>X</b> e para todo <b>v</b>
     * pertencente a <b>Y</b> existe uma aresta.
     * 
     * @param x Quantidade de v�rtices do conjunto X
     * @param y Quantidade de v�rtices do conjunto Y
     * @return GrafoDirigido bipartido completo
     */
    public static GrafoDirigido getGrafoBipartidoCompletoDirigido(int x, int y) {
        GrafoDirigido g = new GrafoDirigido();

        ArrayList<Vertice> conjuntoX = new ArrayList<Vertice>();
        ArrayList<Vertice> conjuntoY = new ArrayList<Vertice>();

        int qtdeVertices = x + y;
        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                if (i <= x) {
                    conjuntoX.add(v);
                } else {
                    conjuntoY.add(v);
                }
            } catch (Exception e) {}
        }

        int minArestas = 0;
        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        int cArestas = 1;
        for (int i = 0; i < x; i++) {
            Vertice u = conjuntoX.get(i);
            for (int j = 0; j < y; j++) {
                Vertice v = conjuntoY.get(j);

                ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}
                cArestas++;

                Pair par = new Pair(u, v);
                arestas.add(par);

                a = new ArestaDirigida(cArestas, v, u);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}
                cArestas++;

                par = new Pair(v, u);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        qtdeArestas -= x * y * 2;

        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            //arestas.remove(sorteada);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido randomico.<br>
     * Esta fun��o gera um grafo n�o dirigido com base nos parâmetros informados.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @param pblnConexo Informa se o grafo deve ser conexo
     * @param pblnSimples Informa se o grafo deve ser simples
     * @param pblnAciclico Informa se o grafo deve ser aciclico
     * @return GrafoNaoDirigido
     */
    public static GrafoNaoDirigido getGrafoRandomicoNaoDirigido(int qtdeVertices, boolean pblnConexo, boolean pblnSimples, boolean pblnAciclico) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if ((pblnSimples || pblnAciclico) && qtdeVertices == 1) {
            return g;
        }

        int minArestas;
        int maxArestas;
        if (pblnConexo) {
            minArestas = qtdeVertices - 1;
            if (pblnSimples) {
                maxArestas = (qtdeVertices * (qtdeVertices - 1)) / 2;
            } else {
                maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);
            }
        } else {
            minArestas = 0;
            if (pblnSimples) {
                maxArestas = (qtdeVertices * (qtdeVertices - 1)) / 2;
            } else {
                maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);
            }
        }

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i; j < qtdeVertices; j++) {

                //sem lacos
                if ((pblnSimples || pblnAciclico) && i == j) {
                    continue;
                }

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            if (pblnSimples) {
                arestas.remove(sorteada);
            }

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
                if (pblnAciclico && !g.ehAciclico()) {
                    arestas.remove(sorteada);
                    g.delAresta(a.getId());
                    cArestas--;
                    if (arestas.size() == 0) {
                        break;
                    }
                }
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        if (pblnConexo && arestas.size() > 0) {
            while (!g.ehConexo()) {
                int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

                Pair sorteada = arestas.get(pos);
                if (pblnSimples) {
                    arestas.remove(sorteada);
                }

                Vertice u = (Vertice) sorteada.getA();
                Vertice v = (Vertice) sorteada.getB();

                ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                    if (pblnAciclico && !g.ehAciclico()) {
                        arestas.remove(sorteada);
                        g.delAresta(a.getId());
                        cArestas--;
                        if (arestas.size() == 0) {
                            break;
                        }
                    }
                } catch (Exception e) {}
                cArestas++;
            }
        }

        return g;
    }

    /**
     * Gera um grafo dirigido randomico.<br>
     * Esta fun��o gera um grafo dirigido com base nos parâmetros informados.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @param pblnConexo Informa se o grafo deve ser conexo
     * @param pblnSimples Informa se o grafo deve ser simples
     * @param pblnAciclico Informa se o grafo deve ser aciclico
     * @return GrafoDirigido
     * @throws Exception
     */
    public static GrafoDirigido getGrafoRandomicoDirigido(int qtdeVertices, boolean pblnConexo, boolean pblnSimples, boolean pblnAciclico) {

        /*if (pblnConexo && pblnAciclico) 
        	throw new Exception("Nao � poss�vel gerar um grafo dirigido conexo e ac�clico");*/

        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if ((pblnSimples || pblnAciclico) && qtdeVertices == 1) {
            return g;
        }

        int minArestas;
        int maxArestas;
        if (pblnConexo) {
            minArestas = qtdeVertices - 1;
            if (pblnSimples) {
                maxArestas = qtdeVertices * (qtdeVertices - 1);
            } else {
                maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);
            }
        } else {
            minArestas = 0;
            if (pblnSimples) {
                maxArestas = qtdeVertices * (qtdeVertices - 1);
            } else {
                maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);
            }
        }

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                //sem lacos
                if ((pblnSimples || pblnAciclico) && i == j) {
                    continue;
                }

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(minArestas, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);
            if (pblnSimples) {
                arestas.remove(sorteada);
            }

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
                if (pblnAciclico && !g.ehAciclico()) {
                    arestas.remove(sorteada);
                    g.delAresta(a.getId());
                    cArestas--;
                    if (arestas.size() == 0) {
                        break;
                    }
                }
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        if (pblnConexo) {
            while (!g.ehConexo()) {
                int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

                Pair sorteada = arestas.get(pos);
                if (pblnSimples) {
                    arestas.remove(sorteada);
                }

                Vertice u = (Vertice) sorteada.getA();
                Vertice v = (Vertice) sorteada.getB();

                ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                    if (pblnAciclico && !g.ehAciclico()) {
                        arestas.remove(sorteada);
                        g.delAresta(a.getId());
                        cArestas--;
                        if (arestas.size() == 0) {
                            break;
                        }
                    }
                } catch (Exception e) {}
                cArestas++;
            }
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido ciclo.<br>
     * Um grafo � ciclo quando possui um �nico v�rtice com um la�o ou<br>
     * � um grafo simples e conexo onde todos os v�rtices e arestas formam um circuito.<br>
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido ciclo
     */
    public static GrafoNaoDirigido getGrafoCicloNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                vertices.add(v);
            } catch (Exception e) {}
        }

        int cArestas = 1;

        if (qtdeVertices == 1) { //se for um vertice cria apenas um laco
            Vertice u = g.getVertice(0);
            Vertice v = g.getVertice(0);

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        } else {
            int pos = Comandos.getIntNumeroAleatorio(0, vertices.size() - 1);

            Vertice ini = vertices.get(pos);
            vertices.remove(ini);

            Vertice u = ini;
            while (vertices.size() > 0) {
                pos = Comandos.getIntNumeroAleatorio(0, vertices.size() - 1);

                Vertice v = vertices.get(pos);
                vertices.remove(v);

                ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}

                cArestas++;

                u = v;
            }

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, ini);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        }

        return g;
    }

    /**
     * Gera um grafo dirigido ciclo.<br>
     * Um grafo � ciclo quando possui um �nico v�rtice com um la�o ou<br>
     * � um grafo simples e conexo onde todos os v�rtices e arestas formam um circuito.<br>
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido ciclo
     */
    public static GrafoDirigido getGrafoCicloDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
                vertices.add(v);
            } catch (Exception e) {}
        }

        int cArestas = 1;

        if (qtdeVertices == 1) { //se for um vertice cria apenas um laco
            Vertice u = g.getVertice(0);
            Vertice v = g.getVertice(0);

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        } else {
            int pos = Comandos.getIntNumeroAleatorio(0, vertices.size() - 1);

            Vertice ini = vertices.get(pos);
            vertices.remove(ini);

            Vertice u = ini;
            while (vertices.size() > 0) {
                pos = Comandos.getIntNumeroAleatorio(0, vertices.size() - 1);

                Vertice v = vertices.get(pos);
                vertices.remove(v);

                ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
                a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
                a.setDado(cArestas);
                a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

                try {
                    g.addAresta(a);
                } catch (Exception e) {}

                cArestas++;

                u = v;
            }

            ArestaDirigida a = new ArestaDirigida(cArestas, u, ini);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        }

        return g;
    }

    /**
     * Gera um grafo n�o dirigido ac�clico.<br>
     * Um grafo � ac�clico se n�o possui ciclos.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoNaoDirigido ac�clico
     */
    public static GrafoNaoDirigido getGrafoAciclicoNaoDirigido(int qtdeVertices) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if (qtdeVertices == 1) {
            return g;
        }

        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = i + 1; j < qtdeVertices; j++) {
                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(0, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaNaoDirigida a = new ArestaNaoDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
                if (!g.ehAciclico()) {
                    arestas.remove(sorteada);
                    g.delAresta(a.getId());
                    cArestas--;
                    if (arestas.size() == 0) {
                        break;
                    }
                }
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }

    /**
     * Gera um grafo dirigido ac�clico.<br>
     * Um grafo � ac�clico se n�o possui ciclos.
     * 
     * @param qtdeVertices Quantidade de v�rtices
     * @return GrafoDirigido ac�clico
     */
    public static GrafoDirigido getGrafoAciclicoDirigido(int qtdeVertices) {
        GrafoDirigido g = new GrafoDirigido();

        for (int i = 1; i <= qtdeVertices; i++) {
            Vertice v = new Vertice(i);
            v.setDado(i);

            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        if (qtdeVertices == 1) {
            return g;
        }

        int maxArestas = qtdeVertices * qtdeVertices + qtdeVertices * qtdeVertices * Comandos.getIntNumeroAleatorio(0, qtdeVertices);

        ArrayList<Pair> arestas = new ArrayList<Pair>();
        for (int i = 0; i < qtdeVertices; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < qtdeVertices; j++) {

                if (i == j) {
                    continue;
                }

                Vertice v = g.getVertice(j);

                Pair par = new Pair(u, v);
                arestas.add(par);
            }
        }

        int qtdeArestas = Comandos.getIntNumeroAleatorio(0, maxArestas);
        int cArestas = 1;
        while (qtdeArestas > 0) {
            int pos = Comandos.getIntNumeroAleatorio(0, arestas.size() - 1);

            Pair sorteada = arestas.get(pos);

            Vertice u = (Vertice) sorteada.getA();
            Vertice v = (Vertice) sorteada.getB();

            ArestaDirigida a = new ArestaDirigida(cArestas, u, v);
            a.setValor(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));
            a.setDado(cArestas);
            a.setCapacidade(Comandos.getDoubleNumeroAleatorio(0.0, 100.0));

            try {
                g.addAresta(a);
                if (!g.ehAciclico()) {
                    arestas.remove(sorteada);
                    g.delAresta(a.getId());
                    cArestas--;
                    if (arestas.size() == 0) {
                        break;
                    }
                }
            } catch (Exception e) {}
            cArestas++;
            qtdeArestas--;
        }

        return g;
    }
}
