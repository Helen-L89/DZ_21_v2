package menu;

import tables.AnimalTable;

public enum Command {

        ADD, LIST, UPDATE, EXIT;

        public static Command fromString(String input) {
            try {
                return Command.valueOf(input.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

