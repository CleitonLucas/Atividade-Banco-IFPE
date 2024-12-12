package bancario.projeto.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utilitarios {
    public static String formatarValor(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }
}
