public class Ataque {
    //variablesDefinidasExplicitamenteAlInicio
    private int id;
    private String nombre;
    private int danoBase;

    //constructorDeLaClase
    public Ataque(int id, String nombre, int danoBase) {
        this.id = id;
        this.nombre = nombre;
        this.danoBase = danoBase;
    }

    //metodoParaEjecutarElAtaque
    public void ejecutarAtaque() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        String mensajeResultado;

        //condicionalIfElseDirigeElFlujo
        if (this.danoBase > 0) {
            mensajeResultado = "El ataque " + this.nombre + " inflige " + this.danoBase + " puntos de dano.";
            System.out.println(mensajeResultado);
        } else {
            mensajeResultado = "El ataque " + this.nombre + " no inflige dano directo.";
            System.out.println(mensajeResultado);
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}