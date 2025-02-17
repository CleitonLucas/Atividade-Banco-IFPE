package bancario.projeto.app;

import java.math.BigDecimal;

import java.time.Month;
import java.util.Scanner;
import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaCorrente;
import bancario.projeto.model.ContaPoupanca;
import bancario.projeto.model.IConta;
import bancario.projeto.persistencia.PersistenciaEmArquivo;
import bancario.projeto.service.ClienteService;
import bancario.projeto.service.ContaService;
import bancario.projeto.service.TransacaoService;
import bancario.projeto.utils.Utilitarios;
import exception.ClienteException;
import exception.ContaException;
import exception.ValorInvalidoException;

public class Programa {
	static PersistenciaEmArquivo p = new PersistenciaEmArquivo();
	static TransacaoService transacaoServ = new TransacaoService(p);
	static ContaService contaServ = new ContaService(p, transacaoServ);
	static ClienteService clienteServ = new ClienteService(p);

	public static void main(String[] args) {
		System.out.println("Bem-vindo ao Programa bancário v1");
		inicial(p);

	}

	/* ------------------------------------------ Introdução à aplicação ----------------------------------------------------- */

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

		switch (opcaoIni) {
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
		nome = sc.nextLine();

		System.out.print("Informe o CPF deste cliente (11 dígitos): ");
		cpf = sc.next();

		if (cpf.length() == 11) {
			Cliente clientetemp = new Cliente(nome, cpf);
			
			try {
				p.adicionarCliente(clientetemp);
			} catch (ClienteException e) {
				e.printStackTrace();
			}
			
			inicial(p);
			
		} else {
			System.out.println("CPF inválido! O CPF deve ter exatamente 11 dígitos numéricos.");
		}

		sc.close();
	}

	private static void removerCliente() {
		Scanner sc = new Scanner(System.in);
		String cpf;

		System.out.print("\nInforme o CPF deste cliente: ");
		cpf = sc.next();

		Cliente clientetemp = new Cliente(null, cpf);

		try {
			p.removerCliente(clientetemp);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
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

		if (clienteLogin == null) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Cliente não encontrado !");
			System.out.println("---------------------------------------------------------------------");

			inicial(p);
			
		} else {
			loginCliente(clienteLogin);
		}

		sc.close();
	}

	/* ------------------------------------------ Metodos referente à Cliente ----------------------------------------------------- */

