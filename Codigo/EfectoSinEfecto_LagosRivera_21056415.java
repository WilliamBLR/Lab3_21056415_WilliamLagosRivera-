import java.util.List;

/**
 * representa un ataque sin acciones adicionales
 *
 * representacion: efecto sin atributos que conserva el dano recibido
 */
public class EfectoSinEfecto_LagosRivera_21056415
        implements Efecto_LagosRivera_21056415 {

    /**
     * construye un efecto que conserva el dano base
     */
    public EfectoSinEfecto_LagosRivera_21056415() {
    }

    /** @return el dano base sin modificar */
    @Override
    public int ejecutar(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 origen,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            int danoBase,
            List<String> argumentos) {
        return danoBase;
    }

    /** @return nombre estable del efecto */
    @Override
    public String getNombre() {
        return "sin_efecto";
    }
}
