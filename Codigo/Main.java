import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * entrega el menu interactivo para crear datos y jugar una partida completa
 *
 * representacion: catalogos compartidos, entrada de consola y partida actual
 */
public class Main {
    /** recibe todas las entradas escritas en el menu */
    private static final Scanner ENTRADA = new Scanner(System.in);
    /** mantiene las cartas creadas durante la ejecucion */
    private static final ArrayList<Carta_LagosRivera_21056415> CARTAS =
            new ArrayList<Carta_LagosRivera_21056415>();
    /** mantiene los ataques y habilidades creados */
    private static final ArrayList<Ataque_LagosRivera_21056415> ATAQUES =
            new ArrayList<Ataque_LagosRivera_21056415>();
    /** mantiene los mazos validos disponibles */
    private static final ArrayList<Mazo_LagosRivera_21056415> MAZOS =
            new ArrayList<Mazo_LagosRivera_21056415>();

    /** referencia la partida que se controla desde el menu */
    private static Juego_LagosRivera_21056415 partidaActual;

    /**
     * evita crear instancias de la clase que solamente contiene el menu
     */
    private Main() {
    }

    /**
     * carga una demostracion legal y mantiene el menu hasta que el usuario sale
     *
     * @param args argumentos de consola no utilizados
     */
    public static void main(String[] args) {
        cargarDatosDemostracion();
        boolean ejecutando = true;
        System.out.println("[sistema] jugadores, cartas y dos mazos legales cargados");
        while (ejecutando) {
            mostrarMenu();
            int opcion = leerEntero("seleccione una opcion: ");
            try {
                ejecutando = ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | IllegalStateException excepcion) {
                System.out.println("[error] " + excepcion.getMessage());
            }
        }
        ENTRADA.close();
        System.out.println("[sistema] programa finalizado");
    }

    /** imprime todas las opciones disponibles */
    private static void mostrarMenu() {
        System.out.println("\n######## pokemon tcg - laboratorio 3 ########");
        System.out.println("--- construccion y preparacion ---");
        System.out.println("1. crear carta de energia                 (rf03)");
        System.out.println("2. crear ataque                           (rf04)");
        System.out.println("3. crear carta pokemon                    (rf05)");
        System.out.println("4. crear carta de entrenador              (rf06)");
        System.out.println("5. crear mazo                             (rf07)");
        System.out.println("6. barajar mazo                           (rf08)");
        System.out.println("7. iniciar juego                          (rf09)");
        System.out.println("8. mostrar catalogos");
        System.out.println("--- durante la partida ---");
        System.out.println("9. mostrar juego                          (rf10)");
        System.out.println("10. jugar pokemon a la banca              (rf11)");
        System.out.println("11. cambiar pokemon activo                (rf12)");
        System.out.println("12. robar carta                           (rf13)");
        System.out.println("13. usar carta de energia                 (rf14)");
        System.out.println("14. evolucionar pokemon                   (rf15)");
        System.out.println("15. usar carta de entrenador              (rf16)");
        System.out.println("16. usar habilidad pokemon                (rf17)");
        System.out.println("17. usar ataque o pasar                   (rf18)");
        System.out.println("0. salir");
    }

    /**
     * dirige una opcion a la operacion correspondiente
     *
     * @param opcion numero seleccionado en el menu
     * @return false solamente cuando se debe salir
     */
    private static boolean ejecutarOpcion(int opcion) {
        if (opcion == 0) {
            return false;
        }
        switch (opcion) {
            case 1:
                crearCartaEnergia();
                break;
            case 2:
                crearAtaque();
                break;
            case 3:
                crearCartaPokemon();
                break;
            case 4:
                crearCartaEntrenador();
                break;
            case 5:
                crearMazo();
                break;
            case 6:
                barajarMazo();
                break;
            case 7:
                iniciarJuego();
                break;
            case 8:
                mostrarCatalogos();
                break;
            case 9:
                System.out.println(obtenerPartida().mostrarJuego());
                break;
            case 10:
                jugarABanca();
                break;
            case 11:
                cambiarPokemonActivo();
                break;
            case 12:
                System.out.println("[ok] " + obtenerPartida().robarCarta());
                break;
            case 13:
                usarCartaEnergia();
                break;
            case 14:
                evolucionarPokemon();
                break;
            case 15:
                usarCartaEntrenador();
                break;
            case 16:
                usarHabilidadPokemon();
                break;
            case 17:
                usarAtaquePokemon();
                break;
            default:
                System.out.println("[error] opcion invalida");
                break;
        }
        return true;
    }

