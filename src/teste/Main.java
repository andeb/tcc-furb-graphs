package teste;

import geradores.GeradorGrafos;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import persistencia.Persistencia;
import algoritmos.AlgoritmoArticulacoes;
import algoritmos.AlgoritmoArticulacoesResultado;
import algoritmos.AlgoritmoBellmanFord;
import algoritmos.AlgoritmoBellmanFordResultado;
import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import algoritmos.AlgoritmoBuscaProfundidade;
import algoritmos.AlgoritmoBuscaProfundidadeResultado;
import algoritmos.AlgoritmoDijkstra;
import algoritmos.AlgoritmoDijkstraResultado;
import algoritmos.AlgoritmoFloydWarshall;
import algoritmos.AlgoritmoFloydWarshallResultado;
import algoritmos.AlgoritmoFordFulkerson;
import algoritmos.AlgoritmoFordFulkersonDijkstra;
import algoritmos.AlgoritmoFordFulkersonResultado;
import algoritmos.AlgoritmoHopcroftTarjan;
import algoritmos.AlgoritmoHopcroftTarjanResultado;
import algoritmos.AlgoritmoKruskal;
import algoritmos.AlgoritmoKruskalResultado;
import algoritmos.AlgoritmoOrdenacaoTopologica;
import algoritmos.AlgoritmoOrdenacaoTopologicaResultado;
import algoritmos.AlgoritmoPontes;
import algoritmos.AlgoritmoPontesResultado;
import algoritmos.AlgoritmoPrim;
import algoritmos.AlgoritmoPrimResultado;
import auxiliar.Comandos;
import auxiliar.Constante;
import auxiliar.PairPriority;
import base.Aresta;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

public class Main {

    public void testeBellmanFord3() throws Exception {
        System.out.println("Teste Bellman-Ford");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoBellmanFord alg = new AlgoritmoBellmanFord();
        alg.executar(g, v1);

        AlgoritmoBellmanFordResultado res = alg.getResultado();
        if (res.getCusto(v3) != Constante.INF) {
            System.out.println("Resultado: " + res.getCusto(v3));
            String caminho = "";

            caminho = (String) v3.getDado();
            for (Vertice ver = res.getPredecessor(v3); ver != null; ver = res.getPredecessor(ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeBellmanFord2() throws Exception {
        System.out.println("Teste Bellman-Ford");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(4);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(3);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v4);
        a3.setValor(3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v2);
        a4.setValor(2);

        ArestaDirigida a5 = new ArestaDirigida(5, v4, v1);
        a5.setValor(4);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v3);
        a6.setValor(9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeBellmanFord2.xml");

        AlgoritmoBellmanFord alg = new AlgoritmoBellmanFord();
        alg.executar(g, v2);

        AlgoritmoBellmanFordResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getCusto(v3));

        String caminho = (String) v3.getDado();
        for (Vertice ver = res.getPredecessor(v3); ver != null; ver = res.getPredecessor(ver)) {
            caminho = ver.getDado() + " -> " + caminho;
        }
        System.out.println(caminho);
    }

    public void testeBellmanFord() throws Exception {
        System.out.println("Teste Bellman-Ford");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaDirigida a4 = new ArestaDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoBellmanFord alg = new AlgoritmoBellmanFord();
        alg.executar(g, v1);

        AlgoritmoBellmanFordResultado res = alg.getResultado();

        if (res.getCusto(v5) != Constante.INF) {
            System.out.println("Resultado: " + res.getCusto(v3));
            String caminho = "";

            caminho = (String) v3.getDado();
            for (Vertice ver = res.getPredecessor(v3); ver != null; ver = res.getPredecessor(ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeFloydWarshall() throws Exception {
        System.out.println("Teste Floyd-Warshall");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaDirigida a4 = new ArestaDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoFloydWarshall alg = new AlgoritmoFloydWarshall();
        alg.executar(g);

        AlgoritmoFloydWarshallResultado res = alg.getResultado();
        Vertice o = v1;
        Vertice d = v5;
        if (res.getCusto(o, d) != Constante.INF) {
            System.out.println("Resultado: " + res.getCusto(o, d));
            String caminho = "";

            caminho = (String) d.getDado();

            for (Vertice ver = res.getPredecessor(o, d); !ver.equals(o); ver = res.getPredecessor(o, ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            caminho = o.getDado() + " -> " + caminho;
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeFloydWarshall2() throws Exception {
        System.out.println("Teste Floyd-Warshall");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(5);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(3);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v4);
        a3.setValor(3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v2);
        a4.setValor(2);

        ArestaDirigida a5 = new ArestaDirigida(5, v4, v1);
        a5.setValor(4);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v3);
        a6.setValor(9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeFloydWarshall2.xml");

        AlgoritmoFloydWarshall alg = new AlgoritmoFloydWarshall();
        alg.executar(g);

        AlgoritmoFloydWarshallResultado res = alg.getResultado();
        Vertice o = v2;
        Vertice d = v3;
        if (res.getCusto(o, d) != Constante.INF) {
            System.out.println("Resultado: " + res.getCusto(o, d));
            String caminho = "";

            caminho = (String) d.getDado();

            for (Vertice ver = res.getPredecessor(o, d); !ver.equals(o); ver = res.getPredecessor(o, ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            caminho = o.getDado() + " -> " + caminho;
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }

        //resumo
        for (int i = 0; i < g.getTamanho(); i++) {
            Vertice vi = g.getVertice(i);
            for (int j = 0; j < g.getTamanho(); j++) {
                Vertice vj = g.getVertice(j);

                System.out.println("" + vi.getDado() + " -> " + vj.getDado() + " = " + res.getCusto(vi, vj));
            }
        }
    }

    public void testeFloydWarshall3() throws Exception {
        System.out.println("Teste Floyd-Warshall");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoFloydWarshall alg = new AlgoritmoFloydWarshall();
        alg.executar(g);

        AlgoritmoFloydWarshallResultado res = alg.getResultado();
        Vertice o = v1;
        Vertice d = v3;
        if (res.getCusto(o, d) != Constante.INF) {
            System.out.println("Resultado: " + res.getCusto(o, d));
            String caminho = "";

            caminho = (String) d.getDado();

            for (Vertice ver = res.getPredecessor(o, d); !ver.equals(o); ver = res.getPredecessor(o, ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            caminho = o.getDado() + " -> " + caminho;
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeDijkstra() throws Exception {
        System.out.println("Teste Dijkstra");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaDirigida a4 = new ArestaDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoDijkstra alg = new AlgoritmoDijkstra();
        alg.executar(g, v1, v5);

        AlgoritmoDijkstraResultado res = alg.getResultado();
        if (!res.getSemCaminho(v5)) {
            System.out.println("Resultado: " + res.getCusto(v5));
            String caminho = "";

            caminho = (String) v5.getDado();
            for (Vertice ver = res.getPredecessor(v5); ver != null; ver = res.getPredecessor(ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeDijkstra2() throws Exception {
        System.out.println("Teste Dijkstra");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(4);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setValor(3);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v4);
        a3.setValor(3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v2);
        a4.setValor(2);

        ArestaDirigida a5 = new ArestaDirigida(5, v4, v1);
        a5.setValor(4);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v3);
        a6.setValor(9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeDijkstra2.xml");

        AlgoritmoDijkstra alg = new AlgoritmoDijkstra();
        alg.executar(g, v2, v3);

        AlgoritmoDijkstraResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getCusto(v3));

        String caminho = (String) v3.getDado();
        for (Vertice ver = res.getPredecessor(v3); ver != null; ver = res.getPredecessor(ver)) {
            caminho = ver.getDado() + " -> " + caminho;
        }
        System.out.println(caminho);
    }

    public void testeDijkstra3() throws Exception {
        System.out.println("Teste Dijkstra");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(1);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setValor(7);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v3);
        a3.setValor(5);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v5);
        a4.setValor(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        AlgoritmoDijkstra alg = new AlgoritmoDijkstra();
        alg.executar(g, v1, v3);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeDijkstra3.xml");

        AlgoritmoDijkstraResultado res = alg.getResultado();
        if (!res.getSemCaminho(v3)) {
            System.out.println("Resultado: " + res.getCusto(v3));
            String caminho = "";

            caminho = (String) v3.getDado();
            for (Vertice ver = res.getPredecessor(v3); ver != null; ver = res.getPredecessor(ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            System.out.println(caminho);
        } else {
            System.out.println("Impossivel");
        }
    }

    public void testeBuscaProfundidade() throws Exception {
        System.out.println("Teste Busca Profundidade");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        Vertice v8 = new Vertice(8);
        Vertice v9 = new Vertice(9);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        v8.setDado("8");
        v9.setDado("9");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);
        g.addVertice(v8);
        g.addVertice(v9);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v5);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v5);

        ArestaDirigida a5 = new ArestaDirigida(5, v3, v6);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v6);

        ArestaDirigida a7 = new ArestaDirigida(7, v4, v5);

        ArestaDirigida a8 = new ArestaDirigida(8, v7, v8);

        ArestaDirigida a9 = new ArestaDirigida(9, v7, v9);

        ArestaDirigida a10 = new ArestaDirigida(10, v8, v9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);
        g.addAresta(a10);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeBuscaProfundidade.xml");

        AlgoritmoBuscaProfundidade alg = new AlgoritmoBuscaProfundidade();
        alg.executar(g);
        //alg.executar(g, v2);
        //alg.executar(g, v2, v6);

        AlgoritmoBuscaProfundidadeResultado res = alg.getResultado();
        System.out.println("Resultado: ");

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            System.out.println(v.getDado());
            System.out.println("Abertura: " + res.getTempoAbertura(v) + " Fechamento: " + res.getTempoFechamento(v) + " Visitado: " + res.getVisitado(v));
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                System.out.println("Predecessor: null");
            } else {
                System.out.println("Predecessor: " + pred.getDado());
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            System.out.print("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + (String) j.getDado();
                }
            }
            System.out.println(desc);
        }
    }

    public void testeBuscaLargura() throws Exception {
        System.out.println("Teste Busca Largura");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        Vertice v8 = new Vertice(8);
        Vertice v9 = new Vertice(9);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        v8.setDado("8");
        v9.setDado("9");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);
        g.addVertice(v8);
        g.addVertice(v9);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v5);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v5);

        ArestaDirigida a5 = new ArestaDirigida(5, v3, v6);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v6);

        ArestaDirigida a7 = new ArestaDirigida(7, v4, v5);

        ArestaDirigida a8 = new ArestaDirigida(8, v7, v8);

        ArestaDirigida a9 = new ArestaDirigida(9, v7, v9);

        ArestaDirigida a10 = new ArestaDirigida(10, v8, v9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);
        g.addAresta(a10);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeBuscaLargura.xml");

        AlgoritmoBuscaLargura alg = new AlgoritmoBuscaLargura();
        alg.executar(g);
        //alg.executar(g, v2);
        //alg.executar(g, v2, v5);

        AlgoritmoBuscaLarguraResultado res = alg.getResultado();
        System.out.println("Resultado: ");

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            System.out.println(v.getDado());
            System.out.println("Abertura: " + res.getTempoAbertura(v) + " Fechamento: " + res.getTempoFechamento(v) + " Visitado: " + res.getVisitado(v));
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                System.out.println("Predecessor: null");
            } else {
                System.out.println("Predecessor: " + pred.getDado());
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            System.out.print("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + (String) j.getDado();
                }
            }
            System.out.println(desc);
        }
    }

    public void testeOrdenacaoTopologica() throws Exception {
        System.out.println("Teste Ordenacao Topologica");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        Vertice v8 = new Vertice(8);
        Vertice v9 = new Vertice(9);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        v8.setDado("8");
        v9.setDado("9");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);
        g.addVertice(v8);
        g.addVertice(v9);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v5);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v5);

        ArestaDirigida a5 = new ArestaDirigida(5, v3, v6);

        ArestaDirigida a6 = new ArestaDirigida(6, v4, v6);

        ArestaDirigida a7 = new ArestaDirigida(7, v4, v5);

        ArestaDirigida a8 = new ArestaDirigida(8, v7, v8);

        ArestaDirigida a9 = new ArestaDirigida(9, v7, v9);

        ArestaDirigida a10 = new ArestaDirigida(10, v8, v9);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);
        g.addAresta(a10);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeOrdenacaoTopologica.xml");

        AlgoritmoOrdenacaoTopologica alg = new AlgoritmoOrdenacaoTopologica();
        alg.executar(g);

        AlgoritmoOrdenacaoTopologicaResultado res = alg.getResultado();
        System.out.println("Resultado: ");

        int tamanhoLista = res.getTamanho();
        String seq = "";
        for (int i = 0; i < tamanhoLista; i++) {
            Vertice v = res.getVertice(i);
            seq = v.getDado() + "[" + res.getTempoAbertura(v) + "/" + res.getTempoFechamento(v) + "]" + " -> " + seq;
        }

        System.out.println(seq);
    }

    public void testePrioridade() {
        PriorityQueue<PairPriority<Vertice>> fila = new PriorityQueue<PairPriority<Vertice>>();

        Vertice v1 = new Vertice(1);
        v1.setDado("1");
        Vertice v2 = new Vertice(2);
        v2.setDado("2");
        Vertice v3 = new Vertice(3);
        v3.setDado("3");
        Vertice v4 = new Vertice(4);
        v4.setDado("4");

        PairPriority<Vertice> p1 = new PairPriority<Vertice>(v1, 10.0);
        PairPriority<Vertice> p2 = new PairPriority<Vertice>(v2, 4.0);
        PairPriority<Vertice> p3 = new PairPriority<Vertice>(v3, 15.0);
        PairPriority<Vertice> p4 = new PairPriority<Vertice>(v4, 1.0);

        fila.offer(p1);
        fila.offer(p2);
        fila.offer(p3);
        fila.offer(p4);

        while (fila.size() > 0) {
            PairPriority<Vertice> p = fila.peek();

            System.out.println(p.getDado().getDado() + " / " + p.getCusto());

            fila.poll();
        }
    }

    public void testePrim() throws Exception {
        System.out.println("Teste Prim");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);

        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");

        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(10923);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setValor(1235);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v3);
        a3.setValor(1200);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v4);
        a4.setValor(1000);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testePrim.xml");

