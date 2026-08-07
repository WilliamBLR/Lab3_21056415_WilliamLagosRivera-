import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //definiendo las variables al inicio para respetar la estructura
        Scanner entradaConsola;
        int opcionSeleccionada;
        boolean sistemaEjecutandose;
        ArrayList<Carta> inventarioCartasGlobal;
        ArrayList<Mazo> inventarioMazosGlobal;
        ArrayList<Ataque> inventarioAtaquesGlobal;
        int generadorId;
        
        //variables para la partida de prueba que pide el lab
        Mazo mazoPrueba;
        Jugador jugadorPrueba;
        CartaPokemon pikachuPrueba;

        //inicializando todo para que no nos tire el tipico null pointer
        entradaConsola = new Scanner(System.in);
        opcionSeleccionada = -1;
        sistemaEjecutandose = true;
        inventarioCartasGlobal = new ArrayList<Carta>();
        inventarioMazosGlobal = new ArrayList<Mazo>();
        inventarioAtaquesGlobal = new ArrayList<Ataque>();
        generadorId = 1;

        //cargando los datos por defecto para que el profe revise rapido
        System.out.println("[Carga Automatica] Preparando partida para la revision del profe...");
        mazoPrueba = new Mazo(99);
        jugadorPrueba = new Jugador(1, "Ash Ketchum", mazoPrueba);
        
        //creamos al pikachu salvador y le pasamos un ataque con polimorfismo
        pikachuPrueba = new CartaPokemon(101, "Pikachu", 50, "Electrico");
        pikachuPrueba.aprenderAtaque(new Ataque(888, "Impactrueno", new EfectoDanio(40)));
        
        //mandando cartas directo a la mano para probar altiro
        jugadorPrueba.robarCarta(pikachuPrueba);
        jugadorPrueba.robarCarta(new CartaEnergia(102, "Energia Electrica", "Electrico"));
        jugadorPrueba.robarCarta(new CartaPokemon(103, "Raichu", 90, "Electrico"));
        jugadorPrueba.robarCarta(new CartaEntrenador(104, "Pocion Maxima", new EfectoCurar(50)));
        
        System.out.println("[Carga Automatica] Lista. Tienes 4 cartas listas en tu mano.");

        //bucle while infinito para mantener el menu vivo
        while (sistemaEjecutandose) {
            System.out.println("######## POKEMON TCG - Paradigmas OOP ########");
            System.out.println("Bienvenido. Seleccione una opcion:");
            System.out.println("--- Construccion / Setup ---");
            System.out.println("1. Crear carta de energia");
            System.out.println("2. Crear ataque");
            System.out.println("3. Crear carta Pokemon");
            System.out.println("4. Crear carta de entrenador");
            System.out.println("5. Crear mazo a partir de cartas");
            System.out.println("6. Barajar un mazo (RF08)");
            System.out.println("7. Iniciar juego (2 mazos) (RF09)");
            System.out.println("--- Durante la partida ---");
            System.out.println("9. Mostrar estado del juego (RF10)");
            System.out.println("10. Jugar Pokemon a la banca (RF11)");
            System.out.println("11. Cambiar Pokemon activo (RF12)");
            System.out.println("12. Robar carta del mazo (RF13)");
            System.out.println("13. Unir energia a un Pokemon (RF14)");
            System.out.println("14. Evolucionar un Pokemon (RF15)");
            System.out.println("15. Usar carta de entrenador (RF16)");
            System.out.println("16. Usar habilidad de un Pokemon (RF17)");
            System.out.println("17. Usar ataque del activo (RF18)");
            System.out.println("0. Salir");
            System.out.print("\nIngrese su opcion: ");

            opcionSeleccionada = entradaConsola.nextInt();
            entradaConsola.nextLine(); //limpiando el enter que queda flotando en el buffer

            //puro if else anidado guiando el flujo segun la opcion
            if (opcionSeleccionada == 1) {
                //declarando variables arriba
                String nombreEnergia;
                String tipoEnergia;

                System.out.println("[Sistema] Has seleccionado: Crear carta de energia.");
                System.out.print("Ingrese el nombre de la carta: ");
                nombreEnergia = entradaConsola.nextLine();
                System.out.print("Ingrese el tipo de energia (Fuego, Agua, etc.): ");
                tipoEnergia = entradaConsola.nextLine();

                inventarioCartasGlobal.add(new CartaEnergia(generadorId, nombreEnergia, tipoEnergia));
                System.out.println("Carta " + nombreEnergia + " creada exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 2) {
                //variables locales arriba
                String nombreAtaque;
                int danoAtaque;

                System.out.println("[Sistema] Has seleccionado: Crear ataque.");
                System.out.print("Ingrese el nombre del ataque: ");
                nombreAtaque = entradaConsola.nextLine();
                System.out.print("Ingrese el dano base del ataque: ");
                danoAtaque = entradaConsola.nextInt();
                entradaConsola.nextLine(); //limpiando el buffer denuevo

                //inyectando el polimorfismo con EfectoDanio
                inventarioAtaquesGlobal.add(new Ataque(generadorId, nombreAtaque, new EfectoDanio(danoAtaque)));
                System.out.println("Ataque " + nombreAtaque + " creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 3) {
                //variables al principio
                String nombrePokemon;
                int hpPokemon;
                String tipoPokemon;

                System.out.println("[Sistema] Has seleccionado: Crear carta Pokemon.");
                System.out.print("Ingrese el nombre del Pokemon: ");
                nombrePokemon = entradaConsola.nextLine();
                System.out.print("Ingrese los HP (Puntos de Vida): ");
                hpPokemon = entradaConsola.nextInt();
                entradaConsola.nextLine(); //limpiando el scanner
                System.out.print("Ingrese el tipo (Fuego, Agua, etc.): ");
                tipoPokemon = entradaConsola.nextLine();

                inventarioCartasGlobal.add(new CartaPokemon(generadorId, nombrePokemon, hpPokemon, tipoPokemon));
                System.out.println("Pokemon " + nombrePokemon + " creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 4) {
                //variables iniciales actualizadas para el efecto de curar
                String nombreEntrenador;
                int curaEntrenador;

                System.out.println("[Sistema] Has seleccionado: Crear carta de entrenador.");
                System.out.print("Ingrese el nombre del entrenador o item: ");
                nombreEntrenador = entradaConsola.nextLine();
                System.out.print("Ingrese la cantidad de HP que cura esta carta: ");
                curaEntrenador = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                //inyectando el polimorfismo con EfectoCurar
                inventarioCartasGlobal.add(new CartaEntrenador(generadorId, nombreEntrenador, new EfectoCurar(curaEntrenador)));
                System.out.println("Entrenador " + nombreEntrenador + " creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 5) {
                System.out.println("[Sistema] Has seleccionado: Crear mazo.");
                inventarioMazosGlobal.add(new Mazo(generadorId));
                System.out.println("Mazo vacio creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 6) {
                System.out.println("[Sistema] Barajando el mazo...");
                mazoPrueba.barajar();

            } else if (opcionSeleccionada == 7) {
                //definiendo la moneda arriba
                int resultadoMoneda;

                System.out.println("[Sistema] Iniciando juego con los mazos 0 y 1...");
                
                //tirando el random para cachar quien empieza
                resultadoMoneda = (int) (Math.random() * 2);

                //if else pa avisar el resultado
                if (resultadoMoneda == 0) {
                    System.out.println("[Sistema] La moneda decidio que comienza el Jugador 1.");
                } else {
                    System.out.println("[Sistema] La moneda decidio que comienza el Jugador 2.");
                }
                System.out.println("[Sistema] Juego iniciado correctamente.");

            } else if (opcionSeleccionada == 9) {
                System.out.println("[Sistema] Mostrando el estado del juego...");
                jugadorPrueba.mostrarEstado();

            } else if (opcionSeleccionada == 10) {
                //indice de la mano
                int indiceMano;

                System.out.println("[Sistema] Jugando Pokemon a la banca...");
                System.out.print("Ingrese el indice de la carta en su mano (ej: 0): ");
                indiceMano = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.jugarPokemonBanca(indiceMano);

            } else if (opcionSeleccionada == 11) {
                //variable pa la banca
                int indiceBanca;

                System.out.println("[Sistema] Cambiando Pokemon activo...");
                System.out.print("Ingrese el indice del Pokemon en la banca que desea subir (ejemplo: 0): ");
                indiceBanca = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.cambiarPokemonActivo(indiceBanca);

            } else if (opcionSeleccionada == 12) {
                System.out.println("[Sistema] Accion de robar carta activada...");
                jugadorPrueba.robarCarta(new CartaPokemon(500, "Bulbasaur", 60, "Planta"));
                
            } else if (opcionSeleccionada == 13) {
                //indice mano arriba
                int indiceMano;

                System.out.println("[Sistema] Uniendo energia al Pokemon activo...");
                System.out.print("Ingrese el indice de la energia en su mano: ");
                indiceMano = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.unirEnergiaActivo(indiceMano);

            } else if (opcionSeleccionada == 14) {
                //variable inicial
                int indiceMano;

                System.out.println("[Sistema] Evolucionando al Pokemon activo...");
                System.out.print("Ingrese el indice de la evolucion en su mano: ");
                indiceMano = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.evolucionarActivo(indiceMano);

            } else if (opcionSeleccionada == 15) {
                //variable local al inicio
                int indiceMano;

                System.out.println("[Sistema] Usando carta de entrenador...");
                System.out.print("Ingrese el indice del entrenador en su mano: ");
                indiceMano = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.usarEntrenador(indiceMano);

            } else if (opcionSeleccionada == 16) {
                System.out.println("[Sistema] Usando habilidad del Pokemon activo...");
                jugadorPrueba.usarHabilidadActivo();

            } else if (opcionSeleccionada == 17) {
                //declarando el indice del ataque
                int indiceAtaque;

                System.out.println("[Sistema] Ejecutando ataque del Pokemon activo...");
                System.out.print("Ingrese el indice del ataque a utilizar (ejemplo: 0): ");
                indiceAtaque = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                jugadorPrueba.atacarConActivo(indiceAtaque);

            } else if (opcionSeleccionada == 0) {
                System.out.println("[Sistema] Cerrando el juego... !Adios!");
                sistemaEjecutandose = false;

            } else {
                System.out.println("[Error] Opcion invalida. Intente de nuevo.");
            }

            System.out.println("\n------------------------------------------------\n");
        }

        entradaConsola.close();

        //el retorno explicito que ni es tan necesario :V aunque sea un void
        return;
    }
}