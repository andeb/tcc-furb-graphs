package base;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Vertice
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class Vertice implements Cloneable {

    private final int id;
    private Object dado;
    private Color cor = new Color(0, 0, 0);
    private final ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private final HashMap<Integer, Aresta> idArestas = new HashMap<Integer, Aresta>();

    /**
     * Construtor da classe Vertice
     * 
     * @param id Identificador �nico do v�rtice
     * @throws Exception
     */
    public Vertice(int id) {
        this.id = id;
        dado = id;
    }

    /**
     * Retorna o identificador �nico do v�rtice
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o dado do v�rtice
     * 
     * @return dado
     */
    public Object getDado() {
        return dado;
    }

    /**
     * Atribui um dado qualquer ao v�rtice
     * 
     * @param dado
     */
    public void setDado(Object dado) {
        this.dado = dado;
    }

    /**
     * Retorna a cor do v�rtice
     * 
     * @return cor
     */
    public Color getCor() {
        return cor;
    }

    /**
     * Atribui uma cor ao v�rtice
     * 
     * @param cor
     */
    public void setCor(Color cor) {
        this.cor = cor;
    }

    /**
     * Adiciona uma aresta ao v�rtice
     * 
     * @param a
     *            Aresta
     */
    public void addAresta(Aresta a) {
        idArestas.put(a.getId(), a);
        arestas.add(a);
    }

    /**
     * Apaga uma aresta do v�rtice
     * 
     * @param a
     *            Aresta
     */
    public void delAresta(Aresta a) {
        while (arestas.contains(a)) {
            arestas.remove(a);
        }
        idArestas.remove(a.getId());
    }

    /**
     * Retorna uma aresta pela sua posi��o
     * 
     * @param i
     *            Posi��o
     * @return Aresta
     */
    public Aresta getAresta(int i) {
        return arestas.get(i);
    }

    /**
     * Retorna a quantidade de arestas
     * 
     * @return grau
     */
    public int getQtdeArestas() {
        return arestas.size();
    }

    /**
     * Limpa as arestas do v�rtice
     */
    public void limpaArestas() {
        arestas.clear();
        idArestas.clear();
    }

    /**
     * Compara o v�rtice
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return getId() == ((Vertice) obj).getId();
    }

    /**
     * Retorna uma String descrevendo o objeto v�rtice
     */
    @Override
    public String toString() {
        return id + " D: " + dado;
    }

    /**
     * Retorna um clone do vertice
     * 
     * @return Vertice
     */
    @Override
    public Vertice clone() {
        Vertice v = null;
        try {
            v = new Vertice(id);
        } catch (Exception e) {} // FIXME
        v.setCor(cor);
        v.setDado(dado);

        return v;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
