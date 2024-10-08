package bot.crew.testapplicationbotcrew.AppInterfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import bot.crew.testapplicationbotcrew.services.UniversityService;

import java.util.Scanner;

@Component
public class CommandLineInterface implements CommandLineRunner {
    @Autowired
    private UniversityService universityService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your command: ");
            String input = scanner.nextLine();
            try {
                if (input.startsWith("Who is head of department")) {
                    String departmentName = input.substring(input.indexOf("department") + 10).trim();
                    System.out.println(universityService.getHeadOfDepartment(departmentName));
                } else if (input.startsWith("Show") && input.contains("statistics")) {
                    String departmentName = input.replace("Show", "").replace("statistics", "").trim();
                    System.out.println(universityService.getDepartmentStatistics(departmentName));
                } else if (input.startsWith("Show the average salary for the department")) {
                    String departmentName = input.substring("Show the average salary for the department".length()).trim();
                    System.out.println(universityService.getAverageSalary(departmentName));
                } else if (input.startsWith("Show count of employee for")) {
                    String departmentName = input.substring(27).trim();
                    System.out.println(universityService.getEmployeeCount(departmentName));
                } else if (input.startsWith("Global search by")) {
                    String template = input.substring(16).trim();
                    System.out.println(universityService.globalSearch(template));
                } else if (input.equalsIgnoreCase("exit")) {
                    break;
                } else {
                    System.out.println("Invalid command");
                }
            } catch(Exception e) {
                System.out.println("You have entered a wrong command, please check a list of available commands");
                e.printStackTrace();
            }
        }
    }
}
