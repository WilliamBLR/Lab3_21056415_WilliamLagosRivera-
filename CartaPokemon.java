import java.util.ArrayList;

public class CartaPokemon extends Carta {
    //definiendoLasVariablesExigidasEnRF05
    private int hpBase;
    private String tipo;
    private String evolucionaDe;
    private String debilidad;
    private String resistencia;
    private int costoRetirada;
    private boolean esEX;
    private Ataque habilidad;
    private ArrayList<Ataque> ataquesDisponibles;

    //constructorDeLaClase
    public CartaPokemon(int id, String nombre, int hpBase, String tipo, String evolucionaDe, String debilidad, String resistencia, int costoRetirada, boolean esEX) {
        super(id, nombre);
        this.hpBase = hpBase;
        this.tipo = tipo;
        this.evolucionaDe = evolucionaDe; //seraNullSiEsBasico
        this.debilidad = debilidad;
        this.resistencia = resistencia;
        this.costoRetirada = costoRetirada;
        this.esEX = esEX;
        this.habilidad = null;
        this.ataquesDisponibles = new ArrayList<Ataque>();
    }

    //metodosGettersBasicosParaLasReglasDeJuego
    public int getHpBase() { return this.hpBase; }
    public String getTipo() { return this.tipo; }
    public String getEvolucionaDe() { return this.evolucionaDe; }
    public String getDebilidad() { return this.debilidad; }
    public String getResistencia() { return this.resistencia; }
    public int getCostoRetirada() { return this.costoRetirada; }
    public boolean getEsEX() { return this.esEX; }
    public ArrayList<Ataque> getAtaquesDisponibles() { return this.ataquesDisponibles; }

    //metodoParaEnsenarAtaque
    public void aprenderAtaque(Ataque nuevoAtaque) {
        //condicionalIfElseDirigeElFlujo
        if (nuevoAtaque != null) {
            this.ataquesDisponibles.add(nuevoAtaque);
        } else {
            System.out.println("Error: Ataque nulo.");
        }
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaAsignarHabilidad
    public void setHabilidad(Ataque nuevaHabilidad) {
        this.habilidad = nuevaHabilidad;
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //implementacionDelMetodoAbstracto
    @Override
    public void jugar() {
        System.out.println("Jugando carta Pokemon: " + this.nombre);
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}