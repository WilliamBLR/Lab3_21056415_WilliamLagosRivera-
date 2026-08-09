import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * coordina una partida completa entre dos jugadores
 *
 * representacion: jugadores, turno actual, estado, ganador y limites del turno
 */
public class Juego_LagosRivera_21056415 {
    /** contiene al primer participante de la partida */
    private final Jugador_LagosRivera_21056415 jugador1;
    /** contiene al segundo participante de la partida */
    private final Jugador_LagosRivera_21056415 jugador2;
    /** controla el barajado y el lanzamiento de moneda */
    private final Random aleatorio;
    /** indica quien puede realizar acciones ahora */
    private Jugador_LagosRivera_21056415 jugadorActual;
    /** guarda al ganador una vez terminada la partida */
    private Jugador_LagosRivera_21056415 ganador;
    /** guarda si la partida esta iniciada, en curso o terminada */
    private String estadoPartida;
    /** indica si el robo opcional sigue disponible */
    private boolean roboDisponible;
    /** controla el limite de una energia por turno */
    private boolean energiaUsada;
    /** controla el limite de un partidario por turno */
    private boolean partidarioUsado;
    /** indica si falta reemplazar un pokemon activo vencido */
    private boolean reposicionObligatoria;
    /** cuenta los turnos globales de la partida */
    private int numeroTurno;

    /**
     * construye una partida nueva sin iniciarla todavia
     *
     * @param jugador1 primer participante
     * @param jugador2 segundo participante
     */
    public Juego_LagosRivera_21056415(
            Jugador_LagosRivera_21056415 jugador1,
            Jugador_LagosRivera_21056415 jugador2) {
        if (jugador1 == null || jugador2 == null || jugador1 == jugador2) {
            throw new IllegalArgumentException("la partida necesita dos jugadores distintos");
        }
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.aleatorio = new Random();
        this.jugadorActual = null;
        this.ganador = null;
        this.estadoPartida = "no iniciada";
        this.roboDisponible = false;
        this.energiaUsada = false;
        this.partidarioUsado = false;
        this.reposicionObligatoria = false;
        this.numeroTurno = 0;
    }

    /**
     * baraja, reparte manos validas, separa premios y lanza la moneda
     *
     * @return resumen de la preparacion
     */
    public String iniciarJuego() {
        if (!"no iniciada".equals(this.estadoPartida)) {
            throw new IllegalStateException("esta instancia de juego ya fue iniciada");
        }
        this.jugador1.getMazo().barajar(this.aleatorio);
        this.jugador2.getMazo().barajar(this.aleatorio);
        this.jugador1.prepararManoInicial(this.aleatorio);
        this.jugador2.prepararManoInicial(this.aleatorio);
        this.jugador1.prepararPremios();
        this.jugador2.prepararPremios();
        this.jugadorActual = this.aleatorio.nextBoolean() ? this.jugador1 : this.jugador2;
        this.estadoPartida = "en curso";
        this.numeroTurno = 1;
        reiniciarLimitesTurno();
        return "juego iniciado. la moneda decidio que comienza "
                + this.jugadorActual.getNombre();
    }

    /**
     * revisa si todavia se pueden realizar acciones
     *
     * @return true mientras la partida esta en curso
     */
    public boolean estaEnCurso() {
        return "en curso".equals(this.estadoPartida);
    }

    /**
     * pone un pokemon basico de la mano actual en banca
     *
     * @param indiceMano indice visible en el menu
     * @return retroalimentacion de la accion
     */
    public String jugarABanca(int indiceMano) {
        validarEnCurso();
        CartaEnJuego_LagosRivera_21056415 pokemon =
                this.jugadorActual.jugarPokemonABanca(indiceMano);
        registrarAccionNormal();
        return pokemon.getCartaActual().getNombre() + " entro a la banca con instancia "
                + pokemon.getIdInstancia();
    }

    /**
     * cambia el activo usando una instancia que esta en banca
     *
     * @param indiceBanca indice visible de la banca
     * @return retroalimentacion de la accion
     */
    public String cambiarPokemonActivo(int indiceBanca) {
        validarEnCurso();
        boolean habiaActivo = this.jugadorActual.getPokemonActivo() != null;
        int costo = this.jugadorActual.cambiarPokemonActivo(indiceBanca);
        this.reposicionObligatoria = false;
        if (habiaActivo) {
            registrarAccionNormal();
        }
        return this.jugadorActual.getPokemonActivo().getCartaActual().getNombre()
                + " es ahora el pokemon activo. costo pagado: " + costo;
    }

