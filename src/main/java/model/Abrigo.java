package model;

import java.util.Date;

/**
 * Abrigo de animais, mantido por uma Associação (Company).
 */
public class Abrigo {
	private int id;
	private String nome;
	private String telefone;
	private Date dataFundacao;
	private int capacidade;
	private boolean aceitaVoluntarios;
	private String descricao;
	private Company company; // Associação mantenedora (chave estrangeira)

	public Abrigo() {}

	public Abrigo(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public boolean isAceitaVoluntarios() {
		return aceitaVoluntarios;
	}

	public void setAceitaVoluntarios(boolean aceitaVoluntarios) {
		this.aceitaVoluntarios = aceitaVoluntarios;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
