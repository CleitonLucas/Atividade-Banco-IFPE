package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import bancario.projeto.model.enumerator.TipoTransacao;

public class Transacao implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal quantia;
	private LocalDateTime dataTransacao;
	private TipoTransacao tipoTransacao;
	private IConta destino;
	
	public Transacao(BigDecimal quantia, LocalDateTime dataTransacao, TipoTransacao tipoTransacao, IConta destino) {
		this.id = new Random().nextLong();
		this.quantia = quantia;
		this.dataTransacao = dataTransacao;
		this.tipoTransacao = tipoTransacao;
		this.destino = destino;
	}
	
	public Transacao() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantia() {
		return quantia;
	}

	public void setQuantia(BigDecimal quantia) {
		this.quantia = quantia;
	}

	public LocalDateTime getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(LocalDateTime dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public IConta getDestino() {
		return destino;
	}

	public void setDestino(IConta destino) {
		this.destino = destino;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", quantia=" + quantia + ", dataTransacao=" + dataTransacao + ", tipoTransacao="
				+ tipoTransacao + ", destino=" + destino + "]";
	}
}
