public class Ataque {
    //definiendo las variables con el dano base incluido
    private int id;
    private String nombre;
    private int danoBase;
    private Efecto efectoAsociado;

    //constructor de la clase actualizado
    public Ataque(int id, String nombre, int danoBase, Efecto efectoAsociado) {
        this.id = id;
        this.nombre = nombre;
        this.danoBase = danoBase;
        this.efectoAsociado = efectoAsociado;
    }

    //getters necesarios para calcular el dano despues
    public int getDanoBase() { return this.danoBase; }
    public String getNombre() { return this.nombre; }

    //metodo para gatillar el ataque polimorfico
    public void ejecutarEfecto() {
        //if else validando que no explote
        if (this.efectoAsociado != null) {
            this.efectoAsociado.ejecutar();
        } else {
            System.out.println("El ataque " + this.nombre + " no tiene efecto adicional.");
        }
        return;
    }
}