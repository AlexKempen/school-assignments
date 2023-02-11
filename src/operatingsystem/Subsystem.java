package src.operatingsystem;

public enum Subsystem {
    CPU {
        @Override
        public String toString() {
            return "cpu";
        }
    },
    MEMORY {
        @Override
        public String toString() {
            return "memory";
        }
    }
}