import java.util.ArrayList;

public class Jugador {
    //variablesDefinidasExplicitamenteAlInicio
    private int id;
    private String nombre;
    private Mazo mazoActual;
    private ArrayList<Carta> mano;
    private ArrayList<CartaPokemon> banca;
    private CartaPokemon pokemonActivo;

    //constructorDeLaClase
    public Jugador(int id, String nombre, Mazo mazoActual) {
        this.id = id;
        this.nombre = nombre;
        this.mazoActual = mazoActual;
        this.mano = new ArrayList<Carta>();
        this.banca = new ArrayList<CartaPokemon>();
        this.pokemonActivo = null;
    }

    //metodoParaRobarCarta
    public void robarCarta(Carta nuevaCarta) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        boolean puedeRobar;

        //condicionalIfElseDirigeElFlujo
        if (nuevaCarta != null) {
            puedeRobar = true;
            this.mano.add(nuevaCarta);
            System.out.println(this.nombre + " ha robado una carta y se anade a su mano.");
        } else {
            puedeRobar = false;
            System.out.println("Error: No hay cartas validas para robar.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaMostrarEstadoDelJugador
    public void mostrarEstado() {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        String nombreActivo;

        System.out.println("--- Estado del Jugador: " + this.nombre + " ---");

        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            nombreActivo = this.pokemonActivo.getNombre();
            System.out.println("Pokemon Activo: " + nombreActivo);
        } else {
            System.out.println("Pokemon Activo: Ninguno asignado aun.");
        }

        System.out.println("Cartas en la mano: " + this.mano.size());
        System.out.println("Pokemon en la banca: " + this.banca.size());

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaJugarPokemonALaBanca
    public void jugarPokemonBanca(CartaPokemon nuevoPokemon) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        int limiteBanca;
        int cantidadActual;

        limiteBanca = 5;
        cantidadActual = this.banca.size();

        //condicionalIfElseDirigeElFlujo
        if (nuevoPokemon != null) {
            if (cantidadActual < limiteBanca) {
                this.banca.add(nuevoPokemon);
                System.out.println(this.nombre + " ha jugado a " + nuevoPokemon.getNombre() + " a la banca.");
            } else {
                System.out.println("La banca ya esta llena (limite de 5 Pokemon).");
            }
        } else {
            System.out.println("Error: El Pokemon a jugar no es valido.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaCambiarPokemonActivo
    public void cambiarPokemonActivo(int indiceBanca) {
        //variablesLocalesDefinidasAlInicioDeLaFuncion
        CartaPokemon pokemonSeleccionado;
        CartaPokemon pokemonAnterior;

        //condicionalIfElseDirigeElFlujo
        if (indiceBanca >= 0) {
            if (indiceBanca < this.banca.size()) {
                pokemonSeleccionado = this.banca.get(indiceBanca);
                pokemonAnterior = this.pokemonActivo;

                //elPokemonSeleccionadoPasaASerElActivo
                this.pokemonActivo = pokemonSeleccionado;
                this.banca.remove(indiceBanca);

                //siHabiaUnPokemonActivoAnteriorRegresaALaBanca
                if (pokemonAnterior != null) {
                    this.banca.add(pokemonAnterior);
                    System.out.println(pokemonAnterior.getNombre() + " regresa a la banca.");
                }

                System.out.println(this.pokemonActivo.getNombre() + " es ahora el Pokemon activo.");
            } else {
                System.out.println("Error: El indice de la banca es invalido o la banca esta vacia.");
            }
        } else {
            System.out.println("Error: El indice debe ser mayor o igual a cero.");
        }

        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }



//metodoParaUnirEnergiaAlActivo
    public void unirEnergiaActivo(CartaEnergia energia) {
        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            if (energia != null) {
                this.pokemonActivo.unirEnergia(energia);
            } else {
                System.out.println("Error: Carta de energia invalida.");
            }
        } else {
            System.out.println("Error: No hay un Pokemon activo para unirle energia.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaAtacarConElActivo
    public void atacarConActivo(int indiceAtaque) {
        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            this.pokemonActivo.atacar(indiceAtaque);
        } else {
            System.out.println("Error: No hay un Pokemon activo para realizar el ataque.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

//metodoParaEvolucionarElPokemonActivo
    public void evolucionarActivo(CartaPokemon nuevaFase) {
        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            if (nuevaFase != null) {
                System.out.println("!Que esta pasando! " + this.pokemonActivo.getNombre() + " esta evolucionando a " + nuevaFase.getNombre() + "!");
                //reemplazamosElPokemonActivoPorSuEvolucion
                this.pokemonActivo = nuevaFase;
            } else {
                System.out.println("Error: La carta de evolucion no es valida.");
            }
        } else {
            System.out.println("Error: No hay un Pokemon activo para evolucionar.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaUsarUnaCartaDeEntrenador
    public void usarEntrenador(CartaEntrenador cartaEntrenador) {
        //condicionalIfElseDirigeElFlujo
        if (cartaEntrenador != null) {
            System.out.println(this.nombre + " va a usar una carta de Entrenador de su mano...");
            //llamamosAlMetodoJugarQueHeredaDeCarta
            cartaEntrenador.jugar();
        } else {
            System.out.println("Error: La carta de entrenador no es valida.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }

    //metodoParaUsarHabilidadDelActivo
    public void usarHabilidadActivo() {
        //condicionalIfElseDirigeElFlujo
        if (this.pokemonActivo != null) {
            this.pokemonActivo.usarHabilidad();
        } else {
            System.out.println("Error: No hay un Pokemon activo para usar una habilidad.");
        }
        
        //retornoExplicitoAlFinalDeLaFuncion
        return;
    }




}