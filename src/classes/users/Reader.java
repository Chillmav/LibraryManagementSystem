package classes.users;

import classes.Role;

public class Reader extends User {

    private Role role = Role.READER;

    public Reader(int id, Role role, String firstName, String secondName) {
        super(id, role, firstName, secondName);
    }

}
