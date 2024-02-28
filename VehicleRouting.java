import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehicleRouting {

    // Backtracking search
    public static List<List<Integer>> backtrackSearch(List<Customer> customers, List<Truck> trucks) {
        List<List<Integer>> solution = new ArrayList<>();
        backtrack(customers, trucks, new ArrayList<>(), solution);
        return solution;
    }

    private static void backtrack(List<Customer> customers, List<Truck> trucks, List<Integer> assignment,
                                  List<List<Integer>> solution) {
        if (assignment.size() == customers.size()) {
            solution.add(new ArrayList<>(assignment));
            return;
        }

        for (int i = 0; i < customers.size(); i++) {
            if (!assignment.contains(i)) {
                Customer customer = customers.get(i);
                for (int j = 0; j < trucks.size(); j++) {
                    Truck truck = trucks.get(j);
                    if (isValidAssignment(customers, assignment, i, truck)) {
                        assignment.add(i);
                        backtrack(customers, trucks, assignment, solution);
                        assignment.remove(assignment.size() - 1);
                    }
                }
            }
        }
    }

    private static boolean isValidAssignment(List<Customer> customers, List<Integer> assignment, int customerIndex,
                                             Truck truck) {
        int totalDemand = 0;
        for (int i : assignment) {
            totalDemand += customers.get(i).demand;
        }
        totalDemand += customers.get(customerIndex).demand;
        return totalDemand <= truck.capacity;
    }

    // Generate random data
    public static void main(String[] args) {
        int numCustomers = 14;
        int numTrucks = 3;

        List<Customer> customers = generateRandomCustomers(numCustomers);
        List<Truck> trucks = generateRandomTrucks(numTrucks);

        List<List<Integer>> solution = backtrackSearch(customers, trucks);

        if (!solution.isEmpty()) {
            System.out.println("Solution found:");
            for (int i = 0; i < solution.size(); i++) {
                System.out.println("Truck " + (i + 1) + ": " + solution.get(i));
            }
        } else {
            System.out.println("No feasible solution found.");
        }
    }

    private static List<Customer> generateRandomCustomers(int numCustomers) {
        Random random = new Random();
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < numCustomers; i++) {
            int demand = getRandomNumberInRange(1, 10);
            int timeWindowStart = getRandomNumberInRange(0, 14);
            int timeWindowEnd = timeWindowStart + getRandomNumberInRange(5, 15);
            customers.add(new Customer(demand, timeWindowStart, timeWindowEnd));
        }
        return customers;
    }

    private static List<Truck> generateRandomTrucks(int numTrucks) {
        Random random = new Random();
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < numTrucks; i++) {
            int capacity = 3;
            trucks.add(new Truck(capacity));
        }
        return trucks;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}