import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

/**
 * representa una carta de energia basica
 *
 * representacion: una carta comun junto a uno de los nueve tipos validos
 */
public class CartaEnergia_LagosRivera_21056415 extends Carta_LagosRivera_21056415 {
    /** contiene los tipos de energia aceptados por el programa */
    private static final List<String> TIPOS_VALIDOS = Arrays.asList(
            "Planta", "Fuego", "Agua", "Rayo", "Psiquica",
            "Lucha", "Oscura", "Metalica", "Hada");

    /** guarda el tipo canonico de esta energia */
    private final String tipoEnergia;

    /**
     * construye una energia basica usando su tipo como nombre
     *
     * @param expansion expansion de la carta
     * @param numero numero dentro de la expansion
     * @param tipoEnergia tipo de energia basica
     */
    public CartaEnergia_LagosRivera_21056415(
            String expansion, int numero, String tipoEnergia) {
        super(expansion, numero, normalizarTipoBasico(tipoEnergia));
        this.tipoEnergia = normalizarTipoBasico(tipoEnergia);
    }

    /**
     * devuelve el tipo canonico de la energia
     *
     * @return tipo de energia
     */
    public String getTipoEnergia() {
        return this.tipoEnergia;
    }

    /**
     * identifica la familia de esta carta
     *
     * @return energia
     */
    @Override
    public String getFamilia() {
        return "energia";
    }

    /**
     * normaliza y valida un tipo de energia basica
     *
     * @param tipo tipo ingresado
     * @return tipo canonico sin tildes
     */
    public static String normalizarTipoBasico(String tipo) {
        String limpio = limpiarTexto(tipo);
        for (String valido : TIPOS_VALIDOS) {
            if (valido.equalsIgnoreCase(limpio)) {
                return valido;
            }
        }
        throw new IllegalArgumentException(
                "tipo de energia invalido. use planta, fuego, agua, rayo, psiquica, lucha, oscura, metalica o hada");
    }

    /**
     * normaliza un tipo usado como costo de ataque
     *
     * @param tipo tipo ingresado
     * @return tipo basico o incolora
     */
    public static String normalizarTipoCosto(String tipo) {
        String limpio = limpiarTexto(tipo);
        if ("Incolora".equalsIgnoreCase(limpio)) {
            return "Incolora";
        }
        return normalizarTipoBasico(limpio);
    }

    /**
     * normaliza el texto usado para comparar tipos de energia
     *
     * @param texto valor que se desea normalizar
     * @return texto sin diferencias de mayusculas ni tildes
     */
    private static String limpiarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("el tipo de energia no puede estar vacio");
        }
        String sinTildes = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.substring(0, 1).toUpperCase()
                + sinTildes.substring(1).toLowerCase();
    }
}
