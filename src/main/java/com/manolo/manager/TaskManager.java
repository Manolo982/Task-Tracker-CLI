package com.manolo.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.manolo.model.Tarea;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private Map<String, Tarea> tasks;
    private Gson gson;
    private String JSON_FILE = "tasks.json";

    public TaskManager(){
        gson = new GsonBuilder().setPrettyPrinting().create();
        tasks = loadTasksFromJson();
    }

    private Map<String, Tarea> loadTasksFromJson() {
        try {
            File file = new File(JSON_FILE);
            if (!file.exists()) {
                return new HashMap<>();
            }

            String jsonContent = new String(Files.readAllBytes(file.toPath()));
            Type type = new TypeToken<HashMap<String, Tarea>>(){}.getType();
            Map<String, Tarea> loadedTasks = gson.fromJson(jsonContent, type);
            return loadedTasks != null ? loadedTasks : new HashMap<>();

        } catch (IOException e) {
            System.err.println("Error al cargar las tareas: " + e.getMessage());
            return new HashMap<>();
        }
    }


    private void saveTasksToJson(){
        try {
            String jsonContent = gson.toJson(tasks);
            Files.write(Paths.get(JSON_FILE), jsonContent.getBytes());
        } catch (IOException e) {
            System.err.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    public void createTask(String id, String name){
        if (tasks.containsKey(id)) {
            System.out.println("Error: Ya existe una tarea con ese ID");
            return;
        }
        tasks.put(id, new Tarea(id, name));
        System.out.println("Tarea creada correctamente");
        saveTasksToJson();
    }

    public void deleteTask(String id) {
        if (tasks.remove(id) != null) {
            saveTasksToJson();
            System.out.println("Tarea eliminada exitosamente");
        } else {
            System.out.println("Error: No se encontró la tarea con ID: " + id);
        }
    }

    public void updateTask(String id, String newName) {
        Tarea task = tasks.get(id);
        if (task != null) {
            task.setName(newName);
            saveTasksToJson();
            System.out.println("Tarea actualizada exitosamente");
        } else {
            System.out.println("Error: No se encontró la tarea con ID: " + id);
        }
    }

    public void toggleComplete(String id) {
        Tarea task = tasks.get(id);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            saveTasksToJson();
            System.out.println("Estado de la tarea actualizado exitosamente");
        } else {
            System.out.println("Error: No se encontró la tarea con ID: " + id);
        }
    }

    public void listTasks() {
        // Recargar las tareas del archivo JSON antes de mostrarlas
        tasks = loadTasksFromJson();

        if (tasks.isEmpty()) {
            System.out.println("No hay tareas registradas");
            return;
        }
        System.out.println("Lista de tareas:");
        tasks.values().forEach(System.out::println);

        System.out.println("---------------------");
        System.out.println("---Lista de tareas completadas: ---");
        tasks.values().stream()
                .filter(Tarea::isCompleted)
                .forEach(System.out::println);

        System.out.println("---------------------");
        System.out.println("---Lista de tareas No Completadas: ---");
        tasks.values().stream()
                .filter(task -> !task.isCompleted())
                .forEach(System.out::println);
    }
}
