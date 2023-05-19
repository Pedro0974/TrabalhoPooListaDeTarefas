package models.tarefa;

import java.time.LocalDate;

public class Tarefa {
	private String titulo;
	private String descricao;
	private LocalDate dataCriacao;
	private LocalDate dataConclusao;
	private boolean status;

	public Tarefa(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataCriacao = LocalDate.now();
		this.status = false;
	}

	public Tarefa(Tarefa novaTarefa) {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public LocalDate getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDate dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public boolean isConcluida() {
		return status;
	}
	
	public String getStatus() {
		if(isConcluida() == false) {
			return "Pendente";
		}else {
			return "Concluida";
		}
	}

	public void concluir() {
		this.status = true;
		this.dataConclusao = LocalDate.now();
	}

	public void reabrir() {
		this.status = false;
		this.dataConclusao = null;
	}
}