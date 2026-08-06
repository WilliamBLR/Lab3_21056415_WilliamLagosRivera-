import java.util.ArrayList;

public class Jugador {
    //definiendo las variables de la clase para cumplir con la pauta
    private int id;
    private String nombre;
    private Mazo mazoActual;
    private ArrayList<Carta> mano;
    private ArrayList<CartaPokemon> banca;
    private CartaPokemon pokemonActivo;
    private ArrayList<Carta> pilaDescarte;

    //constructor de la clase para instanciar al jugador
    public Jugador(int id, String nombre, Mazo mazoActual) {
        this.id = id;
        this.nombre = nombre;
        this.mazoActual = mazoActual;
        this.mano = new ArrayList<Carta>();
        this.banca = new ArrayList<CartaPokemon>();
        this.pokemonActivo = null;
        this.pilaDescarte = new ArrayList<Carta>(); //iniciamos la pila vacia porque asi arranca el juego
    }

    //metodo para robar una carta del mazo
    public void robarCarta(Carta nuevaCarta) {
        //variables locales al principio como lo pide el profe
        boolean puedeRobar;

        //el tipico if else para guiar el flujo y que no se nos caiga el programa
        if (nuevaCarta != null) {
            puedeRobar = true;
            this.mano.add(nuevaCarta);
            System.out.println(this.nombre + " ha robado una carta y se anade a su mano.");
        } else {
            puedeRobar = false;
            System.out.println("Error: No hay cartas validas para robar.");
        }

        //retorno explicito para asegurar la buena practica
        return;
    }

    //metodo para mostrar como va el jugador en la partida
    public void mostrarEstado() {
        //declarando variables locales primero
        String nombreActivo;

        System.out.println("--- Estado del Jugador: " + this.nombre + " ---");

        //verificamos si hay un pokemon activo para no imprimir un null y mandarnos un condoro
        if (this.pokemonActivo != null) {
            nombreActivo = this.pokemonActivo.getNombre();
            System.out.println("Pokemon Activo: " + nombreActivo);
        } else {
            System.out.println("Pokemon Activo: Ninguno asignado aun.");
        }

        System.out.println("Cartas en la mano: " + this.mano.size());
        System.out.println("Pokemon en la banca: " + this.banca.size());
        System.out.println("Cartas en la pila de descarte: " + this.pilaDescarte.size());

        //retornamos explicito al final
        return;
    }

    //metodo para jugar un pokemon a la banca directamente desde la mano
    public void jugarPokemonBanca(int indiceMano) {
        //variables locales al inicio siempre
        int limiteBanca;
        int cantidadActual;
        Carta cartaSeleccionada;
        CartaPokemon nuevoPokemon;

        limiteBanca = 5;
        cantidadActual = this.banca.size();

        //puro if else anidado para controlar el flujo sin dramas
        if (indiceMano >= 0) {
            if (indiceMano < this.mano.size()) {
                cartaSeleccionada = this.mano.get(indiceMano);
                
                //aca aplicamos el instanceof para asegurarnos que sea un pokemon y no otra cosa
                if (cartaSeleccionada instanceof CartaPokemon) {
                    if (cantidadActual < limiteBanca) {
                        nuevoPokemon = (CartaPokemon) cartaSeleccionada;
                        this.banca.add(nuevoPokemon);
                        this.mano.remove(indiceMano); //la sacamos de la mano para no clonar cartas
                        System.out.println(this.nombre + " ha jugado a " + nuevoPokemon.getNombre() + " a la banca desde su mano.");
                    } else {
                        System.out.println("La banca ya esta llena (limite de 5 Pokemon).");
                    }
                } else {
                    System.out.println("Error: La carta seleccionada no es un Pokemon.");
                }
            } else {
                System.out.println("Error: Indice de la mano invalido.");
            }
        } else {
            System.out.println("Error: El indice debe ser mayor o igual a cero.");
        }

        //retorno explicito
        return;
    }

