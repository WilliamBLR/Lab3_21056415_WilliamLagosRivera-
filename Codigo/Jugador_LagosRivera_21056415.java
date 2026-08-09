import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * mantiene todas las zonas y cartas controladas por un jugador
 *
 * representacion: id, nombre, mazo, mano, banca, activo, descarte y premios
 */
public class Jugador_LagosRivera_21056415 {
    /** guarda el siguiente id disponible para los jugadores */
    private static int siguienteId = 0;

    /** identifica al jugador dentro de la ejecucion */
    private final int id;
    /** guarda el nombre visible del jugador */
    private final String nombre;
    /** contiene el mazo independiente usado en la partida */
    private final Mazo_LagosRivera_21056415 mazo;
    /** contiene las cartas disponibles para jugar */
    private final ArrayList<Carta_LagosRivera_21056415> mano;
    /** contiene hasta cinco pokemon de reserva */
    private final ArrayList<CartaEnJuego_LagosRivera_21056415> banca;
    /** referencia el pokemon que combate al frente */
    private CartaEnJuego_LagosRivera_21056415 pokemonActivo;
    /** contiene las cartas que salieron del juego */
    private final ArrayList<Carta_LagosRivera_21056415> pilaDescarte;
    /** contiene las cartas que faltan por obtener como premio */
    private final ArrayList<Carta_LagosRivera_21056415> premios;

