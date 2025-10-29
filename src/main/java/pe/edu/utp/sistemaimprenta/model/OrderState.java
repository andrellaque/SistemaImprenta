package pe.edu.utp.sistemaimprenta.model;

public enum OrderState {
    REGISTRADO(1),
    EN_PROCESO(2),
    FINALIZADO(3),
    ENTREGADO(4),
    CANCELADO(5);

    private final int id;

    OrderState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrderState fromId(int id) {
        for (OrderState state : values()) {
            if (state.id == id) {
                return state;
            }
        }
        return REGISTRADO;
    }
}
