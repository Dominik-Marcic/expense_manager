package com.devot.expense_manager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IDRole;
    private String name;

    public int getIDRole() {
        return IDRole;
    }

    public void setIDRole(int id) {
        this.IDRole = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
