package com.exemplo.controle;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.xml.config.ValidationBootstrapParameters;

import com.exemplo.entidade.Cliente;
import com.exemplo.entidade.Endereco;
import com.exemplo.repositorio.Pesquisa;
import com.exemplo.repositorio.RepositorioCliente;

@ManagedBean(name = "controleCliente")
@SessionScoped
public class ControleCliente {
	public static String valorPes;
	private RepositorioCliente repositorioCliente;
	HtmlInputText nomeText = new HtmlInputText();
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Endereco> pesquisaEndereco;
	private Endereco endereco;
	private List<Endereco> enderecos;
	public String mensagemDeErro = "";
	private String tipoPesq;
	private Pesquisa pesquisa;

	public ControleCliente() {
		repositorioCliente = new RepositorioCliente();
		cliente = new Cliente();
		endereco = new Endereco();
		pesquisa = new Pesquisa();
	}
	
	
	

	public HtmlInputText getNomeText() {
		return nomeText;
	}
	public void setNomeText(HtmlInputText nomeText) {
		this.nomeText = nomeText;
	}






	
	public String getTipoPesq() {
		return tipoPesq;
	}




	public void setTipoPesq(String tipoPesq) {
		this.tipoPesq = tipoPesq;
	}




	public String result() {

	    if(getTipoPesq().equals("1")) {
	    	
	    	return "buscaEnd";
	    }
	    if(getTipoPesq().equals("2")) {
	    
	    	return "buscaUser";
	    }else {
	    	return "nao deu";
	    }
	    
	}
	
	
	public List<Endereco> getEnderecos() {
		enderecos = repositorioCliente.listarEnderecos();
		int idzinhoCliente = cliente.getIdCliente();
		int idzinhoEndereco = endereco.getIdEndereco();
		List<Endereco> listaEndereco = enderecos;
		List<Endereco> listaFiltrada = new ArrayList<Endereco>();

		for (Endereco endereco : listaEndereco) {

			idzinhoEndereco = endereco.getIdEndereco();
			if (idzinhoCliente == idzinhoEndereco) {
				listaFiltrada.add(endereco);
			}
		}

		return listaFiltrada;
	}

	public void setEnderecos(List<Endereco> enderecos) {

		this.enderecos = enderecos;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;

	}

	public List<Cliente> getClientes() {
		clientes = repositorioCliente.listarTodos();
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String salvar() {
		Random random = new Random();
		int idClientinho = random.nextInt(1000);
		cliente.setIdCliente(idClientinho);
		String cpfErrado = "CPF Inv�lido";

		try {
			repositorioCliente.salvar(cliente);		
			return "index";
		}catch(Exception e) {
			System.out.print(e);
			this.mensagemDeErro = "CPF Inv�lido";
			   FacesContext.getCurrentInstance().addMessage("CPF INVALIDO", new FacesMessage(FacesMessage.SEVERITY_INFO,
					   cpfErrado, e.getMessage()));
			   return "formularioCliente";
		}
		

	}

	public String listarPorEndereco() {

	
		return "buscaEnd";

	}

	public String salvarEdit() {
		repositorioCliente.salvar(cliente);

		return "index";
	}

	public String salvarEndereco() {

		repositorioCliente.salvar(endereco);
		return "index";
	}

	public String submitEndereco() {
	    return "buscaEnd";
	}
	
	public String submitUsuario() {
	    return "buscaUser";
	}
	
	public String novaPesquisa() {
		pesquisa = new Pesquisa();
		return "buscaEnd";
	}

	public String novo() {
		cliente = new Cliente();
		return "formularioCliente";
	}

	public String novoEndereco() {
		endereco = new Endereco();
		int idzinhoCliente = cliente.getIdCliente();
		endereco.setIdEndereco(idzinhoCliente);
		return "addEnd";
	}

	public String editar() {
		return "editarCliente";
	}

	public String editarEndereco() {
		return "maisEndereco";
	}

	public String remover() {
		List<Endereco> list = getEnderecos();
		int clienteIdzinho = cliente.getIdCliente();
		repositorioCliente.remover(cliente);

		for (Endereco endereco : list) {
			cliente.setIdCliente(clienteIdzinho);
			repositorioCliente.removerEnd(endereco);
		}

		return null;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	
	public List<Cliente> getPesquisaCliente() {
		clientes = repositorioCliente.listarTodos();
	
		List<Cliente> listaClientes = clientes;
		
		List<Cliente> listaUserPesquisada = new ArrayList<Cliente>();
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    String textpesq = ec.getRequestParameterMap().get("pesquisa:textPesquisa");
		
		for (Cliente cliente : listaClientes) {
			 String nomeUser = cliente.getNome();
			 if (nomeUser.contains(textpesq)){
				 
				 listaUserPesquisada.add(cliente);
				
			}
			
		}
		return listaUserPesquisada;
		
	}
	
	
	

	


	public List<Endereco> getPesquisaEndereco() {
		enderecos = repositorioCliente.listarEnderecos();
	
		List<Endereco> listaEndereco = enderecos;
		
		List<Endereco> listaPesquisada = new ArrayList<Endereco>();
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    String textpesq = ec.getRequestParameterMap().get("pesquisa:textPesquisa");
		
		

		for (Endereco endereco : listaEndereco) {
			 String lograd = endereco.getLogradouro();
			 if (lograd.contains(textpesq)){
				 
				 listaPesquisada.add(endereco);
				
			}
			
		}
		return listaPesquisada;
		
	}


	public void setPesquisaEndereco(List<Endereco> pesquisaEndereco) {
		this.pesquisaEndereco = pesquisaEndereco;
	}






	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

}
