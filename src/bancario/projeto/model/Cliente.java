package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable { /*Serializable - processo de converter um objeto em uma sequência de bytes, 
												afim de realizar uma transmissão via rede ou salvar em um arquivo de persistência*/
	
	private static final long serialVersionUID = 1L; /*Garantir a compatibilidade entre versões de classe que implementam a interface Serializable, 
													afim de evitar erros de serialização e desserialização. Para realização, a JVM inclui este valor ao arquivo
													 e ao final do processo de transmissão de bytes ele compara o valor, se for igual ele desserializa, 
													 caso for diferente da erro*/
	private String cpf;
	private String nome;
	private ArrayList<ContaBancaria> contas; 
	
	public Cliente() {
		
	}
	
	public Cliente(String nome, String cpf) {
		this.cpf = cpf;
		this.nome = nome;
		contas = new ArrayList<ContaBancaria>();
	}
	
	public void adicionarConta(ContaBancaria conta) {
		if(contas.contains(conta)) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta já cadastrada!");
			System.out.println("---------------------------------------------------------------------");
		}else {
			contas.add(conta);
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Conta cadastrada com sucesso!");
			System.out.println("---------------------------------------------------------------------");
		}
	}
	
	public void removerConta(ContaBancaria conta) {
		if(contas.contains(conta)) {
			contas.remove(conta);
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Conta removida com sucesso!");
			System.out.println("---------------------------------------------------------------------");
		}else {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não existente !");
			System.out.println("---------------------------------------------------------------------");
		}
	}
	
	public ContaBancaria localizarConta(Integer numero) {
		ContaBancaria contaTemp = new ContaBancaria();
		contaTemp.setNumeroConta(numero);
		
		if(contas.contains(contaTemp)) {
			int index = contas.indexOf(contaTemp);
			contaTemp = contas.get(index);
			return contaTemp;
		}
		
		return null;

	}
	
	public void atualizarConta(ContaBancaria conta) {
		if(contas.contains(conta)) {
			int index = contas.indexOf(conta);
			contas.set(index, conta);
			
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Conta atualizada com sucesso!");
			System.out.println("---------------------------------------------------------------------");
		}else {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não localizada !");
			System.out.println("---------------------------------------------------------------------");
		}
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

	public ArrayList<ContaBancaria> getContas() {
		return contas;
	}

	public void setContas(ArrayList<ContaBancaria> contas) {
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
				+ "CPF: "+cpf+" \n"
				+ "Contas: "+contas+ "\n";
	}	
}
