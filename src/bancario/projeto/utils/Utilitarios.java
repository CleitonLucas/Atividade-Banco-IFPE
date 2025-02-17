package bancario.projeto.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utilitarios {
    public static String formatarValor(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }
    
    public static String formatarCPF(String cpf) {
    	return cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11);
    }
    
    public static String formatarNumeroConta(Integer numeroConta) {
    	String numeroContaTemp = numeroConta.toString();
    	return numeroContaTemp.substring(0,8) + "-" +numeroContaTemp.substring(8,9); 
    }
}
