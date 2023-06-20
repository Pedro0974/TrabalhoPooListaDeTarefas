package models.tarefa;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class SubTarefa {
	private String titulo;
    private boolean concluida;
    
    
	public SubTarefa(String subtarefaTitulo, boolean subtarefaConcluida) {
		this.titulo = subtarefaTitulo;
		this.concluida = subtarefaConcluida;
	}
	@Override
	public String toString() {
		return "[titulo=" + titulo + ", concluida=" + concluida + "]";
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
	public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("titulo", titulo);
        json.put("concluida", concluida);
        return json;
    }
    
    public static SubTarefa fromJson(JSONObject subtarefaJson) throws JSONException {
        String titulo = subtarefaJson.getString("titulo");
        boolean concluida = subtarefaJson.getBoolean("concluida");
        return new SubTarefa(titulo, concluida);
    }
    
    
}
