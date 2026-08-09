import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * representa una instancia unica de pokemon dentro de la mesa
 *
 * representacion: id de instancia, etapa actual, etapas anteriores, energias,
 * dano, turnos, estado especial y ultimo uso de habilidad
 */
public class CartaEnJuego_LagosRivera_21056415 {
    /** guarda el siguiente id disponible para instancias en mesa */
    private static long siguienteIdInstancia = 0;

    /** diferencia esta instancia de otras copias de la misma carta */
    private final long idInstancia;
    /** contiene la etapa que se encuentra arriba de la pila */
    private CartaPokemon_LagosRivera_21056415 cartaActual;
    /** conserva las etapas anteriores de la evolucion */
    private final ArrayList<CartaPokemon_LagosRivera_21056415> cartasEvolucionadas;
    /** contiene todas las energias unidas a la instancia */
    private final ArrayList<CartaEnergia_LagosRivera_21056415> energiasUnidas;
    /** acumula el dano que todavia no fue curado */
    private int danoAcumulado;
    /** cuenta los turnos propios desde la ultima evolucion */
    private int turnosEnJuego;
    /** guarda el estado especial actual */
    private String estadoEspecial;
    /** recuerda cuando se uso por ultima vez la habilidad */
    private int ultimoTurnoHabilidad;

    /**
     * crea una instancia distinta incluso para dos copias de la misma carta
     *
     * @param cartaBasica pokemon basico que entra a la mesa
     */
    public CartaEnJuego_LagosRivera_21056415(
            CartaPokemon_LagosRivera_21056415 cartaBasica) {
        if (cartaBasica == null || !cartaBasica.esBasico()) {
            throw new IllegalArgumentException("solo un pokemon basico puede crear una instancia en juego");
        }
        this.idInstancia = siguienteIdInstancia;
        siguienteIdInstancia = siguienteIdInstancia + 1;
        this.cartaActual = cartaBasica;
        this.cartasEvolucionadas = new ArrayList<CartaPokemon_LagosRivera_21056415>();
        this.energiasUnidas = new ArrayList<CartaEnergia_LagosRivera_21056415>();
        this.danoAcumulado = 0;
        this.turnosEnJuego = 0;
        this.estadoEspecial = "normal";
        this.ultimoTurnoHabilidad = -1;
    }

    /**
     * devuelve el identificador unico de la instancia
     *
     * @return id distinto de los ids de carta
     */
    public long getIdInstancia() {
        return this.idInstancia;
    }

    /**
     * devuelve la carta pokemon que se encuentra arriba
     *
     * @return etapa actual
     */
    public CartaPokemon_LagosRivera_21056415 getCartaActual() {
        return this.cartaActual;
    }

    /**
     * calcula los ps restantes
     *
     * @return ps que pueden ser cero o negativos
     */
    public int getPsActuales() {
        return this.cartaActual.getPs() - this.danoAcumulado;
    }

    /**
     * devuelve el estado especial actual
     *
     * @return normal o uno de los cuatro estados especiales
     */
    public String getEstadoEspecial() {
        return this.estadoEspecial;
    }

    /**
     * devuelve energias que no pueden modificarse externamente
     *
     * @return vista de energias unidas
     */
    public List<CartaEnergia_LagosRivera_21056415> getEnergiasUnidas() {
        return Collections.unmodifiableList(this.energiasUnidas);
    }

    /**
     * suma un turno propio a esta etapa
     */
    public void incrementarTurnosEnJuego() {
        this.turnosEnJuego = this.turnosEnJuego + 1;
    }

    /**
     * une una carta de energia a esta instancia
     *
     * @param energia carta retirada de la mano
     */
    public void unirEnergia(CartaEnergia_LagosRivera_21056415 energia) {
        if (energia == null) {
            throw new IllegalArgumentException("la energia no puede ser null");
        }
        this.energiasUnidas.add(energia);
    }

    /**
     * retira energias para pagar un cambio de activo
     *
     * @param cantidad costo de retirada
     * @return cartas que deben ir al descarte
     */
    public List<CartaEnergia_LagosRivera_21056415> retirarEnergias(int cantidad) {
        if (cantidad < 0 || this.energiasUnidas.size() < cantidad) {
            throw new IllegalArgumentException("no hay energias suficientes para pagar la retirada");
        }
        ArrayList<CartaEnergia_LagosRivera_21056415> retiradas =
                new ArrayList<CartaEnergia_LagosRivera_21056415>();
        for (int i = 0; i < cantidad; i = i + 1) {
            retiradas.add(this.energiasUnidas.remove(0));
        }
        return retiradas;
    }

    /**
     * aplica debilidad y resistencia al dano de un ataque
     *
     * @param danoBase dano definido despues de ejecutar el efecto
     * @param tipoAtacante tipo del pokemon atacante
     * @return dano final agregado
     */
    public int recibirDano(int danoBase, String tipoAtacante) {
        if (danoBase < 0) {
            throw new IllegalArgumentException("el dano no puede ser negativo");
        }
        int danoFinal = danoBase;
        if (this.cartaActual.getDebilidad() != null
                && this.cartaActual.getDebilidad().equalsIgnoreCase(tipoAtacante)) {
            danoFinal = danoFinal * 2;
        }
        if (this.cartaActual.getResistencia() != null
                && this.cartaActual.getResistencia().equalsIgnoreCase(tipoAtacante)) {
            danoFinal = Math.max(0, danoFinal - 30);
        }
        this.danoAcumulado = this.danoAcumulado + danoFinal;
        return danoFinal;
    }