    /**
     * roba solamente como primera accion opcional del turno
     *
     * @return retroalimentacion o resultado de derrota
     */
    public String robarCarta() {
        validarEnCurso();
        if (this.reposicionObligatoria
                || (this.jugadorActual.getPokemonActivo() == null
                && this.jugadorActual.tienePokemonEnJuego())) {
            throw new IllegalStateException("primero debe reponer el pokemon activo");
        }
        if (!this.roboDisponible) {
            throw new IllegalStateException("el robo ya no esta disponible en este turno");
        }
        this.roboDisponible = false;
        Carta_LagosRivera_21056415 carta = this.jugadorActual.robarCarta();
        if (carta == null) {
            finalizarPartida(rivalDe(this.jugadorActual),
                    "el rival intento robar con el mazo vacio");
            return "el mazo esta vacio. gana " + this.ganador.getNombre();
        }
        return this.jugadorActual.getNombre() + " robo " + carta.getNombre();
    }

    /**
     * une una energia de la mano a un pokemon propio en juego
     *
     * @param idPokemon instancia que recibe la energia
     * @param indiceMano posicion de la energia en la mano
     * @return retroalimentacion de la accion
     */
    public String usarCartaEnergia(long idPokemon, int indiceMano) {
        validarAccionConActivo();
        if (this.energiaUsada) {
            throw new IllegalStateException("ya se unio una energia durante este turno");
        }
        CartaEnJuego_LagosRivera_21056415 pokemon =
                this.jugadorActual.buscarPokemon(idPokemon);
        if (pokemon == null) {
            throw new IllegalArgumentException("la instancia no pertenece al jugador actual");
        }
        Carta_LagosRivera_21056415 carta = this.jugadorActual.getCartaMano(indiceMano);
        if (!(carta instanceof CartaEnergia_LagosRivera_21056415)) {
            throw new IllegalArgumentException("la carta seleccionada no es una energia");
        }
        CartaEnergia_LagosRivera_21056415 energia =
                (CartaEnergia_LagosRivera_21056415) carta;
        pokemon.unirEnergia(energia);
        this.jugadorActual.retirarCartaMano(indiceMano);
        this.energiaUsada = true;
        registrarAccionNormal();
        return "energia " + energia.getTipoEnergia() + " unida a "
                + pokemon.getCartaActual().getNombre();
    }

    /**
     * evoluciona un pokemon propio de banca o activo
     *
     * @param idPokemon instancia que se evoluciona
     * @param indiceMano posicion de la evolucion
     * @return retroalimentacion de la accion
     */
    public String evolucionarPokemon(long idPokemon, int indiceMano) {
        validarAccionConActivo();
        CartaEnJuego_LagosRivera_21056415 pokemon =
                this.jugadorActual.buscarPokemon(idPokemon);
        if (pokemon == null) {
            throw new IllegalArgumentException("la instancia no pertenece al jugador actual");
        }
        Carta_LagosRivera_21056415 carta = this.jugadorActual.getCartaMano(indiceMano);
        if (!(carta instanceof CartaPokemon_LagosRivera_21056415)) {
            throw new IllegalArgumentException("la carta seleccionada no es un pokemon");
        }
        CartaPokemon_LagosRivera_21056415 evolucion =
                (CartaPokemon_LagosRivera_21056415) carta;
        pokemon.evolucionar(evolucion);
        this.jugadorActual.retirarCartaMano(indiceMano);
        registrarAccionNormal();
        return "pokemon evolucionado a " + evolucion.getNombre()
                + " conservando dano y energias";
    }

