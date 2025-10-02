package pe.edu.utp.sistemaimprenta.model;

public enum UserType {
    ADMINISTRADOR(1),
    VENDEDOR(2),
    OPERARIO_PRODUCCION(3);

    private final int id;

    private UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserType fromId(int id) {
        for (UserType type : UserType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Id de UserType no válido: " + id);
    }
}

