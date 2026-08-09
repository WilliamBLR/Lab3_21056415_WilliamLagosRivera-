import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * representa la definicion de una carta pokemon
 *
 * representacion: datos impresos, habilidad opcional y hasta tres ataques
 */
public class CartaPokemon_LagosRivera_21056415 extends Carta_LagosRivera_21056415 {
    /** guarda el nombre de la etapa anterior cuando corresponde */
    private final String evolucionaDe;
    /** guarda los puntos de salud maximos */
    private final int ps;
    /** guarda el tipo canonico del pokemon */
    private final String tipo;
    /** guarda la debilidad opcional */
    private final String debilidad;
    /** guarda la resistencia opcional */
    private final String resistencia;
    /** indica cuantas energias se descartan para retirarlo */
    private final int costoRetirada;
    /** indica si el pokemon entrega dos premios */
    private final boolean esEx;
    /** contiene la habilidad opcional del pokemon */
    private final Ataque_LagosRivera_21056415 habilidad;
    /** contiene hasta tres ataques propios */
    private final ArrayList<Ataque_LagosRivera_21056415> ataques;

    /**
     * construye una carta pokemon validando sus limites de ataques
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param nombre nombre del pokemon
     * @param evolucionaDe nombre de la etapa anterior o null
     * @param ps puntos de salud maximos
     * @param tipo tipo del pokemon
     * @param debilidad debilidad opcional
     * @param resistencia resistencia opcional
     * @param costoRetirada cantidad de energias de retirada
     * @param esEx indica si entrega dos premios
     * @param habilidad habilidad opcional sin costo ni dano base
     * @param ataques lista de hasta tres ataques
     */
    public CartaPokemon_LagosRivera_21056415(
            String expansion,
            int numero,
            String nombre,
            String evolucionaDe,
            int ps,
            String tipo,
            String debilidad,
            String resistencia,
            int costoRetirada,
            boolean esEx,
            Ataque_LagosRivera_21056415 habilidad,
            List<Ataque_LagosRivera_21056415> ataques) {
        super(expansion, numero, validarDatos(
                nombre, ps, tipo, debilidad, resistencia,
                costoRetirada, habilidad, ataques));

        this.evolucionaDe = textoOpcional(evolucionaDe);
        this.ps = ps;
        this.tipo = CartaEnergia_LagosRivera_21056415.normalizarTipoBasico(tipo);
        this.debilidad = tipoOpcional(debilidad);
        this.resistencia = tipoOpcional(resistencia);
        this.costoRetirada = costoRetirada;
        this.esEx = esEx;
        this.habilidad = habilidad;
        this.ataques = new ArrayList<Ataque_LagosRivera_21056415>(ataques);
    }

    /**
     * devuelve el nombre de la etapa anterior
     *
     * @return nombre anterior o null si es basico
     */
    public String getEvolucionaDe() {
        return this.evolucionaDe;
    }

    /**
     * devuelve los puntos de salud maximos
     *
     * @return ps impresos en la carta
     */
    public int getPs() {
        return this.ps;
    }

    /**
     * devuelve el tipo del pokemon
     *
     * @return tipo canonico
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * devuelve la debilidad del pokemon
     *
     * @return debilidad o null
     */
    public String getDebilidad() {
        return this.debilidad;
    }

    /**
     * devuelve la resistencia del pokemon
     *
     * @return resistencia o null
     */
    public String getResistencia() {
        return this.resistencia;
    }

    /**
     * devuelve el costo de retirada incoloro
     *
     * @return cantidad de energias
     */
    public int getCostoRetirada() {
        return this.costoRetirada;
    }

    /**
     * revisa si el pokemon es ex
     *
     * @return true si entrega dos premios
     */
    public boolean esEx() {
        return this.esEx;
    }

    /**
     * devuelve la habilidad opcional
     *
     * @return habilidad o null
     */
    public Ataque_LagosRivera_21056415 getHabilidad() {
        return this.habilidad;
    }

    /**
     * devuelve ataques que no pueden modificarse externamente
     *
     * @return vista de los ataques
     */
    public List<Ataque_LagosRivera_21056415> getAtaques() {
        return Collections.unmodifiableList(this.ataques);
    }

    /**
     * revisa si esta carta es basica
     *
     * @return true cuando no evoluciona desde otra carta
     */
    public boolean esBasico() {
        return this.evolucionaDe == null;
    }

    /**
     * busca un ataque sin distinguir mayusculas
     *
     * @param nombreAtaque nombre solicitado
     * @return ataque encontrado o null
     */
    public Ataque_LagosRivera_21056415 buscarAtaque(String nombreAtaque) {
        if (nombreAtaque == null) {
            return null;
        }
        for (Ataque_LagosRivera_21056415 ataque : this.ataques) {
            if (ataque.getNombre().equalsIgnoreCase(nombreAtaque.trim())) {
                return ataque;
            }
        }
        return null;
    }

    /**
     * identifica la familia de esta carta
     *
     * @return pokemon
     */
    @Override
    public String getFamilia() {
        return "pokemon";
    }

    /**
     * convierte un texto opcional vacio en null
     *
     * @param texto valor que puede venir vacio
     * @return texto limpio o null
     */
    private static String textoOpcional(String texto) {
        return texto == null || texto.trim().isEmpty() ? null : texto.trim();
    }

    /**
     * normaliza un tipo opcional cuando fue ingresado
     *
     * @param tipo tipo que puede venir vacio
     * @return tipo canonico o null
     */
    private static String tipoOpcional(String tipo) {
        return tipo == null || tipo.trim().isEmpty()
                ? null : CartaEnergia_LagosRivera_21056415.normalizarTipoBasico(tipo);
    }

    /**
     * valida los datos propios antes de llamar al constructor padre
     *
     * @param nombre nombre visible del pokemon
     * @param ps puntos de salud maximos
     * @param tipo tipo elemental del pokemon
     * @param debilidad debilidad opcional
     * @param resistencia resistencia opcional
     * @param costoRetirada cantidad de energias de retirada
     * @param habilidad habilidad opcional
     * @param ataques lista de hasta tres ataques
     * @return nombre validado para el constructor padre
     */
    private static String validarDatos(
            String nombre,
            int ps,
            String tipo,
            String debilidad,
            String resistencia,
            int costoRetirada,
            Ataque_LagosRivera_21056415 habilidad,
            List<Ataque_LagosRivera_21056415> ataques) {
        if (ps <= 0) {
            throw new IllegalArgumentException("los ps deben ser mayores a cero");
        }
        if (costoRetirada < 0) {
            throw new IllegalArgumentException("el costo de retirada no puede ser negativo");
        }
        if (ataques == null) {
            throw new IllegalArgumentException("la lista de ataques no puede ser null");
        }
        if (habilidad != null && !habilidad.esHabilidadValida()) {
            throw new IllegalArgumentException("la habilidad debe tener costo vacio y dano base cero");
        }
        int limiteAtaques = habilidad == null ? 3 : 2;
        if (ataques.size() > limiteAtaques) {
            throw new IllegalArgumentException(
                    "el pokemon supera el maximo de " + limiteAtaques + " ataques");
        }
        for (Ataque_LagosRivera_21056415 ataque : ataques) {
            if (ataque == null) {
                throw new IllegalArgumentException("la lista de ataques contiene un valor null");
            }
        }
        CartaEnergia_LagosRivera_21056415.normalizarTipoBasico(tipo);
        tipoOpcional(debilidad);
        tipoOpcional(resistencia);
        return nombre;
    }
}
