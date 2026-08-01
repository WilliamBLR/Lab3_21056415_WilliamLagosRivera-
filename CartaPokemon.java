public class CartaPokemon extends Carta {
    //variablesDefinidasExplicitamenteAlInicio
    private int hp;
    private String tipo;

    //constructorDeLaClaseHija
    public CartaPokemon(int id, String nombre, int hp, String tipo) {
        super(id, nombre);
        this.hp = hp;
        this.tipo = tipo;
    }

    //implementacionDelMetodoAbstracto
    @Override
    public void jugar() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        boolean puedeEntrarABanca;
        String mensajeEstado;

        //condicionalIfElseDirigeElFlujo
        if (this.hp > 0) {
            puedeEntrarABanca = true;
            mensajeEstado = this.nombre + " ha entrado a la banca.";
            System.out.println(mensajeEstado);
        } else {
            puedeEntrarABanca = false;
            mensajeEstado = this.nombre + " no tiene HP para jugar.";
            System.out.println(mensajeEstado);
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }
}