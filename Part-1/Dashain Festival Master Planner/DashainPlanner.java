import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
        super(message);
    }
}

class InvalidGuestCountException extends FestivalPlanningException {
    public InvalidGuestCountException(String message) {
        super(message);
    }
}

class BudgetExceededException extends FestivalPlanningException {
    public BudgetExceededException(String message) {
        super(message);
    }
}

class NoRouteException extends FestivalPlanningException {
    public NoRouteException(String message) {
        super(message);
    }
}

abstract class FestivalActivity {
    protected String activityName;
    protected double estimatedCost;

    public FestivalActivity(String activityName,
                            double estimatedCost) {
        this.activityName = activityName;
        this.estimatedCost = estimatedCost;
    }

    public abstract void planActivity()
            throws FestivalPlanningException;

    public void displayOverview() {
        System.out.println("\nActivity Name: " + activityName);
        System.out.println("Estimated Cost: Rs. " + estimatedCost);
    }
}

class TikaCeremony extends FestivalActivity {

    private int expectedGuests;
    private String mainFamilyElder;

    public TikaCeremony(double estimatedCost,
                        int expectedGuests,
                        String mainFamilyElder) {

        super("Tika Ceremony", estimatedCost);

        this.expectedGuests = expectedGuests;
        this.mainFamilyElder = mainFamilyElder;
    }

    @Override
    public void planActivity()
            throws FestivalPlanningException {

        if (expectedGuests < 5) {
            throw new InvalidGuestCountException(
                "Not enough guests for a lively Tika! Is everyone on vacation?");
        }

        if (estimatedCost > 50000) {
            throw new BudgetExceededException(
                "Tika budget too high! Is this for the whole village?");
        }

        System.out.println(
            "Tika ceremony with "
            + mainFamilyElder
            + " planned successfully for "
            + expectedGuests
            + " guests!");
    }
}

class DeusiBhailo extends FestivalActivity {

    private List<String> plannedRoutes;
    private int numberOfPerformers;

    public DeusiBhailo(double estimatedCost,
                       List<String> plannedRoutes,
                       int numberOfPerformers) {

        super("Deusi Bhailo Program", estimatedCost);

        this.plannedRoutes = plannedRoutes;
        this.numberOfPerformers = numberOfPerformers;
    }

    @Override
    public void planActivity()
            throws FestivalPlanningException {

        if (plannedRoutes.isEmpty()) {
            throw new NoRouteException(
                "No routes planned for Deusi Bhailo! Are we just singing in the living room?");
        }

        if (numberOfPerformers < 3) {
            throw new FestivalPlanningException(
                "Need at least 3 performers for a proper Deusi Bhailo!");
        }

        System.out.println(
            "Deusi Bhailo program with "
            + numberOfPerformers
            + " performers planned for "
            + plannedRoutes.size()
            + " routes!");
    }
}

public class DashainPlanner {

    public static void executeFestivalPlan(
            List<FestivalActivity> activities) {

        for (FestivalActivity activity : activities) {

            activity.displayOverview();

            try {
                activity.planActivity();
            }
            catch (InvalidGuestCountException e) {
                System.out.println(
                        "Planning Warning (Guests): "
                                + e.getMessage());
            }
            catch (BudgetExceededException e) {
                System.out.println(
                        "Planning Warning (Budget): "
                                + e.getMessage());
            }
            catch (NoRouteException e) {
                System.out.println(
                        "Planning Warning (Routes): "
                                + e.getMessage());
            }
            catch (FestivalPlanningException e) {
                System.out.println(
                        "General Planning Error: "
                                + e.getMessage());
            }
            finally {
                System.out.println(
                        "Activity planning attempt for "
                                + activity.activityName
                                + " completed.");
            }
        }
    }

    public static void main(String[] args) {

        List<FestivalActivity> activities =
                new ArrayList<>();

        activities.add(
                new TikaCeremony(
                        30000,
                        15,
                        "Grandfather"));

        activities.add(
                new TikaCeremony(
                        25000,
                        3,
                        "Grandmother"));

        activities.add(
                new TikaCeremony(
                        60000,
                        20,
                        "Uncle"));

        activities.add(
                new DeusiBhailo(
                        10000,
                        Arrays.asList(
                                "Baneshwor",
                                "Koteshwor"),
                        5));

        activities.add(
                new DeusiBhailo(
                        8000,
                        new ArrayList<>(),
                        5));

        activities.add(
                new DeusiBhailo(
                        5000,
                        Arrays.asList("Kalanki"),
                        2));

        executeFestivalPlan(activities);
    }
}