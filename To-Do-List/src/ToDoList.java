import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        boolean exit = false;

        while (!exit) {
            System.out.println("------To-Do-List------");
            System.out.println("1. Dodaj nowe zadanie\n" +
                    "2. Oznacz zadanie jako zakończone\n" +
                    "3. Usuń zadanie\n" +
                    "4. Wyświetl listę zadań\n" +
                    "5. Wyjście");
            System.out.println("Wybierz opcję (1/2/3/4/5):");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Podaj nazwę zadania:");
                    String taskName = scanner.nextLine();
                    System.out.println("Podaj treść zadania:");
                    String taskDescription = scanner.nextLine();
                    Task newTask = new Task(taskName, taskDescription);
                    taskList.add(newTask);
                    System.out.println("Zadanie \"" + taskName + "\" zostało dodane do listy.");
                }
                case 2 -> {
                    displayTasks(taskList);
                    System.out.println("Podaj numer zadania do oznaczenia jako zakończone:");
                    int taskIndexComplete = scanner.nextInt();
                    taskList.get(taskIndexComplete - 1).setCompleted(true);
                    System.out.println("Zadanie \"" + taskList.get(taskIndexComplete - 1).getName() +
                            "\" zostało oznaczone jako zakończone.");
                }
                case 3 -> {
                    displayTasks(taskList);
                    System.out.println("Podaj numer zadania do usunięcia:");
                    int taskIndexRemove = scanner.nextInt();
                    Task removedTask = taskList.remove(taskIndexRemove - 1);
                    System.out.println("Zadanie \"" + removedTask.getName() + "\" zostało usunięte z listy.");
                }
                case 4 -> displayTasks(taskList);
                case 5 -> {
                    System.out.println("Zakończono program.");
                    exit = true;
                }
                default -> System.out.println("Wprowadziłeś niepoprawną opcję. Spróbuj ponownie.");
            }
        }
    }

    private static void displayTasks(ArrayList<Task> taskList) {
        System.out.println("Lista zadań:");
        if (!taskList.isEmpty()) {
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println((i + 1) + ". [" + (task.isCompleted() ? "x" : " ") + "] " +
                        task.getName() + ": " + task.getDescription());
            }
        } else {
            System.out.println("(brak zadań)");
        }
    }
}
