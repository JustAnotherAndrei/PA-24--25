import java.util.Random;

public class Bonus2 {
    public static void main(String[] args) {
        int numStudents = 69;
        int numProjects = 2137;
        Random rand = new Random();

        Student[] students = new Student[numStudents];
        Project[] projects = new Project[numProjects];

        for (int i = 0; i < numProjects; i++) {
            projects[i] = new Project("P" + (i + 1));
        }

        for (int i = 0; i < numStudents; i++) {
            int numPreferences = rand.nextInt(numProjects) + 1;
            int[] preferences = new int[numPreferences];
            for (int j = 0; j < numPreferences; j++) {
                preferences[j] = rand.nextInt(numProjects);
            }
            students[i] = new Student("S" + (i + 1), preferences);
        }

        AllocationProblem problem = new AllocationProblem(students, projects);
        boolean satisfiesHall = problem.isHallConditionSatisfied();
        System.out.println("Hall's Condition Satisfied: " + satisfiesHall);
    }
}