        AlgoritmoPrim alg = new AlgoritmoPrim();
        alg.executar(g);

        AlgoritmoPrimResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getCustoTotal());

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            System.out.println(v.getDado());
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                System.out.println("Predecessor: null");
            } else {
                System.out.println("Predecessor: " + pred.getDado() + " Custo: " + res.getCusto(v));
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            System.out.print("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + (String) j.getDado();
                }
            }
            System.out.println(desc);
        }
    }

    public void testeKruskal() throws Exception {
        System.out.println("Teste Kruskal");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);

        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");

        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(10923);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setValor(1235);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v3);
        a3.setValor(1200);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v4);
        a4.setValor(1000);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v5, v6);
        a5.setValor(10000);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeKruskal.xml");

        AlgoritmoKruskal alg = new AlgoritmoKruskal();
        alg.executar(g);

        AlgoritmoKruskalResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getCustoTotal() + " QtdeArestas: " + res.getArestas().size());

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            System.out.println(v.getDado());
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                System.out.println("Predecessor: null");
            } else {
                System.out.println("Predecessor: " + pred.getDado() + " Custo: " + res.getCusto(v));
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            System.out.print("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + (String) j.getDado();
                }
            }
            System.out.println(desc);
        }
    }

    public void testeHopcroftTarjan() throws Exception {
        System.out.println("Teste Hopcroft-Tarjan");

        GrafoDirigido g = new GrafoDirigido();

        Vertice vA = new Vertice(1);
        Vertice vB = new Vertice(2);
        Vertice vC = new Vertice(3);
        Vertice vD = new Vertice(4);
        vA.setDado("A");
        vB.setDado("B");
        vC.setDado("C");
        vD.setDado("D");

        g.addVertice(vA);
        g.addVertice(vB);
        g.addVertice(vC);
        g.addVertice(vD);

        ArestaDirigida a1 = new ArestaDirigida(1, vA, vD);

        ArestaDirigida a2 = new ArestaDirigida(2, vD, vA);

        ArestaDirigida a3 = new ArestaDirigida(3, vA, vB);

        ArestaDirigida a4 = new ArestaDirigida(4, vB, vD);

        ArestaDirigida a5 = new ArestaDirigida(5, vB, vC);

        ArestaDirigida a6 = new ArestaDirigida(6, vD, vC);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        AlgoritmoHopcroftTarjan alg = new AlgoritmoHopcroftTarjan();
        alg.executar(g);

        AlgoritmoHopcroftTarjanResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getQtdeComponentes());

        for (ArrayList<Vertice> componente : res.getComponentes()) {
            System.out.print("Componente: ");
            for (Vertice v : componente) {
                System.out.print(v.getDado() + " ");
            }
            System.out.println();
        }
    }

    public void testeHopcroftTarjan2() throws Exception {
        System.out.println("Teste Hopcroft-Tarjan");

        GrafoDirigido g = new GrafoDirigido();

        Vertice vA = new Vertice(1);
        Vertice vB = new Vertice(2);
        Vertice vC = new Vertice(3);
        Vertice vD = new Vertice(4);
        Vertice vE = new Vertice(5);
        Vertice vF = new Vertice(6);
        Vertice vG = new Vertice(7);
        Vertice vH = new Vertice(8);
        vA.setDado("A");
        vB.setDado("B");
        vC.setDado("C");
        vD.setDado("D");
        vE.setDado("E");
        vF.setDado("F");
        vG.setDado("G");
        vH.setDado("H");

        g.addVertice(vA);
        g.addVertice(vB);
        g.addVertice(vC);
        g.addVertice(vD);
        g.addVertice(vE);
        g.addVertice(vF);
        g.addVertice(vG);
        g.addVertice(vH);

        ArestaDirigida a1 = new ArestaDirigida(1, vA, vB);

        ArestaDirigida a2 = new ArestaDirigida(2, vE, vA);

        ArestaDirigida a3 = new ArestaDirigida(3, vB, vE);

        ArestaDirigida a4 = new ArestaDirigida(4, vE, vF);

        ArestaDirigida a5 = new ArestaDirigida(5, vB, vF);

        ArestaDirigida a6 = new ArestaDirigida(6, vB, vC);

        ArestaDirigida a7 = new ArestaDirigida(7, vG, vF);

        ArestaDirigida a8 = new ArestaDirigida(8, vF, vG);

        ArestaDirigida a9 = new ArestaDirigida(9, vC, vG);

        ArestaDirigida a10 = new ArestaDirigida(10, vC, vD);

        ArestaDirigida a11 = new ArestaDirigida(11, vD, vC);

        ArestaDirigida a12 = new ArestaDirigida(12, vG, vH);

        ArestaDirigida a13 = new ArestaDirigida(13, vD, vH);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);
        g.addAresta(a10);
        g.addAresta(a11);
        g.addAresta(a12);
        g.addAresta(a13);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeHopcroftTarjan2.xml");

        AlgoritmoHopcroftTarjan alg = new AlgoritmoHopcroftTarjan();
        alg.executar(g);

        AlgoritmoHopcroftTarjanResultado res = alg.getResultado();
        System.out.println("Resultado: " + res.getQtdeComponentes());

        for (ArrayList<Vertice> componente : res.getComponentes()) {
            System.out.print("Componente: ");
            for (Vertice v : componente) {
                System.out.print(v.getDado() + " ");
            }
            System.out.println();
        }

        if (res.mesmaComponente(vE, vB)) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
        if (res.mesmaComponente(vE, vC)) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
    }

    public void testeFordFulkerson() throws Exception {
        System.out.println("Teste Ford-Fulkerson");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setCapacidade(6);

        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        a2.setCapacidade(5);

        ArestaDirigida a3 = new ArestaDirigida(3, v2, v4);
        a3.setCapacidade(3);

        ArestaDirigida a4 = new ArestaDirigida(4, v3, v4);
        a4.setCapacidade(2);

        ArestaDirigida a5 = new ArestaDirigida(5, v4, v5);
        a5.setCapacidade(7);

        ArestaDirigida a6 = new ArestaDirigida(6, v1, v4);
        a6.setCapacidade(2);

        ArestaDirigida a7 = new ArestaDirigida(7, v1, v5);
        a7.setCapacidade(2);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeFordFulkerson.xml");

        AlgoritmoFordFulkerson alg = new AlgoritmoFordFulkerson();
        alg.executar(g, v1, v5);

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        //deve ser 4
        System.out.println("Resultado: " + res.getFluxoMaximo());
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (res.getFluxo(u, v) > 0.0) {
                    System.out.println(u.getDado() + " -> " + v.getDado() + " Fluxo: " + res.getFluxo(u, v));
                }
            }
        }
    }

    public void testeFordFulkerson2() throws Exception {
        System.out.println("Teste Ford-Fulkerson");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setCapacidade(6);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setCapacidade(5);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v4);
        a3.setCapacidade(3);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v3, v4);
        a4.setCapacidade(2);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v4, v5);
        a5.setCapacidade(7);

        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v1, v4);
        a6.setCapacidade(2);

        ArestaNaoDirigida a7 = new ArestaNaoDirigida(7, v1, v5);
        a7.setCapacidade(2);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);

        AlgoritmoFordFulkerson alg = new AlgoritmoFordFulkerson();
        alg.executar(g, v5, v1);

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        //deve ser 4
        System.out.println("Resultado: " + res.getFluxoMaximo());
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (res.getFluxo(u, v) > 0.0) {
                    System.out.println(u.getDado() + " -> " + v.getDado() + " Fluxo: " + res.getFluxo(u, v));
                }
            }
        }
    }

    public void testePropriedadesTrivial() throws Exception {
        System.out.println("Teste Trivial");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        g.addVertice(v1);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v1);

        //g.addAresta(a1);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
    }

    public void testePropriedadesNulo() throws Exception {
        System.out.println("Teste Nulo");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        g.addVertice(v1);
        g.addVertice(v2);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v1);

        //g.addAresta(a1);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
    }

    public void testePropriedadesCompleto() throws Exception {
        System.out.println("Teste Completo");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        v1.setDado("1");
        v2.setDado("2");
        g.addVertice(v1);
        g.addVertice(v2);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v2, v1);

        g.addAresta(a1);
        g.addAresta(a2);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
    }

    public void testePropriedadesConexo() throws Exception {
        System.out.println("Teste Conexo");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v1);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v1);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v1);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Grau: " + g.getGrau(v1));
    }

    public void testePropriedadesConexoDirigido() throws Exception {
        System.out.println("Teste Fortemente Conexo Dirigido");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v3, v2);

        ArestaDirigida a3 = new ArestaDirigida(3, v1, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
    }

    public void testePropriedadesBipartido() throws Exception {
        System.out.println("Teste Bipartido");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v3);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v4);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v1, v4);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v2, v3);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v1, v5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        //g.addAresta(a5);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Grau: " + g.getGrau(v1));
    }

    public void testePropriedadesBipartido2() throws Exception {
        System.out.println("Teste Bipartido");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaDirigida a1 = new ArestaDirigida(1, v2, v1);
        ArestaDirigida a2 = new ArestaDirigida(2, v1, v3);
        ArestaDirigida a3 = new ArestaDirigida(3, v3, v4);
        ArestaDirigida a4 = new ArestaDirigida(4, v4, v2);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Grau: " + g.getGrauSaida(v1));
    }

    public void testePropriedadesBipartidoCompleto() throws Exception {
        System.out.println("Teste Bipartido Completo");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v3);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v4);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v1, v4);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v2, v3);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v1, v5);

        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v2, v5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Grau: " + g.getGrau(v1));

        ArestaNaoDirigida a7 = (ArestaNaoDirigida) g.getArestaById(5);
        System.out.println(a7.getVi().getDado() + " -> " + a7.getVj(a7.getVi()).getDado());

        Vertice v10 = g.getVerticeById(3);
        System.out.println(v10.getDado());
    }

    public void testeOperacoesNaoDirigido() throws Exception {
        System.out.println("Teste Operacoes");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v3, v1);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v1, v3);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v3);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v2, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(4);
        System.out.println("Apaga aresta 4");
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(3);
        System.out.println("Apaga aresta 3");
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(2);
        System.out.println("Apaga aresta 2");
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());

        //g.delVertice(3);
        g.delVertice(2);
        System.out.println("Apaga vertice 2");
        System.out.println("Grau V1: " + g.getGrau(v1));
        //System.out.println("Grau V2: " + v2.getGrau());
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
    }

    public void testeOperacoesDirigido() throws Exception {
        System.out.println("Teste Operacoes");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);

        ArestaDirigida a2 = new ArestaDirigida(2, v3, v1);

        ArestaDirigida a3 = new ArestaDirigida(3, v1, v3);

        ArestaDirigida a4 = new ArestaDirigida(4, v1, v3);

        ArestaDirigida a5 = new ArestaDirigida(5, v2, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(4);
        System.out.println("Apaga aresta 4");
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(3);
        System.out.println("Apaga aresta 3");
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delAresta(2);
        System.out.println("Apaga aresta 2");
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());

        //g.delVertice(3);
        g.delVertice(2);
        System.out.println("Apaga vertice 2");
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        //System.out.println("Grau V2: " + v2.getGrau() + " Entrada: " + v2.getGrauEntrada());
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
    }

    public void testeOperacoesDirigido2() throws Exception {
        System.out.println("Teste Operacoes 2");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaDirigida a1 = new ArestaDirigida(1, v4, v1);

        ArestaDirigida a2 = new ArestaDirigida(2, v4, v2);

        ArestaDirigida a3 = new ArestaDirigida(3, v4, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Grau V4: " + g.getGrauSaida(v4) + " Entrada: " + g.getGrauEntrada(v4));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delVertice(4);
        System.out.println("Apaga vertice 4");
        System.out.println("Grau V1: " + g.getGrauSaida(v1) + " Entrada: " + g.getGrauEntrada(v1));
        System.out.println("Grau V2: " + g.getGrauSaida(v2) + " Entrada: " + g.getGrauEntrada(v2));
        System.out.println("Grau V3: " + g.getGrauSaida(v3) + " Entrada: " + g.getGrauEntrada(v3));
        System.out.println("Grau V4: " + g.getGrauSaida(v4) + " Entrada: " + g.getGrauEntrada(v4));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
    }

    public void testeOperacoesNaoDirigido2() throws Exception {
        System.out.println("Teste Operacoes 2");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);

        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v4, v1);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v4, v2);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v4, v3);

        //ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        //g.addAresta(a4);

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Grau V4: " + g.getGrau(v4));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        g.delVertice(4);
        System.out.println("Apaga vertice 4");
        System.out.println("Grau V1: " + g.getGrau(v1));
        System.out.println("Grau V2: " + g.getGrau(v2));
        System.out.println("Grau V3: " + g.getGrau(v3));
        System.out.println("Grau V4: " + g.getGrau(v4));
        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Qtde Arestas: " + g.getQtdeArestas());

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
    }

    public void testeOperacoesNaoDirigido3() throws Exception {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);

        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(100);
        a1.setCapacidade(101);
        a1.setDado("A");

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v3);
        a2.setValor(200);
        a2.setCapacidade(201);
        a2.setDado("B");

        g.addAresta(a1);
        g.addAresta(a2);

        for (int i = 0; i < g.getQtdeArestas(); i++) {
            Aresta a = g.getAresta(i);

            System.out.println("Id: " + a.getId() + " Valor: " + a.getValor() + " Capacidade: " + a.getCapacidade() + " Dado: " + a.getDado());
        }

        a1.setValor(123);

        for (int i = 0; i < g.getQtdeArestas(); i++) {
            Aresta a = g.getAresta(i);

            System.out.println("Id: " + a.getId() + " Valor: " + a.getValor() + " Capacidade: " + a.getCapacidade() + " Dado: " + a.getDado());
        }

        a2.setCapacidade(321);

        for (int i = 0; i < g.getQtdeArestas(); i++) {
            Aresta a = g.getAresta(i);

            System.out.println("Id: " + a.getId() + " Valor: " + a.getValor() + " Capacidade: " + a.getCapacidade() + " Dado: " + a.getDado());
        }

        a1.setDado("uhull");

        for (int i = 0; i < g.getQtdeArestas(); i++) {
            Aresta a = g.getAresta(i);

            System.out.println("Id: " + a.getId() + " Valor: " + a.getValor() + " Capacidade: " + a.getCapacidade() + " Dado: " + a.getDado());
        }
    }

    public void testeGeracaoGrafos() {
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoCompletoNaoDirigido(500);
        //GrafoDirigido g = GeradorGrafos.getGrafoCompletoDirigido(50);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoSimplesNaoDirigido(500);
        //GrafoDirigido g = GeradorGrafos.getGrafoSimplesDirigido(500);
        //GrafoDirigido g = GeradorGrafos.getGrafoDensoDirigido(100);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoDensoNaoDirigido(100);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoEsparsoNaoDirigido(500);
        //GrafoDirigido g = GeradorGrafos.getGrafoEsparsoDirigido(500);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoMultigrafoNaoDirigido(100);
        //GrafoDirigido g = GeradorGrafos.getGrafoMultigrafoDirigido(10);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoConexoNaoDirigido(500);
        /*GrafoNaoDirigido g = null;
        try {
        	g = GeradorGrafos.getGrafoDesconexoNaoDirigido(10);
        } catch (Exception e) {
        	e.printStackTrace();
        }*/

        /*GrafoDirigido g = null;
        try {
        	g = GeradorGrafos.getGrafoDesconexoDirigido(10);
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }*/

        /*GrafoNaoDirigido g = null;
        try {
        	g = GeradorGrafos.getGrafoRegularNaoDirigido(3, 4);
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }*/
        //GrafoDirigido g = GeradorGrafos.getGrafoRegularDirigido(1, 3);

        //GrafoDirigido g = GeradorGrafos.getGrafoBipartidoDirigido(4, 3);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoBipartidoNaoDirigido(4, 3);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoBipartidoCompletoNaoDirigido(4, 3);
        //GrafoDirigido g = GeradorGrafos.getGrafoBipartidoCompletoDirigido(4, 3);

        //GrafoNaoDirigido g = GeradorGrafos.getGrafoCicloNaoDirigido(2);
        //GrafoDirigido g = GeradorGrafos.getGrafoCicloDirigido(2);

        //GrafoNaoDirigido g = GeradorGrafos.getGrafoAciclicoNaoDirigido(3);
        GrafoDirigido g = GeradorGrafos.getGrafoAciclicoDirigido(3);

        System.out.println("Tamanho grafo: " + g.getTamanho());
        System.out.println("Grau V1: " + g.getGrauSaida(g.getVertice(0)));

        System.out.println("Conexo: " + g.ehConexo());
        System.out.println("Desconexo: " + g.ehDesconexo());
        System.out.println("Denso: " + g.ehDenso());
        System.out.println("Esparso: " + g.ehEsparso());
        System.out.println("Nulo: " + g.ehNulo());
        System.out.println("Trivial: " + g.ehTrivial());
        System.out.println("Regular: " + g.ehRegular());
        System.out.println("Simples: " + g.ehSimples());
        System.out.println("Completo: " + g.ehCompleto());
        System.out.println("Multigrafo: " + g.ehMultigrafo());
        System.out.println("Bipartido: " + g.ehBipartido());
        System.out.println("Bipartido Completo: " + g.ehBipartidoCompleto());
        System.out.println("Ciclo: " + g.ehCiclo());
        System.out.println("Aciclo: " + g.ehAciclico());
        System.out.println("Arvore: " + g.ehArborescencia());
        System.out.println("Floresta: " + g.ehFloresta());
    }

    public void testePersistenciaNaoDirigido() throws Exception {
        System.out.println("Teste Persistencia");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);

        v1.setDado("A");
        v2.setDado("B");
        v3.setDado("C");

        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(100.0);
        a1.setCapacidade(1123.456);
        a1.setDado("R1");

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v3);
        a2.setValor(12.0);
        a2.setCapacidade(9.0);
        a2.setDado("R2");

        g.addAresta(a1);
        g.addAresta(a2);

        Persistencia.salvarGrafo(g, "/home/xsplyter/teste.xml");

        GrafoNaoDirigido g2 = (GrafoNaoDirigido) Persistencia.carregarGrafo("/home/xsplyter/teste.xml");

        Persistencia.salvarGrafo(g2, "/home/xsplyter/teste2.xml");

        System.out.println(g2.ehSimples());
    }

    public void testePersistenciaDirigido() throws Exception {
        System.out.println("Teste Persistencia");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);

        v1.setDado("A");
        v2.setDado("B");
        v3.setDado("C");

        v1.setCor(new Color(1, 2, 3));

        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(100.0);
        a1.setCapacidade(1123.456);
        a1.setDado("R1");

        ArestaDirigida a2 = new ArestaDirigida(2, v2, v3);
        a2.setValor(12.0);
        a2.setCapacidade(9.0);
        a2.setDado("R2");

        g.addAresta(a1);
        g.addAresta(a2);

        Persistencia.salvarGrafo(g, "/home/xsplyter/teste3.xml");

        Grafo g2 = Persistencia.carregarGrafo("/home/xsplyter/teste3.xml");

        Persistencia.salvarGrafo(g2, "/home/xsplyter/teste4.xml");

        System.out.println(g2.ehSimples());
    }

    public void testeFordFulkersonDijkstra() throws Exception {
        System.out.println("Teste Ford-Fulkerson Dijkstra");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        Vertice v8 = new Vertice(8);
        Vertice v9 = new Vertice(9);
        Vertice v10 = new Vertice(10);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        v8.setDado("8");
        v9.setDado("9");
        v10.setDado("10");
        g.addVertice(v9);
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);
        g.addVertice(v8);
        g.addVertice(v10);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setCapacidade(1);
        a1.setValor(0);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        a2.setCapacidade(2);
        a2.setValor(0);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v1, v4);
        a3.setCapacidade(1);
        a3.setValor(0);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v5, v8);
        a4.setCapacidade(1);
        a4.setValor(0);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v6, v8);
        a5.setCapacidade(1);
        a5.setValor(0);

        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v7, v8);
        a6.setCapacidade(1);
        a6.setValor(0);

        ArestaNaoDirigida a7 = new ArestaNaoDirigida(7, v2, v6);
        a7.setCapacidade(1);
        a7.setValor(1);

        ArestaNaoDirigida a8 = new ArestaNaoDirigida(8, v3, v7);
        a8.setCapacidade(1);
        a8.setValor(1);

        ArestaNaoDirigida a9 = new ArestaNaoDirigida(9, v2, v5);
        a9.setCapacidade(1);
        a9.setValor(2);

        ArestaNaoDirigida a10 = new ArestaNaoDirigida(10, v3, v6);
        a10.setCapacidade(1);
        a10.setValor(2);

        ArestaNaoDirigida a11 = new ArestaNaoDirigida(11, v4, v7);
        a11.setCapacidade(1);
        a11.setValor(2);

        ArestaNaoDirigida a12 = new ArestaNaoDirigida(12, v9, v1);
        a12.setCapacidade(3);
        a12.setValor(0);

        ArestaNaoDirigida a13 = new ArestaNaoDirigida(13, v8, v10);
        a13.setCapacidade(3);
        a13.setValor(0);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);
        g.addAresta(a10);
        g.addAresta(a11);
        g.addAresta(a12);
        g.addAresta(a13);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeFordFulkersonDijkstra.xml");

        //AlgoritmoFordFulkersonDijkstra alg = new AlgoritmoFordFulkersonDijkstra();
        AlgoritmoFordFulkerson alg = new AlgoritmoFordFulkerson();
        alg.executar(g, v9, v10);

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        //deve ser 6 o custo
        System.out.println("Resultado: " + res.getFluxoMaximo() + " Custo: " + res.getCustoFluxo());
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (res.getFluxo(u, v) > 0.0) {
                    System.out.println(u.getDado() + " -> " + v.getDado() + " Fluxo: " + res.getFluxo(u, v));
                }
            }
        }
    }

    public void testeCiclo() {
        GrafoDirigido g = new GrafoDirigido();

        Vertice v = new Vertice(1);
        ArestaDirigida a = new ArestaDirigida(1, v, v);

        try {
            g.addVertice(v);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            g.addAresta(a);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(g.ehCiclo());
    }

    public void testaPerformanceBFSDFS() {
        int vertices = 20;

        //GrafoDirigido g = GeradorGrafos.getGrafoSimplesDirigido(vertices);
        //GrafoNaoDirigido g = GeradorGrafos.getGrafoSimplesNaoDirigido(vertices);
        GrafoDirigido g = GeradorGrafos.getGrafoCompletoDirigido(vertices);

        System.out.println("Vértices: " + vertices);
        Vertice v1 = g.getVertice(0);
        Vertice v2 = g.getVertice(vertices - 1);

        AlgoritmoBuscaLargura algBFS = new AlgoritmoBuscaLargura();
        Cronometro.start();
        algBFS.executar(g, v1, v2);
        Cronometro.stop();
        AlgoritmoBuscaLarguraResultado resBFS = algBFS.getResultado();
        System.out.println("Resultado busca em largura: " + resBFS.getTempoAbertura(v2));

        AlgoritmoBuscaProfundidade algDFS = new AlgoritmoBuscaProfundidade();
        Cronometro.start();
        algDFS.executar(g, v1, v2);
        Cronometro.stop();
        AlgoritmoBuscaProfundidadeResultado resDFS = algDFS.getResultado();
        System.out.println("Resultado busca em profundidade: " + resDFS.getTempoAbertura(v2));
    }

    public void testaPerformanceDijkstraFloydFord() {
        int vertices = 100;

        GrafoDirigido g = GeradorGrafos.getGrafoSimplesDirigido(vertices);

        AlgoritmoFloydWarshall algFloyd = new AlgoritmoFloydWarshall();
        AlgoritmoDijkstra algDij = new AlgoritmoDijkstra();
        AlgoritmoBellmanFord algFord = new AlgoritmoBellmanFord();

        System.out.println("Vértices: " + vertices);
        Vertice v1 = g.getVertice(0);
        Vertice v2 = g.getVertice(vertices - 1);

        Cronometro.start();
        algDij.executar(g, v1);
        Cronometro.stop();
        AlgoritmoDijkstraResultado resDij = algDij.getResultado();
        System.out.println("Resultado Dijkstra: " + resDij.getCusto(v2) + " Tempo: " + Cronometro.getDiferenca() + "ms");

        Cronometro.start();
        algFord.executar(g, v1);
        Cronometro.stop();
        AlgoritmoBellmanFordResultado resFord = algFord.getResultado();
        System.out.println("Resultado Bellman-Ford: " + resFord.getCusto(v2) + " Tempo: " + Cronometro.getDiferenca() + "ms");

        Cronometro.start();
        algFloyd.executar(g);
        Cronometro.stop();
        AlgoritmoFloydWarshallResultado resFloyd = algFloyd.getResultado();
        System.out.println("Resultado Floyd-Warshall: " + resFloyd.getCusto(v1, v2) + " Tempo: " + Cronometro.getDiferenca() + "ms");
    }

    public void testaPerformanceDijkstraFloydFordConsultas() {
        int vertices = 100;
        int consultas = 100;

        long tempoDijkstra = 0;
        long tempoFord = 0;
        long tempoFloyd = 0;

        GrafoDirigido g = null;
        try {
            g = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, true, false);
        } catch (Exception e) {}

        AlgoritmoFloydWarshall algFloyd = new AlgoritmoFloydWarshall();
        AlgoritmoDijkstra algDij = new AlgoritmoDijkstra();
        AlgoritmoBellmanFord algFord = new AlgoritmoBellmanFord();

        System.out.println("Vértices: " + vertices + " Consultas: " + consultas + " Arestas: " + g.getQtdeArestas());
        Vertice v1 = g.getVertice(0);

        Cronometro.start();
        algFord.executar(g, v1);
        Cronometro.stop();
        tempoFord += Cronometro.getDiferenca();

        Cronometro.start();
        algFloyd.executar(g);
        Cronometro.stop();
        tempoFloyd += Cronometro.getDiferenca();

        Cronometro.start();
        algDij.executar(g, v1);
        Cronometro.stop();
        tempoDijkstra += Cronometro.getDiferenca();

        System.out.println("Resultado Dijkstra: Tempo: " + tempoDijkstra + "ms");
        System.out.println("Resultado Bellman-Ford: Tempo: " + tempoFord + "ms");
        System.out.println("Resultado Floyd-Warshall: Tempo: " + tempoFloyd + "ms");
    }

    public void testaPerformanceDijkstraFloydFordConsultasVerticesAleatorios() {
        int vertices = 500;
        int consultas = 500;

        boolean calcular;

        long tempoDijkstra = 0;
        long tempoFord = 0;
        long tempoFloyd = 0;

        HashMap<Vertice, Boolean> calculados = new HashMap<Vertice, Boolean>();

        GrafoDirigido g = null;
        try {
            g = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, true, false);
        } catch (Exception e) {}

        for (int i = 0; i < vertices; i++) {
            Vertice v = g.getVertice(i);
            calculados.put(v, false);
        }

        AlgoritmoFloydWarshall algFloyd = new AlgoritmoFloydWarshall();
        AlgoritmoDijkstra algDij = new AlgoritmoDijkstra();
        AlgoritmoBellmanFord algFord = new AlgoritmoBellmanFord();

        System.out.println("Vértices 2: " + vertices + " Consultas 2: " + consultas + " Arestas: " + g.getQtdeArestas());

        Cronometro.start();
        algFloyd.executar(g);
        Cronometro.stop();
        tempoFloyd += Cronometro.getDiferenca();

        for (int i = consultas - 1; i >= 0; i--) {
            Vertice v1 = g.getVertice(i);

            //perde tempo....
            if (calculados.get(v1) == null || calculados.get(v1) == false) {
                calcular = true;
                calculados.put(v1, true);
            } else {
                calcular = false;
            }

            Cronometro.start();
            algDij.executar(g, v1);
            Cronometro.stop();
            tempoDijkstra += Cronometro.getDiferenca();

            Cronometro.start();
            algFord.executar(g, v1);
            Cronometro.stop();
            tempoFord += Cronometro.getDiferenca();
        }
        System.out.println("Resultado Dijkstra: Tempo: " + tempoDijkstra + "ms");
        System.out.println("Resultado Bellman-Ford: Tempo: " + tempoFord + "ms");
        System.out.println("Resultado Floyd-Warshall: Tempo: " + tempoFloyd + "ms");
    }

    public void testaPerformanceFordFulkerson() {
        int vertices = 100;
        GrafoDirigido g = GeradorGrafos.getGrafoSimplesDirigido(vertices);

        System.out.println("Vértices: " + vertices);

        Vertice v1 = g.getVertice(0);
        Vertice v2 = g.getVertice(vertices - 1);

        AlgoritmoFordFulkerson alg = new AlgoritmoFordFulkerson();

        Cronometro.start();
        alg.executar(g, v1, v2);
        Cronometro.stop();

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        System.out.println("Resultado DFS: " + res.getFluxoMaximo() + " Custo: " + res.getCustoFluxo() + " Tempo: " + Cronometro.getDiferenca() + "ms");

        AlgoritmoFordFulkersonDijkstra alg2 = new AlgoritmoFordFulkersonDijkstra();

        Cronometro.start();
        alg2.executar(g, v1, v2);
        Cronometro.stop();

        res = alg2.getResultado();
        System.out.println("Resultado DIJKSTRA: " + res.getFluxoMaximo() + " Custo: " + res.getCustoFluxo() + " Tempo: " + Cronometro.getDiferenca() + "ms");
    }

    public void testaPerformancePrimKruskal() {
        int vertices = 100;
        GrafoNaoDirigido g = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, true, true, false);

        System.out.println("Multigrafo: " + g.ehMultigrafo());

        System.out.println("Vértices: " + vertices);

        AlgoritmoPrim alg = new AlgoritmoPrim();

        Cronometro.start();
        alg.executar(g);
        Cronometro.stop();

        AlgoritmoPrimResultado res = alg.getResultado();
        System.out.println("Resultado Prim: " + res.getCustoTotal() + " Tempo: " + Cronometro.getDiferenca() + "ms");

        AlgoritmoKruskal alg2 = new AlgoritmoKruskal();

        Cronometro.start();
        alg2.executar(g);
        Cronometro.stop();

        AlgoritmoKruskalResultado res2 = alg2.getResultado();
        System.out.println("Resultado Kruskal: " + res2.getCustoTotal() + " Tempo: " + Cronometro.getDiferenca() + "ms");
    }

    public void testeGeracaoGrafosComparacao() throws Exception {
        int testes = 15;
        int minVertices = 1;
        int maxVertices = 40;

        for (int i = 1; i <= testes; i++) {
            int vertices = Comandos.getIntNumeroAleatorio(minVertices, maxVertices);
            System.out.println("Teste " + i + " Vertices " + vertices);
            System.out.println("DIRIGIDOS");

            GrafoDirigido dirigido = GeradorGrafos.getGrafoBipartidoCompletoDirigido(vertices, vertices);
            if (!dirigido.ehBipartidoCompleto()) {
                System.out.println("Erro DIRIGIDO Bipartido Completo: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoBipartidoDirigido(vertices, vertices);
            if (!dirigido.ehBipartido()) {
                System.out.println("Erro DIRIGIDO Bipartido: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoCompletoDirigido(vertices);
            if (!dirigido.ehCompleto()) {
                System.out.println("Erro DIRIGIDO Completo: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoConexoDirigido(vertices);
            if (!dirigido.ehConexo()) {
                System.out.println("Erro DIRIGIDO Conexo: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoDensoDirigido(vertices);
            if (!dirigido.ehDenso()) {
                System.out.println("Erro DIRIGIDO Denso: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            try {
                dirigido = GeradorGrafos.getGrafoDesconexoDirigido(vertices);
                if (!dirigido.ehDesconexo()) {
                    System.out.println("Erro DIRIGIDO Desconexo: Vertices: " + vertices);
                }

                dirigido = null;
                System.gc();
            } catch (Exception e) {}

            dirigido = GeradorGrafos.getGrafoEsparsoDirigido(vertices);
            if (!dirigido.ehEsparso()) {
                System.out.println("Erro DIRIGIDO Esparso: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoMultigrafoDirigido(vertices);
            if (!dirigido.ehMultigrafo()) {
                System.out.println("Erro DIRIGIDO Multigrafo: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoRegularDirigido(vertices, 3);
            if (!dirigido.ehRegular()) {
                System.out.println("Erro DIRIGIDO Regular 1: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();
            dirigido = GeradorGrafos.getGrafoRegularDirigido(vertices, 4);
            if (!dirigido.ehRegular()) {
                System.out.println("Erro DIRIGIDO Regular 2: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoSimplesDirigido(vertices);
            if (!dirigido.ehSimples()) {
                System.out.println("Erro DIRIGIDO Simples: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            try {
                dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, true, true);
                if (dirigido.ehDesconexo()) {
                    System.out.println("Erro DIRIGIDO RANDOMICO VVV: [Desconexo] Vertices: " + vertices);
                } else if (dirigido.ehMultigrafo()) {
                    System.out.println("Erro DIRIGIDO RANDOMICO VVV: [Multigrafo] Vertices: " + vertices);
                } else if (!dirigido.ehAciclico()) {
                    System.out.println("Erro DIRIGIDO RANDOMICO VVV: [Nao aciclico] Vertices: " + vertices);
                }
            } catch (Exception e) {

            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, false, true, true);
            if (dirigido.ehMultigrafo()) {
                System.out.println("Erro DIRIGIDO RANDOMICO FVV: [Multigrafo] Vertices: " + vertices);
            } else if (!dirigido.ehAciclico()) {
                System.out.println("Erro DIRIGIDO RANDOMICO FVV: [Nao aciclico] Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();
            try {
                dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, false, true);
                if (dirigido.ehDesconexo()) {
                    System.out.println("Erro DIRIGIDO RANDOMICO VFV: [Desconexo] Vertices: " + vertices);
                } else if (!dirigido.ehAciclico()) {
                    System.out.println("Erro DIRIGIDO RANDOMICO VFV: [Nao aciclico] Vertices: " + vertices);
                }
            } catch (Exception e) {

            }
            dirigido = null;
            System.gc();
            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, false, false, true);
            if (!dirigido.ehAciclico()) {
                System.out.println("Erro DIRIGIDO RANDOMICO FFV: [Nao aciclico] Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, true, false);
            if (dirigido.ehDesconexo()) {
                System.out.println("Erro DIRIGIDO RANDOMICO VVF: [Desconexo] Vertices: " + vertices);
            } else if (dirigido.ehMultigrafo()) {
                System.out.println("Erro DIRIGIDO RANDOMICO VVF: [Multigrafo] Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();
            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, false, true, false);
            if (dirigido.ehMultigrafo()) {
                System.out.println("Erro DIRIGIDO RANDOMICO FVF: [Multigrafo] Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();
            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, true, false, false);
            if (dirigido.ehDesconexo()) {
                System.out.println("Erro DIRIGIDO RANDOMICO VFF: [Desconexo] Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();
            dirigido = GeradorGrafos.getGrafoRandomicoDirigido(vertices, false, false, false);
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoCicloDirigido(vertices);
            if (!dirigido.ehCiclo()) {
                System.out.println("Erro DIRIGIDO Ciclo: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            dirigido = GeradorGrafos.getGrafoAciclicoDirigido(vertices);
            if (!dirigido.ehAciclico()) {
                System.out.println("Erro DIRIGIDO Aciclico: Vertices: " + vertices);
            }
            dirigido = null;
            System.gc();

            //NAO DIRIGIDOS
            System.out.println("NAO DIRIGIDOS");
            //System.out.println("ehBipartidoCompleto");
            GrafoNaoDirigido naoDirigido = GeradorGrafos.getGrafoBipartidoCompletoNaoDirigido(vertices, vertices);
            if (!naoDirigido.ehBipartidoCompleto()) {
                System.out.println("Erro NAO DIRIGIDO Bipartido Completo: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("ehBipartido");
            naoDirigido = GeradorGrafos.getGrafoBipartidoNaoDirigido(vertices, vertices);
            if (!naoDirigido.ehBipartido()) {
                System.out.println("Erro NAO DIRIGIDO Bipartido: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("ehCompleto");
            naoDirigido = GeradorGrafos.getGrafoCompletoNaoDirigido(vertices);
            if (!naoDirigido.ehCompleto()) {
                System.out.println("Erro NAO DIRIGIDO Completo: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("ehConexo");
            naoDirigido = GeradorGrafos.getGrafoConexoNaoDirigido(vertices);
            if (!naoDirigido.ehConexo()) {
                System.out.println("Erro NAO DIRIGIDO Conexo: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("ehDenso");
            naoDirigido = GeradorGrafos.getGrafoDensoNaoDirigido(vertices);
            if (!naoDirigido.ehDenso()) {
                System.out.println("Erro NAO DIRIGIDO Denso: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            try {
                //System.out.println("ehDesconexo");
                naoDirigido = GeradorGrafos.getGrafoDesconexoNaoDirigido(vertices);
                if (!naoDirigido.ehDesconexo()) {
                    System.out.println("Erro NAO DIRIGIDO Desconexo: Vertices: " + vertices);
                }
                naoDirigido = null;
                System.gc();

            } catch (Exception e) {}

            //System.out.println("ehEsparso");
            naoDirigido = GeradorGrafos.getGrafoEsparsoNaoDirigido(vertices);
            if (!naoDirigido.ehEsparso()) {
                System.out.println("Erro NAO DIRIGIDO Esparso: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("ehMultigrafo");
            naoDirigido = GeradorGrafos.getGrafoMultigrafoNaoDirigido(vertices);
            if (!naoDirigido.ehMultigrafo()) {
                System.out.println("Erro NAO DIRIGIDO Multigrafo: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            try {
                //System.out.println("ehRegular");
                naoDirigido = GeradorGrafos.getGrafoRegularNaoDirigido(vertices, 3);
                if (!naoDirigido.ehRegular()) {
                    System.out.println("Erro NAO DIRIGIDO Regular 1: Vertices: " + vertices);
                }
                naoDirigido = null;
                System.gc();

            } catch (Exception e) {}
            try {
                //System.out.println("ehRegular 2");
                naoDirigido = GeradorGrafos.getGrafoRegularNaoDirigido(vertices, 4);
                if (!naoDirigido.ehRegular()) {
                    System.out.println("Erro NAO DIRIGIDO Regular 2: Vertices: " + vertices);
                }
                naoDirigido = null;
                System.gc();

            } catch (Exception e) {}

            //System.out.println("ehSimples");
            naoDirigido = GeradorGrafos.getGrafoSimplesNaoDirigido(vertices);
            if (!naoDirigido.ehSimples()) {
                System.out.println("Erro NAO DIRIGIDO Simples: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            //System.out.println("getGrafoRandomicoNaoDirigido");
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, true, true, true);
            if (naoDirigido.ehDesconexo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VVV: [Desconexo] Vertices: " + vertices);
            } else if (naoDirigido.ehMultigrafo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VVV: [Multigrafo] Vertices: " + vertices);
            } else if (!naoDirigido.ehAciclico()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VVV: [Nao Aciclico] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, false, true, true);
            if (naoDirigido.ehMultigrafo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO FVV: [Multigrafo] Vertices: " + vertices);
            } else if (!naoDirigido.ehAciclico()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO FVV: [Nao Aciclico] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, true, false, true);
            if (naoDirigido.ehDesconexo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VFV: [Desconexo] Vertices: " + vertices);
            } else if (!naoDirigido.ehAciclico()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VFV: [Nao Aciclico] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, false, false, true);
            if (!naoDirigido.ehAciclico()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO FFV: [Nao aciclico] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, true, true, false);
            if (naoDirigido.ehDesconexo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VVF: [Desconexo] Vertices: " + vertices);
            } else if (naoDirigido.ehMultigrafo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VVF: [Multigrafo] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, false, true, false);
            if (naoDirigido.ehMultigrafo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO FVF: [Multigrafo] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, true, false, false);
            if (naoDirigido.ehDesconexo()) {
                System.out.println("Erro NAO DIRIGIDO RANDOMICO VFF: [Desconexo] Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();
            naoDirigido = GeradorGrafos.getGrafoRandomicoNaoDirigido(vertices, false, false, false);
            naoDirigido = null;
            System.gc();

            naoDirigido = GeradorGrafos.getGrafoCicloNaoDirigido(vertices);
            if (!naoDirigido.ehCiclo()) {
                System.out.println("Erro NAO DIRIGIDO Ciclo: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

            naoDirigido = GeradorGrafos.getGrafoAciclicoNaoDirigido(vertices);
            if (!naoDirigido.ehAciclico()) {
                System.out.println("Erro NAO DIRIGIDO Aciclico: Vertices: " + vertices);
            }
            naoDirigido = null;
            System.gc();

        }

        System.out.println("FIM TESTES");
    }

    public void testeArticulacoesNaoDirigido() throws Exception {
        System.out.println("Teste Articulacoes");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v3);
        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v3, v1);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v5);
        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v5, v6);
        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v6, v4);

        ArestaNaoDirigida a7 = new ArestaNaoDirigida(7, v3, v4);

        ArestaNaoDirigida a8 = new ArestaNaoDirigida(8, v6, v7);
        ArestaNaoDirigida a9 = new ArestaNaoDirigida(9, v2, v7);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        //g.addAresta(a9);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeArticulacaoNaoDirigido.xml");

        AlgoritmoArticulacoes alg = new AlgoritmoArticulacoes();
        alg.executar(g);

        AlgoritmoArticulacoesResultado res = alg.getResultado();

        System.out.println("Resultado: " + res.getQtdeArticulacoes());
        for (int i = 0; i < res.getQtdeArticulacoes(); i++) {
            System.out.print(res.getArticulacao(i).getDado() + " ");
        }
        System.out.println();
    }

    public void testeArticulacoesDirigido() throws Exception {
        System.out.println("Teste Articulacoes");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        ArestaDirigida a2 = new ArestaDirigida(2, v2, v3);
        ArestaDirigida a3 = new ArestaDirigida(3, v3, v1);

        ArestaDirigida a4 = new ArestaDirigida(4, v4, v5);
        ArestaDirigida a5 = new ArestaDirigida(5, v5, v6);
        ArestaDirigida a6 = new ArestaDirigida(6, v6, v4);

        ArestaDirigida a7 = new ArestaDirigida(7, v3, v4);
        ArestaDirigida a8 = new ArestaDirigida(8, v4, v3);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeArticulacaoDirigido.xml");

        AlgoritmoArticulacoes alg = new AlgoritmoArticulacoes();
        alg.executar(g);

        AlgoritmoArticulacoesResultado res = alg.getResultado();

        System.out.println("Resultado: " + res.getQtdeArticulacoes());
        for (int i = 0; i < res.getQtdeArticulacoes(); i++) {
            System.out.print(res.getArticulacao(i).getDado() + " ");
        }
        System.out.println();
    }

    public void testePontesNaoDirigido() throws Exception {
        System.out.println("Teste Pontes");

        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        Vertice v7 = new Vertice(7);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        v7.setDado("7");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);
        g.addVertice(v7);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v3);
        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v3, v1);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v5);
        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v5, v6);
        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v6, v4);

        ArestaNaoDirigida a7 = new ArestaNaoDirigida(7, v3, v4);

        ArestaNaoDirigida a8 = new ArestaNaoDirigida(8, v6, v7);

        ArestaNaoDirigida a9 = new ArestaNaoDirigida(9, v3, v4);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        //g.addAresta(a9);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testePonteNaoDirigido.xml");

        AlgoritmoPontes alg = new AlgoritmoPontes();
        alg.executar(g);

        AlgoritmoPontesResultado res = alg.getResultado();

        System.out.println("Resultado: " + res.getQtdePontes());
        for (int i = 0; i < res.getQtdePontes(); i++) {
            ArestaNaoDirigida a = (ArestaNaoDirigida) res.getPonte(i);

            System.out.println(a.getVi().getDado() + " <-> " + a.getVj(a.getVi()).getDado());
        }
    }

    public void testePontesDirigido() throws Exception {
        System.out.println("Teste Pontes");

        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        Vertice v6 = new Vertice(6);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        v4.setDado("4");
        v5.setDado("5");
        v6.setDado("6");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);
        g.addVertice(v4);
        g.addVertice(v5);
        g.addVertice(v6);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        ArestaDirigida a2 = new ArestaDirigida(2, v2, v3);
        ArestaDirigida a3 = new ArestaDirigida(3, v3, v1);

        ArestaDirigida a4 = new ArestaDirigida(4, v4, v5);
        ArestaDirigida a5 = new ArestaDirigida(5, v5, v6);
        ArestaDirigida a6 = new ArestaDirigida(6, v6, v4);

        ArestaDirigida a7 = new ArestaDirigida(7, v3, v4);
        ArestaDirigida a8 = new ArestaDirigida(8, v4, v3);
        ArestaDirigida a9 = new ArestaDirigida(9, v3, v4);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);
        g.addAresta(a7);
        g.addAresta(a8);
        g.addAresta(a9);

        Persistencia.salvarGrafo(g, "/home/xsplyter/testePonteDirigido.xml");

        AlgoritmoPontes alg = new AlgoritmoPontes();
        alg.executar(g);

        AlgoritmoPontesResultado res = alg.getResultado();

        System.out.println("Resultado: " + res.getQtdePontes());
        for (int i = 0; i < res.getQtdePontes(); i++) {
            ArestaDirigida a = (ArestaDirigida) res.getPonte(i);

            System.out.println(a.getVi().getDado() + " -> " + a.getVj().getDado());
        }
    }

    public void testeAciclicoDirigido() throws IOException {
        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        ArestaDirigida a2 = new ArestaDirigida(2, v2, v1);

        try {
            g.addVertice(v1);
            g.addVertice(v2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            g.addAresta(a1);
            g.addAresta(a2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeAciclicoDirigido.xml");

        System.out.println(g.ehAciclico());
    }

    public void testeAciclicoNaoDirigido() throws IOException {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v1, v3);
        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v4);
        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v3);

        try {
            g.addVertice(v1);
            g.addVertice(v2);
            g.addVertice(v3);
            g.addVertice(v4);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            g.addAresta(a1);
            g.addAresta(a2);
            g.addAresta(a3);
            g.addAresta(a4);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Persistencia.salvarGrafo(g, "/home/xsplyter/testeAciclicoNaoDirigido.xml");

        System.out.println(g.ehAciclico());
    }

    public void testeSimplificaGrafoDirigido() throws Exception {
        GrafoDirigido g = new GrafoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaDirigida a1 = new ArestaDirigida(1, v1, v2);
        a1.setValor(10);
        a1.setCapacidade(2);

        ArestaDirigida a2 = new ArestaDirigida(2, v3, v1);
        a2.setValor(20);
        a2.setCapacidade(2);

        ArestaDirigida a3 = new ArestaDirigida(3, v1, v3);
        a3.setValor(15);
        a3.setCapacidade(3);

        ArestaDirigida a4 = new ArestaDirigida(4, v1, v3);
        a4.setValor(30);
        a4.setCapacidade(4);

        ArestaDirigida a5 = new ArestaDirigida(5, v2, v3);
        a5.setValor(12);
        a5.setCapacidade(1);

        ArestaDirigida a6 = new ArestaDirigida(6, v2, v2);
        a6.setValor(32);
        a6.setCapacidade(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        GrafoDirigido h = g.getGrafoSimplificado(false);
        GrafoDirigido i = g.getGrafoSimplificado(true);

        System.out.println("" + h.ehSimples());
        System.out.println("" + i.ehSimples());

    }

    public void testeSimplificaGrafoNaoDirigido() throws Exception {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Vertice v1 = new Vertice(1);
        Vertice v2 = new Vertice(2);
        Vertice v3 = new Vertice(3);
        v1.setDado("1");
        v2.setDado("2");
        v3.setDado("3");
        g.addVertice(v1);
        g.addVertice(v2);
        g.addVertice(v3);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        a1.setValor(10);
        a1.setCapacidade(2);

        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v3, v1);
        a2.setValor(20);
        a2.setCapacidade(2);

        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v1, v3);
        a3.setValor(15);
        a3.setCapacidade(3);

        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v1, v3);
        a4.setValor(30);
        a4.setCapacidade(4);

        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v2, v3);
        a5.setValor(12);
        a5.setCapacidade(1);

        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v2, v2);
        a6.setValor(32);
        a6.setCapacidade(5);

        g.addAresta(a1);
        g.addAresta(a2);
        g.addAresta(a3);
        g.addAresta(a4);
        g.addAresta(a5);
        g.addAresta(a6);

        GrafoNaoDirigido h = g.getGrafoSimplificado(false);
        GrafoNaoDirigido i = g.getGrafoSimplificado(true);

        System.out.println("" + h.ehSimples());
        System.out.println("" + i.ehSimples());

    }

    public void testeArestas() throws Exception {
        GrafoDirigido g1 = new GrafoDirigido();

        Vertice v1g1 = new Vertice(1);
        Vertice v2g1 = new Vertice(2);

        ArestaDirigida a1g1 = new ArestaDirigida(1, v1g1, v2g1);
        ArestaDirigida a2g1 = new ArestaDirigida(2, v1g1, v2g1);

        g1.addVertice(v1g1);
        g1.addVertice(v2g1);

        g1.addAresta(a1g1);
        g1.addAresta(a2g1);

        System.out.println("Paralela " + g1.existeParalelismo(v1g1, v2g1));
        System.out.println("Anti-Paralela " + g1.existeAntiParalelismo(v1g1, v2g1));

        g1.delAresta(2);

        System.out.println("Paralela " + g1.existeParalelismo(v1g1, v2g1));
        System.out.println("Anti-Paralela " + g1.existeAntiParalelismo(v1g1, v2g1));

        ArestaDirigida a3g1 = new ArestaDirigida(2, v2g1, v1g1);
        g1.addAresta(a3g1);

        System.out.println("Paralela " + g1.existeParalelismo(v1g1, v2g1));
        System.out.println("Anti-Paralela " + g1.existeAntiParalelismo(v1g1, v2g1));

        GrafoNaoDirigido g2 = new GrafoNaoDirigido();

        Vertice v1g2 = new Vertice(1);
        Vertice v2g2 = new Vertice(2);

        ArestaNaoDirigida a1g2 = new ArestaNaoDirigida(1, v1g2, v2g2);
        ArestaNaoDirigida a2g2 = new ArestaNaoDirigida(2, v1g2, v2g2);

        g2.addVertice(v1g2);
        g2.addVertice(v2g2);

        g2.addAresta(a1g2);
        g2.addAresta(a2g2);

        System.out.println("Paralela " + g2.existeParalelismo(v1g2, v2g2));

        g2.delAresta(2);

        System.out.println("Paralela " + g2.existeParalelismo(v1g2, v2g2));

        ArestaNaoDirigida a3g2 = new ArestaNaoDirigida(2, v2g2, v1g2);
        g2.addAresta(a3g2);

        System.out.println("Paralela " + g2.existeParalelismo(v1g2, v2g2));
    }

    public static void main(String args[]) {

        Main teste = new Main();

        for (int i = 0; i < 50; i++) {
            //System.out.println(Comandos.getIntNumeroAleatorio(1, 2));
            //System.out.println(Comandos.getDoubleNumeroAleatorio(1.0, 1.2));
        }

        try {

            //teste.testeDijkstra();
            //teste.testeDijkstra2();
            //teste.testeDijkstra3();
            //teste.testeBellmanFord();
            //teste.testeBellmanFord2();
            //teste.testeBellmanFord3();
            //teste.testeFloydWarshall();
            //teste.testeFloydWarshall2();
            //teste.testeFloydWarshall3();

            //teste.testeBuscaProfundidade();
            //teste.testeBuscaLargura();
            //teste.testeOrdenacaoTopologica();

            //teste.testePrioridade();
            //teste.testePrim();
            //teste.testeKruskal();
            //teste.testeHopcroftTarjan();
            //teste.testeHopcroftTarjan2();

            //teste.testeFordFulkerson();
            //teste.testeFordFulkerson2();

            //teste.testePropriedadesNulo();
            //teste.testePropriedadesTrivial();
            //teste.testePropriedadesCompleto();
            //teste.testePropriedadesConexo();
            //teste.testePropriedadesConexoDirigido();

            //teste.testePropriedadesBipartido();
            //teste.testePropriedadesBipartidoCompleto();

            //teste.testeOperacoesNaoDirigido();
            //teste.testeOperacoesNaoDirigido2();
            //teste.testeOperacoesDirigido();
            //teste.testeOperacoesDirigido2();

            //teste.testeGeracaoGrafos();
            //teste.testePersistenciaNaoDirigido();
            //teste.testePersistenciaDirigido();

            //teste.testeOperacoesNaoDirigido3();

            //teste.testePropriedadesBipartido2();
            //teste.testeFordFulkersonDijkstra();
            //teste.testeCiclo();

            //teste.testaPerformanceFordFulkerson();
            //teste.testaPerformanceDijkstraFloydFord();

            teste.testaPerformanceDijkstraFloydFord();
            //teste.testaPerformanceDijkstraFloydFordConsultas();
            //teste.testaPerformanceDijkstraFloydFordConsultasVerticesAleatorios();

            //teste.testaPerformancePrimKruskal();

            //teste.testeGeracaoGrafosComparacao(); 

            //teste.testeArticulacoesNaoDirigido(); 
            //teste.testeArticulacoesDirigido(); 

            //teste.testePontesNaoDirigido(); 
            //teste.testePontesDirigido(); 

            //teste.testeAciclicoDirigido();
            //teste.testeAciclicoNaoDirigido();

            //teste.testeSimplificaGrafoDirigido();
            //teste.testeSimplificaGrafoNaoDirigido();

            //teste.testeArestas();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
