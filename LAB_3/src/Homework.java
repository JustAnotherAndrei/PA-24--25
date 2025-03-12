public class Homework {

    // clasa de baza: Person
    public static abstract class Person {
        private String name, dateOfBirth;

        public Person(String name, String dob) {
            this.name = name;
            this.dateOfBirth = dob;
        }

        public String getName() { return name; }
        public String getDateOfBirth() { return dateOfBirth; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person p = (Person) o;
            return name.equals(p.name) && dateOfBirth.equals(p.dateOfBirth);
        }

        @Override
        public String toString() {
            return name + " (DOB: " + dateOfBirth + ")";
        }
    }

    public static class Student extends Person {
        private String regNumber;

        public Student(String name, String dob, String regNo) {
            super(name, dob);
            this.regNumber = regNo;
        }

        public String getRegNumber() { return regNumber; }

        @Override
        public boolean equals(Object o) {
            if (!super.equals(o)) return false;
            if (!(o instanceof Student)) return false;
            Student s = (Student) o;
            return regNumber.equals(s.regNumber);
        }

        @Override
        public String toString() {
            return super.toString() + " - RegNo: " + regNumber;
        }
    }

    public static class Teacher extends Person {
        private Project[] proposed;
        private int count;

        public Teacher(String name, String dob, int maxProjects) {
            super(name, dob);
            this.proposed = new Project[maxProjects];
            this.count = 0;
        }

        public boolean addProject(Project p) { // 'addProject' adauga un proiect propus de profesor, daca
                                               //  mai exista loc in array si daca nu e deja prezent
            if (count >= proposed.length) return false;
            for (int i = 0; i < count; i++) {
                if (proposed[i].equals(p)) return false;
            }
            proposed[count++] = p;
            return true;
        }

        public Project[] getProposedProjects() { // 'getProposedProjects' returneaza un nou array care contine//  doar proiectele adaugate
            Project[] copy = new Project[count];
            System.arraycopy(proposed, 0, copy, 0, count);
            return copy;
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o); // nu mai verificam proiectele
        }

        @Override
        public String toString() {
            return super.toString() + " - Teacher";
        }
    }

    public static class Project {
        private String title;
        private Teacher proposer;

        public Project(String title, Teacher proposer) {
            this.title = title;
            this.proposer = proposer;
        }

        public String getTitle() { return title; }
        public Teacher getProposer() { return proposer; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Project)) return false;
            Project p = (Project) o;
            return title.equals(p.title) && proposer.equals(p.proposer);
        }

        @Override
        public String toString() {
            return "Project '" + title + "' by " + proposer.getName();
        }
    }

    public static class Problem {
        private Student[] students;
        private Teacher[] teachers;
        private Project[] projects;
        private int sCount, tCount, pCount;

        public Problem(int maxS, int maxT, int maxP) {
            students = new Student[maxS];
            teachers = new Teacher[maxT];
            projects = new Project[maxP];
        }

        public boolean addStudent(Student s) {
            if (sCount >= students.length) return false;
            for (int i = 0; i < sCount; i++) {
                if (students[i].equals(s)) return false;
            }
            students[sCount++] = s;
            return true;
        }

        public boolean addTeacher(Teacher t) {
            if (tCount >= teachers.length) return false;
            for (int i = 0; i < tCount; i++) {
                if (teachers[i].equals(t)) return false;
            }
            teachers[tCount++] = t;
            return true;
        }

        public boolean addProject(Project p) {
            if (pCount >= projects.length) return false;
            for (int i = 0; i < pCount; i++) {
                if (projects[i].equals(p)) return false;
            }
            projects[pCount++] = p;
            return true;
        }

        // metode ce returneaza un array exact cu studentii, profesorii si proiectele
        public Student[] getStudents() {
            Student[] copy = new Student[sCount];
            System.arraycopy(students, 0, copy, 0, sCount);
            return copy;
        }

        public Teacher[] getTeachers() {
            Teacher[] copy = new Teacher[tCount];
            System.arraycopy(teachers, 0, copy, 0, tCount);
            return copy;
        }

        public Project[] getProjects() {
            Project[] copy = new Project[pCount];
            System.arraycopy(projects, 0, copy, 0, pCount);
            return copy;
        }

        // returneaza toti studentii si profesorii din 'Problem'
        public Person[] getAllPersons() {
            Person[] all = new Person[sCount + tCount];
            for (int i = 0; i < sCount; i++) all[i] = students[i];
            for (int i = 0; i < tCount; i++) all[sCount + i] = teachers[i];
            return all;
        }
    }

    public static class Solution {
        private Problem problem;
        private Project[] allocation; // allocation[i] = proiectul studentului i

        public Solution(Problem problem) {
            this.problem = problem;
            this.allocation = new Project[problem.getStudents().length];
        }

        public void allocateProjects() { //Greedy simplu
            Student[] s = problem.getStudents();
            Project[] p = problem.getProjects();
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < p.length; j++) {
                    if (!isTaken(p[j])) {
                        allocation[i] = p[j];
                        break;
                    }
                }
            }
        }

        private boolean isTaken(Project pr) {
            for (Project prj : allocation) {
                if (prj != null && prj.equals(pr)) return true;
            }
            return false;
        }

        public Project[] getAllocation() {
            return allocation;
        }
    }



    public static void main(String[] args) {
        Problem prob = new Problem(5, 3, 10);

        Teacher t1 = new Teacher("Ion Iliescu", "1930-03-04", 3);
        Teacher t2 = new Teacher("Traian Basescu", "1951-11-04", 2);
        prob.addTeacher(t1);
        prob.addTeacher(t2);

        Project pj1 = new Project("AI Research", t1);
        Project pj2 = new Project("Data Mining", t1);
        Project pj3 = new Project("Mobile Dev", t2);
        t1.addProject(pj1);
        t1.addProject(pj2);
        t2.addProject(pj3);
        prob.addProject(pj1);
        prob.addProject(pj2);
        prob.addProject(pj3);

        Student s1 = new Student("Andrei", "2004-04-18", "S001");
        Student s2 = new Student("Adi", "1999-10-15", "S002");
        Student s3 = new Student("Cosmin", "2005-08-30", "S003");
        prob.addStudent(s1);
        prob.addStudent(s2);
        prob.addStudent(s3);

        System.out.println("All persons:");
        for (Person p : prob.getAllPersons()) {
            System.out.println("  " + p);
        }

        Solution sol = new Solution(prob);
        sol.allocateProjects();

        Student[] studs = prob.getStudents();
        Project[] aloc = sol.getAllocation();
        System.out.println("\nAllocations:");
        for (int i = 0; i < studs.length; i++) {
            System.out.println("  " + studs[i].getName() + " -> "
                    + (aloc[i] == null ? "No project" : aloc[i].getTitle()));
        }
    }
}
