import java.util.ArrayList;

public class CartaPokemon extends Carta {
    //variablesDefinidasExplicitamenteAlInicio
    private int hp;
    private String tipo;
    private ArrayList<CartaEnergia> energiasUnidas;
    private ArrayList<Ataque> ataquesDisponibles;

    //constructorDeLaClaseHija
    public CartaPokemon(int id, String nombre, int hp, String tipo) {
        super(id, nombre);
        this.hp = hp;
        this.tipo = tipo;
        this.energiasUnidas = new ArrayList<CartaEnergia>();
        this.ataquesDisponibles = new ArrayList<Ataque>();
    }

    //metodoParaAgregarEnergia
    public void unirEnergia(CartaEnergia nuevaEnergia) {
        //condicionalIfElseDirigeElFlujo
        if (nuevaEnergia != null) {
            this.energiasUnidas.add(nuevaEnergia);
            System.out.println("Energia unida correctamente a " + this.nombre + ". Total energias: " + this.energiasUnidas.size());
        } else {
            System.out.println("Error: La carta de energia no es valida.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaEnsenarAtaque
    public void aprenderAtaque(Ataque nuevoAtaque) {
        //condicionalIfElseDirigeElFlujo
        if (nuevoAtaque != null) {
            this.ataquesDisponibles.add(nuevoAtaque);
            System.out.println(this.nombre + " ha aprendido un nuevo ataque.");
        } else {
            System.out.println("Error: El ataque no es valido.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaAtacar
    public void atacar(int indiceAtaque) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        Ataque ataqueSeleccionado;

        //condicionalIfElseDirigeElFlujo
        if (indiceAtaque >= 0) {
            if (indiceAtaque < this.ataquesDisponibles.size()) {
                ataqueSeleccionado = this.ataquesDisponibles.get(indiceAtaque);
                System.out.println(this.nombre + " se prepara para atacar...");
                ataqueSeleccionado.ejecutarAtaque();
            } else {
                System.out.println("Error: Indice de ataque invalido o el Pokemon no conoce ese ataque.");
            }
        } else {
            System.out.println("Error: El indice debe ser mayor o igual a cero.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
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