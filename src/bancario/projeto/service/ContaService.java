package bancario.projeto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import bancario.projeto.model.IConta;
import bancario.projeto.model.Transacao;
import bancario.projeto.model.enumerator.TipoTransacao;
import bancario.projeto.persistencia.PersistenciaEmArquivo;
import exception.ContaException;
import exception.ValorInvalidoException;

public class ContaService {
	 private PersistenciaEmArquivo persistencia;
	 private TransacaoService transacao;
	 
	 public ContaService(PersistenciaEmArquivo persistencia, TransacaoService transacao) {
		 this.persistencia = persistencia;
		 this.transacao = transacao;
	 }
	
	
    public void depositar(IConta conta, BigDecimal quantia) throws ValorInvalidoException, ContaException {
        if (!conta.isStatus()) throw new ContaException("Conta desativada");
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) throw new ValorInvalidoException("Valor inválido para depósito");
        
        conta.setSaldo(conta.getSaldo().add(quantia.setScale(2, RoundingMode.HALF_DOWN)));
        transacao.registrarTransacao(conta, quantia, TipoTransacao.CREDITO, null);
    }

    public void sacar(IConta conta, BigDecimal quantia) throws ValorInvalidoException, ContaException {
        if (!conta.isStatus()) throw new ContaException("Conta desativada");
        if (quantia.compareTo(BigDecimal.ZERO) <= 0 || quantia.compareTo(conta.getSaldo()) > 0) 
            throw new ValorInvalidoException("Saldo insuficiente ou valor inválido");

        conta.setSaldo(conta.getSaldo().subtract(quantia.setScale(2, RoundingMode.HALF_DOWN)));
        conta.getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.DEBITO, null));
        transacao.registrarTransacao(conta, quantia, TipoTransacao.DEBITO, null);
    }

    public void transferir(IConta origem, IConta destino, BigDecimal quantia) throws ValorInvalidoException, ContaException {
        if (!origem.isStatus() || !destino.isStatus()) {
            throw new ContaException("Conta de origem ou destino está desativada");
        }

        if (quantia.compareTo(BigDecimal.ZERO) <= 0 || quantia.compareTo(origem.getSaldo()) > 0) {
            throw new ValorInvalidoException("Saldo insuficiente ou valor inválido");
        }
        
        if (origem.getClass() != destino.getClass()) {
            origem.setSaldo(origem.getSaldo().subtract(quantia.add(quantia.multiply(new BigDecimal(IConta.TAXA_ADMINISTRATIVA)))).setScale(2, RoundingMode.HALF_DOWN));
            destino.setSaldo(destino.getSaldo().add(quantia));
        }else {
        	origem.setSaldo(origem.getSaldo().subtract(quantia.setScale(2, RoundingMode.HALF_DOWN)));
            destino.setSaldo(destino.getSaldo().add(quantia));
        }

        transacao.registrarTransacao(origem, quantia, TipoTransacao.DEBITO_TRANSACAO, destino);
        transacao.registrarTransacao(destino, quantia, TipoTransacao.CREDITO_TRANSACAO, null);
    }


}
