package pe.edu.utp.sistemaimprenta.model;

public enum ProductType {
    PAPELERIA(1),
    PUBLICIDAD(2),
    DIGITAL(3),
    SERIGRAFIA(4),
    EMPAQUE(5),
    SERVICIO(6),
    OTRO(7);

    private final int id;

    ProductType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProductType fromId(int id) {
        for (ProductType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return OTRO;
    }
}
