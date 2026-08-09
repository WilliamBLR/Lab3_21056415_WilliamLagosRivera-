import java.util.List;

/**
 * recupera contadores de dano de un pokemon propio
 *
 * representacion: cantidad positiva de contadores que se pueden retirar
 */
public class EfectoCurar_LagosRivera_21056415
        implements Efecto_LagosRivera_21056415 {
    /** guarda la cantidad maxima que puede curarse */
    private final int cantidadCura;

    /**
     * construye un efecto de curacion
     *
     * @param cantidadCura cantidad maxima de dano que se elimina
     */
    public EfectoCurar_LagosRivera_21056415(int cantidadCura) {
        if (cantidadCura <= 0) {
            throw new IllegalArgumentException("la curacion debe ser mayor a cero");
        }
        this.cantidadCura = cantidadCura;
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
        CartaEnJuego_LagosRivera_21056415 pokemon = origen != null ? origen : objetivo;
        if (pokemon == null || !usuario.contienePokemon(pokemon)) {
            throw new IllegalArgumentException("debe seleccionar un pokemon propio para curar");
        }
        pokemon.curar(this.cantidadCura);
        return danoBase;
    }

    /** @return nombre estable del efecto */
    @Override
    public String getNombre() {
        return "curar_" + this.cantidadCura;
    }
}
