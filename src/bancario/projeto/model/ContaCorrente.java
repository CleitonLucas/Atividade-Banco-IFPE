package bancario.projeto.model;

import java.io.Serializable;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import bancario.projeto.model.enumerator.TipoTransacao;
import exception.ContaException;
import exception.ValorInvalidoException;

public class ContaCorrente implements IConta, Serializable {
	private static final long serialVersionUID = 1L;
    private Integer numeroConta;
    private BigDecimal saldo;
    private LocalDateTime dataAbertura;
    private boolean status;
    
    private List<Transacao> transacoes = new ArrayList<>();
    
    public ContaCorrente(Integer numeroConta) {
        this();
        this.numeroConta = numeroConta;
    }

    public ContaCorrente() {
        this.numeroConta = new Random().nextInt(999999999);
        this.saldo = BigDecimal.ZERO;
        this.dataAbertura = LocalDateTime.now();
        this.status = true;
    }

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public List<Transacao> getTransacoes() {
		// TODO Auto-generated method stub
		return transacoes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numeroConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaCorrente other = (ContaCorrente) obj;
		return Objects.equals(numeroConta, other.numeroConta);
	}

	@Override
	public String toString() {
		return "ContaCorrente [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
				+ ", status=" + status + "]";
	}
}
