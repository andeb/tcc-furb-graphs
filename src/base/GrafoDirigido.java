package base;

import java.util.ArrayList;
import java.util.HashMap;

import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import auxiliar.Constante;
import auxiliar.Pair;

/**
 * Classe GrafoDirigido
 *
 * @author Maicon Rafael Zatelli
 *
 */
public class GrafoDirigido extends Grafo {

    private final ArrayList<ArestaDirigida> arestas = new ArrayList<ArestaDirigida>();

    /**
     * Adiciona uma aresta ao grafo
     *
     * @param a
     *            Aresta
     * @throws Exception
     *             caso existe uma aresta com esta identificação
     */
    public void addAresta(ArestaDirigida a) throws Exception {
        if (super.getArestaById(a.getId()) != null) {
            throw new Exception("Existe uma aresta com este identificador");
        }

        a.setGrafo(this);
        idArestas.put(a.getId(), a);

        super.addConexao(a.getVi(), a.getVj());
        a.getVi().addAresta(a);
        arestas.add(a);
    }

    /**
     * Retorna a quantidade de arestas do grafo incluindo as geradas pelo próprio.
     *
     * @return Quantidade de arestas
     */
    @Override
    public int getQtdeArestas() {
        return arestas.size();
    }

    /**
     * Retorna uma aresta por sua posição
     *
     * @param i
     *            Posição
     * @return ArestaDirigida
     */
    @Override
    public ArestaDirigida getAresta(int i) {
        return arestas.get(i);
    }

    /**
     * Retorna se o grafo é regular.<br>
     * Um grafo é regular se todos os vértices possuem o mesmo grau.
     *
     * @return true ou false
     */
    @Override
    public boolean ehRegular() {
        int grau = getGrauEntrada(getUmVertice());
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            if (grau != getGrauEntrada(getVertice(i))) {
                return false;
            }
        }

        grau = getGrauSaida(getUmVertice());
        for (int i = 0; i < tamanhoGrafo; i++) {
            if (grau != getGrauSaida(getVertice(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna um grafo transposto a partir do grafo atual.<br>
     * Um grafo transposto é um grafo gerado a partir da inversão de todas as arestas.
     *
     * @return GrafoDirigido transposto
     */
    public GrafoDirigido getTransposto() {
        GrafoDirigido g = new GrafoDirigido();

        int tamanhoGrafo = getTamanho();
        HashMap<Vertice, Vertice> vertices = new HashMap<Vertice, Vertice>();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice vAnt = getVertice(i);
            Vertice vNovo = new Vertice(vAnt.getId());
            vNovo.setDado(vAnt.getDado());

            // adiciona vertices ao novo grafo
            try {
                g.addVertice(vNovo);
            } catch (Exception e) {}
            vertices.put(vAnt, vNovo);
        }

        int qtdeArestas = getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida a = getAresta(i);

            // cria aresta invertida
            ArestaDirigida aNova = new ArestaDirigida(a.getId(), vertices.get(a.getVj()), vertices.get(a.getVi()));
            try {
                g.addAresta(aNova);
            } catch (Exception e) {}
        }

        return g;
    }

    /**
     * Retorna se o grafo é conexo.<br>
     * Um grafo é conexo se for possível visitar qualquer vértice, partindo de um outro e passando por arestas.<br>
     * Em grafos dirigidos é gerado um grafo subjacente e então é testado se ele é conexo. fortemente conexa.
     *
     * @return true ou false
     */
    @Override
    public boolean ehConexo() {
        GrafoNaoDirigido g = getGrafoSubjacente();

        return g.ehConexo();
    }

    /**
     * Retorna se o grafo é conexo.<br>
     * Um grafo dirigido é fortemente conexo se for possível visitar qualquer vértice, partindo de um outro e passando
     * por arestas.<br>
     * fortemente conexa.
     *
     * @return true ou false
     */
    public boolean ehFortementeConexo() {
        AlgoritmoBuscaLargura alg = new AlgoritmoBuscaLargura();

        alg.executar(this, getUmVertice());

        AlgoritmoBuscaLarguraResultado res = alg.getResultado();

        int tamanhoGrafo = getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            if (res.getVisitado(v) != true) {
                return false;
            }
        }

        // executa no grafo transposto
        Grafo g = getTransposto();
        alg = new AlgoritmoBuscaLargura();

        alg.executar(g, g.getUmVertice());

        res = alg.getResultado();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);
            if (res.getVisitado(v) != true) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retorna um grafo subjacente do grafo atual.<br>
     * Um grafo subjacente é um grafo não dirigido gerado a partir de um grafo dirigido.
     *
     * @return GrafoNaoDirigido
     */
    public GrafoNaoDirigido getGrafoSubjacente() {
        // cria um grafo não dirigido
        GrafoNaoDirigido g = new GrafoNaoDirigido();
        int tamanhoGrafo = getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i).clone();
            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        int qtdeArestas = getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida ant = getAresta(i);
            ArestaNaoDirigida a = new ArestaNaoDirigida(ant.getId(), g.getVerticeById(ant.getVi().getId()), g.getVerticeById(ant.getVj().getId()));
            a.setCapacidade(ant.getCapacidade());
            a.setDado(ant.getDado());
            a.setGrafo(g);
            a.setValor(ant.getValor());

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        }