    /**
     * usa una carta de entrenador y controla el limite de partidarios
     *
     * @param indiceMano posicion de la carta en mano
     * @param idObjetivo instancia opcional elegida para el efecto
     * @param argumentos argumentos adicionales
     * @return retroalimentacion de la accion
     */
    public String usarCartaEntrenador(
            int indiceMano, Long idObjetivo, List<String> argumentos) {
        validarAccionConActivo();
        Carta_LagosRivera_21056415 carta = this.jugadorActual.getCartaMano(indiceMano);
        if (!(carta instanceof CartaEntrenador_LagosRivera_21056415)) {
            throw new IllegalArgumentException("la carta seleccionada no es un entrenador");
        }
        CartaEntrenador_LagosRivera_21056415 entrenador =
                (CartaEntrenador_LagosRivera_21056415) carta;
        if ("partidario".equals(entrenador.getTipo()) && this.partidarioUsado) {
            throw new IllegalStateException("ya se uso un partidario durante este turno");
        }
        CartaEnJuego_LagosRivera_21056415 objetivo = idObjetivo == null
                ? null : buscarPokemonEnJuego(idObjetivo.longValue());
        if (idObjetivo != null && objetivo == null) {
            throw new IllegalArgumentException("la instancia objetivo no existe en la mesa");
        }
        int danoEfecto = entrenador.usar(
                this, this.jugadorActual, objetivo, argumentosSeguros(argumentos));
        if (danoEfecto > 0 && objetivo != null) {
            objetivo.recibirDanoDirecto(danoEfecto);
        }
        this.jugadorActual.retirarCartaMano(indiceMano);
        this.jugadorActual.descartarCarta(entrenador);
        if ("partidario".equals(entrenador.getTipo())) {
            this.partidarioUsado = true;
        }
        registrarAccionNormal();
        revisarKnockOutPorEfecto(objetivo);
        return "entrenador " + entrenador.getNombre() + " usado y enviado al descarte";
    }

    /**
     * usa una habilidad desde el activo o la banca una vez por turno
     *
     * @param idPokemon instancia que posee la habilidad
     * @param idObjetivo instancia opcional elegida para el efecto
     * @param argumentos argumentos adicionales
     * @return retroalimentacion de la accion
     */
    public String usarHabilidadPokemon(
            long idPokemon, Long idObjetivo, List<String> argumentos) {
        validarAccionConActivo();
        CartaEnJuego_LagosRivera_21056415 origen =
                this.jugadorActual.buscarPokemon(idPokemon);
        if (origen == null) {
            throw new IllegalArgumentException("la instancia no pertenece al jugador actual");
        }
        Ataque_LagosRivera_21056415 habilidad = origen.getCartaActual().getHabilidad();
        if (habilidad == null) {
            throw new IllegalArgumentException("el pokemon seleccionado no tiene habilidad");
        }
        if (!origen.puedeUsarHabilidad(this.numeroTurno)) {
            throw new IllegalStateException("ese pokemon ya uso su habilidad durante este turno");
        }
        CartaEnJuego_LagosRivera_21056415 objetivo = idObjetivo == null
                ? null : buscarPokemonEnJuego(idObjetivo.longValue());
        if (idObjetivo != null && objetivo == null) {
            throw new IllegalArgumentException("la instancia objetivo no existe en la mesa");
        }
        int danoEfecto = habilidad.ejecutarEfecto(
                this, this.jugadorActual, origen, objetivo, argumentosSeguros(argumentos));
        if (danoEfecto > 0 && objetivo != null) {
            objetivo.recibirDanoDirecto(danoEfecto);
        }
        origen.marcarHabilidadUsada(this.numeroTurno);
        registrarAccionNormal();
        revisarKnockOutPorEfecto(objetivo);
        return "habilidad " + habilidad.getNombre() + " usada por "
                + origen.getCartaActual().getNombre();
    }

