package bancario.projeto.model;

import java.math.BigDecimal;

import java.time.Month;
import java.util.List;

import exception.ContaException;
import exception.ValorInvalidoException;

public interface IConta {
	final float TAXA_ADMINISTRATIVA = 0.02f; 
	 
	 //Gets e Sets
	 boolean isStatus(); 
	 void setStatus(boolean status);
	 BigDecimal getSaldo();
	 void setSaldo(BigDecimal novoSaldo);
	 List<Transacao> getTransacoes();
	 public Integer getNumeroConta();
}
