import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //variables iniciales
        Scanner entradaConsola;
        int opcionSeleccionada;
        boolean sistemaEjecutandose;
        ArrayList<Carta> inventarioCartasGlobal;
        ArrayList<Mazo> inventarioMazosGlobal;
        ArrayList<Ataque> inventarioAtaquesGlobal;
        int generadorId;
        
        //variables para la partida real
        Mazo mazoJ1;
        Mazo mazoJ2;
        Jugador jugador1;
        Jugador jugador2;
        Juego partidaActual;

        //inicializando
        entradaConsola = new Scanner(System.in);
        opcionSeleccionada = -1;
        sistemaEjecutandose = true;
        inventarioCartasGlobal = new ArrayList<Carta>();
        inventarioMazosGlobal = new ArrayList<Mazo>();
        inventarioAtaquesGlobal = new ArrayList<Ataque>();
        generadorId = 1;

        //cargando los datos por defecto
        System.out.println("[Carga Automatica] Llenando mazos legales para la revision...");
        mazoJ1 = new Mazo(1);
        mazoJ2 = new Mazo(2);
        
        //agregamos el pokemon basico obligatorio a cada mazo
        mazoJ1.agregarCarta(new CartaPokemon(101, "Pikachu", 50, "Electrico", null, "Lucha", "Acero", 1, false));
        mazoJ2.agregarCarta(new CartaPokemon(201, "Squirtle", 60, "Agua", null, "Planta", null, 1, false));
        
        //for loop llenando el resto con energias que no tienen limite de copias (RF07)
        for(int i = 0; i < 59; i = i + 1) {
            mazoJ1.agregarCarta(new CartaEnergia(1000+i, "Energia Electrica", "Electrico"));
            mazoJ2.agregarCarta(new CartaEnergia(2000+i, "Energia Agua", "Agua"));
        }

        jugador1 = new Jugador(1, "Ash", mazoJ1);
        jugador2 = new Jugador(2, "Gary", mazoJ2);
        
        //creamos la partida pero aun no la iniciamos
        partidaActual = new Juego(jugador1, jugador2);
        System.out.println("[Carga Automatica] Listo. Presione la opcion 7 para iniciar el duelo.");

        //bucle while infinito
        while (sistemaEjecutandose) {
            System.out.println("\n######## POKEMON TCG - Paradigmas OOP ########");
            System.out.println("1 al 6. Opciones de Setup de cartas omitidas en el print para ahorrar espacio...");
            System.out.println("7. Iniciar juego (2 mazos) (RF09)");
            System.out.println("--- Durante la partida ---");
            System.out.println("9. Mostrar estado del juego (RF10)");
            System.out.println("10. Jugar Pokemon a la banca (RF11)");
            System.out.println("11. Cambiar Pokemon activo (RF12)");
            System.out.println("12. Robar carta del mazo (RF13)");
            System.out.println("17. Usar ataque del activo y pasar el turno (RF18)");
            System.out.println("0. Salir");
            System.out.print("\nIngrese su opcion: ");

            opcionSeleccionada = entradaConsola.nextInt();
            entradaConsola.nextLine(); //buffer

            //if else
            if (opcionSeleccionada == 7) {
                partidaActual.iniciarPartida();

            } else if (opcionSeleccionada == 9) {
                //if else validando que el juego inicio
                if (partidaActual.getJugadorActual() != null) {
                    partidaActual.mostrarEstadoPartida();
                } else {
                    System.out.println("Debe iniciar la partida primero (Opcion 7).");
                }

            } else if (opcionSeleccionada == 10) {
                //variable
                int indiceMano;
                System.out.print("Ingrese el indice de la carta en su mano (ej: 0): ");
                indiceMano = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer
                partidaActual.getJugadorActual().jugarPokemonBanca(indiceMano);

            } else if (opcionSeleccionada == 11) {
                //variable
                int indiceBanca;
                System.out.print("Ingrese el indice del Pokemon en la banca (ej: 0): ");
                indiceBanca = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer
                partidaActual.getJugadorActual().cambiarPokemonActivo(indiceBanca);

            } else if (opcionSeleccionada == 12) {
                System.out.println("[Sistema] " + partidaActual.getJugadorActual().getNombre() + " decide robar...");
                partidaActual.getJugadorActual().robarCartaDelMazo();

            } else if (opcionSeleccionada == 17) {
                //variables locales del menu
                int decisionAtacar;
                int indiceAtaque;

                System.out.println("1. Ejecutar un ataque");
                System.out.println("2. Solo pasar el turno");
                System.out.print("Elija: ");
                decisionAtacar = entradaConsola.nextInt();
                entradaConsola.nextLine(); //buffer

                //if else
                if (decisionAtacar == 1) {
                    System.out.print("Ingrese el indice del ataque a utilizar: ");
                    indiceAtaque = entradaConsola.nextInt();
                    entradaConsola.nextLine(); //buffer
                    
                    //atacamos mandandole el rival por parametro
                    partidaActual.getJugadorActual().atacarAlRival(indiceAtaque, partidaActual.getJugadorRival());
                }

                //por regla general, el turno se acaba despues de atacar o pasar
                System.out.println("[Sistema] Fin del turno.");
                partidaActual.pasarTurno();

            } else if (opcionSeleccionada == 0) {
                System.out.println("[Sistema] Cerrando el juego... !Adios!");
                sistemaEjecutandose = false;
            } else {
                System.out.println("Opcion omitida en este Main abreviado o invalida.");
            }
        }
        entradaConsola.close();
        //retorno explicito
        return;
    }
}