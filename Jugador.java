import java.util.ArrayList;

public class Jugador {
    //variablesDefinidasExplicitamenteAlInicio
    private int id;
    private String nombre;
    private Mazo mazoActual;
    private ArrayList<Carta> mano;
    private ArrayList<CartaPokemon> banca;
    private CartaPokemon pokemonActivo;

    //constructorDeLaClase
    public Jugador(int id, String nombre, Mazo mazoActual) {
        this.id = id;
        this.nombre = nombre;
        this.mazoActual = mazoActual;
        this.mano = new ArrayList<Carta>();
        this.banca = new ArrayList<CartaPokemon>();
        this.pokemonActivo = null;
    }

    //metodoParaRobarCarta
    public void robarCarta(Carta nuevaCarta) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        boolean puedeRobar;

        //condicionalIfElseDirigeElFlujo
        if (nuevaCarta != null) {
            puedeRobar = true;
            this.mano.add(nuevaCarta);
            System.out.println(this.nombre + " ha robado una carta y se anade a su mano.");
        } else {
            puedeRobar = false;
            System.out.println("Error: No hay cartas validas para robar.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaMostrarEstadoDelJugador
    public void mostrarEstado() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        String nombreActivo;

        System.out.println("--- Estado del Jugador: " + this.nombre + " ---");

        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            nombreActivo = this.pokemonActivo.getNombre();
            System.out.println("Pokemon Activo: " + nombreActivo);
        } else {
            System.out.println("Pokemon Activo: Ninguno asignado aun.");
        }

        System.out.println("Cartas en la mano: " + this.mano.size());
        System.out.println("Pokemon en la banca: " + this.banca.size());

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}