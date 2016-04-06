package algoritmos;

/**
 * @author Anderson.Borba
 */
public class AlgoritmoHipercuboResultado {

    private boolean ehHipercubo;

    public AlgoritmoHipercuboResultado(boolean ehHipercubo) {
        this.ehHipercubo = ehHipercubo;
    }

    public static AlgoritmoHipercuboResultado FALSE = new AlgoritmoHipercuboResultado(false);
    public static AlgoritmoHipercuboResultado TRUE = new AlgoritmoHipercuboResultado(true);

    /**
     * @return the ehHipercubo
     */
    public boolean isEhHipercubo() {
        return ehHipercubo;
    }

}
