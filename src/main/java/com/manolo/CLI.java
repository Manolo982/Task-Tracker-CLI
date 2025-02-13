package com.manolo;

import com.manolo.manager.TaskManager;
import com.manolo.model.Tarea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private Scanner scan;
    private boolean running;
    private TaskManager manager;

    public CLI() {
        this.scan = new Scanner(System.in);
        this.manager = new TaskManager();
        running = true;
    }

    public void start() {
            executeLines();
    }

    private void executeLines() {
        while (running){
            mostrarMensaje();
            System.out.println("\nIntroduce una opcion");

            String input = scan.nextLine().trim();

            if(input.equalsIgnoreCase("exit")){
                running = false;
                break;
            }

            String[] partes = input.split(" "); // Esto divide la cadena en partes separadas por un espacio
                                                      // {"task", "create", "1", "Estudiar Java"}

            if(partes.length < 2){
                System.out.println("Error: Debes introducir un comando valido!");
                continue;
            }

            String command = partes[1];

            if (!partes[0].equalsIgnoreCase("task")){
                System.out.println("Error: Comando no valido");
                continue;
            }

                switch (command) {
                    case "create":
                        if (partes.length < 4) {
                            System.out.println("Uso: task create <id> <name> sadas sadas asdas");
                            break;
                        }
                        String name = String.join(" ", Arrays.copyOfRange(partes, 3, partes.length)); // El copyOfRange es para coger el nombre completo de la tarea (coge una porcion del array partes)
                        // Es decir parts = ["task", "create", "1", "hacer", "compras", "urgentes"]
                        // Arrays.copyOfRange(parts, 3, parts.length) El metodo devuelve {"hacer", "compras", "urgentes"}
                        manager.createTask(partes[2], name);

                        break;
                    case "delete":
                        if (partes.length < 3) {
                            System.out.println("Uso: task delete <id>");
                            break;
                        }
                        manager.deleteTask(partes[2]);
                        break;
                    case "update":
                        if (partes.length < 4) {
                            System.out.println("Uso: task update <id> <newName>");
                            break;
                        }
                        manager.updateTask(partes[2], String.join(" ", Arrays.copyOfRange(partes, 3, partes.length)));
                    case "list":
                        manager.listTasks();
                        break;
                    case "toggle":
                        if (partes.length < 3) {
                            System.out.println("Uso: task toggle <id>");
                            break;
                        }
                        manager.toggleComplete(partes[2]);
                        break;

                }
        }
        scan.close();

    }

    private void mostrarMensaje() {
        System.out.println("\nComandos disponibles:");
        System.out.println("1. task create <id> <name>");
        System.out.println("2. task delete <id>");
        System.out.println("3. task update <id> <new_name>");
        System.out.println("4. task toggle <id>");
        System.out.println("5. task list");
        System.out.println("6. exit");
    }

    public static void main(String[] args) {

        CLI cli = new CLI();

        cli.start();
    }

}