    /**
     * ejecuta un ataque o pasa si el nombre es null
     *
     * @param nombreAtaque nombre del ataque o null para pasar
     * @param argumentos argumentos adicionales del efecto
     * @return log resumido del ataque y cambio de turno
     */
    public String usarAtaquePokemon(String nombreAtaque, List<String> argumentos) {
        validarAccionConActivo();
        if (nombreAtaque == null || nombreAtaque.trim().isEmpty()) {
            String jugadorQuePasa = this.jugadorActual.getNombre();
            String cierre = pasarTurno();
            return jugadorQuePasa + " paso sin atacar. " + cierre;
        }

        CartaEnJuego_LagosRivera_21056415 atacante =
                this.jugadorActual.getPokemonActivo();
        if ("paralizado".equals(atacante.getEstadoEspecial())
                || "dormido".equals(atacante.getEstadoEspecial())) {
            throw new IllegalStateException(
                    "el pokemon " + atacante.getEstadoEspecial() + " no puede atacar");
        }
        Ataque_LagosRivera_21056415 ataque =
                atacante.getCartaActual().buscarAtaque(nombreAtaque);
        if (ataque == null) {
            throw new IllegalArgumentException("el pokemon activo no conoce ese ataque");
        }
        if (!ataque.puedePagarseCon(atacante.getEnergiasUnidas())) {
            throw new IllegalStateException("el pokemon no tiene las energias necesarias");
        }

        Jugador_LagosRivera_21056415 defensor = rivalDe(this.jugadorActual);
        if ("confundido".equals(atacante.getEstadoEspecial())
                && !this.aleatorio.nextBoolean()) {
            atacante.recibirDanoDirecto(30);
            StringBuilder confusion = new StringBuilder(
                    "la moneda salio sello y el pokemon confundido se hizo 30 de dano");
            if (atacante.getPsActuales() <= 0) {
                confusion.append(". ").append(resolverKnockOut(this.jugadorActual, defensor));
            }
            if (estaEnCurso()) {
                confusion.append(". ").append(pasarTurno());
            }
            return confusion.toString();
        }
        CartaEnJuego_LagosRivera_21056415 objetivo = defensor.getPokemonActivo();
        int dano = ataque.ejecutarEfecto(
                this, this.jugadorActual, atacante, objetivo, argumentosSeguros(argumentos));
        StringBuilder log = new StringBuilder();
        log.append(this.jugadorActual.getNombre()).append(" uso ").append(ataque.getNombre());
        if (objetivo == null) {
            log.append(" pero el rival no tenia pokemon activo");
        } else {
            int danoFinal = objetivo.recibirDano(dano, atacante.getCartaActual().getTipo());
            log.append(" e hizo ").append(danoFinal).append(" de dano");
            if (objetivo.getPsActuales() <= 0) {
                log.append(". ").append(resolverKnockOut(defensor, this.jugadorActual));
            }
        }

        if (estaEnCurso()) {
            log.append(". ").append(pasarTurno());
        }
        return log.toString();
    }

    /**
     * construye y retorna toda la informacion visible de la partida
     *
     * @return un unico string con saltos de linea
     */
    public String mostrarJuego() {
        StringBuilder texto = new StringBuilder();
        texto.append("\n========== estado del juego ==========\n");
        texto.append("estado: ").append(this.estadoPartida).append("\n");
        if (this.jugadorActual != null) {
            texto.append("turno ").append(this.numeroTurno).append(" de ")
                    .append(this.jugadorActual.getNombre()).append("\n");
            if (this.reposicionObligatoria) {
                texto.append("accion obligatoria: reponer pokemon activo\n");
            }
        }
        if (this.ganador != null) {
            texto.append("ganador: ").append(this.ganador.getNombre()).append("\n");
        }
        texto.append(describirJugador(this.jugador1, this.jugador1 == this.jugadorActual));
        texto.append(describirJugador(this.jugador2, this.jugador2 == this.jugadorActual));
        texto.append("=====================================\n");
        return texto.toString();
    }

    /**
     * construye los ataques disponibles del activo actual
     *
     * @return listado legible de ataques
     */
    public String mostrarAtaquesDelActivo() {
        validarAccionConActivo();
        List<Ataque_LagosRivera_21056415> ataques =
                this.jugadorActual.getPokemonActivo().getCartaActual().getAtaques();
        if (ataques.isEmpty()) {
            return "el pokemon activo no tiene ataques";
        }
        StringBuilder texto = new StringBuilder();
        for (Ataque_LagosRivera_21056415 ataque : ataques) {
            texto.append("- ").append(ataque.getNombre())
                    .append(" | dano ").append(ataque.getDanoBase())
                    .append(" | costo ").append(ataque.costoComoTexto()).append("\n");
        }
        return texto.toString();
    }

    /** impide acciones cuando la partida no esta en curso */
    private void validarEnCurso() {
        if (!estaEnCurso()) {
            throw new IllegalStateException("primero debe iniciar una partida en curso");
        }
    }

