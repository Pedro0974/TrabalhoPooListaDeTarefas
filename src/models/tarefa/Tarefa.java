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
    private SubTarefa subtarefa;

    public Tarefa(String titulo, String descricao, CategoriaTarefa categoria, SubTarefa subtarefa) {
        this.subtarefa = subtarefa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.status = false;
        this.categoria = categoria;
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

    public SubTarefa getSubtarefa() {
        return subtarefa;
    }

    public void setSubtarefa(SubTarefa subtarefa) {
        this.subtarefa = subtarefa;
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

        if (subtarefa != null) {
            json.put("subtarefa", subtarefa.toJson());
        } else {
            json.put("subtarefa", JSONObject.NULL);
        }

        return json;
    }


    public static Tarefa fromJson(JSONObject json) throws JSONException {
        String titulo = json.getString("titulo");
        String descricao = json.getString("descricao");
        Tarefa tarefa = new Tarefa(titulo, descricao, null, null);
        tarefa.setDataConclusao(json.has("dataConclusao") && !json.isNull("dataConclusao") ? LocalDate.parse(json.getString("dataConclusao")) : null);
        tarefa.status = json.getBoolean("status");
        tarefa.setCategoria(new CategoriaTarefa(json.getString("categoria")));

        if (json.has("subtarefa") && !json.isNull("subtarefa")) {
            JSONObject subtarefaJson = json.getJSONObject("subtarefa");
            SubTarefa subtarefa = SubTarefa.fromJson(subtarefaJson);
            tarefa.setSubtarefa(subtarefa);
        }

        return tarefa;
    }

    
    @Override
    public String toString() {
        return "Título: " + this.getTitulo() + "\n"
                + "Descrição: " + this.getDescricao() + "\n"
                + "Categoria: " + this.getCategoria().toString() + "\n"
                + "Status: " + this.getStatus();
    }
}
