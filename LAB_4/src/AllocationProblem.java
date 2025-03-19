public class AllocationProblem {
    Student[] students;
    Project[] projects;

    public AllocationProblem(Student[] students, Project[] projects) {
        this.students = students;
        this.projects = projects;
    }

    public boolean isHallConditionSatisfied() {
        int n = students.length;
        for (int k = 1; k <= n; k++) {
            if (!checkSubsetHall(k)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubsetHall(int k) {
        int projectCount = 0;
        boolean[] counted = new boolean[projects.length];
        for (int i = 0; i < k; i++) {
            for (int p : students[i].preferences) {
                if (!counted[p]) {
                    counted[p] = true;
                    projectCount++;
                }
            }
        }
        return projectCount >= k;
    }
}

