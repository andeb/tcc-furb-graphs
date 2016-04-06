/*
 * Created on 11/11/2014.
 *
 * Copyright 2014 Senior Ltda. All rights reserved.
 */
package persistencia;

import java.util.List;

import aplicacao.GraphViewer.ArestaVisual;
import aplicacao.GraphViewer.VerticeVisual;

public class DefinicaoGrafoVisual {

    public List<VerticeVisual> vertices;
    public List<ArestaVisual> arestas;
    public int ultimoIdVertice;

    public DefinicaoGrafoVisual(List<VerticeVisual> nodes, List<ArestaVisual> edges, int ultimoIdVertice) {
        this.vertices = nodes;
        this.arestas = edges;
        this.ultimoIdVertice = ultimoIdVertice;
    }

}