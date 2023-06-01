package models.tarefa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import models.tarefa.SubTarefa;

public class Tarefa {
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private boolean status;
    private CategoriaTarefa categoria;
    private List<SubTarefa> subtarefas;

    public Tarefa(String titulo, String descricao, CategoriaTarefa categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.status = false;
        this.categoria = categoria;
        this.subtarefas = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
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
        if (isConcluida()) {
            return "Concluída";
        } else {
            return "Pendente";
        }
    }

    public CategoriaTarefa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTarefa categoria) {
        this.categoria = categoria;
    }

    public List<SubTarefa> getSubtarefas() {
        return subtarefas;
    }

    public void adicionarSubtarefa(SubTarefa subtarefa) {
        subtarefas.add(subtarefa);
    }

    public void removerSubtarefa(SubTarefa subtarefa) {
        subtarefas.remove(subtarefa);
    }

    public void concluir() {
        this.status = true;
        this.dataConclusao = LocalDate.now();
    }

    public void reabrir() {
        this.status = false;
        this.dataConclusao = null;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("titulo", this.titulo);
        json.put("descricao", this.descricao);
        json.put("dataCriacao", this.dataCriacao.toString());
        json.put("dataConclusao", this.dataConclusao != null ? this.dataConclusao.toString() : null);
        json.put("status", this.status);
        json.put("categoria", this.categoria.getNome());

        JSONArray subtarefasJson = new JSONArray();
        for (SubTarefa subtarefa : subtarefas) {
            JSONObject subtarefaJson = new JSONObject();
            subtarefaJson.put("titulo", subtarefa.getTitulo());
            subtarefaJson.put("concluida", subtarefa.isConcluida());
            subtarefasJson.put(subtarefaJson);
        }
        json.put("subtarefas", subtarefasJson);

        return json;
    }

    public static Tarefa fromJson(JSONObject json) throws JSONException {
        String titulo = json.getString("titulo");
        String descricao = json.getString("descricao");
        Tarefa tarefa = new Tarefa(titulo, descricao, null);
        tarefa.setDataConclusao(json.has("dataConclusao") && !json.isNull("dataConclusao") ? LocalDate.parse(json.getString("dataConclusao")) : null);
        tarefa.status = json.getBoolean("status");
        tarefa.setCategoria(new CategoriaTarefa(json.getString("categoria")));

        if (json.has("subtarefas")) {
            JSONArray subtarefasJson = json.getJSONArray("subtarefas");
            for (int i = 0; i < subtarefasJson.length(); i++) {
                JSONObject subtarefaJson = subtarefasJson.getJSONObject(i);
                String subtarefaTitulo = subtarefaJson.getString("titulo");
                boolean subtarefaConcluida = subtarefaJson.getBoolean("concluida");
                SubTarefa subtarefa = new SubTarefa(subtarefaTitulo, subtarefaConcluida);
                tarefa.adicionarSubtarefa(subtarefa);
            }
        }

        return tarefa;
    }

}