    /** solicita y registra una carta de energia */
    private static void crearCartaEnergia() {
        String expansion = leerTexto("expansion: ");
        int numero = leerEntero("numero: ");
        String tipo = leerTexto("tipo de energia: ");
        CartaEnergia_LagosRivera_21056415 carta =
                new CartaEnergia_LagosRivera_21056415(expansion, numero, tipo);
        CARTAS.add(carta);
        System.out.println("[ok] energia creada con id " + carta.getId());
    }

    /** solicita y registra un ataque o habilidad */
    private static void crearAtaque() {
        String nombre = leerTexto("nombre del ataque: ");
        String descripcion = leerTexto("descripcion: ");
        int dano = leerEntero("dano base: ");
        int cantidadTipos = leerEntero("cantidad de pares de costo: ");
        if (cantidadTipos < 0) {
            throw new IllegalArgumentException("la cantidad de pares no puede ser negativa");
        }
        LinkedHashMap<String, Integer> costo = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < cantidadTipos; i = i + 1) {
            String tipo = leerTexto("tipo de energia del par " + (i + 1) + ": ");
            int cantidad = leerEntero("cantidad para " + tipo + ": ");
            costo.put(tipo, cantidad);
        }
        Efecto_LagosRivera_21056415 efecto = crearEfectoDesdeMenu();
        Ataque_LagosRivera_21056415 ataque = new Ataque_LagosRivera_21056415(
                costo, nombre, descripcion, dano, efecto);
        ATAQUES.add(ataque);
        System.out.println("[ok] ataque creado con id " + ataque.getId());
    }

    /** solicita y registra una carta pokemon */
    private static void crearCartaPokemon() {
        mostrarAtaques();
        String expansion = leerTexto("expansion: ");
        int numero = leerEntero("numero: ");
        String nombre = leerTexto("nombre: ");
        String evolucionaDe = leerTextoOpcional("evoluciona de, vacio si es basico: ");
        int ps = leerEntero("ps: ");
        String tipo = leerTexto("tipo: ");
        String debilidad = leerTextoOpcional("debilidad, vacio si no tiene: ");
        String resistencia = leerTextoOpcional("resistencia, vacio si no tiene: ");
        int retirada = leerEntero("costo de retirada: ");
        boolean esEx = leerBooleano("es ex? (s/n): ");
        Integer idHabilidad = leerEnteroOpcional("id de habilidad, vacio si no tiene: ");
        Ataque_LagosRivera_21056415 habilidad = idHabilidad == null
                ? null : buscarAtaque(idHabilidad.intValue());
        String idsAtaques = leerTextoOpcional(
                "ids de ataques separados por coma, vacio si no tiene: ");
        ArrayList<Ataque_LagosRivera_21056415> ataquesPokemon =
                new ArrayList<Ataque_LagosRivera_21056415>();
        for (Integer id : convertirEnteros(idsAtaques)) {
            ataquesPokemon.add(buscarAtaque(id.intValue()));
        }
        CartaPokemon_LagosRivera_21056415 carta =
                new CartaPokemon_LagosRivera_21056415(
                        expansion, numero, nombre, evolucionaDe, ps, tipo,
                        debilidad, resistencia, retirada, esEx, habilidad, ataquesPokemon);
        CARTAS.add(carta);
        System.out.println("[ok] pokemon creado con id " + carta.getId());
    }

    /** solicita y registra una carta de entrenador */
    private static void crearCartaEntrenador() {
        String expansion = leerTexto("expansion: ");
        int numero = leerEntero("numero: ");
        String nombre = leerTexto("nombre: ");
        String tipo = leerTexto("tipo partidario u objeto: ");
        String texto = leerTexto("texto de la carta: ");
        Efecto_LagosRivera_21056415 efecto = crearEfectoDesdeMenu();
        CartaEntrenador_LagosRivera_21056415 carta =
                new CartaEntrenador_LagosRivera_21056415(
                        expansion, numero, nombre, tipo, texto, efecto);
        CARTAS.add(carta);
        System.out.println("[ok] entrenador creado con id " + carta.getId());
    }

    /** crea un mazo usando ids del catalogo */
    private static void crearMazo() {
        mostrarCartas();
        String nombre = leerTexto("nombre del mazo: ");
        String ids = leerTexto("ingrese exactamente 60 ids separados por coma o espacio: ");
        ArrayList<Carta_LagosRivera_21056415> cartasMazo =
                new ArrayList<Carta_LagosRivera_21056415>();
        for (Integer id : convertirEnteros(ids)) {
            cartasMazo.add(buscarCarta(id.intValue()));
        }
        Mazo_LagosRivera_21056415 mazo =
                new Mazo_LagosRivera_21056415(nombre, cartasMazo);
        MAZOS.add(mazo);
        System.out.println("[ok] mazo creado con id " + mazo.getId());
    }

    /** baraja uno de los mazos disponibles */
    private static void barajarMazo() {
        mostrarMazos();
        int id = leerEntero("id del mazo: ");
        Mazo_LagosRivera_21056415 mazo = buscarMazo(id);
        mazo.barajar();
        System.out.println("[ok] mazo " + mazo.getNombre() + " barajado");
    }

    /** prepara una partida con dos jugadores y mazos */
    private static void iniciarJuego() {
        mostrarMazos();
        int primerId = MAZOS.get(0).getId();
        int segundoId = MAZOS.size() > 1 ? MAZOS.get(1).getId() : primerId;
        Integer id1 = leerEnteroOpcional("id mazo jugador 1, vacio para " + primerId + ": ");
        Integer id2 = leerEnteroOpcional("id mazo jugador 2, vacio para " + segundoId + ": ");
        String nombre1 = leerTextoOpcional("nombre jugador 1, vacio para ash: ");
        String nombre2 = leerTextoOpcional("nombre jugador 2, vacio para gary: ");
        Mazo_LagosRivera_21056415 mazo1 = buscarMazo(id1 == null ? primerId : id1.intValue());
        Mazo_LagosRivera_21056415 mazo2 = buscarMazo(id2 == null ? segundoId : id2.intValue());
        Jugador_LagosRivera_21056415 jugador1 =
                new Jugador_LagosRivera_21056415(
                        nombre1 == null ? "ash" : nombre1, mazo1.crearCopiaJugable());
        Jugador_LagosRivera_21056415 jugador2 =
                new Jugador_LagosRivera_21056415(
                        nombre2 == null ? "gary" : nombre2, mazo2.crearCopiaJugable());
        partidaActual = new Juego_LagosRivera_21056415(jugador1, jugador2);
        System.out.println("[ok] " + partidaActual.iniciarJuego());
    }

    /** juega un pokemon basico de la mano a la banca */
    private static void jugarABanca() {
        System.out.println(obtenerPartida().mostrarJuego());
        int indice = leerEntero("indice de la carta pokemon en mano: ");
        System.out.println("[ok] " + obtenerPartida().jugarABanca(indice));
    }

    /** cambia el pokemon activo por uno de la banca */
    private static void cambiarPokemonActivo() {
        System.out.println(obtenerPartida().mostrarJuego());
        int indice = leerEntero("indice del pokemon en banca: ");
        System.out.println("[ok] " + obtenerPartida().cambiarPokemonActivo(indice));
    }

    /** une una energia de la mano a un pokemon propio */
    private static void usarCartaEnergia() {
        System.out.println(obtenerPartida().mostrarJuego());
        long idPokemon = leerLong("id de instancia que recibe la energia: ");
        int indice = leerEntero("indice de la energia en mano: ");
        System.out.println("[ok] " + obtenerPartida().usarCartaEnergia(idPokemon, indice));
    }

    /** evoluciona una instancia propia usando la mano */
    private static void evolucionarPokemon() {
        System.out.println(obtenerPartida().mostrarJuego());
        long idPokemon = leerLong("id de instancia que evoluciona: ");
        int indice = leerEntero("indice de la evolucion en mano: ");
        System.out.println("[ok] " + obtenerPartida().evolucionarPokemon(idPokemon, indice));
    }

    /** ejecuta una carta de entrenador de la mano */
    private static void usarCartaEntrenador() {
        System.out.println(obtenerPartida().mostrarJuego());
        int indice = leerEntero("indice del entrenador en mano: ");
        Long objetivo = leerLongOpcional("id de instancia objetivo, vacio si no necesita: ");
        List<String> argumentos = leerArgumentos();
        System.out.println("[ok] " + obtenerPartida().usarCartaEntrenador(
                indice, objetivo, argumentos));
    }

    /** ejecuta la habilidad de un pokemon propio */
    private static void usarHabilidadPokemon() {
        System.out.println(obtenerPartida().mostrarJuego());
        long origen = leerLong("id de la instancia que usa su habilidad: ");
        Long objetivo = leerLongOpcional("id de instancia objetivo, vacio si no necesita: ");
        List<String> argumentos = leerArgumentos();
        System.out.println("[ok] " + obtenerPartida().usarHabilidadPokemon(
                origen, objetivo, argumentos));
    }

    /** ataca con el pokemon activo o pasa el turno */
    private static void usarAtaquePokemon() {
        System.out.println(obtenerPartida().mostrarAtaquesDelActivo());
        String nombre = leerTextoOpcional("nombre exacto del ataque, vacio para pasar: ");
        List<String> argumentos = leerArgumentos();
        System.out.println("[ok] " + obtenerPartida().usarAtaquePokemon(nombre, argumentos));
    }

    /**
     * construye el efecto elegido en el menu
     *
     * @return efecto polimorfico listo para asociar
     */
    private static Efecto_LagosRivera_21056415 crearEfectoDesdeMenu() {
        System.out.println("efectos: 1 sin efecto | 2 dano fijo | 3 curar | 4 estado | 5 robar");
        int opcion = leerEntero("tipo de efecto: ");
        if (opcion == 1) {
            return new EfectoSinEfecto_LagosRivera_21056415();
        }
        if (opcion == 2) {
            return new EfectoDanio_LagosRivera_21056415(
                    leerEntero("cantidad de dano: "));
        }
        if (opcion == 3) {
            return new EfectoCurar_LagosRivera_21056415(
                    leerEntero("cantidad de curacion: "));
        }
        if (opcion == 4) {
            return new EfectoEstado_LagosRivera_21056415(
                    leerTexto("estado aplicado: "));
        }
        if (opcion == 5) {
            return new EfectoRobar_LagosRivera_21056415(
                    leerEntero("cantidad de cartas: "));
        }
        throw new IllegalArgumentException("tipo de efecto invalido");
    }

    /** imprime cartas, ataques y mazos registrados */
    private static void mostrarCatalogos() {
        mostrarCartas();
        mostrarAtaques();
        mostrarMazos();
    }

    /** imprime el catalogo actual de cartas */
    private static void mostrarCartas() {
        System.out.println("\n--- cartas ---");
        for (Carta_LagosRivera_21056415 carta : CARTAS) {
            System.out.println(carta.comoTextoCatalogo());
        }
    }

    /** imprime el catalogo actual de ataques */
    private static void mostrarAtaques() {
        System.out.println("\n--- ataques y habilidades ---");
        for (Ataque_LagosRivera_21056415 ataque : ATAQUES) {
            System.out.println(ataque.comoTextoCatalogo());
        }
    }

    /** imprime el catalogo actual de mazos */
    private static void mostrarMazos() {
        System.out.println("\n--- mazos ---");
        for (Mazo_LagosRivera_21056415 mazo : MAZOS) {
            System.out.println(mazo.comoTextoCatalogo());
        }
    }

    /**
     * devuelve la partida actual despues de validar su existencia
     *
     * @return partida iniciada desde el menu
     */
    private static Juego_LagosRivera_21056415 obtenerPartida() {
        if (partidaActual == null) {
            throw new IllegalStateException("primero debe iniciar el juego con la opcion 7");
        }
        return partidaActual;
    }

    /**
     * busca una carta por su identificador
     *
     * @param id identificador global de carta
     * @return carta encontrada
     */
    private static Carta_LagosRivera_21056415 buscarCarta(int id) {
        for (Carta_LagosRivera_21056415 carta : CARTAS) {
            if (carta.getId() == id) {
                return carta;
            }
        }
        throw new IllegalArgumentException("no existe una carta con id " + id);
    }

    /**
     * busca un ataque por su identificador
     *
     * @param id identificador del ataque
     * @return ataque encontrado
     */
    private static Ataque_LagosRivera_21056415 buscarAtaque(int id) {
        for (Ataque_LagosRivera_21056415 ataque : ATAQUES) {
            if (ataque.getId() == id) {
                return ataque;
            }
        }
        throw new IllegalArgumentException("no existe un ataque con id " + id);
    }

    /**
     * busca un mazo por su identificador
     *
     * @param id identificador del mazo
     * @return mazo encontrado
     */
    private static Mazo_LagosRivera_21056415 buscarMazo(int id) {
        for (Mazo_LagosRivera_21056415 mazo : MAZOS) {
            if (mazo.getId() == id) {
                return mazo;
            }
        }
        throw new IllegalArgumentException("no existe un mazo con id " + id);
    }

    /**
     * convierte una lista de ids separados por coma
     *
     * @param texto entrada escrita por el usuario
     * @return numeros obtenidos en el mismo orden
     */
    private static List<Integer> convertirEnteros(String texto) {
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        if (texto == null || texto.trim().isEmpty()) {
            return numeros;
        }
        String[] partes = texto.trim().split("[,\\s]+");
        for (String parte : partes) {
            try {
                numeros.add(Integer.valueOf(parte));
            } catch (NumberFormatException excepcion) {
                throw new IllegalArgumentException("el valor " + parte + " no es un id valido");
            }
        }
        return numeros;
    }

    /**
     * lee argumentos opcionales separados por coma
     *
     * @return argumentos limpios en el mismo orden
     */
    private static List<String> leerArgumentos() {
        String texto = leerTextoOpcional(
                "argumentos adicionales separados por coma, vacio si no necesita: ");
        if (texto == null) {
            return new ArrayList<String>();
        }
        return new ArrayList<String>(Arrays.asList(texto.split("\\s*,\\s*")));
    }

    /**
     * insiste hasta recibir un numero entero
     *
     * @param mensaje texto que indica el dato solicitado
     * @return numero ingresado
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException excepcion) {
                System.out.println("[error] ingrese un numero entero");
            }
        }
    }

    /**
     * lee un entero o null cuando la entrada queda vacia
     *
     * @param mensaje texto que indica el dato solicitado
     * @return numero ingresado o null
     */
    private static Integer leerEnteroOpcional(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return Integer.valueOf(texto);
            } catch (NumberFormatException excepcion) {
                System.out.println("[error] ingrese un entero o deje vacio");
            }
        }
    }

    /**
     * insiste hasta recibir un numero long
     *
     * @param mensaje texto que indica el dato solicitado
     * @return numero ingresado
     */
    private static long leerLong(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).trim();
            try {
                return Long.parseLong(texto);
            } catch (NumberFormatException excepcion) {
                System.out.println("[error] ingrese un identificador numerico");
            }
        }
    }

    /**
     * lee un long o null cuando la entrada queda vacia
     *
     * @param mensaje texto que indica el dato solicitado
     * @return numero ingresado o null
     */
    private static Long leerLongOpcional(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return Long.valueOf(texto);
            } catch (NumberFormatException excepcion) {
                System.out.println("[error] ingrese un identificador o deje vacio");
            }
        }
    }

    /**
     * insiste hasta recibir un texto no vacio
     *
     * @param mensaje texto que indica el dato solicitado
     * @return entrada sin espacios exteriores
     */
    private static String leerTexto(String mensaje) {
        String texto = leerLinea(mensaje).trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("el texto no puede quedar vacio");
        }
        return texto;
    }

    /**
     * lee un texto que puede quedar vacio
     *
     * @param mensaje texto que indica el dato solicitado
     * @return entrada sin espacios exteriores
     */
    private static String leerTextoOpcional(String mensaje) {
        String texto = leerLinea(mensaje).trim();
        return texto.isEmpty() ? null : texto;
    }

    /**
     * interpreta las respuestas si o no
     *
     * @param mensaje texto que indica el dato solicitado
     * @return true para si y false para no
     */
    private static boolean leerBooleano(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje).trim();
            if ("s".equalsIgnoreCase(texto) || "si".equalsIgnoreCase(texto)) {
                return true;
            }
            if ("n".equalsIgnoreCase(texto) || "no".equalsIgnoreCase(texto)) {
                return false;
            }
            System.out.println("[error] responda s o n");
        }
    }

    /**
     * muestra un mensaje y lee una linea completa
     *
     * @param mensaje texto que se imprime antes de leer
     * @return linea ingresada
     */
    private static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!ENTRADA.hasNextLine()) {
            throw new IllegalStateException("la entrada de consola termino inesperadamente");
        }
        return ENTRADA.nextLine();
    }

    /** carga cartas y mazos legales para entrar directo a jugar */
    private static void cargarDatosDemostracion() {
        Map<String, Integer> costoRayo = costo("Rayo", 1);
        Map<String, Integer> costoAgua = costo("Agua", 1);
        Map<String, Integer> costoRayoIncoloro = costo("Rayo", 1, "Incolora", 1);
        Map<String, Integer> costoAguaIncoloro = costo("Agua", 1, "Incolora", 1);
        Map<String, Integer> sinCosto = new LinkedHashMap<String, Integer>();

        Ataque_LagosRivera_21056415 recuperacion =
                new Ataque_LagosRivera_21056415(
                        sinCosto, "recuperacion", "cura 10 de dano", 0,
                        new EfectoCurar_LagosRivera_21056415(10));
        Ataque_LagosRivera_21056415 impactrueno =
                new Ataque_LagosRivera_21056415(
                        costoRayo, "impactrueno", "puede paralizar al rival", 20,
                        new EfectoEstado_LagosRivera_21056415("paralizado"));
        Ataque_LagosRivera_21056415 golpeRayo =
                new Ataque_LagosRivera_21056415(
                        costoRayoIncoloro, "golpe rayo", "ataque electrico directo", 40,
                        new EfectoSinEfecto_LagosRivera_21056415());
        Ataque_LagosRivera_21056415 caparazon =
                new Ataque_LagosRivera_21056415(
                        sinCosto, "caparazon", "cura 10 de dano", 0,
                        new EfectoCurar_LagosRivera_21056415(10));
        Ataque_LagosRivera_21056415 chorroAgua =
                new Ataque_LagosRivera_21056415(
                        costoAgua, "chorro de agua", "ataque de agua", 20,
                        new EfectoSinEfecto_LagosRivera_21056415());
        Ataque_LagosRivera_21056415 olaFuerte =
                new Ataque_LagosRivera_21056415(
                        costoAguaIncoloro, "ola fuerte", "ataque de agua directo", 40,
                        new EfectoSinEfecto_LagosRivera_21056415());
        ATAQUES.addAll(Arrays.asList(
                recuperacion, impactrueno, golpeRayo, caparazon, chorroAgua, olaFuerte));

        CartaPokemon_LagosRivera_21056415 pikachu =
                new CartaPokemon_LagosRivera_21056415(
                        "demo rayo", 1, "pikachu", null, 70, "rayo", "lucha",
                        "metalica", 1, false, recuperacion,
                        Arrays.asList(impactrueno, golpeRayo));
        CartaPokemon_LagosRivera_21056415 raichu =
                new CartaPokemon_LagosRivera_21056415(
                        "demo rayo", 2, "raichu", "pikachu", 120, "rayo", "lucha",
                        "metalica", 2, false, null, Arrays.asList(golpeRayo));
        CartaEnergia_LagosRivera_21056415 energiaRayo =
                new CartaEnergia_LagosRivera_21056415("demo rayo", 3, "rayo");
        CartaEntrenador_LagosRivera_21056415 pocionRayo =
                new CartaEntrenador_LagosRivera_21056415(
                        "demo rayo", 4, "pocion rayo", "objeto", "cura 20 de dano",
                        new EfectoCurar_LagosRivera_21056415(20));
        CartaEntrenador_LagosRivera_21056415 investigacionRayo =
                new CartaEntrenador_LagosRivera_21056415(
                        "demo rayo", 5, "investigacion rayo", "partidario", "roba dos cartas",
                        new EfectoRobar_LagosRivera_21056415(2));

        CartaPokemon_LagosRivera_21056415 squirtle =
                new CartaPokemon_LagosRivera_21056415(
                        "demo agua", 1, "squirtle", null, 70, "agua", "planta",
                        null, 1, false, caparazon, Arrays.asList(chorroAgua, olaFuerte));
        CartaPokemon_LagosRivera_21056415 wartortle =
                new CartaPokemon_LagosRivera_21056415(
                        "demo agua", 2, "wartortle", "squirtle", 120, "agua", "planta",
                        null, 2, false, null, Arrays.asList(olaFuerte));
        CartaEnergia_LagosRivera_21056415 energiaAgua =
                new CartaEnergia_LagosRivera_21056415("demo agua", 3, "agua");
        CartaEntrenador_LagosRivera_21056415 pocionAgua =
                new CartaEntrenador_LagosRivera_21056415(
                        "demo agua", 4, "pocion agua", "objeto", "cura 20 de dano",
                        new EfectoCurar_LagosRivera_21056415(20));
        CartaEntrenador_LagosRivera_21056415 investigacionAgua =
                new CartaEntrenador_LagosRivera_21056415(
                        "demo agua", 5, "investigacion agua", "partidario", "roba dos cartas",
                        new EfectoRobar_LagosRivera_21056415(2));

        CARTAS.addAll(Arrays.asList(
                pikachu, raichu, energiaRayo, pocionRayo, investigacionRayo,
                squirtle, wartortle, energiaAgua, pocionAgua, investigacionAgua));

        ArrayList<Carta_LagosRivera_21056415> cartasRayo = crearListaDemo(
                pikachu, raichu, energiaRayo, pocionRayo, investigacionRayo);
        ArrayList<Carta_LagosRivera_21056415> cartasAgua = crearListaDemo(
                squirtle, wartortle, energiaAgua, pocionAgua, investigacionAgua);
        MAZOS.add(new Mazo_LagosRivera_21056415("mazo rayo demo", cartasRayo));
        MAZOS.add(new Mazo_LagosRivera_21056415("mazo agua demo", cartasAgua));
    }

    /**
     * construye un mazo de demostracion con sesenta cartas
     *
     * @param basico pokemon basico principal
     * @param evolucion evolucion compatible
     * @param energia energia basica del mazo
     * @param objeto entrenador de tipo objeto
     * @param partidario entrenador de tipo partidario
     * @return lista legal de sesenta cartas
     */
    private static ArrayList<Carta_LagosRivera_21056415> crearListaDemo(
            CartaPokemon_LagosRivera_21056415 basico,
            CartaPokemon_LagosRivera_21056415 evolucion,
            CartaEnergia_LagosRivera_21056415 energia,
            CartaEntrenador_LagosRivera_21056415 objeto,
            CartaEntrenador_LagosRivera_21056415 partidario) {
        ArrayList<Carta_LagosRivera_21056415> cartas =
                new ArrayList<Carta_LagosRivera_21056415>();
        agregarCopias(cartas, basico, 4);
        agregarCopias(cartas, evolucion, 4);
        agregarCopias(cartas, objeto, 4);
        agregarCopias(cartas, partidario, 4);
        agregarCopias(cartas, energia, 44);
        return cartas;
    }

    /**
     * agrega varias referencias de una carta a una lista
     *
     * @param destino lista que recibe las referencias
     * @param carta carta que se agrega
     * @param cantidad numero de copias
     */
    private static void agregarCopias(
            List<Carta_LagosRivera_21056415> destino,
            Carta_LagosRivera_21056415 carta,
            int cantidad) {
        for (int i = 0; i < cantidad; i = i + 1) {
            destino.add(carta);
        }
    }

    /**
     * crea un costo de energia a partir de pares tipo y cantidad
     *
     * @param pares tipos y cantidades alternados
     * @return mapa que conserva el orden de entrada
     */
    private static Map<String, Integer> costo(Object... pares) {
        LinkedHashMap<String, Integer> costo = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < pares.length; i = i + 2) {
            costo.put((String) pares[i], (Integer) pares[i + 1]);
        }
        return costo;
    }
}
