package models.tarefa;

import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;

public class Tarefa {
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private boolean status;
    private static CategoriaTarefa categoria;

    public Tarefa(String titulo, String descricao, CategoriaTarefa categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.status = false;
        Tarefa.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public CategoriaTarefa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTarefa categoria) {
        Tarefa.categoria = categoria;
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
        if (isConcluida() == false) {
            return "Pendente";
        } else {
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

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("titulo", this.titulo);
        json.put("descricao", this.descricao);
        json.put("dataCriacao", this.dataCriacao.toString());
        json.put("dataConclusao", this.dataConclusao != null ? this.dataConclusao.toString() : null);
        json.put("categoria", Tarefa.categoria.getNome());
        json.put("status", this.status);
        return json;
    }

    public static Tarefa fromJson(JSONObject json) throws JSONException {
        String titulo = json.getString("titulo");
        String descricao = json.getString("descricao");
        Tarefa tarefa = new Tarefa(titulo, descricao, categoria);
        tarefa.setDataConclusao(json.has("dataConclusao") && !json.isNull("dataConclusao") ? LocalDate.parse(json.getString("dataConclusao")) : null);
        tarefa.status = json.getBoolean("status");
        return tarefa;
    }
}