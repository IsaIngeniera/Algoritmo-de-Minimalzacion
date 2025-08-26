import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Minimizador de Autómatas Finitos Deterministas ---");
        System.out.print("Introduce el número de casos de prueba: ");
        int numCases = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numCases; i++) {
            System.out.println("\n--- Procesando Caso #" + (i + 1) + " ---");
            solve(scanner);
        }
        scanner.close();
    }

    public static void solve(Scanner scanner) {
        // --- Guía para el usuario ---
        System.out.print("Número de estados del autómata: ");
        int n = Integer.parseInt(scanner.nextLine());

        System.out.print("Alfabeto (símbolos separados por espacios): ");
        String[] alphabetSymbols = scanner.nextLine().split(" ");

        System.out.print("Estados finales (separados por espacios, o enter si no hay): ");
        String finalStatesLine = scanner.nextLine();
        Set<Integer> finalStates = new HashSet<>();
        if (!finalStatesLine.trim().isEmpty()) {
            Arrays.stream(finalStatesLine.split(" "))
                    .map(Integer::parseInt)
                    .forEach(finalStates::add);
        }

        System.out.println("Tabla de transiciones (una línea por estado, ej: 0 1 2):");
        int[][] transitions = new int[n][alphabetSymbols.length];
        for (int j = 0; j < n; j++) {
            // Se asume que cada línea es para el estado 'j'
            String[] row = scanner.nextLine().split(" ");
            for (int k = 0; k < alphabetSymbols.length; k++) {
                // El PDF indica que la línea empieza con el número de estado, que ignoramos.
                // Por eso se usa row[k + 1].
                transitions[j][k] = Integer.parseInt(row[k + 1]);
            }
        }

        // --- Inicio del Algoritmo de Minimización ---

        // Matriz para marcar pares de estados distinguibles.
        boolean[][] marked = new boolean[n][n];

        // Paso 1: Marcar todos los pares (p, q) donde uno es final y el otro no.
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                boolean pIsFinal = finalStates.contains(p);
                boolean qIsFinal = finalStates.contains(q);
                if (pIsFinal != qIsFinal) {
                    marked[p][q] = true;
                }
            }
        }

        // Paso 2: Iterar y marcar pares adicionales hasta que no haya más cambios.
        boolean changed;
        do {
            changed = false;
            for (int p = 0; p < n; p++) {
                for (int q = p + 1; q < n; q++) {
                    // Si el par (p, q) aún no está marcado...
                    if (!marked[p][q]) {
                        // ...revisar si algún símbolo del alfabeto los lleva a un par ya marcado.
                        for (int k = 0; k < alphabetSymbols.length; k++) {
                            int p_next = transitions[p][k];
                            int q_next = transitions[q][k];

                            // Nos aseguramos de buscar en la matriz triangular superior.
                            int s1 = Math.min(p_next, q_next);
                            int s2 = Math.max(p_next, q_next);

                            if (s1 != s2 && marked[s1][s2]) {
                                marked[p][q] = true;
                                changed = true;
                                break; // Si ya se marcó, no es necesario seguir con este par.
                            }
                        }
                    }
                }
            }
        } while (changed);

        // Paso 3: Los pares no marcados son equivalentes. Recopilarlos para la salida.
        List<String> equivalentPairs = new ArrayList<>();
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                if (!marked[p][q]) {
                    equivalentPairs.add("(" + p + ", " + q + ")");
                }
            }
        }

        System.out.print("\nPares de estados equivalentes: ");
        System.out.println(String.join(" ", equivalentPairs));
    }
}