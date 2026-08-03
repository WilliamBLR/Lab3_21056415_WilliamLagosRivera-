import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //variablesDefinidasExplicitamenteAlInicio
        Scanner entradaConsola;
        int opcionSeleccionada;
        boolean sistemaEjecutandose;
        ArrayList<Carta> inventarioCartasGlobal;
        ArrayList<Mazo> inventarioMazosGlobal;
        ArrayList<Ataque> inventarioAtaquesGlobal;
        int generadorId;
        
        //variablesParaElJuegoActivo
        Mazo mazoPrueba;
        Jugador jugadorPrueba;
        CartaPokemon cartaPruebaRobo;
        Ataque ataquePrueba;

        //inicializacionDeVariables
        entradaConsola = new Scanner(System.in);
        opcionSeleccionada = -1;
        sistemaEjecutandose = true;
        inventarioCartasGlobal = new ArrayList<Carta>();
        inventarioMazosGlobal = new ArrayList<Mazo>();
        inventarioAtaquesGlobal = new ArrayList<Ataque>();
        generadorId = 1;

        //inicializacionDeEntornosDePrueba
        mazoPrueba = new Mazo(99);
        jugadorPrueba = new Jugador(1, "Ash Ketchum", mazoPrueba);
        cartaPruebaRobo = new CartaPokemon(999, "Pikachu", 50, "Electrico");
        ataquePrueba = new Ataque(888, "Impactrueno", 40);
        
        //leEnsenamosElAtaqueAlPokemonDePrueba
        cartaPruebaRobo.aprenderAtaque(ataquePrueba);

        //bucleWhileParaIterarElMenu
        while (sistemaEjecutandose) {
            System.out.println("######## POKEMON TCG - Paradigmas OOP ########");
            System.out.println("Bienvenido. Seleccione una opcion:");
            System.out.println("--- Construccion / Setup ---");
            System.out.println("1. Crear carta de energia");
            System.out.println("2. Crear ataque");
            System.out.println("3. Crear carta Pokemon");
            System.out.println("4. Crear carta de entrenador");
            System.out.println("5. Crear mazo a partir de cartas");
            System.out.println("--- Durante la partida ---");
            System.out.println("9. Mostrar estado del juego (RF10)");
            System.out.println("10. Jugar Pokemon a la banca (RF11)");
            System.out.println("11. Cambiar Pokemon activo (RF12)");
            System.out.println("12. Robar carta del mazo (RF13)");
            System.out.println("13. Unir energia a un Pokemon (RF14)");
            System.out.println("17. Usar ataque del activo (RF18)");
            System.out.println("0. Salir");
            System.out.print("\nIngrese su opcion: ");

            opcionSeleccionada = entradaConsola.nextInt();
            entradaConsola.nextLine(); //limpiarElBufferDeEntrada

            //condicionalIfElseIfElseDirigeElFlujo
            if (opcionSeleccionada == 1) {
                System.out.println("[Sistema] Has seleccionado: Crear carta de energia.");
                inventarioCartasGlobal.add(new CartaEnergia(generadorId, "Energia Fuego", "Fuego"));
                System.out.println("Carta creada exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 2) {
                System.out.println("[Sistema] Has seleccionado: Crear ataque.");
                inventarioAtaquesGlobal.add(new Ataque(generadorId, "Llamarada", 80));
                System.out.println("Ataque creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 3) {
                System.out.println("[Sistema] Has seleccionado: Crear carta Pokemon.");
                inventarioCartasGlobal.add(new CartaPokemon(generadorId, "Charmander", 60, "Fuego"));
                System.out.println("Pokemon creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 4) {
                System.out.println("[Sistema] Has seleccionado: Crear carta de entrenador.");
                inventarioCartasGlobal.add(new CartaEntrenador(generadorId, "Pocion", "Cura 30 de dano"));
                System.out.println("Entrenador creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 5) {
                System.out.println("[Sistema] Has seleccionado: Crear mazo.");
                inventarioMazosGlobal.add(new Mazo(generadorId));
                System.out.println("Mazo vacio creado exitosamente.");
                generadorId = generadorId + 1;

            } else if (opcionSeleccionada == 9) {
                System.out.println("[Sistema] Mostrando el estado del juego...");
                jugadorPrueba.mostrarEstado();

            } else if (opcionSeleccionada == 10) {
                System.out.println("[Sistema] Jugando Pokemon a la banca...");
                jugadorPrueba.jugarPokemonBanca(cartaPruebaRobo);

            } else if (opcionSeleccionada == 11) {
                System.out.println("[Sistema] Cambiando Pokemon activo...");
                jugadorPrueba.cambiarPokemonActivo(0);

            } else if (opcionSeleccionada == 12) {
                System.out.println("[Sistema] Accion de robar carta activada...");
                jugadorPrueba.robarCarta(cartaPruebaRobo);
                
            } else if (opcionSeleccionada == 13) {
                System.out.println("[Sistema] Uniendo energia al Pokemon activo...");
                //simulamosLaUnionDeUnaEnergiaElectrica
                jugadorPrueba.unirEnergiaActivo(new CartaEnergia(777, "Energia Electrica", "Electrico"));
                
            } else if (opcionSeleccionada == 17) {
                System.out.println("[Sistema] Ejecutando ataque del Pokemon activo...");
                //ejecutamosElAtaqueEnElIndiceCeroQueLeEnsenamosArriba
                jugadorPrueba.atacarConActivo(0);

            } else if (opcionSeleccionada == 0) {
                System.out.println("[Sistema] Cerrando el juego... !Adios!");
                sistemaEjecutandose = false;

            } else {
                System.out.println("[Error] Opcion invalida. Intente de nuevo.");
            }

            System.out.println("\n------------------------------------------------\n");
        }

        entradaConsola.close();

        //retornoExplicito
        return;
    }
}