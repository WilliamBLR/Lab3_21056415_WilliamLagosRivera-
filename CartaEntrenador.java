public class CartaEntrenador extends Carta {
    //variablesDefinidasExplicitamenteAlInicio
    private String efecto;

    //constructorDeLaClaseHija
    public CartaEntrenador(int id, String nombre, String efecto) {
        super(id, nombre);
        this.efecto = efecto;
    }

    //implementacionDelMetodoAbstracto
    @Override
    public void jugar() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        String mensajeAccion;

        //condicionalIfElseDirigeElFlujo
        if (this.efecto != null) {
            mensajeAccion = "El entrenador " + this.nombre + " aplica el efecto: " + this.efecto;
            System.out.println(mensajeAccion);
        } else {
            mensajeAccion = "Esta carta de entrenador no tiene efecto.";
            System.out.println(mensajeAccion);
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}