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

    //metodoParaBarajarElMazo
    public void barajar() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        int totalCartas;
        int indiceAleatorio;
        Carta cartaTemporal;

        totalCartas = this.cartas.size();

        //condicionalIfElseDirigeElFlujo
        if (totalCartas > 0) {
            //bucleForParaIntercambiarPosiciones
            for (int i = 0; i < totalCartas; i = i + 1) {
                indiceAleatorio = (int) (Math.random() * totalCartas);
                cartaTemporal = this.cartas.get(i);
                
                //intercambiamosLaCartaEnLaPosicionActualPorUnaAlAzar
                this.cartas.set(i, this.cartas.get(indiceAleatorio));
                this.cartas.set(indiceAleatorio, cartaTemporal);
            }
            System.out.println("El mazo ha sido barajado exitosamente al azar.");
        } else {
            System.out.println("Error: El mazo esta vacio, no se puede barajar.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}