        return g;
    }

    @Override
    protected HashMap<Vertice, Integer> calcBipartido() {
        // cria um grafo não dirigido
        GrafoNaoDirigido g = getGrafoSubjacente();

        HashMap<Vertice, Integer> corTemp = g.calcBipartido();
        if (corTemp == null) {
            return null;
        }

        HashMap<Vertice, Integer> cor = new HashMap<Vertice, Integer>();

        for (int i = 0; i < getTamanho(); i++) {
            Vertice vTemp = g.getVertice(i);
            Vertice v = getVerticeById(vTemp.getId());

            cor.put(v, corTemp.get(vTemp));
        }

        return cor;
    }

    /**
     * Apaga uma aresta por seu identificador
     *
     * @param id
     *            Identificador da aresta
     * @return quantidade de arestas apagadas
     */
    @Override
    public int delAresta(int id) {
        ArestaDirigida a = (ArestaDirigida) getArestaById(id);

        // remove todas as arestas do mesmo id
        int qtde = 0;
        while (arestas.contains(a)) {
            arestas.remove(a);
            qtde++;
        }

        // apaga aresta dos id
        idArestas.remove(a.getId());

        // apaga a aresta dos vertices
        a.getVi().delAresta(a);

        // verifica se encontra ainda alguma aresta u -> v
        boolean achou = false;
        int grauVertice = getGrauSaida(a.getVi());
        for (int i = 0; i < grauVertice; i++) {
            ArestaDirigida b = (ArestaDirigida) a.getVi().getAresta(i);

            if (b.getVj().equals(a.getVj())) {
                achou = true;
                break;
            }
        }
        if (!achou) {
            delConexao(a.getVi(), a.getVj());
        }

        return qtde;
    }

    /**
     * Retorna o tipo do grafo
     *
     * @return 1
     */
    @Override
    public int getTipo() {
        return 1;
    }

    /**
     * Base é conjunto de vértices que não podem ser alcançados a partir<br>
     * de outros vértices.
     *
     * @return Vértices de grau de entrada 0
     */
    public ArrayList<Vertice> getBase() {
        ArrayList<Vertice> temp = new ArrayList<Vertice>();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);

            if (getGrauEntrada(v) == 0) {
                temp.add(v);
            }
        }

        return temp;
    }

    /**
     * Antibase é conjunto de vértices que não alcançam outros vértices<br>
     *
     * @return Vértices de grau de saída 0
     */
    public ArrayList<Vertice> getAntibase() {
        ArrayList<Vertice> temp = new ArrayList<Vertice>();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);

            if (getGrauSaida(v) == 0) {
                temp.add(v);
            }
        }

        return temp;
    }

    /**
     * Retorna se o grafo é ciclo.<br>
     * Um grafo é ciclo quando possui um único vértice com um laço ou<br>
     * é um grafo simples e conexo onde todos os vértices e arestas formam um circuito.<br>
     *
     * @return true ou false
     */
    @Override
    public boolean ehCiclo() {
        if (ehRegular() && getGrauSaida(getUmVertice()) == 1 && getGrauEntrada(getUmVertice()) == 1 && ehConexo()) {
            return true;
        }
        return false;
    }

    private boolean dfsCiclo(HashMap<Vertice, Integer> cor, Vertice u) {
        cor.put(u, 2);

        ArrayList<Vertice> adjU = getAdjacentes(u);
        for (Vertice v : adjU) {
            if (cor.get(v) == 1) {
                if (dfsCiclo(cor, v)) {
                    return true;
                }
            } else if (cor.get(v) == 2) {
                return true;
            }
        }

        cor.put(u, 3);
        return false;
    }

    /**
     * Retorna se o grafo é acíclico.<br>
     * Um grafo é acíclico se não possui nenhum ciclo
     *
     * @return true ou false
     */
    @Override
    public boolean ehAciclico() {
        HashMap<Vertice, Integer> cor = new HashMap<Vertice, Integer>();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            cor.put(v, 1);
        }

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            if (cor.get(v) == 1) {
                if (dfsCiclo(cor, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retorna se o grafo é Ã¡rvore.<br>
     * Um grafo é uma Ã¡rvore se não possui nenhum ciclo e é conexo
     *
     * @return true ou false
     */
    public boolean ehArborescencia() {
        if (ehAciclico()) {
            Vertice grauEntrada0 = null;
            int tamanhoGrafo = getTamanho();

            for (int i = 0; i < tamanhoGrafo; i++) {
                if (getGrauEntrada(getVertice(i)) > 1) {
                    return false;
                } else if (getGrauEntrada(getVertice(i)) == 0) {
                    grauEntrada0 = getVertice(i);
                }
            }

            AlgoritmoBuscaLargura bfs = new AlgoritmoBuscaLargura();
            bfs.executar(this, grauEntrada0);

            AlgoritmoBuscaLarguraResultado res = bfs.getResultado();

            if (res.getQtdeAtingidos() == tamanhoGrafo) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna se o grafo é floresta.<br>
     * Um grafo é uma floresta se também é acíclico e o grau de entrada <br>
     * do vértice é no mÃ¡ximo 1
     *
     * @return true ou false
     */
    @Override
    public boolean ehFloresta() {
        if (ehAciclico()) {
            int tamanhoGrafo = getTamanho();

            for (int i = 0; i < tamanhoGrafo; i++) {
                if (getGrauEntrada(getVertice(i)) > 1) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Retorna o grau de entrada do vértice
     *
     * @param v
     *            Vertice
     * @return grau de entrada
     */
    public int getGrauEntrada(Vertice v) {
        int temp = 0;
        for (ArestaDirigida a : arestas) {
            if (a.getVj().equals(v)) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * Retorna o grau de saída do vértice
     *
     * @param v
     *            Vertice
     * @return grau de saída
     */
    public int getGrauSaida(Vertice v) {
        return v.getQtdeArestas();
    }

    /**
     * Retorna uma aresta por vértice inicial e final
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return Aresta ou null
     */
    @Override
    public ArestaDirigida getArestaByVertices(Vertice u, Vertice v) {
        int qtdeArestas = getGrauSaida(u);
        for (int j = 0; j < qtdeArestas; j++) {
            ArestaDirigida a = (ArestaDirigida) u.getAresta(j);

            if (a.getVj().equals(v)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do menor custo
     */
    @Override
    public double getCustoMinimo(Vertice u, Vertice v) {
        double menor = Constante.INF;
        int i = getGrauSaida(u) - 1;
        for (; i >= 0; i--) {
            ArestaDirigida temp = (ArestaDirigida) u.getAresta(i);
            if (temp.getVj().equals(v)) {
                if (temp.getValor() < menor) {
                    menor = temp.getValor();
                }
            }
        }
        return menor;
    }

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return ArestaDirigida do menor custo
     */
    @Override
    public ArestaDirigida getArestaCustoMinimo(Vertice u, Vertice v) {
        double menor = Constante.INF;
        ArestaDirigida aMenor = null;

        int i = getGrauSaida(u) - 1;
        for (; i >= 0; i--) {
            ArestaDirigida temp = (ArestaDirigida) u.getAresta(i);
            if (temp.getVj().equals(v)) {
                if (temp.getValor() < menor) {
                    menor = temp.getValor();
                    aMenor = temp;
                }
            }
        }
        return aMenor;
    }

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do maior custo
     */
    @Override
    public double getCustoMaximo(Vertice u, Vertice v) {
        double maior = -Constante.INF;
        int i = getGrauSaida(u) - 1;
        for (; i >= 0; i--) {
            ArestaDirigida temp = (ArestaDirigida) u.getAresta(i);
            if (temp.getVj().equals(v)) {
                if (temp.getValor() > maior) {
                    maior = temp.getValor();
                }
            }
        }
        return maior;
    }

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return ArestaDirigida do maior custo
     */
    @Override
    public ArestaDirigida getArestaCustoMaximo(Vertice u, Vertice v) {
        double maior = Constante.INF;
        ArestaDirigida aMaior = null;

        int i = getGrauSaida(u) - 1;
        for (; i >= 0; i--) {
            ArestaDirigida temp = (ArestaDirigida) u.getAresta(i);
            if (temp.getVj().equals(v)) {
                if (temp.getValor() > maior) {
                    maior = temp.getValor();
                    aMaior = temp;
                }
            }
        }
        return aMaior;
    }

    /**
     * Retorna se existe aresta paralela a u -> v.<br>
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return true ou false
     */
    @Override
    public boolean existeParalelismo(Vertice u, Vertice v) {
        HashMap<Vertice, Boolean> atingiu = new HashMap<Vertice, Boolean>();
        int qtdeArestas = getGrauSaida(u);
        for (int j = 0; j < qtdeArestas; j++) {
            ArestaDirigida a = (ArestaDirigida) u.getAresta(j);

            if (atingiu.get(a.getVj()) != null && v.equals(a.getVj())) {
                return true;
            }

            atingiu.put(a.getVj(), true);
        }

        return false;
    }

    /**
     * Retorna se existe aresta anti-paralela a u -> v.<br>
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return true ou false
     */
    public boolean existeAntiParalelismo(Vertice u, Vertice v) {
        int qtdeArestas = getGrauSaida(v);
        for (int j = 0; j < qtdeArestas; j++) {
            ArestaDirigida a = (ArestaDirigida) v.getAresta(j);

            if (u.equals(a.getVj())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retorna se o grafo é multigrafo.<br>
     * Um grafo é multigrafo se possui arestas paralelas ou laços.
     *
     * @return true ou false
     */
    @Override
    public boolean ehMultigrafo() {
        int tamanhoGrafo = getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = getVertice(i);

            HashMap<Vertice, Boolean> atingiu = new HashMap<Vertice, Boolean>();
            int qtdeArestas = getGrauSaida(u);
            for (int j = 0; j < qtdeArestas; j++) {
                ArestaDirigida a = (ArestaDirigida) u.getAresta(j);

                if (atingiu.get(a.getVj()) != null) {
                    return true;
                }
                if (a.getVj().equals(u)) {
                    return true;
                }

                atingiu.put(a.getVj(), true);
            }
        }

        return false;
    }

    /**
     * Retorna se o grafo é completo.<br>
     * Um grafo é completo se ele é simples e existe uma aresta ligando cada par de vértices distintos.
     *
     * @return true ou false
     */
    @Override
    public boolean ehCompleto() {
        if (ehMultigrafo()) {
            return false;
        }

        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = getVertice(i);

            // o grau de saida dos vertices eh o numero de vertices -1
            if (getGrauSaida(u) != tamanhoGrafo - 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retorna a lista de vértices adjacentes ao vértice
     *
     * @param v
     *            Vertice
     * @return Conjunto de vértices adjacentes
     */
    @Override
    public ArrayList<Vertice> getAdjacentes(Vertice v) {
        ArrayList<Vertice> adj = new ArrayList<Vertice>();
        HashMap<Vertice, Boolean> visitado = new HashMap<Vertice, Boolean>();

        int qtdeArestas = v.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida a = (ArestaDirigida) v.getAresta(i);
            if (visitado.get(a.getVj()) == null) {
                visitado.put(a.getVj(), true);
                adj.add(a.getVj());
            }
        }
        return adj;
    }

    /**
     * Apaga um vértice por seu identificador
     *
     * @param id
     *            Identificador do vértice
     */
    @Override
    public void delVertice(int id) {
        Vertice v = getVerticeById(id);

        while (vertices.contains(v)) {
            vertices.remove(v);
        }
        idVertices.remove(v.getId());

        int qtdeArestas = getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida a = getAresta(i);
            int del = 0;
            if (a.getVi().equals(v) || a.getVj().equals(v)) {
                del = delAresta(a.getId());
                qtdeArestas -= del;
            }
            i -= del;
            if (i < -1) {
                i = -1;
            }
        }
        v.limpaArestas();
    }

    /**
     * Retorna um grafo simples
     *
     * @param pblnMaximo
     *            O peso de uma aresta é o peso da aresta paralela de maior valor
     * @return Grafo
     */
    @Override
    public GrafoDirigido getGrafoSimplificado(boolean pblnMaximo) {
        GrafoDirigido g = new GrafoDirigido();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            try {
                g.addVertice(getVertice(i).clone());
            } catch (Exception e) {}
        }

        HashMap<Pair, Integer> pares = new HashMap<Pair, Integer>();
        int qtdeArestas = getQtdeArestas();
        int cAresta = 1;
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaDirigida aAnt = getAresta(i);

            Vertice viNovo = g.getVerticeById(aAnt.getVi().getId());
            Vertice vjNovo = g.getVerticeById(aAnt.getVj().getId());

            if (viNovo.equals(vjNovo)) {
                continue;
            }

            Pair par = new Pair(viNovo, vjNovo);
            if (pares.get(par) != null) {
                int idAresta = pares.get(par);

                Aresta aAtual = g.getArestaById(idAresta);

                aAtual.setCapacidade(aAtual.getCapacidade() + aAnt.getCapacidade());
                if (pblnMaximo) {
                    if (aAnt.getValor() > aAtual.getValor()) {
                        aAtual.setValor(aAnt.getValor());
                    }
                } else {
                    if (aAnt.getValor() < aAtual.getValor()) {
                        aAtual.setValor(aAnt.getValor());
                    }
                }

            } else {
                ArestaDirigida aNova = new ArestaDirigida(cAresta, viNovo, vjNovo);

                aNova.setCapacidade(aAnt.getCapacidade());
                aNova.setDado(aAnt.getDado());
                aNova.setValor(aAnt.getValor());

                try {
                    g.addAresta(aNova);
                    pares.put(par, cAresta);
                } catch (Exception e) {}

                cAresta++;
            }
        }

        return g;
    }

    @Override
    public GrafoDirigido clone() {
        return (GrafoDirigido) super.clone();
    }

}
