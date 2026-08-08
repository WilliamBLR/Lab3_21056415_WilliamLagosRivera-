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

        limiteCartas = 60;

        //condicionalIfElseDirigeElFlujo
        if (this.cartas.size() < limiteCartas) {
            this.cartas.add(nuevaCarta);
        } else {
            System.out.println("Error: El mazo ya tiene 60 cartas.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaRobarLaPrimeraCartaDelMazo
    public Carta robarCartaSuperior() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        Carta cartaRobada;

        //condicionalIfElseDirigeElFlujo
        if (this.cartas.size() > 0) {
            //sacamosLaCartaDeLaPosicionCero
            cartaRobada = this.cartas.remove(0);
        } else {
            cartaRobada = null;
        }

        return cartaRobada;
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
                this.cartas.set(i, this.cartas.get(indiceAleatorio));
                this.cartas.set(indiceAleatorio, cartaTemporal);
            }
            System.out.println("Mazo barajado exitosamente.");
        } else {
            System.out.println("Mazo vacio.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaValidarReglasDelMazo
    public boolean esValido() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        boolean valido;
        boolean tieneBasico;
        Carta cartaActual;
        CartaPokemon pokemonActual;

        valido = true;
        tieneBasico = false;

        //condicionalIfElseDirigeElFlujo
        if (this.cartas.size() == 60) {
            //bucleForParaBuscarPokemonBasico
            for (int i = 0; i < this.cartas.size(); i = i + 1) {
                cartaActual = this.cartas.get(i);
                if (cartaActual instanceof CartaPokemon) {
                    pokemonActual = (CartaPokemon) cartaActual;
                    
                    //siEvolucionaDeEsNullEntoncesEsBasico
                    if (pokemonActual.getEvolucionaDe() == null) {
                        tieneBasico = true;
                    } else {
                        //noHaceNada
                    }
                } else {
                    //noHaceNada
                }
            }

            //siTerminoDeBuscarYNoHayBasicoEsInvalido
            if (tieneBasico == false) {
                valido = false;
                System.out.println("Error: El mazo no tiene Pokemon basicos.");
            } else {
                //noHaceNada
            }
            
        } else {
            valido = false;
            System.out.println("Error: El mazo no tiene exactamente 60 cartas.");
        }

        return valido;
    }

    //getter para ver el tamano del mazo
    public ArrayList<Carta> getCartas() { return this.cartas; }

}