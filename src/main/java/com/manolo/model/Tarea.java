package com.manolo.model;


public class Tarea {

    private String id;
    private String name;
    private Boolean completed;

    public Tarea(String id, String name){
        this.id = id;
        this.name = name;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() { return completed; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", id, name, completed ? "Completada" : "Pendiente");
    }
}
