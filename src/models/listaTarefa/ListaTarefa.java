package models.listaTarefa;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



import models.tarefa.Tarefa;

public class ListaTarefa {
	private List<Tarefa> tarefasPendentes;
	private List<Tarefa> tarefasConcluidas;
	private String nomeUsuario;
	private String caminhoArquivo;

	public ListaTarefa(String nomeUsuario, String caminhoArquivo) {
		this.nomeUsuario = nomeUsuario;
		this.caminhoArquivo = caminhoArquivo;
		this.tarefasPendentes = new ArrayList<>();
		this.tarefasConcluidas = new ArrayList<>();
	}

	public void adicionarTarefaPendente(String titulo, String descricao) {
		Tarefa tarefa = new Tarefa(titulo, descricao);
		this.tarefasPendentes.add(tarefa);
		salvarTarefas(tarefa);
	}

	public void adicionarTarefaConcluida(Tarefa tarefa) {
		tarefa.concluir();
		this.tarefasPendentes.remove(tarefa);
		this.tarefasConcluidas.add(tarefa);
//		salvarTarefas();
	}

	public List<Tarefa> getTarefasPendentes() {
		return this.tarefasPendentes;
	}

	public List<Tarefa> getTarefasConcluidas() {
		return this.tarefasConcluidas;
	}

	public Tarefa buscarTarefa(String titulo) {
		for (Tarefa tarefa : this.tarefasPendentes) {
			if (tarefa.getTitulo().equals(titulo)) {
				return tarefa;
			}
		}
		for (Tarefa tarefa : this.tarefasConcluidas) {
			if (tarefa.getTitulo().equals(titulo)) {
				return tarefa;
			}
		}
		return null;
	}

	public void concluirTarefa(String titulo) {
		Tarefa tarefa = buscarTarefa(titulo);
		if (tarefa != null) {
			tarefa.concluir();
			tarefasConcluidas.add(tarefa);
			tarefasPendentes.remove(tarefa);
			salvarTarefas(tarefa);
		}
	}

	private void salvarTarefas(Tarefa novaTarefa) {
	    try {
	        PrintWriter pw = new PrintWriter(new FileWriter(this.caminhoArquivo, true)); // true indica modo de anexação
	        for (Tarefa tarefa : this.tarefasPendentes) {
	            pw.println(tarefa.getTitulo() + ";" + tarefa.getDescricao() + ";false");
	        }
	        for (Tarefa tarefa : this.tarefasConcluidas) {
	            pw.println(tarefa.getTitulo() + ";" + tarefa.getDescricao() + ";true");
	        }
	        pw.close();
	    } catch (IOException e) {
	        System.out.println("Erro ao salvar as tarefas no arquivo!");
	        e.printStackTrace();
	    }
	}
	
	public void exibirTarefasPendentes() {
	    System.out.println("Tarefas Pendentes:");
	    for (Tarefa tarefa : this.tarefasPendentes) {
	        System.out.println("Título: " + tarefa.getTitulo());
	        System.out.println("Descrição: " + tarefa.getDescricao());
	        System.out.println("Status: " + tarefa.getStatus());
	        System.out.println("---------------------");
	    }
	}
}
