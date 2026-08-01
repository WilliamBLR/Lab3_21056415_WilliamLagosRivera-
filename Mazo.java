import java.util.ArrayList;

public class Mazo {
    //variablesDefinidasExplicitamenteAlInicio
    private int id;
    private ArrayList<Carta> cartas;

    //constructorDeLaClase
    public Mazo(int id) {
        this.id = id;
        this.cartas = new ArrayList<Carta>();
    }

    //metodoParaAgregarCartaAlMazo
    public void agregarCarta(Carta nuevaCarta) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        int limiteCartas;
        int cantidadActual;

        limiteCartas = 60;
        cantidadActual = this.cartas.size();

        //condicionalIfElseDirigeElFlujo
        if (cantidadActual < limiteCartas) {
            this.cartas.add(nuevaCarta);
            System.out.println("Carta agregada exitosamente al mazo.");
        } else {
            System.out.println("El mazo ya esta lleno con 60 cartas.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}