package bancario.projeto.service;

import java.util.List;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.IConta;
import bancario.projeto.persistencia.PersistenciaEmArquivo;
import exception.ClienteException;
import exception.ContaException;

public class ClienteService {

    private PersistenciaEmArquivo persistencia;

    public ClienteService(PersistenciaEmArquivo persistencia) {
        this.persistencia = persistencia;
    }

    public void adicionarConta(Cliente cliente, IConta conta) throws ContaException {
        List<IConta> contas = cliente.getContas();
        
        if (contas.contains(conta)) {
            throw new ContaException("Conta já cadastrada");
        } else {
            contas.add(conta);
            try {
				persistencia.atualizarCliente(cliente);
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("\n---------------------------------------------------------------------");
            System.out.println("Conta cadastrada com sucesso!");
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public void removerConta(Cliente cliente, IConta conta) throws ContaException {
        List<IConta> contas = cliente.getContas();

        if (contas.contains(conta)) {
            contas.remove(conta);
            try {
				persistencia.atualizarCliente(cliente);
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("\n---------------------------------------------------------------------");
            System.out.println("Conta removida com sucesso!");
            System.out.println("---------------------------------------------------------------------");
        } else {
            throw new ContaException("Conta não existente");
        }
    }

    public IConta localizarConta(Cliente cliente, Integer numero) {
        return cliente.getContas().stream()
                .filter(conta -> conta.getNumeroConta().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public void atualizarConta(Cliente cliente, IConta conta) throws ContaException {
        List<IConta> contas = cliente.getContas();

        if (contas.contains(conta)) {
            int index = contas.indexOf(conta);
            contas.set(index, conta);
            try {
				persistencia.atualizarCliente(cliente);
			} catch (ClienteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("\n---------------------------------------------------------------------");
            System.out.println("Conta atualizada com sucesso!");
            System.out.println("---------------------------------------------------------------------");
        } else {
            throw new ContaException("Conta não localizada");
        }
    }
}
