package bancario.projeto.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import bancario.projeto.model.IConta;
import bancario.projeto.model.Transacao;
import bancario.projeto.model.enumerator.TipoTransacao;
import bancario.projeto.persistencia.PersistenciaEmArquivo;

public class TransacaoService {
	private PersistenciaEmArquivo persistencia;
	
	public TransacaoService(PersistenciaEmArquivo persistencia) {
		this.persistencia = persistencia;
	}
	
	public void consultarExtrato(List<Transacao> transacoes, Month mes, int ano) {
        for (Transacao t : transacoes) {
            if (t.getDataTransacao().getMonth() == mes && t.getDataTransacao().getYear() == ano) {
                System.out.println(t);
            }
        }
    }
	
	public void registrarTransacao(IConta conta, BigDecimal quantia, TipoTransacao tipoTransacao, IConta contaDestino) {
        Transacao transacao = new Transacao(quantia, LocalDateTime.now(), tipoTransacao, contaDestino);
        conta.getTransacoes().add(transacao);
    }
}
