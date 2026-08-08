import java.util.ArrayList;

public class Jugador {
    //variablesDefinidasExplicitamenteAlInicio
    private int id;
    private String nombre;
    private Mazo mazoActual;
    private ArrayList<Carta> mano;
    private ArrayList<CartaEnJuego> banca;
    private CartaEnJuego pokemonActivo;
    private ArrayList<Carta> pilaDescarte;
    private ArrayList<Carta> premios;

    //constructorDeLaClase
    public Jugador(int id, String nombre, Mazo mazoActual) {
        this.id = id;
        this.nombre = nombre;
        this.mazoActual = mazoActual;
        this.mano = new ArrayList<Carta>();
        this.banca = new ArrayList<CartaEnJuego>();
        this.pokemonActivo = null;
        this.pilaDescarte = new ArrayList<Carta>();
        this.premios = new ArrayList<Carta>();
    }

    //gettersParaQueElJuegoPuedaLeerLosDatos
    public String getNombre() { return this.nombre; }
    public Mazo getMazoActual() { return this.mazoActual; }
    public CartaEnJuego getPokemonActivo() { return this.pokemonActivo; }

    //metodoSilenciosoParaRepartirCartasAlInicioSinSpam
    public void robarCartaDelMazoSilencioso() {
        //variablesLocales
        Carta cartaRobada;
        
        cartaRobada = this.mazoActual.robarCartaSuperior();
        
        //condicionalIfElseDirigeElFlujo
        if (cartaRobada != null) {
            this.mano.add(cartaRobada);
        } else {
            //noHaceNadaSiElMazoEstaVacio
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaSacarUnaCartaDelMazoYPonerlaEnPremios
    public void separarPremio() {
        //variablesLocales
        Carta cartaPremio;
        
        cartaPremio = this.mazoActual.robarCartaSuperior();
        
        //condicionalIfElse
        if (cartaPremio != null) {
            this.premios.add(cartaPremio);
        } else {
            //noHaceNada
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaRobarUnaCartaRealDelMazoEnElTurno
    public void robarCartaDelMazo() {
        //variablesLocales
        Carta cartaRobada;
        
        cartaRobada = this.mazoActual.robarCartaSuperior();

        //ifElseAvisandoSiPierdePorMazoVacio
        if (cartaRobada != null) {
            this.mano.add(cartaRobada);
            System.out.println(this.nombre + " ha robado a " + cartaRobada.getNombre() + " del mazo.");
        } else {
            System.out.println("Error: El mazo de " + this.nombre + " esta vacio. !Pierde la partida!");
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaMostrarElEstadoConPremiosYMazo
    public void mostrarEstado() {
        //variablesLocales
        String nombreActivo;

        System.out.println("- Jugador: " + this.nombre + " -");

        //ifElseRevisandoSiHayActivo
        if (this.pokemonActivo != null) {
            nombreActivo = this.pokemonActivo.getCartaOriginal().getNombre();
            System.out.println("  Activo: " + nombreActivo + " (HP: " + this.pokemonActivo.getHpActual() + ")");
        } else {
            System.out.println("  Activo: Ninguno asignado aun.");
        }

        System.out.println("  Mano: " + this.mano.size() + " | Banca: " + this.banca.size());
        System.out.println("  Mazo: " + this.mazoActual.getCartas().size() + " | Premios restantes: " + this.premios.size());
        
        //retornoExplicito
        return;
    }

    //metodoParaLimpiarAlPokemonMuerto
    public void enviarActivoAlDescarte() {
        //ifElse
        if (this.pokemonActivo != null) {
            System.out.println(this.pokemonActivo.getCartaOriginal().getNombre() + " es enviado a la pila de descarte.");
            this.pilaDescarte.add(this.pokemonActivo.getCartaOriginal());
            this.pokemonActivo = null;
        } else {
            //noHaceNada
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaSacarUnaCartaDePremioCuandoVencesAlRival
    public void tomarPremio(boolean rivalEraEX) {
        //variablesLocales
        int cantidadTomar;
        Carta premio;

        //ifElseRevisandoSiElRivalEraEXParaCobrarDoble
        if (rivalEraEX == true) {
            cantidadTomar = 2;
        } else {
            cantidadTomar = 1;
        }

        //bucleForParaCobrarLasCartas
        for (int i = 0; i < cantidadTomar; i = i + 1) {
            if (this.premios.size() > 0) {
                premio = this.premios.remove(0);
                this.mano.add(premio);
                System.out.println(this.nombre + " toma una carta de premio y se va a su mano.");
            } else {
                //noHaceNadaSiYaNoQuedanPremios
            }
        }

        //ifElseParaValidarVictoria
        if (this.premios.size() == 0) {
            System.out.println("!!! " + this.nombre + " HA TOMADO TODOS SUS PREMIOS Y GANA LA PARTIDA !!!");
        } else {
            //sigueJugando
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaAtacarAlRivalYCobrarPremios
    public void atacarAlRival(int indiceAtaque, Jugador rival) {
        //variablesLocales
        Ataque ataqueSeleccionado;
        CartaEnJuego activoRival;
        String miTipo;
        int dano;

        activoRival = rival.getPokemonActivo();

        //ifElseAnidadosParaValidarTodoElAtaque
        if (this.pokemonActivo != null) {
            if (activoRival != null) {
                if (indiceAtaque >= 0) {
                    if (indiceAtaque < this.pokemonActivo.getCartaOriginal().getAtaquesDisponibles().size()) {
                        ataqueSeleccionado = this.pokemonActivo.getCartaOriginal().getAtaquesDisponibles().get(indiceAtaque);
                        miTipo = this.pokemonActivo.getCartaOriginal().getTipo();
                        dano = ataqueSeleccionado.getDanoBase();

                        System.out.println(this.nombre + " ordena usar " + ataqueSeleccionado.getNombre() + "!");
                        ataqueSeleccionado.ejecutarEfecto();
                        
                        //aplicandoElDanoRealAlRival
                        activoRival.recibirDano(dano, miTipo);

                        //ifElseRevisandoSiLogramosElKnockOut
                        if (activoRival.getHpActual() <= 0) {
                            System.out.println("!El Pokemon rival ha sido debilitado!");
                            this.tomarPremio(activoRival.getCartaOriginal().getEsEX());
                            rival.enviarActivoAlDescarte();
                        } else {
                            System.out.println("El Pokemon rival resistio el golpe.");
                        }
                    } else {
                        System.out.println("Error: Indice de ataque invalido.");
                    }
                } else {
                    System.out.println("Error: Indice negativo.");
                }
            } else {
                System.out.println("Error: El rival no tiene un Pokemon activo para atacar.");
            }
        } else {
            System.out.println("Error: No tienes un Pokemon activo para atacar.");
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaJugarPokemonALaBancaDesdeLaMano
    public void jugarPokemonBanca(int indiceMano) {
        //variablesLocales
        int limiteBanca;
        Carta cartaSeleccionada;
        CartaPokemon nuevoPokemon;
        CartaEnJuego nuevoEnJuego;

        limiteBanca = 5;

        //ifElseParaGuiarElFlujo
        if (indiceMano >= 0) {
            if (indiceMano < this.mano.size()) {
                cartaSeleccionada = this.mano.get(indiceMano);
                
                //verificandoQueSeaPokemon
                if (cartaSeleccionada instanceof CartaPokemon) {
                    nuevoPokemon = (CartaPokemon) cartaSeleccionada;
                    
                    //validandoQueSeaBasicoParaEntrarALaBanca
                    if (nuevoPokemon.getEvolucionaDe() == null) {
                        if (this.banca.size() < limiteBanca) {
                            //creandoLaInstanciaEnJuego
                            nuevoEnJuego = new CartaEnJuego((int)(Math.random() * 1000), nuevoPokemon);
                            this.banca.add(nuevoEnJuego);
                            this.mano.remove(indiceMano);
                            System.out.println(this.nombre + " ha jugado a " + nuevoPokemon.getNombre() + " a la banca.");
                        } else {
                            System.out.println("La banca ya esta llena.");
                        }
                    } else {
                        System.out.println("Error: Solo los Pokemon basicos pueden jugarse directo a la banca.");
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
        
        //retornoExplicito
        return;
    }

    //metodoParaCambiarPokemonActivoPagandoRetirada
    public void cambiarPokemonActivo(int indiceBanca) {
        //variablesLocales
        CartaEnJuego pokemonSeleccionado;
        CartaEnJuego pokemonAnterior;
        int costoRetirada;

        //ifElseAnidados
        if (indiceBanca >= 0) {
            if (indiceBanca < this.banca.size()) {
                pokemonSeleccionado = this.banca.get(indiceBanca);
                pokemonAnterior = this.pokemonActivo;

                //siHayActivoHayQuePagarRetirada
                if (pokemonAnterior != null) {
                    costoRetirada = pokemonAnterior.getCartaOriginal().getCostoRetirada();
                    
                    if (pokemonAnterior.getEnergiasUnidas().size() >= costoRetirada) {
                        System.out.println("Pagando costo de retirada de " + costoRetirada + " energias...");
                        
                        //bucleForParaEliminarEnergiasComoPago
                        for (int i = 0; i < costoRetirada; i = i + 1) {
                            pokemonAnterior.getEnergiasUnidas().remove(0);
                        }
                        
                        this.banca.add(pokemonAnterior);
                        this.pokemonActivo = pokemonSeleccionado;
                        this.banca.remove(indiceBanca);
                        System.out.println(this.pokemonActivo.getCartaOriginal().getNombre() + " es ahora el Pokemon activo.");
                    } else {
                        System.out.println("Error: No tienes suficientes energias para pagar el costo de retirada.");
                    }
                } else {
                    //siNoHabiaActivoElCambioEsGratis
                    this.pokemonActivo = pokemonSeleccionado;
                    this.banca.remove(indiceBanca);
                    System.out.println(this.pokemonActivo.getCartaOriginal().getNombre() + " es ahora el Pokemon activo.");
                }
            } else {
                System.out.println("Error: Indice de la banca invalido.");
            }
        } else {
            System.out.println("Error: El indice debe ser mayor o igual a cero.");
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaUnirEnergiaAlActivo
    public void unirEnergiaActivo(int indiceMano) {
        //variablesLocales
        Carta cartaSeleccionada;
        CartaEnergia nuevaEnergia;

        //ifElse
        if (this.pokemonActivo != null) {
            if (indiceMano >= 0) {
                if (indiceMano < this.mano.size()) {
                    cartaSeleccionada = this.mano.get(indiceMano);

                    //verificamosQueSeaEnergia
                    if (cartaSeleccionada instanceof CartaEnergia) {
                        nuevaEnergia = (CartaEnergia) cartaSeleccionada;
                        this.pokemonActivo.unirEnergia(nuevaEnergia);
                        this.mano.remove(indiceMano);
                        System.out.println("Energia unida correctamente al activo.");
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
        
        //retornoExplicito
        return;
    }

    //metodoParaEvolucionarElPokemonActivo
    public void evolucionarActivo(int indiceMano) {
        //variablesLocales
        Carta cartaSeleccionada;
        CartaPokemon nuevaFase;
        CartaEnJuego nuevaInstanciaEvolucionada;
        String nombreActivo;

        //ifElseControlandoElFlujo
        if (this.pokemonActivo != null) {
            if (indiceMano >= 0) {
                if (indiceMano < this.mano.size()) {
                    cartaSeleccionada = this.mano.get(indiceMano);

                    if (cartaSeleccionada instanceof CartaPokemon) {
                        nuevaFase = (CartaPokemon) cartaSeleccionada;
                        nombreActivo = this.pokemonActivo.getCartaOriginal().getNombre();

                        //validandoQueSeaSuEvolucionCorrecta
                        if (nuevaFase.getEvolucionaDe() != null) {
                            if (nuevaFase.getEvolucionaDe().equals(nombreActivo)) {
                                System.out.println("!Evolucionando a " + nuevaFase.getNombre() + "!");
                                
                                //creandoElNuevoEnvoltorio
                                nuevaInstanciaEvolucionada = new CartaEnJuego((int)(Math.random() * 1000), nuevaFase);
                                
                                //bucleForTransfiriendoLasEnergiasAlNuevoPokemon
                                for (int i = 0; i < this.pokemonActivo.getEnergiasUnidas().size(); i = i + 1) {
                                    nuevaInstanciaEvolucionada.unirEnergia(this.pokemonActivo.getEnergiasUnidas().get(i));
                                }

                                this.pokemonActivo = nuevaInstanciaEvolucionada;
                                this.mano.remove(indiceMano);
                            } else {
                                System.out.println("Error: Esta carta no evoluciona de " + nombreActivo);
                            }
                        } else {
                            System.out.println("Error: La carta seleccionada es un Pokemon basico, no una evolucion.");
                        }
                    } else {
                        System.out.println("Error: La carta no es un Pokemon.");
                    }
                } else {
                    System.out.println("Error: Indice invalido.");
                }
            } else {
                System.out.println("Error: Indice negativo.");
            }
        } else {
            System.out.println("Error: No hay activo.");
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaUsarUnaCartaDeEntrenador
    public void usarEntrenador(int indiceMano) {
        //variablesLocales
        Carta cartaSeleccionada;
        CartaEntrenador cartaEntrenador;

        //ifElse
        if (indiceMano >= 0) {
            if (indiceMano < this.mano.size()) {
                cartaSeleccionada = this.mano.get(indiceMano);

                if (cartaSeleccionada instanceof CartaEntrenador) {
                    cartaEntrenador = (CartaEntrenador) cartaSeleccionada;
                    cartaEntrenador.jugar();
                    this.pilaDescarte.add(cartaEntrenador);
                    this.mano.remove(indiceMano);
                    System.out.println("Carta enviada al descarte.");
                } else {
                    System.out.println("Error: No es un Entrenador.");
                }
            } else {
                System.out.println("Error: Indice invalido.");
            }
        } else {
            System.out.println("Error: Indice negativo.");
        }
        
        //retornoExplicito
        return;
    }

    //metodoParaUsarHabilidad
    public void usarHabilidadActivo() {
        System.out.println("Habilidad activada (Metodo general de placeholder)");
        //retornoExplicito
        return;
    }
}