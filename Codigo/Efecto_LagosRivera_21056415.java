import java.util.List;

/**
 * define el comportamiento variable de ataques, habilidades y entrenadores
 *
 * representacion: contrato sin estado comun que recibe el contexto de la partida
 */
public interface Efecto_LagosRivera_21056415 {
    /**
     * ejecuta el efecto sobre el estado real de una partida
     *
     * @param juego partida en curso
     * @param usuario jugador que usa el efecto
     * @param origen pokemon que origina el efecto o null
     * @param objetivo pokemon que recibe el efecto o null
     * @param danoBase dano base de un ataque
     * @param argumentos argumentos adicionales ingresados por el usuario
     * @return dano que debe aplicar el ataque despues del efecto
     */
    int ejecutar(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 origen,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            int danoBase,
            List<String> argumentos);

    /**
     * devuelve el nombre estable del efecto
     *
     * @return identificador legible del efecto
     */
    String getNombre();
}
