package models.usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import models.listaTarefa.ListaTarefa;
import models.tarefa.Tarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {
	private String nome;
	private String senha;
	private ListaTarefa listaDeTarefas;

	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
		String caminhoArquivo = nome + ".json";
		this.listaDeTarefas = new ListaTarefa(nome, caminhoArquivo);
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ListaTarefa getListaDeTarefas() {
		return this.listaDeTarefas;
	}

	public void setListaDeTarefas(ListaTarefa listaDeTarefas) {
		this.listaDeTarefas = listaDeTarefas;
	}

	public void criarArquivoJSON() {
		String nomeArquivo = this.nome + ".json";
		File arquivo = new File(nomeArquivo);
		if (arquivo.exists()) {
			System.out.println("Já existe um arquivo com o nome " + nomeArquivo);
			return;
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("nome", this.nome);
			jsonObject.put("senha", this.senha);
			JSONArray tarefasArray = new JSONArray();

			// Adicione aqui as tarefas do usuário no array JSON
			for (Tarefa tarefa : this.listaDeTarefas.getTarefasPendentes()) {
				tarefasArray.put(tarefa.toJson());
			}

			jsonObject.put("Tarefas", tarefasArray);

			FileWriter fileWriter = new FileWriter(arquivo);
			fileWriter.write(jsonObject.toString());
			fileWriter.flush();
			fileWriter.close();
			System.out.println("Arquivo JSON criado com sucesso: " + nomeArquivo);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	public static Usuario validarLogin(String nome, String senha) throws IOException, JSONException {
		File arquivoUsuario = new File(nome + ".json");

		if (!arquivoUsuario.exists()) {
			System.out.println("Usuário não encontrado.");
			return null;
		} else {
			Scanner scanner = new Scanner(arquivoUsuario);
			StringBuilder jsonUsuario = new StringBuilder();

			while (scanner.hasNextLine()) {
				jsonUsuario.append(scanner.nextLine());
			}

			JSONObject jsonObject = new JSONObject(jsonUsuario.toString());

			String nomeUsuario = jsonObject.getString("nome");
			String senhaUsuario = jsonObject.getString("senha");

			if (senha.equals(senhaUsuario)) {
				return new Usuario(nomeUsuario, senhaUsuario);
			} else {
				System.out.println("Senha incorreta.");
				return null;
			}
		}
	}

	public void carregarTarefasUsuario() {
		try {
			File arquivo = new File(this.listaDeTarefas.getCaminhoArquivo());

			if (!arquivo.exists()) {
				System.out.println("Arquivo de tarefas não encontrado.");
				return;
			}

			String content = new String(Files.readAllBytes(Paths.get(this.listaDeTarefas.getCaminhoArquivo())));

			JSONObject jsonObject = new JSONObject(content);
			JSONArray jsonArray = jsonObject.getJSONArray("Tarefas");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tarefaJson = jsonArray.getJSONObject(i);
				Tarefa tarefa = Tarefa.fromJson(tarefaJson);
				boolean status = tarefaJson.getBoolean("status");
				if (status) {
					tarefa.concluir();
					this.listaDeTarefas.getTarefasConcluidas().add(tarefa);
				} else {
					this.listaDeTarefas.getTarefasPendentes().add(tarefa);
				}
			}

			System.out.println("Tarefas do usuário carregadas com sucesso.");
		} catch (IOException e) {
			System.out.println("Erro ao carregar as tarefas do usuário do arquivo!");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("Erro ao processar o arquivo JSON de tarefas do usuário!");
			e.printStackTrace();
		}
	}

//	public void carregarTarefasUsuario() {
//		try {
//			File arquivo = new File(this.listaDeTarefas.getCaminhoArquivo());
//
//			if (!arquivo.exists()) {
//				System.out.println("Arquivo de tarefas não encontrado.");
//				return;
//			}
//
//			String content = new String(Files.readAllBytes(Paths.get(this.listaDeTarefas.getCaminhoArquivo())));
//
//			if (content.isEmpty()) {
//				System.out.println("Arquivo de tarefas vazio.");
//				return;
//			}
//
//			JSONArray jsonArray = new JSONArray(content);
//
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject tarefaJson = jsonArray.getJSONObject(i);
//				Tarefa tarefa = Tarefa.fromJson(tarefaJson);
//				boolean status = tarefaJson.getBoolean("status");
//				if (status) {
//					tarefa.concluir();
//					this.listaDeTarefas.getTarefasConcluidas().add(tarefa);
//				} else {
//					this.listaDeTarefas.getTarefasPendentes().add(tarefa);
//				}
//			}
//
//			System.out.println("Tarefas do usuário carregadas com sucesso.");
//		} catch (IOException e) {
//			System.out.println("Erro ao carregar as tarefas do usuário do arquivo!");
//			e.printStackTrace();
//		} catch (JSONException e) {
//			System.out.println("Erro ao processar o arquivo JSON de tarefas do usuário!");
//			e.printStackTrace();
//		}
//	}
}

//package models.usuario;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//
//import models.listaTarefa.ListaTarefa;
//import models.tarefa.Tarefa;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Usuario {
//    private String nome;
//    private String senha;
//    private ListaTarefa listaDeTarefas;
//
//    public Usuario(String nome, String senha) {
//        this.nome = nome;
//        this.senha = senha;
//        String nomeUsuario = this.nome;
//        String caminhoArquivo = nome + ".txt";
//        this.listaDeTarefas = new ListaTarefa(nomeUsuario, caminhoArquivo);
//    }
//
//    public String getNome() {
//        return this.nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getSenha() {
//        return this.senha;
//    }
//
//    public void setSenha(String senha) {
//        this.senha = senha;
//    }
//
//    public ListaTarefa getListaDeTarefas() {
//        return this.listaDeTarefas;
//    }
//
//    public void setListaDeTarefas(ListaTarefa listaDeTarefas) {
//        this.listaDeTarefas = listaDeTarefas;
//    }
//
//    public void criarArquivoUsuario() throws IOException, JSONException {
//        File arquivoUsuario = new File(this.nome + ".txt");
//
//        if (!arquivoUsuario.exists()) {
//            arquivoUsuario.createNewFile();
//            FileWriter writer = new FileWriter(arquivoUsuario);
//
//            JSONObject jsonUsuario = new JSONObject();
//            jsonUsuario.put("Nome", this.nome);
//            jsonUsuario.put("Senha", this.senha);
//
//            // Adicione as tarefas do usuário no JSON
//            JSONArray jsonTarefas = new JSONArray();
//            for (Tarefa tarefa : this.listaDeTarefas.getTarefasPendentes()) {
//                jsonTarefas.put(tarefa.toJson());
//            }
//            jsonUsuario.put("Tarefas", jsonTarefas);
//
//            writer.write(jsonUsuario.toString());
//            writer.close();
//            System.out.println("Arquivo do usuário " + this.nome + " criado com sucesso.");
//        } else {
//            System.out.println("Arquivo do usuário " + this.nome + " já existe.");
//        }
//    }
//
//
//
//    public static Usuario validarLogin(String nome, String senha) throws IOException, JSONException {
//        File arquivoUsuario = new File(nome + ".txt");
//
//        if (!arquivoUsuario.exists()) {
//            System.out.println("Usuário não encontrado.");
//            return null;
//        } else {
//            Scanner scanner = new Scanner(arquivoUsuario);
//            StringBuilder jsonUsuario = new StringBuilder();
//
//            while (scanner.hasNextLine()) {
//                jsonUsuario.append(scanner.nextLine());
//            }
//
//            JSONObject jsonObject = new JSONObject(jsonUsuario.toString());
//
//            String nomeUsuario = jsonObject.getString("Nome");
//            String senhaUsuario = jsonObject.getString("Senha");
//
//            if (senha.equals(senhaUsuario)) {
//                return new Usuario(nomeUsuario, senhaUsuario);
//            } else {
//                System.out.println("Senha incorreta.");
//                return null;
//            }
//        }
//    }
//
//}