	private static void loginCliente(Cliente clienteLogin) {
		Scanner sc = new Scanner(System.in);
		int opcaoLoginCliente;

		System.out.println("\nOlá " + clienteLogin.getNome() + ", o que gostaria de fazer ? \n"
				+ "Opção 1 - Criar uma nova conta \n"
				+ "Opção 2 - Remover uma conta possuidora \n"
				+ "Opção 3 - Listar todas as contas possuidora \n"
				+ "Opção 4 - Ingressar em uma conta \n"
				+ "Opção 5 - Balanço geral de todas as contas \n"
				+ "Opção 6 - Logout do cliente \n"
				+ "Opção 7 - Sair da aplicação");

		opcaoLoginCliente = sc.nextInt();

		switch (opcaoLoginCliente) {
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
		Scanner sc = new Scanner(System.in);
		int opcaoCriarConta;
		IConta contaTemp = null;

		System.out.println("\nQual tipo conta você deseja criar ?\n"
				+ "Opção 1 - Conta corrente\n"
				+ "Opção 2 - Conta poupança");

		opcaoCriarConta = sc.nextInt();
		sc.nextLine();

		switch (opcaoCriarConta) {
		case 1:
			contaTemp = new ContaCorrente();
			break;
		case 2:
			contaTemp = new ContaPoupanca();
			break;
		default:
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Opção inválida tente novamente !");
			System.out.println("---------------------------------------------------------------------");
			criarConta(clienteTemp);
		}

		try {
			clienteServ.adicionarConta(clienteTemp, contaTemp);
		} catch (ContaException e) {
			e.printStackTrace();
		}

		try {
			p.atualizarCliente(clienteTemp);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
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

		IConta contaTemp = clienteServ.localizarConta(clienteTemp, numeroContaTemp);

		try {
			clienteServ.removerConta(clienteTemp, contaTemp);
		} catch (ContaException e) {
			e.printStackTrace();
		}
		try {
			p.atualizarCliente(clienteTemp);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
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

		IConta contaLogin = clienteServ.localizarConta(clienteTemp, numeroContaTemp);

		if (contaLogin == null) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não existente !");
			System.out.println("---------------------------------------------------------------------");

			loginCliente(clienteTemp);
			
		} else {
			loginConta(contaLogin, clienteTemp);
		}

		sc.close();

	}

	public static void balancoContas(Cliente clienteTemp) {
		BigDecimal balanco = BigDecimal.ZERO;

		for (IConta conta : clienteTemp.getContas()) {
			balanco = balanco.add(conta.getSaldo());
		}

		System.out.println("\n---------------------------------------------------------------------");
		System.out.println("Balanço total das contas: " + Utilitarios.formatarValor(balanco));
		System.out.println("---------------------------------------------------------------------");

		loginCliente(clienteTemp);
	}

	/* ------------------------------------------ Metodos referente à Conta ----------------------------------------------------- */

	private static void loginConta(IConta contaLogin, Cliente clienteLogin) {
		Scanner sc = new Scanner(System.in);
		int opcaoLoginConta;

		System.out.println("\nTipo da conta: " + contaLogin.getClass().getSimpleName() + "\n"
				+ "Você está na conta: " + contaLogin.getNumeroConta() + "\n"
				+ "Saldo atual: " + Utilitarios.formatarValor(contaLogin.getSaldo()) + "\n"
				+ "Selecione a opção que você deseja realizar:\n"
				+ "Opção 1 - Realizar depósito \n"
				+ "Opção 2 - Realizar saque \n"
				+ "Opção 3 - Realizar Transferência \n"
				+ "Opção 4 - Emitir extrato das operações \n"
				+ "Opção 5 - Logout da conta \n"
				+ "Opção 6 - Sair da aplicação");

		opcaoLoginConta = sc.nextInt();

		switch (opcaoLoginConta) {
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
			emitirExtrato(contaLogin, clienteLogin);
			break;
		case 5:
			loginCliente(clienteLogin);
			break;
		case 6:
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

	public static void realizarDeposito(IConta contaTemp, Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		BigDecimal quantia;

		System.out.print("\nDigite quanto você quer depositar: R$ ");
		quantia = sc.nextBigDecimal();

		try {
			contaServ.depositar(contaTemp, quantia);
		} catch (ValorInvalidoException | ContaException e) {
			e.printStackTrace();
		}

		try {
			p.atualizarCliente(clienteTemp);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
		loginConta(contaTemp, clienteTemp);
		sc.close();
	}

	public static void realizarSaque(IConta contaTemp, Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		BigDecimal quantia;

		System.out.print("\nDigite quanto você quer sacar: R$ ");
		quantia = sc.nextBigDecimal();

		try {
			contaServ.sacar(contaTemp, quantia);
		} catch (ValorInvalidoException | ContaException e) {
			e.printStackTrace();
		}
		
		try {
			p.atualizarCliente(clienteTemp);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
		loginConta(contaTemp, clienteTemp);
		sc.close();
	}

	public static void realizarTransferencia(IConta contaTempOrigem, Cliente clienteTempOrigem) {
		Scanner sc = new Scanner(System.in);
		BigDecimal quantia;
		Integer numeroContaDestino;

		System.out.print("\nDigite quanto você quer transferir: R$ ");
		quantia = sc.nextBigDecimal();

		System.out.print("Digite o número da conta para a qual você quer transferir: ");
		numeroContaDestino = sc.nextInt();

		IConta contaTempDestino = clienteServ.localizarConta(clienteTempOrigem, numeroContaDestino);

		if (contaTempDestino == null) {
			for (Cliente cliente : p.getClientes()) {
				if (!cliente.equals(clienteTempOrigem)) {
					contaTempDestino = clienteServ.localizarConta(clienteTempOrigem, numeroContaDestino);
					if (contaTempDestino != null) {
						break;
					}
				}
			}
		}

		if (contaTempDestino == null) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Conta não existente !");
			System.out.println("---------------------------------------------------------------------");
		} else {
			try {
				contaServ.transferir(contaTempOrigem, contaTempDestino, quantia);
			} catch (ValorInvalidoException | ContaException e) {
				e.printStackTrace();
			}
		}

		try {
			p.atualizarCliente(clienteTempOrigem);
		} catch (ClienteException e) {
			e.printStackTrace();
		}
		
		loginConta(contaTempOrigem, clienteTempOrigem);
		sc.close();
	}

	public static void emitirExtrato(IConta contaTemp, Cliente clienteTemp) {
		Scanner sc = new Scanner(System.in);
		int numeroMes;
		Month mes;
		int ano;

		System.out.println("\nDigite o número do mês referente que você quer obter o extrato (1 a 12): ");
		numeroMes = sc.nextInt();

		if (numeroMes < 1 || numeroMes > 12) {
			System.err.println("Mês inválido! Digite um número entre 1 e 12.");
			emitirExtrato(contaTemp, clienteTemp);
		} else {
			mes = Month.of(numeroMes);

			System.out.print("Digite o ano (Exemplo: 2023): ");
			ano = sc.nextInt();

			System.out.println("\n---------------------------------------------------------------------");
			transacaoServ.consultarExtrato(contaTemp.getTransacoes(), mes, ano);
			System.out.println("---------------------------------------------------------------------");

			loginConta(contaTemp, clienteTemp);
		}

		sc.close();
	}

}