    /** valida que el turno tenga un pokemon activo disponible */
    private void validarAccionConActivo() {
        validarEnCurso();
        if (this.reposicionObligatoria || this.jugadorActual.getPokemonActivo() == null) {
            throw new IllegalStateException("primero debe poner un pokemon activo desde la banca");
        }
    }

    /** cierra la oportunidad de robo luego de otra accion */
    private void registrarAccionNormal() {
        this.roboDisponible = false;
    }

    /** habilita los limites propios de un turno nuevo */
    private void reiniciarLimitesTurno() {
        this.roboDisponible = true;
        this.energiaUsada = false;
        this.partidarioUsado = false;
    }

    /**
     * entrega el turno al rival y actualiza los contadores
     *
     * @return resultado de estados especiales y nuevo turno
     */
    private String pasarTurno() {
        StringBuilder cierre = new StringBuilder();
        CartaEnJuego_LagosRivera_21056415 activoSaliente =
                this.jugadorActual.getPokemonActivo();
        if (activoSaliente != null
                && "envenenado".equals(activoSaliente.getEstadoEspecial())) {
            activoSaliente.recibirDanoDirecto(10);
            cierre.append("el veneno agrego 10 de dano. ");
            if (activoSaliente.getPsActuales() <= 0) {
                cierre.append(resolverKnockOut(
                        this.jugadorActual, rivalDe(this.jugadorActual))).append(". ");
            }
        } else if (activoSaliente != null
                && "paralizado".equals(activoSaliente.getEstadoEspecial())) {
            activoSaliente.setEstadoEspecial("normal");
            cierre.append("el estado paralizado termino. ");
        } else if (activoSaliente != null
                && "dormido".equals(activoSaliente.getEstadoEspecial())
                && this.aleatorio.nextBoolean()) {
            activoSaliente.setEstadoEspecial("normal");
            cierre.append("la moneda desperto al pokemon. ");
        }
        if (!estaEnCurso()) {
            cierre.append("la partida finalizo y gana ").append(this.ganador.getNombre());
            return cierre.toString();
        }
        this.jugadorActual = rivalDe(this.jugadorActual);
        this.numeroTurno = this.numeroTurno + 1;
        this.jugadorActual.incrementarTurnosPokemon();
        reiniciarLimitesTurno();
        this.reposicionObligatoria = this.jugadorActual.getPokemonActivo() == null
                && !this.jugadorActual.getBanca().isEmpty();
        cierre.append(mensajeTurnoActual());
        return cierre.toString();
    }

    /**
     * resuelve descarte, premios y victoria despues de un ko
     *
     * @param vencido jugador cuyo pokemon activo fue vencido
     * @param atacante jugador que recibe los premios
     * @return resumen de la resolucion
     */
    private String resolverKnockOut(
            Jugador_LagosRivera_21056415 vencido,
            Jugador_LagosRivera_21056415 atacante) {
        CartaEnJuego_LagosRivera_21056415 pokemon = vencido.descartarActivoVencido();
        int premios = atacante.tomarPremios(pokemon.getCartaActual().esEx() ? 2 : 1);
        String texto = pokemon.getCartaActual().getNombre() + " fue vencido y "
                + atacante.getNombre() + " tomo " + premios + " premio(s)";
        if (atacante.getCantidadPremios() == 0) {
            finalizarPartida(atacante, "tomo su ultimo premio");
        } else if (!vencido.tienePokemonEnJuego()) {
            finalizarPartida(atacante, "el rival quedo sin pokemon en juego");
        }
        return texto;
    }

    /**
     * revisa un posible ko causado fuera del ataque normal
     *
     * @param objetivo instancia que recibio el efecto
     */
    private void revisarKnockOutPorEfecto(CartaEnJuego_LagosRivera_21056415 objetivo) {
        if (objetivo == null || objetivo.getPsActuales() > 0 || !estaEnCurso()) {
            return;
        }
        Jugador_LagosRivera_21056415 controlador = controladorDe(objetivo);
        if (controlador != null && controlador.getPokemonActivo() == objetivo) {
            resolverKnockOut(controlador, rivalDe(controlador));
        }
    }

