public class EfectoCurar implements Efecto {
    //variable inicial para la curacion
    private int cantidadCura;

    //constructor
    public EfectoCurar(int cantidadCura) {
        this.cantidadCura = cantidadCura;
    }

    //sobrescribiendo el metodo para que haga algo distinto
    @Override
    public void ejecutar() {
        System.out.println("[Efecto Activado] El Pokemon recupera " + this.cantidadCura + " Puntos de Salud.");
        
        //retorno explicito
        return;
    }
}