    //metodo para subir a un pokemon de la banca al combate
    public void cambiarPokemonActivo(int indiceBanca) {
        //variables al inicio de la funcion
        CartaPokemon pokemonSeleccionado;
        CartaPokemon pokemonAnterior;

        //if else manejando el flujo de los indices
        if (indiceBanca >= 0) {
            if (indiceBanca < this.banca.size()) {
                pokemonSeleccionado = this.banca.get(indiceBanca);
                pokemonAnterior = this.pokemonActivo;

                //pasamos el seleccionado a activo y lo borramos de la banca
                this.pokemonActivo = pokemonSeleccionado;
                this.banca.remove(indiceBanca);

                //si ya teniamos uno peleando lo devolvemos a la banca para no perderlo
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

        //el buen return explicito
        return;
    }

    //metodo para ponerle una energia al pokemon que esta activo
    public void unirEnergiaActivo(int indiceMano) {
        //variables listas arriba
        Carta cartaSeleccionada;
        CartaEnergia nuevaEnergia;

        //controlando que no falte nada con if else
        if (this.pokemonActivo != null) {
            if (indiceMano >= 0) {
                if (indiceMano < this.mano.size()) {
                    cartaSeleccionada = this.mano.get(indiceMano);

                    //verificamos con instanceof que sea de energia para no meterle un entrenador por error
                    if (cartaSeleccionada instanceof CartaEnergia) {
                        nuevaEnergia = (CartaEnergia) cartaSeleccionada;
                        this.pokemonActivo.unirEnergia(nuevaEnergia);
                        this.mano.remove(indiceMano); //se descuenta de la mano
                    } else {
                        System.out.println("Error: La carta seleccionada no es de Energia.");
                    }
                } else {
                    System.out.println("Error: Indice de la mano invalido.");
                }
            } else {
                System.out.println("Error: El indice debe ser mayor o igual a cero.");
            }
        } else {
            System.out.println("Error: No hay un Pokemon activo para unirle energia.");
        }
        
        //retorno explicito
        return;
    }

    //metodo para evolucionar al pokemon usando una carta de la mano
    public void evolucionarActivo(int indiceMano) {
        //declaraciones iniciales
        Carta cartaSeleccionada;
        CartaPokemon nuevaFase;

        //if else dirigiendo todo el show
        if (this.pokemonActivo != null) {
            if (indiceMano >= 0) {
                if (indiceMano < this.mano.size()) {
                    cartaSeleccionada = this.mano.get(indiceMano);

                    //instanceof salvando la nota para validar la evolucion
                    if (cartaSeleccionada instanceof CartaPokemon) {
                        nuevaFase = (CartaPokemon) cartaSeleccionada;
                        System.out.println("!Que esta pasando! " + this.pokemonActivo.getNombre() + " esta evolucionando a " + nuevaFase.getNombre() + "!");
                        this.pokemonActivo = nuevaFase;
                        this.mano.remove(indiceMano); //chao de la mano
                    } else {
                        System.out.println("Error: La carta seleccionada no es un Pokemon para evolucionar.");
                    }
                } else {
                    System.out.println("Error: Indice de la mano invalido.");
                }
            } else {
                System.out.println("Error: El indice debe ser mayor o igual a cero.");
            }
        } else {
            System.out.println("Error: No hay un Pokemon activo para evolucionar.");
        }
        
        //retornamos
        return;
    }

    //metodo clave para usar el entrenador y que se vaya al descarte
    public void usarEntrenador(int indiceMano) {
        //variables arribita
        Carta cartaSeleccionada;
        CartaEntrenador cartaEntrenador;

        //guiando el flujo con los condicionales
        if (indiceMano >= 0) {
            if (indiceMano < this.mano.size()) {
                cartaSeleccionada = this.mano.get(indiceMano);

                //validamos que sea entrenador antes de usarla
                if (cartaSeleccionada instanceof CartaEntrenador) {
                    cartaEntrenador = (CartaEntrenador) cartaSeleccionada;
                    System.out.println(this.nombre + " usa la carta de Entrenador: " + cartaEntrenador.getNombre());
                    
                    //aprovechando el polimorfismo llamando al metodo jugar
                    cartaEntrenador.jugar();
                    
                    //cumpliendo con la pauta: la carta usada se va al descarte y sale de la mano
                    this.pilaDescarte.add(cartaEntrenador);
                    this.mano.remove(indiceMano);
                    System.out.println("La carta " + cartaEntrenador.getNombre() + " fue enviada a la pila de descarte.");

                } else {
                    System.out.println("Error: La carta seleccionada no es un Entrenador.");
                }
            } else {
                System.out.println("Error: Indice de la mano invalido.");
            }
        } else {
            System.out.println("Error: El indice debe ser mayor o igual a cero.");
        }
        
        //retorno explicito final
        return;
    }

    //metodo corto para usar la habilidad
    public void usarHabilidadActivo() {
        //condicional if else basico
        if (this.pokemonActivo != null) {
            this.pokemonActivo.usarHabilidad();
        } else {
            System.out.println("Error: No hay un Pokemon activo para usar una habilidad.");
        }
        
        //retorno
        return;
    }

    //metodo para mandar el ataque
    public void atacarConActivo(int indiceAtaque) {
        //condicional if else
        if (this.pokemonActivo != null) {
            this.pokemonActivo.atacar(indiceAtaque);
        } else {
            System.out.println("Error: No hay un Pokemon activo para realizar el ataque.");
        }
        
        //retorno
        return;
    }
}