    /**
     * aplica dano de efecto sin debilidad ni resistencia
     *
     * @param cantidad contadores agregados
     */
    public void recibirDanoDirecto(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("el dano directo no puede ser negativo");
        }
        this.danoAcumulado = this.danoAcumulado + cantidad;
    }

    /**
     * elimina contadores de dano sin superar el total existente
     *
     * @param cantidad cantidad maxima a retirar
     */
    public void curar(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("la curacion debe ser mayor a cero");
        }
        this.danoAcumulado = Math.max(0, this.danoAcumulado - cantidad);
    }

    /**
     * coloca una evolucion conservando instancia, energias y dano
     *
     * @param evolucion carta compatible retirada de la mano
     */
    public void evolucionar(CartaPokemon_LagosRivera_21056415 evolucion) {
        if (evolucion == null || evolucion.getEvolucionaDe() == null
                || !evolucion.getEvolucionaDe().equalsIgnoreCase(this.cartaActual.getNombre())) {
            throw new IllegalArgumentException("la carta no evoluciona del pokemon seleccionado");
        }
        if (this.turnosEnJuego < 1) {
            throw new IllegalArgumentException("el pokemon debe llevar al menos un turno en juego");
        }
        this.cartasEvolucionadas.add(this.cartaActual);
        this.cartaActual = evolucion;
        this.estadoEspecial = "normal";
        this.turnosEnJuego = 0;
        this.ultimoTurnoHabilidad = -1;
    }

    /**
     * cambia el estado y reemplaza cualquier estado anterior
     *
     * @param estado nuevo estado
     */
    public void setEstadoEspecial(String estado) {
        this.estadoEspecial = normalizarEstado(estado);
    }

    /**
     * revisa el limite de una habilidad por pokemon y turno
     *
     * @param numeroTurno turno global actual
     * @return true si todavia se puede usar
     */
    public boolean puedeUsarHabilidad(int numeroTurno) {
        return this.ultimoTurnoHabilidad != numeroTurno;
    }

    /**
     * registra el turno en que se uso la habilidad
     *
     * @param numeroTurno turno global actual
     */
    public void marcarHabilidadUsada(int numeroTurno) {
        this.ultimoTurnoHabilidad = numeroTurno;
    }

    /**
     * reune todas las etapas que deben descartarse
     *
     * @return cartas pokemon de la instancia
     */
    public List<CartaPokemon_LagosRivera_21056415> getCartasPokemonParaDescarte() {
        ArrayList<CartaPokemon_LagosRivera_21056415> cartas =
                new ArrayList<CartaPokemon_LagosRivera_21056415>(this.cartasEvolucionadas);
        cartas.add(this.cartaActual);
        return cartas;
    }

    /**
     * construye el resumen completo exigido para mostrar la partida
     *
     * @return datos actuales de la instancia
     */
    public String comoTextoDetallado() {
        StringBuilder texto = new StringBuilder();
        texto.append("instancia ").append(this.idInstancia)
                .append(" | ").append(this.cartaActual.getNombre())
                .append(" | tipo ").append(this.cartaActual.getTipo())
                .append(" | ps ").append(getPsActuales()).append("/")
                .append(this.cartaActual.getPs())
                .append(" | ").append(this.cartaActual.esBasico()
                        ? "basico" : "evolucion de " + this.cartaActual.getEvolucionaDe())
                .append(" | dano ").append(this.danoAcumulado)
                .append(" | energias ").append(energiasComoTexto())
                .append(" | debilidad ").append(valorOpcional(this.cartaActual.getDebilidad()))
                .append(" | resistencia ").append(valorOpcional(this.cartaActual.getResistencia()))
                .append(" | estado ").append(this.estadoEspecial)
                .append(" | turnos ").append(this.turnosEnJuego);
        return texto.toString();
    }

    /**
     * normaliza uno de los cuatro estados especiales o normal
     *
     * @param estado texto ingresado
     * @return estado canonico
     */
    public static String normalizarEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("el estado no puede estar vacio");
        }
        String limpio = Normalizer.normalize(estado.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase();
        if ("normal".equals(limpio) || "dormido".equals(limpio)
                || "paralizado".equals(limpio) || "confundido".equals(limpio)
                || "envenenado".equals(limpio)) {
            return limpio;
        }
        throw new IllegalArgumentException(
                "estado invalido. use dormido, paralizado, confundido o envenenado");
    }

    /**
     * construye el resumen de energias para mostrar el juego
     *
     * @return tipos y cantidades de energias unidas
     */
    private String energiasComoTexto() {
        if (this.energiasUnidas.isEmpty()) {
            return "0 []";
        }
        StringBuilder tipos = new StringBuilder();
        for (CartaEnergia_LagosRivera_21056415 energia : this.energiasUnidas) {
            if (tipos.length() > 0) {
                tipos.append(", ");
            }
            tipos.append(energia.getTipoEnergia());
        }
        return this.energiasUnidas.size() + " [" + tipos + "]";
    }

    /**
     * reemplaza los valores opcionales ausentes por un guion
     *
     * @param valor texto que puede ser null
     * @return valor original o un guion
     */
    private static String valorOpcional(String valor) {
        return valor == null ? "ninguna" : valor;
    }
}
