package model;

import java.util.Date;

/**
 * Animal disponível para adoção, abrigado em um Abrigo.
 */
public class Animal {
	private int id;
	private String nome;
	private String especie;   // Cachorro, Gato, Outro
	private String porte;     // Pequeno, Médio, Grande
	private Date dataResgate;
	private double peso;      // kg
	private boolean castrado;
	private String status;    // Disponível, Em processo, Adotado
	private Abrigo abrigo;    // chave estrangeira

	public Animal() {}

	public Animal(int id) {
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

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	public Date getDataResgate() {
		return dataResgate;
	}

	public void setDataResgate(Date dataResgate) {
		this.dataResgate = dataResgate;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public boolean isCastrado() {
		return castrado;
	}

	public void setCastrado(boolean castrado) {
		this.castrado = castrado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Abrigo getAbrigo() {
		return abrigo;
	}

	public void setAbrigo(Abrigo abrigo) {
		this.abrigo = abrigo;
	}
}
