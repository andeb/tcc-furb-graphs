package aplicacao;

import base.Vertice;

public class VerticeGeometrico extends Vertice {

    private int x;
    private int y;
    private int raio;

    public VerticeGeometrico(int id) {
        super(id);
    }

    public VerticeGeometrico(int id, int x, int y, int raio) {
        super(id);
        this.x = x;
        this.y = y;
        this.raio = raio;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

}
