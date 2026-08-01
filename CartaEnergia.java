public class CartaEnergia extends Carta {
    //variablesDefinidasExplicitamenteAlInicio
    private String tipoEnergia;

    //constructorDeLaClaseHija
    public CartaEnergia(int id, String nombre, String tipoEnergia) {
        super(id, nombre);
        this.tipoEnergia = tipoEnergia;
    }

    //implementacionDelMetodoAbstracto
    @Override
    public void jugar() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        String mensajeAccion;

        //condicionalIfElseDirigeElFlujo
        if (this.tipoEnergia != null) {
            mensajeAccion = "Se ha unido la energia de tipo " + this.tipoEnergia + " a un Pokemon.";
            System.out.println(mensajeAccion);
        } else {
            mensajeAccion = "La carta no tiene un tipo de energia valido.";
            System.out.println(mensajeAccion);
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}