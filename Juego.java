public class Juego {
    //variables del TDA que exige el laboratorio
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;

    //constructor
    public Juego(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = null;
    }

    //metodo que cumple con el RF09 completo
    public void iniciarPartida() {
        //variable para la moneda
        int resultadoMoneda;

        System.out.println("[Juego] Barajando ambos mazos...");
        this.jugador1.getMazoActual().barajar();
        this.jugador2.getMazoActual().barajar();

        System.out.println("[Juego] Repartiendo 7 cartas iniciales y 6 cartas de premio...");
        //for para repetir el robo de la mano
        for (int i = 0; i < 7; i = i + 1) {
            this.jugador1.robarCartaDelMazoSilencioso();
            this.jugador2.robarCartaDelMazoSilencioso();
        }
        
        //for para separar los premios
        for (int i = 0; i < 6; i = i + 1) {
            this.jugador1.separarPremio();
            this.jugador2.separarPremio();
        }

        //sorteo para ver quien parte
        resultadoMoneda = (int) (Math.random() * 2);
        
        //if else para asignar el turno
        if (resultadoMoneda == 0) {
            this.jugadorActual = this.jugador1;
            System.out.println("[Juego] La moneda cayo cara. Comienza " + this.jugador1.getNombre());
        } else {
            this.jugadorActual = this.jugador2;
            System.out.println("[Juego] La moneda cayo sello. Comienza " + this.jugador2.getNombre());
        }
        
        return;
    }

    //metodo para mostrar la mesa completa RF10
    public void mostrarEstadoPartida() {
        System.out.println("\n--- ESTADO DE LA PARTIDA ---");
        System.out.println("Turno de: " + this.jugadorActual.getNombre());
        this.jugador1.mostrarEstado();
        this.jugador2.mostrarEstado();
        System.out.println("----------------------------\n");
        return;
    }

    //getters basicos
    public Jugador getJugadorActual() {
        return this.jugadorActual;
    }

    //metodo para sacar al rival dependiendo de quien juega
    public Jugador getJugadorRival() {
        //if else
        if (this.jugadorActual == this.jugador1) {
            return this.jugador2;
        } else {
            return this.jugador1;
        }
    }

    //metodo para avanzar al siguiente turno
    public void pasarTurno() {
        //if else cambiando la referencia
        if (this.jugadorActual == this.jugador1) {
            this.jugadorActual = this.jugador2;
        } else {
            this.jugadorActual = this.jugador1;
        }
        System.out.println("\n=== Ahora es el turno de " + this.jugadorActual.getNombre() + " ===");
        return;
    }
}