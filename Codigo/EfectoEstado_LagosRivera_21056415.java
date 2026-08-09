import java.util.List;

/**
 * reemplaza el estado especial del pokemon objetivo
 *
 * representacion: uno de los cuatro estados especiales validos
 */
public class EfectoEstado_LagosRivera_21056415
        implements Efecto_LagosRivera_21056415 {
    /** guarda el estado especial que recibe el objetivo */
    private final String estado;

    /**
     * construye un efecto que aplica un estado
     *
     * @param estado estado especial que se aplicara
     */
    public EfectoEstado_LagosRivera_21056415(String estado) {
        this.estado = CartaEnJuego_LagosRivera_21056415.normalizarEstado(estado);
        if ("normal".equals(this.estado)) {
            throw new IllegalArgumentException("el efecto debe aplicar un estado especial");
        }
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
        if (objetivo != null) {
            objetivo.setEstadoEspecial(this.estado);
        }
        return danoBase;
    }

    /** @return nombre estable del efecto */
    @Override
    public String getNombre() {
        return "estado_" + this.estado;
    }
}
