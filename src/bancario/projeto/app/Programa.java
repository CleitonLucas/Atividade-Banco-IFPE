package bancario.projeto.app;

import java.math.BigDecimal;
import java.util.Scanner;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;
import bancario.projeto.persistencia.PersistenciaEmArquivo;
import bancario.projeto.utils.Utilitarios;

public class Programa {
	static PersistenciaEmArquivo p = new PersistenciaEmArquivo();
	
	public static void main(String[] args) {
		System.out.println("Bem-vindo ao Programa bancário v1");
		inicial(p);
	}
	
/* ------------------------------------------ Introdução à aplicação -----------------------------------------------------*/
	
	public static void inicial(PersistenciaEmArquivo p) {
		Scanner sc = new Scanner(System.in);
		int opcaoIni;
		System.out.println("\nSelecione a opção que você deseja realizar:\n"
				+ "Opção 1 - Cadastrar novo cliente \n"
				+ "Opção 2 - Remover um cliente \n"
				+ "Opção 3 - Listar todos os clientes \n"
				+ "Opção 4 - Ingressar com um cliente \n"
				+ "Opção 5 - Sair da aplicação");
		
		opcaoIni = sc.nextInt();
		
		switch(opcaoIni) {
		case 1:
			cadastrarCliente();
			break;
		case 2:
			removerCliente();
			break;
		case 3:
			listarTodosOsClientes();
			break;
		case 4:
			ingressarComCliente();
			break;
		case 5:
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Obrigado por utilizar nossos serviços !");
			System.out.println("---------------------------------------------------------------------");
			opcaoIni = 0;
			break;
		default:
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Opção inválida tente novamente !");
			System.out.println("---------------------------------------------------------------------");
			inicial(p);
		}
		
		sc.close();
	}

	private static void cadastrarCliente() {
		Scanner sc = new Scanner(System.in);
		String nome;
		String cpf;
		
		System.out.print("\nInforme o nome do cliente: ");
		nome = sc.next();
		System.out.print("Informe o CPF deste cliente: ");
		cpf = sc.next();
		
		Cliente clientetemp = new Cliente(nome, cpf);
		
		p.adicionarCliente(clientetemp);
		inicial(p);
		
		sc.close();
	}
	
	private static void removerCliente() {
		Scanner sc = new Scanner(System.in);
		String cpf;
		
		System.out.print("\nInforme o CPF deste cliente: ");
		cpf = sc.next();
		
		Cliente clientetemp = new Cliente(null, cpf);
		
		p.removerCliente(clientetemp);
		inicial(p);
		
		sc.close();
	}
	
	private static void listarTodosOsClientes() {
		System.out.println("\n---------------------------------------------------------------------");
		System.out.println(p.getClientes());
		System.out.println("---------------------------------------------------------------------");
		
		inicial(p);
	}
	
	private static void ingressarComCliente() {
		Scanner sc = new Scanner(System.in);
		String cpf;
		
		System.out.print("\nInforme o CPF do cliente para login: ");
		cpf = sc.next();
		
		Cliente clienteLogin = p.localizarCliente(cpf);
		
		if(clienteLogin == null) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Cliente não encontrado !");
			System.out.println("---------------------------------------------------------------------");
			
			inicial(p);
		}else {
			loginCliente(clienteLogin);
		}
		
		sc.close();
	}
	
/* ------------------------------------------ Metodos referente à Cliente -----------------------------------------------------*/

	private static void loginCliente(Cliente clienteLogin) {
		Scanner sc = new Scanner(System.in);
		int opcaoLoginCliente;
		
		System.out.println("\nOlá " +clienteLogin.getNome()+ ", o que gostaria de fazer ? \n"
				+ "Opção 1 - Criar uma nova conta \n"
				+ "Opção 2 - Remover uma conta possuidora \n"
				+ "Opção 3 - Listar todas as contas possuidora \n"
				+ "Opção 4 - Ingressar em uma conta \n"
				+ "Opção 5 - Balanço geral de todas as contas \n"
				+ "Opção 6 - Logout do cliente \n"
				+ "Opção 7 - Sair da aplicação");
		
		opcaoLoginCliente = sc.nextInt();
		
		switch(opcaoLoginCliente) {
		case 1:
			criarConta(clienteLogin);
			break;
		case 2:
			removerConta(clienteLogin);
			break;
		case 3:
			listarTodosAsContas(clienteLogin);
			break;
		case 4:
			ingressarComConta(clienteLogin);
			break;
		case 5:
			balancoContas(clienteLogin);
			break;
		case 6:
			inicial(p);
			break;
		case 7:
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Obrigado por utilizar nossos serviços !");
			System.out.println("---------------------------------------------------------------------");
			opcaoLoginCliente = 0;
			break;
		default:
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Opção inválida tente novamente !");
			System.out.println("---------------------------------------------------------------------");
			loginCliente(clienteLogin);
		}
		
		sc.close();
	}
	
	public static void criarConta(Cliente clienteTemp) {
		ContaBancaria contaTemp = new ContaBancaria();
		clienteTemp.adicionarConta(contaTemp);
		p.atualizarCliente(clienteTemp);
		loginCliente(clienteTemp);
	}
	
	public static void removerConta(Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		Integer numeroContaTemp;
		
		System.out.println("\n---------------------------------------------------------------------");
		System.out.println(clienteTemp.getContas());
		System.out.println("\n---------------------------------------------------------------------");
		
		System.out.print("\nDigite o número da conta que deseja remover: ");
		numeroContaTemp = sc.nextInt();
		
		ContaBancaria contaTemp = new ContaBancaria(numeroContaTemp);
		
		clienteTemp.removerConta(contaTemp);
		p.atualizarCliente(clienteTemp);
		loginCliente(clienteTemp);
		
		sc.close();
	}
	
	public static void listarTodosAsContas(Cliente clienteTemp) {
		System.out.println("\n---------------------------------------------------------------------");
		System.out.println(clienteTemp.getContas());
		System.out.println("---------------------------------------------------------------------"); 
		
		loginCliente(clienteTemp);
	}
	
	public static void ingressarComConta(Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		Integer numeroContaTemp;
		
		System.out.print("\nInforme o número da conta para login: ");
		numeroContaTemp = sc.nextInt();
		
		ContaBancaria contaLogin = clienteTemp.localizarConta(numeroContaTemp);
		
		if(contaLogin == null) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não existente !");
			System.out.println("---------------------------------------------------------------------");
			
			loginCliente(clienteTemp);
		}else {
			loginConta(contaLogin, clienteTemp);
		}
		
		sc.close();
		
	}
	
	public static void balancoContas(Cliente clienteTemp) {
		BigDecimal balanco = BigDecimal.ZERO;
		
		for(ContaBancaria conta: clienteTemp.getContas()) {
			balanco = balanco.add(conta.getSaldo());
		}
		
		System.out.println("\n---------------------------------------------------------------------");
		System.out.println("Balanço total das contas: " + Utilitarios.formatarValor(balanco));
		System.out.println("---------------------------------------------------------------------");
		
		loginCliente(clienteTemp);
	}
	
