package models.listaTarefa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import models.tarefa.Tarefa;
import models.tarefa.CategoriaTarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void adicionarTarefaPendente(String titulo, String descricao, CategoriaTarefa categoria)
            throws JSONException, IOException {
        Tarefa tarefa = new Tarefa(titulo, descricao, categoria);
        this.tarefasPendentes.add(tarefa);
        this.atualizarTarefasUsuario();
    }

    public void adicionarTarefaConcluida(Tarefa tarefa) throws JSONException {
        tarefa.concluir();
        this.tarefasPendentes.remove(tarefa);
        this.tarefasConcluidas.add(tarefa);
        salvarTarefas();
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

    public void concluirTarefa(String titulo) throws JSONException {
        Tarefa tarefa = buscarTarefa(titulo);
        if (tarefa != null) {
            tarefa.concluir();
            tarefasConcluidas.add(tarefa);
            tarefasPendentes.remove(tarefa);
            salvarTarefas();
        }
    }

    private void atualizarTarefasUsuario() throws IOException, JSONException {
        try {
            File arquivo = new File(this.caminhoArquivo);

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            String content = new String(Files.readAllBytes(Paths.get(this.caminhoArquivo)));
            JSONObject jsonObject;

            if (!content.isEmpty()) {
                jsonObject = new JSONObject(content);
            } else {
                jsonObject = new JSONObject();
            }

            JSONArray jsonArray;

            if (jsonObject.has("Tarefas")) {
                jsonArray = jsonObject.getJSONArray("Tarefas");
            } else {
                jsonArray = new JSONArray();
            }

            for (Tarefa tarefa : this.tarefasPendentes) {
                jsonArray.put(tarefa.toJson());
            }

            jsonObject.put("Tarefas", jsonArray);

            FileWriter writer = new FileWriter(this.caminhoArquivo);
            writer.write(jsonObject.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao atualizar as tarefas do usuário no arquivo!");
            e.printStackTrace();
        }
    }

    private void salvarTarefas() throws JSONException {
        try {
            File arquivo = new File(this.caminhoArquivo);

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            JSONObject jsonObject;

            if (Files.exists(Paths.get(this.caminhoArquivo))) {
                String content = new String(Files.readAllBytes(Paths.get(this.caminhoArquivo)));
                if (!content.isEmpty()) {
                    jsonObject = new JSONObject(content);
                } else {
                    jsonObject = new JSONObject();
                }
            } else {
                jsonObject = new JSONObject();
            }

            JSONArray jsonArray;

            if (jsonObject.has("Tarefas")) {
                jsonArray = jsonObject.getJSONArray("Tarefas");
            } else {
                jsonArray = new JSONArray();
            }

            for (Tarefa tarefa : this.tarefasPendentes) {
                JSONObject tarefaJson = tarefa.toJson();
                tarefaJson.put("status", false);
                jsonArray.put(tarefaJson);
            }

            for (Tarefa tarefa : this.tarefasConcluidas) {
                JSONObject tarefaJson = tarefa.toJson();
                tarefaJson.put("status", true);
                jsonArray.put(tarefaJson);
            }

            jsonObject.put("Tarefas", jsonArray);

            FileWriter fileWriter = new FileWriter(this.caminhoArquivo);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar as tarefas no arquivo!");
            e.printStackTrace();
        }
    }

    public void exibirTarefasPendentes() {
    	   System.out.println("Tarefas Pendentes:");
    	   List<Tarefa> tarefasPendentes = this.getTarefasPendentes();

    	   if (tarefasPendentes.isEmpty()) {
    	      System.out.println("Não há tarefas pendentes.");
    	      return;
    	   }

    	   for (Tarefa tarefa : tarefasPendentes) {
    	      System.out.println("Título: " + tarefa.getTitulo());
    	      System.out.println("Descrição: " + tarefa.getDescricao());
    	      
    	      CategoriaTarefa categoria = tarefa.getCategoria();
    	      if (categoria != null) {
    	         System.out.println("Categoria: " + categoria.toString());
    	      } else {
    	         System.out.println("Categoria: (Categoria não definida)");
    	      }
    	      
    	      System.out.println("Status: " + tarefa.getStatus());
    	      System.out.println("---------------------");
    	   }
    	}


    public void exibirTarefasConcluidas() {
        System.out.println("Tarefas Concluídas:");
        for (Tarefa tarefa : this.tarefasConcluidas) {
            System.out.println("Título: " + tarefa.getTitulo());
            System.out.println("Descrição: " + tarefa.getDescricao());
            System.out.println("Status: " + tarefa.getStatus());
            System.out.println("---------------------");
        }
    }

    public void carregarTarefas() throws JSONException {
        try {
            File arquivo = new File(this.caminhoArquivo);

            if (!arquivo.exists()) {
                System.out.println("Arquivo de tarefas não encontrado.");
                return;
            }

            String content = new String(Files.readAllBytes(Paths.get(this.caminhoArquivo)));

            if (content.isEmpty()) {
                System.out.println("Arquivo de tarefas vazio.");
                return;
            }

            JSONObject jsonObject = new JSONObject(content);

            JSONArray jsonArray;

            if (jsonObject.has("Tarefas")) {
                jsonArray = jsonObject.getJSONArray("Tarefas");
            } else {
                jsonArray = new JSONArray();
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tarefaJson = jsonArray.getJSONObject(i);
                Tarefa tarefa = Tarefa.fromJson(tarefaJson);
                boolean status = tarefaJson.getBoolean("status");
                if (status) {
                    tarefa.concluir();
                    this.tarefasConcluidas.add(tarefa);
                } else {
                    this.tarefasPendentes.add(tarefa);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar as tarefas do arquivo!");
            e.printStackTrace();
        }
    }

    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    public List<Tarefa> buscarTarefasPorCategoria(String nomeCategoria) {
        List<Tarefa> tarefasPorCategoria = new ArrayList<>();

        for (Tarefa tarefa : this.tarefasPendentes) {
            if (tarefa.getCategoria().toString().equals(nomeCategoria)) {
                tarefasPorCategoria.add(tarefa);
            }
        }

        return tarefasPorCategoria;
    }
}
