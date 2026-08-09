import java.util.List;

/**
 * representa una carta de entrenador partidario u objeto
 *
 * representacion: datos comunes, tipo, texto y efecto polimorfico asociado
 */
public class CartaEntrenador_LagosRivera_21056415 extends Carta_LagosRivera_21056415 {
    /** guarda si la carta es partidario u objeto */
    private final String tipo;
    /** contiene la descripcion de las acciones de la carta */
    private final String texto;
    /** contiene el comportamiento que se ejecuta al usar la carta */
    private final Efecto_LagosRivera_21056415 efectoAsociado;

    /**
     * construye una carta de entrenador con efecto polimorfico
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre nombre de la carta
     * @param tipo partidario u objeto
     * @param texto descripcion de sus acciones
     * @param efectoAsociado comportamiento concreto
     */
    public CartaEntrenador_LagosRivera_21056415(
            String expansion,
            int numero,
            String nombre,
            String tipo,
            String texto,
            Efecto_LagosRivera_21056415 efectoAsociado) {
        super(expansion, numero, validarDatos(nombre, tipo, texto, efectoAsociado));
        this.tipo = tipo.trim().toLowerCase();
        this.texto = texto.trim();
        this.efectoAsociado = efectoAsociado;
    }

    /**
     * devuelve el tipo de entrenador
     *
     * @return partidario u objeto
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * ejecuta el efecto concreto de esta carta
     *
     * @param juego partida en curso
     * @param usuario jugador que usa la carta
     * @param objetivo pokemon objetivo o null
     * @param argumentos argumentos adicionales
     * @return valor de dano ignorado por los entrenadores
     */
    public int usar(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            List<String> argumentos) {
        return this.efectoAsociado.ejecutar(
                juego, usuario, null, objetivo, 0, argumentos);
    }

    /**
     * identifica la familia de esta carta
     *
     * @return entrenador
     */
    @Override
    public String getFamilia() {
        return "entrenador";
    }

    /**
     * agrega el tipo y texto al resumen comun de carta
     *
     * @return datos completos para el catalogo
     */
    @Override
    public String comoTextoCatalogo() {
        return super.comoTextoCatalogo() + " | " + this.tipo + " | " + this.texto;
    }

    /**
     * valida los datos propios antes de llamar al constructor padre
     *
     * @param nombre nombre visible de la carta
     * @param tipo partidario u objeto
     * @param texto descripcion de sus acciones
     * @param efectoAsociado comportamiento concreto de la carta
     * @return nombre validado para el constructor padre
     */
    private static String validarDatos(
            String nombre,
            String tipo,
            String texto,
            Efecto_LagosRivera_21056415 efectoAsociado) {
        if (tipo == null
                || !("partidario".equalsIgnoreCase(tipo.trim())
                || "objeto".equalsIgnoreCase(tipo.trim()))) {
            throw new IllegalArgumentException("el tipo de entrenador debe ser partidario u objeto");
        }
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("el texto del entrenador no puede estar vacio");
        }
        if (efectoAsociado == null) {
            throw new IllegalArgumentException("el entrenador debe tener un efecto asociado");
        }
        return nombre;
    }
}
