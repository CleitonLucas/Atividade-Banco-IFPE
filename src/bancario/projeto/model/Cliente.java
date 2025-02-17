package bancario.projeto.model;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bancario.projeto.utils.Utilitarios;
import exception.ContaException;

public class Cliente implements Serializable { /*Serializable - processo de converter um objeto em uma sequência de bytes, 
												afim de realizar uma transmissão via rede ou salvar em um arquivo de persistência*/
	
	private static final long serialVersionUID = 1L; /*Garantir a compatibilidade entre versões de classe que implementam a interface Serializable, 
													afim de evitar erros de serialização e desserialização. Para realização, a JVM inclui este valor ao arquivo
													 e ao final do processo de transmissão de bytes ele compara o valor, se for igual ele desserializa, 
													 caso for diferente da erro*/
	private String cpf;
	private String nome;
	private List<IConta> contas; 
	
	public Cliente() {
		
	}
	
	public Cliente(String nome, String cpf) {
		this.cpf = cpf;
		this.nome = nome;
		contas = new ArrayList<IConta>();
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<IConta> getContas() {
		return contas;
	}

	public void setContas(List<IConta> contas) {
		this.contas = contas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "\nNome: "+nome+" \n"
				+ "CPF: "+Utilitarios.formatarCPF(cpf)+" \n"
				+ "Contas possuidoras: "+contas.size()+ "\n";
	}	
}
