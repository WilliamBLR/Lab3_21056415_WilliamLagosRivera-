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

        //inicializacionDeVariables
        entradaConsola = new Scanner(System.in);
        opcionSeleccionada = -1;
        sistemaEjecutandose = true;
        inventarioCartasGlobal = new ArrayList<Carta>();
        inventarioMazosGlobal = new ArrayList<Mazo>();
        inventarioAtaquesGlobal = new ArrayList<Ataque>();
        generadorId = 1;

        //bucleWhileParaIterarElMenu
        while (sistemaEjecutandose) {
            System.out.println("######## POKEMON TCG - Paradigmas OOP ########");
            System.out.println("Bienvenido. Seleccione una opcion:");
            System.out.println("--- Construccion / Setup ---");
            System.out.println("1. Crear carta de energia (RF03)");
            System.out.println("2. Crear ataque (RF04)");
            System.out.println("3. Crear carta Pokemon (RF05)");
            System.out.println("4. Crear carta de entrenador (RF06)");
            System.out.println("5. Crear mazo a partir de cartas (RF07)");
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
                inventarioAtaquesGlobal.add(new Ataque(generadorId, "Impactrueno", 40));
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