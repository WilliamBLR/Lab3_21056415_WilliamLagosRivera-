import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * representa un ataque y tambien se reutiliza para habilidades
 *
 * representacion: id propio, costo por tipo, nombre, descripcion, dano y efecto
 */
public class Ataque_LagosRivera_21056415 {
    /** guarda el siguiente id disponible para ataques y habilidades */
    private static int siguienteId = 0;

    /** identifica este ataque dentro del catalogo */
    private final int id;
    /** contiene el costo separado por tipo de energia */
    private final LinkedHashMap<String, Integer> costo;
    /** guarda el nombre visible del ataque */
    private final String nombre;
    /** explica brevemente el resultado del ataque */
    private final String descripcion;
    /** guarda el dano previo a modificadores y efectos */
    private final int danoBase;
    /** contiene el comportamiento adicional del ataque */
    private final Efecto_LagosRivera_21056415 efectoAsociado;

    /**
     * construye un ataque con id propio autoincremental
     *
     * @param costo pares de tipo y cantidad de energia
     * @param nombre nombre del ataque
     * @param descripcion texto explicativo
     * @param danoBase dano impreso en la carta
     * @param efectoAsociado efecto ejecutado polimorficamente
     */
    public Ataque_LagosRivera_21056415(
            Map<String, Integer> costo,
            String nombre,
            String descripcion,
            int danoBase,
            Efecto_LagosRivera_21056415 efectoAsociado) {
        if (costo == null) {
            throw new IllegalArgumentException("el costo debe ser una lista vacia o con pares validos");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre del ataque no puede estar vacio");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("la descripcion del ataque no puede ser null");
        }
        if (danoBase < 0) {
            throw new IllegalArgumentException("el dano base no puede ser negativo");
        }
        if (efectoAsociado == null) {
            throw new IllegalArgumentException("el ataque debe tener un efecto asociado");
        }

        LinkedHashMap<String, Integer> costoValidado = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> par : costo.entrySet()) {
            if (par.getValue() == null || par.getValue() <= 0) {
                throw new IllegalArgumentException("cada cantidad de energia debe ser mayor a cero");
            }
            String tipo = CartaEnergia_LagosRivera_21056415.normalizarTipoCosto(par.getKey());
            int anterior = costoValidado.containsKey(tipo) ? costoValidado.get(tipo) : 0;
            costoValidado.put(tipo, anterior + par.getValue());
        }
        this.id = siguienteId;
        siguienteId = siguienteId + 1;
        this.costo = costoValidado;
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.danoBase = danoBase;
        this.efectoAsociado = efectoAsociado;
    }

    /**
     * devuelve el id propio del ataque
     *
     * @return id autoincremental
     */
    public int getId() {
        return this.id;
    }

    /**
     * devuelve el nombre del ataque
     *
     * @return nombre visible
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * devuelve el dano base impreso
     *
     * @return dano base
     */
    public int getDanoBase() {
        return this.danoBase;
    }

    /**
     * revisa si puede reutilizarse como habilidad
     *
     * @return true cuando no tiene costo ni dano base
     */
    public boolean esHabilidadValida() {
        return this.costo.isEmpty() && this.danoBase == 0;
    }

    /**
     * revisa primero los tipos exactos y usa lo restante para incolora
     *
     * @param energias energias unidas al pokemon
     * @return true si el costo se puede pagar
     */
    public boolean puedePagarseCon(List<CartaEnergia_LagosRivera_21056415> energias) {
        LinkedHashMap<String, Integer> disponibles = new LinkedHashMap<String, Integer>();
        for (CartaEnergia_LagosRivera_21056415 energia : energias) {
            String tipo = energia.getTipoEnergia();
            int cantidad = disponibles.containsKey(tipo) ? disponibles.get(tipo) : 0;
            disponibles.put(tipo, cantidad + 1);
        }

        int gastadasEspecificas = 0;
        for (Map.Entry<String, Integer> par : this.costo.entrySet()) {
            if (!"Incolora".equals(par.getKey())) {
                int cantidadDisponible = disponibles.containsKey(par.getKey())
                        ? disponibles.get(par.getKey()) : 0;
                if (cantidadDisponible < par.getValue()) {
                    return false;
                }
                gastadasEspecificas = gastadasEspecificas + par.getValue();
            }
        }

        int incoloras = this.costo.containsKey("Incolora") ? this.costo.get("Incolora") : 0;
        return energias.size() - gastadasEspecificas >= incoloras;
    }

    /**
     * delega el comportamiento al efecto concreto
     *
     * @param juego partida en curso
     * @param usuario jugador que ejecuta el efecto
     * @param origen pokemon que origina el efecto
     * @param objetivo pokemon objetivo o null
     * @param argumentos argumentos adicionales
     * @return dano definido para esta ejecucion
     */
    public int ejecutarEfecto(
            Juego_LagosRivera_21056415 juego,
            Jugador_LagosRivera_21056415 usuario,
            CartaEnJuego_LagosRivera_21056415 origen,
            CartaEnJuego_LagosRivera_21056415 objetivo,
            List<String> argumentos) {
        return this.efectoAsociado.ejecutar(
                juego, usuario, origen, objetivo, this.danoBase, argumentos);
    }

    /**
     * construye un costo legible para el menu
     *
     * @return costo separado por tipos
     */
    public String costoComoTexto() {
        if (this.costo.isEmpty()) {
            return "sin costo";
        }
        StringBuilder texto = new StringBuilder();
        for (Map.Entry<String, Integer> par : this.costo.entrySet()) {
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append(par.getValue()).append(" ").append(par.getKey());
        }
        return texto.toString();
    }

    /**
     * construye un resumen para elegir el ataque desde el menu
     *
     * @return datos principales del ataque
     */
    public String comoTextoCatalogo() {
        return "id " + this.id + " | " + this.nombre + " | dano " + this.danoBase
                + " | costo " + costoComoTexto() + " | efecto "
                + this.efectoAsociado.getNombre() + " | " + this.descripcion;
    }
}
