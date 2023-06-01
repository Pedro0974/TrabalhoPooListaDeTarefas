package models.tarefa;

public class SubTarefa {
	private String titulo;
    private boolean concluida;
    
    
	public SubTarefa(String subtarefaTitulo, boolean subtarefaConcluida) {
		this.titulo = subtarefaTitulo;
		this.concluida = subtarefaConcluida;
	}
	@Override
	public String toString() {
		return "SubTarefa [titulo=" + titulo + ", concluida=" + concluida + "]";
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public boolean isConcluida() {
		return concluida;
	}
	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}
    
    
}
