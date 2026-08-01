public abstract class Carta {
    //variablesDefinidasExplicitamenteAlInicio
    protected int id;
    protected String nombre;

    //constructorDeLaClasePadre
    public Carta(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    //metodosGetters
    public int getId() {
        //retornoExplicito
        return this.id;
    }

    public String getNombre() {
        //retornoExplicito
        return this.nombre;
    }

    //metodoAbstractoQueDefiniranLosHijos
    public abstract void jugar();
}