package classes.users;

import classes.Role;

import java.sql.Connection;

public class Manager extends Employee {

    private Role role = Role.MANAGER;
    private Connection conn;


    public Manager(int id, Role role, String firstName, String secondName) {
        super(id, role, firstName, secondName);
    }
}
