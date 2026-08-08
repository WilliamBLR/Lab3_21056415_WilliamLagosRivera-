import java.util.ArrayList;

public class CartaEnJuego {
    //variablesDefinidasExplicitamenteAlInicio
    private int idInstancia;
    private CartaPokemon cartaOriginal;
    private int hpActual;
    private ArrayList<CartaEnergia> energiasUnidas;
    private int turnosEnJuego;
    private String estadoEspecial;

    //constructorDeLaClase
    public CartaEnJuego(int idInstancia, CartaPokemon cartaOriginal) {
        this.idInstancia = idInstancia;
        this.cartaOriginal = cartaOriginal;
        this.hpActual = cartaOriginal.getHpBase();
        this.energiasUnidas = new ArrayList<CartaEnergia>();
        this.turnosEnJuego = 0;
        this.estadoEspecial = "Normal";
    }

    //metodosGetters
    public CartaPokemon getCartaOriginal() { return this.cartaOriginal; }
    public int getHpActual() { return this.hpActual; }
    public ArrayList<CartaEnergia> getEnergiasUnidas() { return this.energiasUnidas; }
    public int getTurnosEnJuego() { return this.turnosEnJuego; }

    //metodoParaUnirEnergia
    public void unirEnergia(CartaEnergia energia) {
        this.energiasUnidas.add(energia);
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaRecibirDanoConsiderandoDebilidadYResistencia
    public void recibirDano(int danoBase, String tipoAtacante) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        int danoFinal;
        String debilidad;
        String resistencia;

        danoFinal = danoBase;
        debilidad = this.cartaOriginal.getDebilidad();
        resistencia = this.cartaOriginal.getResistencia();

        //condicionalIfElseParaDebilidad
        if (debilidad != null) {
            if (debilidad.equals(tipoAtacante)) {
                danoFinal = danoFinal * 2;
                System.out.println("!Es super efectivo! Dano duplicado por debilidad.");
            } else {
                //noHaceNada
            }
        } else {
            //noHaceNada
        }

        //condicionalIfElseParaResistencia
        if (resistencia != null) {
            if (resistencia.equals(tipoAtacante)) {
                danoFinal = danoFinal - 30;
                
                //ifElseParaQueElDanoNoSeaNegativo
                if (danoFinal < 0) {
                    danoFinal = 0;
                } else {
                    //mantieneElValor
                }
                System.out.println("El Pokemon resiste el ataque. Dano reducido en 30.");
            } else {
                //noHaceNada
            }
        } else {
            //noHaceNada
        }

        this.hpActual = this.hpActual - danoFinal;
        System.out.println(this.cartaOriginal.getNombre() + " recibe " + danoFinal + " de dano. HP restante: " + this.hpActual);
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaAvanzarTurno
    public void sumarTurno() {
        this.turnosEnJuego = this.turnosEnJuego + 1;
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}