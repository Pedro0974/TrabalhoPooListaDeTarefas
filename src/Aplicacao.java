import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import models.tarefa.Tarefa;
import models.usuario.Usuario;

public class Aplicacao {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Usuario usuarioAtual = null;

        while (true) {
            System.out.println("\nBem-vindo ao Sistema de Tarefas!");
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("1. Criar uma nova conta");
            System.out.println("2. Fazer login");
            System.out.println("3. Adicionar nova tarefa");
            System.out.println("4. Listar tarefas pendentes");
            System.out.println("5. Fechar tarefa");
            System.out.println("6. Sair");

            int opcao = input.nextInt();
            input.nextLine(); // limpa o buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.println("\nDigite o nome do usuário:");
                    String nome = input.nextLine();
                    System.out.println("Digite a senha:");
                    String senha = input.nextLine();

                    Usuario novoUsuario = new Usuario(nome, senha);
                    novoUsuario.criarArquivoUsuario();
                    System.out.println("Usuário criado com sucesso!");
                    break;

                case 2:
                    System.out.println("\nDigite o nome do usuário:");
                    String nomeUsuario = input.nextLine();
                    System.out.println("Digite a senha:");
                    String senhaUsuario = input.nextLine();

                    Usuario usuario = Usuario.validarLogin(nomeUsuario, senhaUsuario);
                    if (usuario != null) {
                        usuarioAtual = usuario;
                        System.out.println("Login realizado com sucesso!");
                    } else {
                        System.out.println("Usuário ou senha inválidos!");
                    }
                    break;

                case 3:
                    if (usuarioAtual == null) {
                        System.out.println("\nVocê precisa fazer login para acessar essa opção!");
                        break;
                    }

                    System.out.println("\nDigite o título da nova tarefa:");
                    String titulo = input.nextLine();
                    System.out.println("Digite a descrição:");
                    String descricao = input.nextLine();

                    Tarefa novaTarefa = new Tarefa(titulo, descricao);
                    usuarioAtual.getListaDeTarefas().adicionarTarefaPendente(titulo, descricao);
                    System.out.println("Tarefa adicionada com sucesso!");
                    break;
                
                case 4:
                	usuarioAtual.getListaDeTarefas().exibirTarefasPendentes();
                	break;

                case 5:
                    if (usuarioAtual == null) {
                        System.out.println("\nVocê precisa fazer login para acessar essa opção!");
                        break;
                    }

                    System.out.println("\nDigite o título da tarefa que deseja fechar:");
                    String tituloTarefa = input.nextLine();
                    Tarefa tarefa = usuarioAtual.getListaDeTarefas().buscarTarefa(tituloTarefa);
                    if (tarefa != null) {
                        usuarioAtual.getListaDeTarefas().concluirTarefa(tituloTarefa);
                        System.out.println("Tarefa fechada com sucesso!");
                    } else {
                        System.out.println("Tarefa não encontrada na lista de pendentes!");
                    }
                    break;

                case 6:
                    System.out.println("\nObrigado por usar nosso sistema!");
                    System.exit(0);

                default:
                    System.out.println("\nOpção inválida! Escolha novamente.");
                    break;
            }
        }
    }
}
