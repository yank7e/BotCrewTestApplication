package bot.crew.testapplicationbotcrew.services;

import bot.crew.testapplicationbotcrew.entities.Degree;
import bot.crew.testapplicationbotcrew.entities.Department;
import bot.crew.testapplicationbotcrew.entities.Lector;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bot.crew.testapplicationbotcrew.repositories.DepartmentRepository;
import bot.crew.testapplicationbotcrew.repositories.LectorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectorRepository lectorRepository;

    public String getHeadOfDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return "Head of " + departmentName + " department is " + department.getHeadOfDepartment().getFirstName() + " " + department.getHeadOfDepartment().getLastName();
    }

    @Transactional
    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        List<Lector> lectors = department.getLectors();
        System.out.println("Number of lectors in the department: " + lectors.size());

        if (lectors.isEmpty()) {
            return "No lectors found for department '" + departmentName + "'.";
        }

        long assistants = lectors.stream().filter(l -> l.getDegree() == Degree.ASSISTANT).count();
        long associateProfessors = lectors.stream().filter(l -> l.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        long professors = lectors.stream().filter(l -> l.getDegree() == Degree.PROFESSOR).count();

        return "assistants - " + assistants + "\nassociate professors - " + associateProfessors + "\nprofessors - " + professors;
    }

    public String getAverageSalary(String departmentName) {
        System.out.println("Searching for department: " + departmentName);
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        double averageSalary = department.getLectors().stream().mapToDouble(Lector::getSalary).average().orElse(0.0);
        return "The average salary of " + departmentName + " is " + averageSalary;
    }

    public String getEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return "The employee count for " + departmentName + " is " + department.getLectors().size();
    }

    public String globalSearch(String template) {
        List<Lector> lectors = lectorRepository.searchByTemplate(template);
        return lectors.stream()
                .map(lector -> lector.getFirstName() + " " + lector.getLastName())
                .collect(Collectors.joining(", "));
    }
}
