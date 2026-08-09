import java.util.List;

/**
 * fija el dano de un ataque o aplica dano directo desde una habilidad
 *
 * representacion: cantidad de dano no negativa
 */
public class EfectoDanio_LagosRivera_21056415
        implements Efecto_LagosRivera_21056415 {
    /** guarda el dano adicional aplicado al objetivo */
    private final int cantidadDano;

    /**
     * construye un efecto de dano
     *
     * @param cantidadDano cantidad de dano producida por el efecto
     */
    public EfectoDanio_LagosRivera_21056415(int cantidadDano) {
        if (cantidadDano < 0) {
            throw new IllegalArgumentException("el dano del efecto no puede ser negativo");
        }
        this.cantidadDano = cantidadDano;
    }

    /** @return dano fijado por el efecto */
    @Override
    public int ejecutar(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 origen,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            int danoBase,
            List<String> argumentos) {
        return this.cantidadDano;
    }

    /** @return nombre estable del efecto */
    @Override
    public String getNombre() {
        return "dano_" + this.cantidadDano;
    }
}