    /**
     * registra el ganador y el motivo de termino
     *
     * @param ganador jugador que gano la partida
     * @param motivo condicion que produjo la victoria
     */
    private void finalizarPartida(
            Jugador_LagosRivera_21056415 ganador,
            String motivo) {
        this.ganador = ganador;
        this.estadoPartida = "finalizada: " + motivo;
        this.reposicionObligatoria = false;
    }

    /**
     * devuelve el rival de un jugador de esta partida
     *
     * @param jugador participante cuyo rival se busca
     * @return el otro participante
     */
    private Jugador_LagosRivera_21056415 rivalDe(
            Jugador_LagosRivera_21056415 jugador) {
        return jugador == this.jugador1 ? this.jugador2 : this.jugador1;
    }

    /**
     * busca al jugador que controla una instancia
     *
     * @param pokemon instancia que se desea ubicar
     * @return controlador o null si no esta en la mesa
     */
    private Jugador_LagosRivera_21056415 controladorDe(
            CartaEnJuego_LagosRivera_21056415 pokemon) {
        if (this.jugador1.contienePokemon(pokemon)) {
            return this.jugador1;
        }
        if (this.jugador2.contienePokemon(pokemon)) {
            return this.jugador2;
        }
        return null;
    }

    /**
     * busca una instancia entre ambos lados de la mesa
     *
     * @param idInstancia identificador unico de mesa
     * @return instancia encontrada o null
     */
    private CartaEnJuego_LagosRivera_21056415 buscarPokemonEnJuego(long idInstancia) {
        CartaEnJuego_LagosRivera_21056415 pokemon = this.jugador1.buscarPokemon(idInstancia);
        return pokemon != null ? pokemon : this.jugador2.buscarPokemon(idInstancia);
    }

    /**
     * reemplaza una lista de argumentos nula por una lista vacia
     *
     * @param argumentos lista recibida desde el menu
     * @return lista original o una lista vacia
     */
    private List<String> argumentosSeguros(List<String> argumentos) {
        return argumentos == null ? Collections.<String>emptyList() : argumentos;
    }

    /**
     * construye el mensaje corto del turno actual
     *
     * @return nombre del jugador que continua
     */
    private String mensajeTurnoActual() {
        return "ahora juega " + this.jugadorActual.getNombre();
    }

    /**
     * construye el detalle visible de las zonas de un jugador
     *
     * @param jugador participante que se desea mostrar
     * @param mostrarManoCompleta indica si se revelan sus cartas
     * @return texto con sus zonas y cantidades
     */
    private String describirJugador(
            Jugador_LagosRivera_21056415 jugador,
            boolean mostrarManoCompleta) {
        StringBuilder texto = new StringBuilder();
        texto.append("\n--- ").append(jugador.getNombre()).append(" ---\n");
        texto.append("activo: ");
        if (jugador.getPokemonActivo() == null) {
            texto.append("ninguno\n");
        } else {
            texto.append(jugador.getPokemonActivo().comoTextoDetallado()).append("\n");
        }
        texto.append("banca:\n");
        if (jugador.getBanca().isEmpty()) {
            texto.append("  ninguna\n");
        } else {
            for (int i = 0; i < jugador.getBanca().size(); i = i + 1) {
                texto.append("  [").append(i).append("] ")
                        .append(jugador.getBanca().get(i).comoTextoDetallado()).append("\n");
            }
        }
        if (mostrarManoCompleta) {
            texto.append("mano:\n");
            if (jugador.getMano().isEmpty()) {
                texto.append("  vacia\n");
            } else {
                for (int i = 0; i < jugador.getMano().size(); i = i + 1) {
                    texto.append("  [").append(i).append("] ")
                            .append(jugador.getMano().get(i).comoTextoCatalogo()).append("\n");
                }
            }
        } else {
            texto.append("cartas en mano: ").append(jugador.getMano().size()).append("\n");
        }
        texto.append("premios restantes: ").append(jugador.getCantidadPremios()).append("\n");
        texto.append("cartas en mazo: ").append(jugador.getMazo().getCantidadCartas()).append("\n");
        texto.append("descarte: ").append(jugador.descarteComoTexto()).append("\n");
        return texto.toString();
    }
}
