package org.geekhub.pavlo.model;

public enum RegisterMovementTypes {
        PLUS(1),
        MINUS(-1);

        private int value;

        private RegisterMovementTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
}
