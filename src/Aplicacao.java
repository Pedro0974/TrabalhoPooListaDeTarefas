import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;

import models.listaTarefa.ListaTarefa;
import models.tarefa.CategoriaTarefa;
import models.tarefa.SubTarefa;
import models.tarefa.Tarefa;
import models.usuario.Usuario;


public class Aplicacao {

    private static Scanner input = new Scanner(System.in);
    private static Usuario usuarioAtual = null;
    private static String nomeCategoria = null;

    public static void main(String[] args) throws IOException, JSONException {
        exibirMenuLogin();
        exibirMenuTarefas();
    }

    private static void exibirMenuLogin() throws IOException, JSONException {
        System.out.println("Bem-vindo ao Sistema de Tarefas!");

        while (usuarioAtual == null) {
            System.out.println("\nMenu de Login:");
            System.out.println("1. Criar uma nova conta");
            System.out.println("2. Fazer login");
            System.out.println("3. Sair");

            int opcaoLogin = input.nextInt();
            input.nextLine(); // limpa o buffer do teclado

            switch (opcaoLogin) {
                case 1:
                    criarNovaConta();
                    break;

                case 2:
                    fazerLogin();
                    break;

                case 3:
                    System.out.println("\nObrigado por usar nosso sistema!");
                    System.exit(0);

                default:
                    System.out.println("\nOpção inválida! Escolha novamente.");
                    break;
            }
        }
    }

    private static void criarNovaConta() throws IOException, JSONException {
        System.out.println("\nDigite o nome do usuário:");
        String nome = input.nextLine();
        System.out.println("Digite a senha:");
        String senha = input.nextLine();

        Usuario novoUsuario = new Usuario(nome, senha);
        novoUsuario.criarArquivoJSON();

        usuarioAtual = novoUsuario;
    }

    private static void fazerLogin() throws IOException, JSONException {
        System.out.println("\nDigite o nome do usuário:");
        String nomeUsuario = input.nextLine();
        System.out.println("Digite a senha:");
        String senhaUsuario = input.nextLine();

        Usuario usuario = Usuario.validarLogin(nomeUsuario, senhaUsuario);
        if (usuario != null) {
            usuarioAtual = usuario;
            usuarioAtual.carregarTarefasUsuario(); // Carrega as tarefas do usuário
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Usuário ou senha inválidos!");
        }
    }

    private static void exibirMenuTarefas() throws JSONException, IOException {
        while (true) {
            System.out.println("\nMenu de Tarefas:");
            System.out.println("1. Adicionar nova tarefa");
            System.out.println("2. Listar tarefas pendentes");
            System.out.println("3. Filtrar por Categoria");
            System.out.println("4. Fechar tarefa");
            System.out.println("5. Listar tarefas concluídas");
            System.out.println("6. Buscar Tarefa Pelo Nome");
            System.out.println("7. Sair");

            int opcaoTarefas = input.nextInt();
            input.nextLine(); // limpa o buffer do teclado

            switch (opcaoTarefas) {
                case 1:
                    adicionarNovaTarefa();
                    break;

                case 2:
                    usuarioAtual.getListaDeTarefas().exibirTarefasPendentes();
                    break;

                case 3:
                    filtrarTarefasPorCategoria();
                    break;

                case 4:
                    fecharTarefa();
                    break;

                case 5:
                    usuarioAtual.getListaDeTarefas().exibirTarefasConcluidas();
                    break;

                case 6:
                    System.out.println("Digite o título da tarefa: ");
                    String titulo = input.nextLine();
                    Tarefa tarefaEncontrada = usuarioAtual.getListaDeTarefas().buscarTarefa(titulo);
                    if (tarefaEncontrada != null) {
                        System.out.println(tarefaEncontrada.toString());
                    } else {
                        System.out.println("Tarefa não encontrada!");
                    }
                    break;

                case 7:
                    System.out.println("\nObrigado por usar nosso sistema!");
                    System.exit(0);

                default:
                    System.out.println("\nOpção inválida! Escolha novamente.");
                    break;
            }
        }
    }

    private static void adicionarNovaTarefa() throws JSONException, IOException {
        System.out.println("\nDigite o título da nova tarefa:");
        String titulo = input.nextLine();
        System.out.println("Digite a descrição:");
        String descricao = input.nextLine();

        System.out.println("Digite a Categoria:");
        nomeCategoria = input.nextLine();
        CategoriaTarefa categoria = new CategoriaTarefa(nomeCategoria);
        
        System.out.println("Digite a Subtarefa:");
        String nomesubtarefa = input.nextLine();
        SubTarefa subtarefa = new SubTarefa(nomesubtarefa, false);

		Tarefa novaTarefa = new Tarefa(titulo, descricao, categoria, subtarefa);

        usuarioAtual.getListaDeTarefas().adicionarTarefaPendente(novaTarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private static void filtrarTarefasPorCategoria() {
        System.out.println("Digite a Categoria:");
        nomeCategoria = input.nextLine();

        List<Tarefa> tarefasFiltradas = usuarioAtual.getListaDeTarefas().buscarTarefasPorCategoria(nomeCategoria);
        for (Tarefa tarefa : tarefasFiltradas) {
            System.out.println("Título: " + tarefa.getTitulo());
            System.out.println("Descrição: " + tarefa.getDescricao());
            System.out.println("Categoria: " + tarefa.getCategoria().toString());
            System.out.println("Subtarefa: " + tarefa.getSubtarefa().toString());
            System.out.println("Status: " + tarefa.getStatus());
            System.out.println("---------------------");
        }
    }

    private static void fecharTarefa() throws JSONException {
        System.out.println("\nDigite o título da tarefa que deseja fechar:");
        String tituloTarefa = input.nextLine();
        Tarefa tarefa = usuarioAtual.getListaDeTarefas().buscarTarefa(tituloTarefa);
        if (tarefa != null) {
            usuarioAtual.getListaDeTarefas().concluirTarefa(tituloTarefa);
            System.out.println("Tarefa fechada com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada na lista de pendentes!");
        }
    }
}
