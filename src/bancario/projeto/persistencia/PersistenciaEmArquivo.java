package bancario.projeto.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import bancario.projeto.model.Cliente;

public class PersistenciaEmArquivo implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Cliente> clientes;
	
	public PersistenciaEmArquivo() {
		clientes = new ArrayList<Cliente>();
		carregarArquivo();
	}
	
	public void adicionarCliente(Cliente cliente) {
		if(clientes.contains(cliente)) {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Cliente já existente!");
			System.out.println("---------------------------------------------------------------------");
		}else {
			clientes.add(cliente);
			salvarArquivo();
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Cliente cadastrado com sucesso!");
			System.out.println("---------------------------------------------------------------------");
		}
	}
	
	public void removerCliente(Cliente cliente) {
		if(clientes.contains(cliente)) {
			clientes.remove(cliente);
			salvarArquivo();
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Cliente removido com sucesso!");
			System.out.println("---------------------------------------------------------------------");
		}else {
			System.out.println("\n---------------------------------------------------------------------");
			System.err.println("Cliente não existente!");
			System.out.println("---------------------------------------------------------------------");
		}
	}
	
	public ArrayList<Cliente> getClientes(){
		return clientes;
	}
	
	public Cliente localizarCliente(String cpf) {
		Cliente clienteTemp = new Cliente();
		clienteTemp.setCpf(cpf);
		
		if(clientes.contains(clienteTemp)) {
			int index = clientes.indexOf(clienteTemp);
			clienteTemp = clientes.get(index);
			return clienteTemp;
		}
		
		return null;
	}
	
	public void atualizarCliente(Cliente cliente) {
		if(clientes.contains(cliente)) {
			int index = clientes.indexOf(cliente);
			clientes.set(index, cliente);
			salvarArquivo();
		}else {
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("Cliente não localizado !");
			System.out.println("\n---------------------------------------------------------------------");
		}
	}
	
	private void salvarArquivo() {
		try {
			FileOutputStream fos = new FileOutputStream("dados.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(clientes);
			oos.close();
			fos.close();
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	private void carregarArquivo() {
		try {
			FileInputStream fis = new FileInputStream("dados.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			clientes = (ArrayList<Cliente>) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
}
