public class CartaEntrenador extends Carta {
    //variable usando la interfaz para respetar el requerimiento
    private Efecto efectoAsociado;

    //constructor
    public CartaEntrenador(int id, String nombre, Efecto efectoAsociado) {
        super(id, nombre);
        this.efectoAsociado = efectoAsociado;
    }

    //sobrescribiendo el metodo abstracto
    @Override
    public void jugar() {
        System.out.println("Activando carta entrenador: " + this.nombre);
        
        //if else pa evitar caidas y llamar al efecto
        if (this.efectoAsociado != null) {
            this.efectoAsociado.ejecutar();
        } else {
            System.out.println("Esta carta no tiene efecto asociado.");
        }
        
        //retorno explicito
        return;
    }
}