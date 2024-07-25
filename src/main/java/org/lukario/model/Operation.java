package org.lukario.model;

public class Operation {
    private final Type type;
    private final Income previousState;
    private final String operationName;
    public enum Type {
        CREATE,
        ADD
    }

    private Operation(Type type, String operationName, Income previousState) {
        this.type = type;
        this.operationName = operationName;
        this.previousState = previousState;
    }

    public static Operation create() {
        return new Operation(Type.CREATE, "Creating income", null);
    }
}
