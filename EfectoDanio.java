public class EfectoDanio implements Efecto {
    //variable inicial para guardar el daño
    private int cantidadDano;

    //constructor
    public EfectoDanio(int cantidadDano) {
        this.cantidadDano = cantidadDano;
    }

    //sobrescribiendo el metodo aplicando polimorfismo
    @Override
    public void ejecutar() {
        System.out.println("[Efecto Activado] Se ha infligido " + this.cantidadDano + " de dano al rival.");
        
        //retorno explicito
        return;
    }
}