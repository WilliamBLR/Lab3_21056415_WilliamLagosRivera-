/**
 * representa una carta comun del juego pokemon tcg
 *
 * representacion: id global, expansion, numero y nombre inmutables
 */
public abstract class Carta_LagosRivera_21056415 {
    /** guarda el siguiente id global disponible */
    private static int siguienteId = 0;

    /** identifica la carta sin repetirse entre sus familias */
    private final int id;
    /** indica la expansion a la que pertenece la carta */
    private final String expansion;
    /** indica el numero impreso dentro de la expansion */
    private final int numero;
    /** guarda el nombre visible de la carta */
    private final String nombre;

    /**
     * construye una carta y asigna su id global automaticamente
     *
     * @param expansion expansion a la que pertenece la carta
     * @param numero numero de la carta dentro de la expansion
     * @param nombre nombre visible de la carta
     */
    protected Carta_LagosRivera_21056415(String expansion, int numero, String nombre) {
        if (expansion == null || expansion.trim().isEmpty()) {
            throw new IllegalArgumentException("la expansion no puede estar vacia");
        }
        if (numero < 0) {
            throw new IllegalArgumentException("el numero de la carta no puede ser negativo");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio");
        }

        this.id = siguienteId;
        siguienteId = siguienteId + 1;
        this.expansion = expansion.trim();
        this.numero = numero;
        this.nombre = nombre.trim();
    }

    /**
     * devuelve el id unico entre todas las familias de cartas
     *
     * @return id global de la carta
     */
    public int getId() {
        return this.id;
    }

    /**
     * devuelve el nombre de la carta
     *
     * @return nombre visible
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * identifica la familia concreta de la carta
     *
     * @return energia, pokemon o entrenador
     */
    public abstract String getFamilia();

    /**
     * construye un resumen util para el menu
     *
     * @return datos principales de la carta
     */
    public String comoTextoCatalogo() {
        return "id " + this.id + " | " + getFamilia() + " | " + this.nombre
                + " | " + this.expansion + " #" + this.numero;
    }
}
