package models.usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import models.listaTarefa.ListaTarefa;

public class Usuario {
    private String nome;
    private String senha;
    private ListaTarefa listaDeTarefas;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        String nomeUsuario = this.nome;
        String caminhoArquivo = nome + ".txt";
        this.listaDeTarefas = new ListaTarefa(nomeUsuario, caminhoArquivo);
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

    public void criarArquivoUsuario() throws IOException {
        File arquivoUsuario = new File(this.nome + ".txt");

        if (!arquivoUsuario.exists()) {
            arquivoUsuario.createNewFile();
            FileWriter writer = new FileWriter(arquivoUsuario);
            writer.write("Nome: " + this.nome + "\n");
            writer.write("Senha: " + this.senha + "\n");
            writer.close();
            System.out.println("Arquivo do usuário " + this.nome + " criado com sucesso.");
        } else {
            System.out.println("Arquivo do usuário " + this.nome + " já existe.");
        }
    }

    public static Usuario validarLogin(String nome, String senha) throws IOException {
        File arquivoUsuario = new File(nome + ".txt");

        if (!arquivoUsuario.exists()) {
            System.out.println("Usuário não encontrado.");
            return null;
        } else {
            Scanner scanner = new Scanner(arquivoUsuario);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.contains("Senha")) {
                    String senhaArquivo = linha.split(": ")[1];

                    if (senha.equals(senhaArquivo)) {
                        return new Usuario(nome, senha);
                    } else {
                        System.out.println("Senha incorreta.");
                        return null;
                    }
                }
            }
        }

        System.out.println("Senha não encontrada.");
        return null;
    }
    
    public void lerArquivoTarefas() throws IOException {
        File arquivoTarefas = new File(this.nome + ".txt");
        
        if (!arquivoTarefas.exists()) {
            System.out.println("Arquivo de tarefas do usuário " + this.nome + " não encontrado.");
            return;
        }
        
        Scanner scanner = new Scanner(arquivoTarefas);
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            System.out.println(linha);
        }
        scanner.close();
    }
}