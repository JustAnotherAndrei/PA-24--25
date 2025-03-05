import java.util.*;
enum ProjectType {
    THEORETICAL, PRACTICAL;
}

class Project {
    private String name;
    private ProjectType type;

    public Project(String name, ProjectType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}

class Student {
    private String name;
    private List<Project> preferences;
    private Project assignedProject;

    public Student(String name, List<Project> preferences) {
        this.name = name;
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public List<Project> getPreferences() {
        return preferences;
    }

    public void assignProject(Project project) {
        this.assignedProject = project;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    @Override
    public String toString() {
        return name + " -> " + (assignedProject != null ? assignedProject.toString() : "No project assigned");
    }
}

// Clasa pentru alocarea proiectelor
class Allocation {
    private List<Student> students;
    private List<Project> projects;

    public Allocation(List<Student> students, List<Project> projects) {
        this.students = students;
        this.projects = new ArrayList<>(projects);
    }

    public void allocate() {
        Set<Project> assignedProjects = new HashSet<>();

        for (Student student : students) {
            for (Project preference : student.getPreferences()) {
                if (!assignedProjects.contains(preference)) {
                    student.assignProject(preference);
                    assignedProjects.add(preference);
                    break;
                }
            }
        }
    }

    public void printAllocations() {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Project p1 = new Project("P1", ProjectType.THEORETICAL);
        Project p2 = new Project("P2", ProjectType.PRACTICAL);
        Project p3 = new Project("P3", ProjectType.THEORETICAL);
        Project p4 = new Project("P4", ProjectType.PRACTICAL);


        Student s1 = new Student("S1", Arrays.asList(p1, p2));
        Student s2 = new Student("S2", Arrays.asList(p1, p3));
        Student s3 = new Student("S3", Arrays.asList(p3, p4));
        Student s4 = new Student("S4", Arrays.asList(p1, p4));

        List<Student> students = Arrays.asList(s1, s2, s3, s4);
        List<Project> projects = Arrays.asList(p1, p2, p3, p4);


        Allocation allocation = new Allocation(students, projects);
        allocation.allocate();

        allocation.printAllocations();
    }
}
