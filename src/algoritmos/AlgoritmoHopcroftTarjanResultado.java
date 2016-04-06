package algoritmos;

import java.util.ArrayList;

import base.Vertice;

/**
 * Classe AlgoritmoHopcroftTarjanResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoHopcroftTarjanResultado {

    private final ArrayList<ArrayList<Vertice>> componentes = new ArrayList<ArrayList<Vertice>>();

    /**
     * Adiciona uma componente fortemente conexa
     * 
     * @param componente Componente fortemente conexa
     */
    public void addComponente(ArrayList<Vertice> componente) {
        componentes.add(componente);
    }

    /**
     * Retorna a quantidade de componentes fortemente conexas
     * 
     * @return Quantidade
     */
    public int getQtdeComponentes() {
        return componentes.size();
    }

    /**
     * Retorna o conjunto de componentes fortemente conexas
     * 
     * @return Conjunto de componentes fortemente conexas
     */
    public ArrayList<ArrayList<Vertice>> getComponentes() {
        return componentes;
    }

    /**
     * Retorna a componente conexa pela sua posição
     * 
     * @param i Posição
     * @return Componente fortemente conexa
     */
    public ArrayList<Vertice> getComponente(int i) {
        return componentes.get(i);
    }

    /**
     * Retorna se os dois vértices informados estão na mesma componente fortemente conexa
     * 
     * @param v1 Vértice 1
     * @param v2 Vértice 2
     * @return true ou false
     */
    public boolean mesmaComponente(Vertice v1, Vertice v2) {
        for (ArrayList<Vertice> componente : componentes) {
            if (componente.contains(v1)) {
                if (componente.contains(v2)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