/* ------------------------------------------ Metodos referente à Conta -----------------------------------------------------*/
	
	private static void loginConta(ContaBancaria contaLogin, Cliente clienteLogin) {
		Scanner sc = new Scanner(System.in);
		int opcaoLoginConta;
		
		System.out.println("\nVocê está na conta: " + contaLogin.getNumeroConta() + "\n"
				+ "Saldo atual: " + Utilitarios.formatarValor(contaLogin.getSaldo()) + "\n"
				+ "Selecione a opção que você deseja realizar:\n"
				+ "Opção 1 - Realizar depósito \n"
				+ "Opção 2 - Realizar saque \n"
				+ "Opção 3 - Realizar Transferência \n"
				+ "Opção 4 - Logout da conta \n"
				+ "Opção 5 - Sair da aplicação");
		
		
		opcaoLoginConta = sc.nextInt();
		
		switch(opcaoLoginConta) {
		case 1:
			realizarDeposito(contaLogin, clienteLogin);
			break;
		case 2:
			realizarSaque(contaLogin, clienteLogin);
			break;
		case 3:
			realizarTransferencia(contaLogin, clienteLogin);
			break;
		case 4:
			loginCliente(clienteLogin);
			break;
		case 5:
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Obrigado por utilizar nossos serviços !");
			System.out.println("---------------------------------------------------------------------");
			opcaoLoginConta = 0;
			break;
		default:
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Opção inválida tente novamente !");
			System.out.println("---------------------------------------------------------------------");
			loginConta(contaLogin, clienteLogin);
		}
		
		sc.close();
	}
	
	public static void realizarDeposito(ContaBancaria contaTemp, Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		BigDecimal quantia;
		
		System.out.print("\nDigite quanto você quer depositar: R$ ");
		quantia = sc.nextBigDecimal();
		
		contaTemp.depositar(quantia);
		p.atualizarCliente(clienteTemp);
		loginConta(contaTemp, clienteTemp);
	}
	
	public static void realizarSaque(ContaBancaria contaTemp, Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		BigDecimal quantia;
		
		System.out.print("\nDigite quanto você quer sacar: R$ ");
		quantia = sc.nextBigDecimal();
		
		contaTemp.sacar(quantia);
		p.atualizarCliente(clienteTemp);
		loginConta(contaTemp, clienteTemp);
		
		sc.close();
	}
	
	public static void realizarTransferencia(ContaBancaria contaTempOrigem, Cliente clienteTempOrigem) {
	    Scanner sc = new Scanner(System.in);
	    BigDecimal quantia;
	    Integer numeroContaDestino;
	    
	    System.out.print("\nDigite quanto você quer transferir: R$ ");
	    quantia = sc.nextBigDecimal();
	    
	    System.out.print("Digite o número da conta para a qual você quer transferir: ");
	    numeroContaDestino = sc.nextInt();

	    ContaBancaria contaTempDestino = clienteTempOrigem.localizarConta(numeroContaDestino);
	    
	    if (contaTempDestino == null) {
	        for (Cliente cliente : p.getClientes()) {
	            if (!cliente.equals(clienteTempOrigem)) { 
	                contaTempDestino = cliente.localizarConta(numeroContaDestino);
	                if (contaTempDestino != null) {
	                    break; 
	                }
	            }
	        }
	    }
	    
	    if(contaTempDestino == null) {
	    	System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não existente !");
			System.out.println("---------------------------------------------------------------------");
	    }else {
	    	contaTempOrigem.transferir(contaTempDestino, quantia);
	    }
	    
	   p.atualizarCliente(clienteTempOrigem);
	   loginConta(contaTempOrigem, clienteTempOrigem);
	   
	   sc.close();
	}

}
