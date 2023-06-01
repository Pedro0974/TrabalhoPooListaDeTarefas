package models.tarefa;

public class CategoriaTarefa {
	private String nome;

	@Override
	public String toString() {
		return nome;
	}

	public CategoriaTarefa(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
