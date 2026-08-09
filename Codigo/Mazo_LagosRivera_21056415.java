import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * representa una coleccion ordenada y valida de sesenta cartas
 *
 * representacion: id, nombre y lista ordenada que respeta las reglas de copias
 */
public class Mazo_LagosRivera_21056415 {
    /** guarda el siguiente id disponible para los mazos */
    private static int siguienteId = 0;

    /** identifica el mazo dentro del catalogo */
    private final int id;
    /** guarda el nombre visible del mazo */
    private final String nombre;
    /** mantiene las cartas en el orden actual del mazo */
    private final ArrayList<Carta_LagosRivera_21056415> cartas;

    /**
     * construye el mazo solamente cuando cumple todas sus invariantes
     *
     * @param nombre nombre usado desde el menu
     * @param cartas lista de exactamente sesenta cartas
     */
    public Mazo_LagosRivera_21056415(
            String nombre, List<Carta_LagosRivera_21056415> cartas) {
        this(siguienteId, nombre, cartas, true);
        siguienteId = siguienteId + 1;
    }

    /**
     * crea una copia interna sin volver a validar sus cartas
     *
     * @param id identificador que conserva la copia
     * @param nombre nombre que conserva la copia
     * @param cartas lista independiente de cartas
     * @param validar indica si deben revisarse las invariantes
     */
    private Mazo_LagosRivera_21056415(
            int id,
            String nombre,
            List<Carta_LagosRivera_21056415> cartas,
            boolean validar) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre del mazo no puede estar vacio");
        }
        if (cartas == null) {
            throw new IllegalArgumentException("la lista de cartas no puede ser null");
        }
        if (validar) {
            validarCartas(cartas);
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.cartas = new ArrayList<Carta_LagosRivera_21056415>(cartas);
    }

    /**
     * devuelve el id autoincremental del mazo
     *
     * @return identificador del mazo
     */
    public int getId() {
        return this.id;
    }

    /**
     * devuelve el nombre del mazo
     *
     * @return nombre visible
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * devuelve la cantidad actual de cartas
     *
     * @return cartas que permanecen en el mazo
     */
    public int getCantidadCartas() {
        return this.cartas.size();
    }

    /**
     * revuelve el orden usando una fuente aleatoria nueva
     */
    public void barajar() {
        barajar(new Random());
    }

    /**
     * revuelve el orden usando la fuente aleatoria de la partida
     *
     * @param aleatorio generador usado para el barajado
     */
    public void barajar(Random aleatorio) {
        if (aleatorio == null) {
            throw new IllegalArgumentException("el generador aleatorio no puede ser null");
        }
        Collections.shuffle(this.cartas, aleatorio);
    }

    /**
     * retira la carta superior
     *
     * @return carta robada o null si no quedan cartas
     */
    public Carta_LagosRivera_21056415 robarCartaSuperior() {
        return this.cartas.isEmpty() ? null : this.cartas.remove(0);
    }

    /**
     * devuelve cartas al mazo antes de repetir una mano inicial
     *
     * @param cartasDevueltas cartas que vuelven al mazo
     */
    public void devolverCartas(List<Carta_LagosRivera_21056415> cartasDevueltas) {
        if (cartasDevueltas == null) {
            throw new IllegalArgumentException("las cartas devueltas no pueden ser null");
        }
        this.cartas.addAll(cartasDevueltas);
    }

    /**
     * crea una copia independiente para iniciar una partida nueva
     *
     * @return copia con el mismo catalogo y orden actual
     */
    public Mazo_LagosRivera_21056415 crearCopiaJugable() {
        return new Mazo_LagosRivera_21056415(
                this.id, this.nombre, this.cartas, false);
    }

    /**
     * construye un resumen visible desde el menu
     *
     * @return id, nombre y cantidad
     */
    public String comoTextoCatalogo() {
        return "id " + this.id + " | " + this.nombre + " | " + this.cartas.size() + " cartas";
    }

    /**
     * aplica las invariantes de cantidad, basicos y copias
     *
     * @param cartas lista que se desea usar como mazo
     */
    private static void validarCartas(List<Carta_LagosRivera_21056415> cartas) {
        if (cartas.size() != 60) {
            throw new IllegalArgumentException("el mazo debe tener exactamente 60 cartas");
        }

        boolean tieneBasico = false;
        Map<String, Integer> copiasPorNombre = new LinkedHashMap<String, Integer>();
        for (Carta_LagosRivera_21056415 carta : cartas) {
            if (carta == null) {
                throw new IllegalArgumentException("el mazo no puede contener cartas null");
            }
            if (carta instanceof CartaPokemon_LagosRivera_21056415
                    && ((CartaPokemon_LagosRivera_21056415) carta).esBasico()) {
                tieneBasico = true;
            }
            if (!(carta instanceof CartaEnergia_LagosRivera_21056415)) {
                String nombre = carta.getNombre().toLowerCase();
                int cantidad = copiasPorNombre.containsKey(nombre)
                        ? copiasPorNombre.get(nombre) + 1 : 1;
                copiasPorNombre.put(nombre, cantidad);
                if (cantidad > 4) {
                    throw new IllegalArgumentException(
                            "el mazo supera cuatro copias de " + carta.getNombre());
                }
            }
        }
        if (!tieneBasico) {
            throw new IllegalArgumentException("el mazo debe contener al menos un pokemon basico");
        }
    }
}
