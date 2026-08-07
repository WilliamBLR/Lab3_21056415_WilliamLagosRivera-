public class Ataque {
    //definiendo las variables
    private int id;
    private String nombre;
    private Efecto efectoAsociado; //aqui aplicamos la interfaz

    //constructor
    public Ataque(int id, String nombre, Efecto efectoAsociado) {
        this.id = id;
        this.nombre = nombre;
        this.efectoAsociado = efectoAsociado;
    }

    //metodo para gatillar el ataque
    public void ejecutarAtaque() {
        //if else controlando el flujo
        if (this.efectoAsociado != null) {
            System.out.println("!El ataque " + this.nombre + " golpea con fuerza!");
            //aqui se ejecuta la magia del polimorfismo
            this.efectoAsociado.ejecutar();
        } else {
            System.out.println("El ataque " + this.nombre + " no tiene efecto asociado.");
        }
        
        //retorno explicito
        return;
    }
}