    /**
     * construye un jugador con todas sus zonas vacias
     *
     * @param nombre nombre visible
     * @param mazo copia del mazo usado en la partida
     */
    public Jugador_LagosRivera_21056415(
            String nombre, Mazo_LagosRivera_21056415 mazo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre del jugador no puede estar vacio");
        }
        if (mazo == null) {
            throw new IllegalArgumentException("el jugador debe tener un mazo");
        }
        this.id = siguienteId;
        siguienteId = siguienteId + 1;
        this.nombre = nombre.trim();
        this.mazo = mazo;
        this.mano = new ArrayList<Carta_LagosRivera_21056415>();
        this.banca = new ArrayList<CartaEnJuego_LagosRivera_21056415>();
        this.pokemonActivo = null;
        this.pilaDescarte = new ArrayList<Carta_LagosRivera_21056415>();
        this.premios = new ArrayList<Carta_LagosRivera_21056415>();
    }

    /**
     * devuelve el id propio del jugador
     *
     * @return identificador autoincremental
     */
    public int getId() {
        return this.id;
    }

    /**
     * devuelve el nombre visible
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * devuelve el mazo usado en la partida
     *
     * @return mazo jugable
     */
    public Mazo_LagosRivera_21056415 getMazo() {
        return this.mazo;
    }

    /**
     * devuelve el pokemon activo
     *
     * @return activo o null
     */
    public CartaEnJuego_LagosRivera_21056415 getPokemonActivo() {
        return this.pokemonActivo;
    }

    /**
     * devuelve una vista de la mano
     *
     * @return cartas en mano
     */
    public List<Carta_LagosRivera_21056415> getMano() {
        return Collections.unmodifiableList(this.mano);
    }

    /**
     * devuelve una vista de la banca
     *
     * @return pokemon en banca
     */
    public List<CartaEnJuego_LagosRivera_21056415> getBanca() {
        return Collections.unmodifiableList(this.banca);
    }

    /**
     * devuelve la cantidad de premios restantes
     *
     * @return premios que faltan por tomar
     */
    public int getCantidadPremios() {
        return this.premios.size();
    }

    /**
     * roba siete cartas y repite hasta encontrar un pokemon basico
     *
     * @param aleatorio generador usado para cada nuevo barajado
     */
    public void prepararManoInicial(Random aleatorio) {
        boolean tieneBasico = false;
        while (!tieneBasico) {
            this.mano.clear();
            for (int i = 0; i < 7; i = i + 1) {
                Carta_LagosRivera_21056415 carta = this.mazo.robarCartaSuperior();
                if (carta == null) {
                    throw new IllegalStateException("el mazo no alcanzo para repartir la mano inicial");
                }
                this.mano.add(carta);
            }
            tieneBasico = tienePokemonBasico(this.mano);
            if (!tieneBasico) {
                this.mazo.devolverCartas(this.mano);
                this.mano.clear();
                this.mazo.barajar(aleatorio);
            }
        }
    }

    /** separa las siguientes seis cartas como premios */
    public void prepararPremios() {
        for (int i = 0; i < 6; i = i + 1) {
            Carta_LagosRivera_21056415 carta = this.mazo.robarCartaSuperior();
            if (carta == null) {
                throw new IllegalStateException("el mazo no alcanzo para separar los premios");
            }
            this.premios.add(carta);
        }
    }

    /**
     * roba la carta superior para la accion normal del turno
     *
     * @return carta robada o null cuando el mazo esta vacio
     */
    public Carta_LagosRivera_21056415 robarCarta() {
        Carta_LagosRivera_21056415 carta = this.mazo.robarCartaSuperior();
        if (carta != null) {
            this.mano.add(carta);
        }
        return carta;
    }

    /**
     * roba por un efecto sin provocar derrota con mazo vacio
     *
     * @param cantidad cantidad maxima que intenta robar
     * @return cantidad que realmente pudo robar
     */
    public int robarPorEfecto(int cantidad) {
        int robadas = 0;
        for (int i = 0; i < cantidad; i = i + 1) {
            Carta_LagosRivera_21056415 carta = this.mazo.robarCartaSuperior();
            if (carta == null) {
                break;
            }
            this.mano.add(carta);
            robadas = robadas + 1;
        }
        return robadas;
    }

    /**
     * pone un pokemon basico de la mano en la banca
     *
     * @param indiceMano posicion dentro de la mano
     * @return nueva instancia puesta en juego
     */
    public CartaEnJuego_LagosRivera_21056415 jugarPokemonABanca(int indiceMano) {
        if (this.banca.size() >= 5) {
            throw new IllegalArgumentException("la banca ya tiene cinco pokemon");
        }
        Carta_LagosRivera_21056415 carta = getCartaMano(indiceMano);
        if (!(carta instanceof CartaPokemon_LagosRivera_21056415)) {
            throw new IllegalArgumentException("la carta seleccionada no es un pokemon");
        }
        CartaPokemon_LagosRivera_21056415 pokemon =
                (CartaPokemon_LagosRivera_21056415) carta;
        if (!pokemon.esBasico()) {
            throw new IllegalArgumentException("solo un pokemon basico puede jugarse a la banca");
        }
        CartaEnJuego_LagosRivera_21056415 instancia =
                new CartaEnJuego_LagosRivera_21056415(pokemon);
        this.banca.add(instancia);
        this.mano.remove(indiceMano);
        return instancia;
    }

    /**
     * pone un pokemon de la banca como activo y paga la retirada si corresponde
     *
     * @param indiceBanca posicion de la instancia en banca
     * @return costo de retirada pagado
     */
    public int cambiarPokemonActivo(int indiceBanca) {
        if (indiceBanca < 0 || indiceBanca >= this.banca.size()) {
            throw new IllegalArgumentException("el indice de banca no existe");
        }
        CartaEnJuego_LagosRivera_21056415 nuevoActivo = this.banca.get(indiceBanca);
        int costoPagado = 0;
        if (this.pokemonActivo != null) {
            costoPagado = this.pokemonActivo.getCartaActual().getCostoRetirada();
            List<CartaEnergia_LagosRivera_21056415> energias =
                    this.pokemonActivo.retirarEnergias(costoPagado);
            this.pilaDescarte.addAll(energias);
            this.banca.add(this.pokemonActivo);
        }
        this.pokemonActivo = nuevoActivo;
        this.banca.remove(indiceBanca);
        return costoPagado;
    }

    /**
     * busca una instancia propia entre activo y banca
     *
     * @param idInstancia identificador unico
     * @return instancia o null
     */
    public CartaEnJuego_LagosRivera_21056415 buscarPokemon(long idInstancia) {
        if (this.pokemonActivo != null
                && this.pokemonActivo.getIdInstancia() == idInstancia) {
            return this.pokemonActivo;
        }
        for (CartaEnJuego_LagosRivera_21056415 pokemon : this.banca) {
            if (pokemon.getIdInstancia() == idInstancia) {
                return pokemon;
            }
        }
        return null;
    }

    /**
     * revisa si la instancia pertenece a este jugador
     *
     * @param pokemon instancia que se revisa
     * @return true cuando esta como activo o en banca
     */
    public boolean contienePokemon(CartaEnJuego_LagosRivera_21056415 pokemon) {
        return pokemon != null && buscarPokemon(pokemon.getIdInstancia()) == pokemon;
    }

    /**
     * revisa si queda un pokemon propio en la mesa
     *
     * @return true cuando hay activo o al menos uno en banca
     */
    public boolean tienePokemonEnJuego() {
        return this.pokemonActivo != null || !this.banca.isEmpty();
    }

    /**
     * incrementa el contador de todos los pokemon propios en juego
     */
    public void incrementarTurnosPokemon() {
        if (this.pokemonActivo != null) {
            this.pokemonActivo.incrementarTurnosEnJuego();
        }
        for (CartaEnJuego_LagosRivera_21056415 pokemon : this.banca) {
            pokemon.incrementarTurnosEnJuego();
        }
    }

    /**
     * obtiene una carta validando el indice de la mano
     *
     * @param indice posicion solicitada
     * @return carta encontrada
     */
    public Carta_LagosRivera_21056415 getCartaMano(int indice) {
        if (indice < 0 || indice >= this.mano.size()) {
            throw new IllegalArgumentException("el indice de la mano no existe");
        }
        return this.mano.get(indice);
    }

    /**
     * retira una carta despues de una accion valida
     *
     * @param indice posicion dentro de la mano
     */
    public void retirarCartaMano(int indice) {
        getCartaMano(indice);
        this.mano.remove(indice);
    }

    /**
     * agrega una carta usada al descarte
     *
     * @param carta carta que se descarta
     */
    public void descartarCarta(Carta_LagosRivera_21056415 carta) {
        if (carta != null) {
            this.pilaDescarte.add(carta);
        }
    }

    /**
     * descarta al activo vencido con energias y todas sus etapas
     *
     * @return instancia que salio del juego
     */
    public CartaEnJuego_LagosRivera_21056415 descartarActivoVencido() {
        if (this.pokemonActivo == null) {
            throw new IllegalStateException("no existe un pokemon activo para descartar");
        }
        CartaEnJuego_LagosRivera_21056415 vencido = this.pokemonActivo;
        this.pilaDescarte.addAll(vencido.getCartasPokemonParaDescarte());
        this.pilaDescarte.addAll(vencido.getEnergiasUnidas());
        this.pokemonActivo = null;
        return vencido;
    }

    /**
     * mueve premios a la mano
     *
     * @param cantidad uno o dos segun el pokemon vencido
     * @return cantidad efectivamente tomada
     */
    public int tomarPremios(int cantidad) {
        int tomadas = 0;
        for (int i = 0; i < cantidad && !this.premios.isEmpty(); i = i + 1) {
            this.mano.add(this.premios.remove(0));
            tomadas = tomadas + 1;
        }
        return tomadas;
    }

    /**
     * construye los nombres de todas las cartas descartadas
     *
     * @return listado legible para mostrar el juego
     */
    public String descarteComoTexto() {
        if (this.pilaDescarte.isEmpty()) {
            return "ninguna";
        }
        StringBuilder texto = new StringBuilder();
        for (Carta_LagosRivera_21056415 carta : this.pilaDescarte) {
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append(carta.getNombre());
        }
        return texto.toString();
    }

    /**
     * revisa si una lista permite conservar la mano inicial
     *
     * @param cartas cartas repartidas al jugador
     * @return true cuando existe un pokemon basico
     */
    private static boolean tienePokemonBasico(List<Carta_LagosRivera_21056415> cartas) {
        for (Carta_LagosRivera_21056415 carta : cartas) {
            if (carta instanceof CartaPokemon_LagosRivera_21056415
                    && ((CartaPokemon_LagosRivera_21056415) carta).esBasico()) {
                return true;
            }
        }
        return false;
    }
}
