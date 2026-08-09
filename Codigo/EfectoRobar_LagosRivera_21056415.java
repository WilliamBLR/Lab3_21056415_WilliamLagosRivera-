import java.util.List;

/**
 * roba cartas sin provocar derrota cuando el mazo queda vacio
 *
 * representacion: cantidad positiva de cartas que intenta robar
 */
public class EfectoRobar_LagosRivera_21056415
        implements Efecto_LagosRivera_21056415 {
    /** guarda la cantidad maxima de cartas que se intenta robar */
    private final int cantidadCartas;

    /**
     * construye un efecto de robo
     *
     * @param cantidadCartas cantidad maxima que intenta robar
     */
    public EfectoRobar_LagosRivera_21056415(int cantidadCartas) {
        if (cantidadCartas <= 0) {
            throw new IllegalArgumentException("la cantidad a robar debe ser mayor a cero");
        }
        this.cantidadCartas = cantidadCartas;
    }

    /** @return dano base del ataque sin modificar */
    @Override
    public int ejecutar(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 origen,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            int danoBase,
            List<String> argumentos) {
        usuario.robarPorEfecto(this.cantidadCartas);
        return danoBase;
    }

    /** @return nombre estable del efecto */
    @Override
    public String getNombre() {
        return "robar_" + this.cantidadCartas;
    }
}
