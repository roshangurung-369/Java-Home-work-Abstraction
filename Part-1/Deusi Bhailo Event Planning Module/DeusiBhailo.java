import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FestivalPlanningException extends Exception {
    public FestivalPlanningException(String message) {
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
        System.out.println("Activity Name: " + activityName);
        System.out.println("Estimated Cost: Rs. " + estimatedCost);
    }
}

public class DeusiBhailo extends FestivalActivity {

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
                "No routes planned for Deusi Bhailo! Are we just singing in the living room?"
            );
        }

        if (numberOfPerformers < 3) {
            throw new FestivalPlanningException(
                "Need at least 3 performers for a proper Deusi Bhailo!"
            );
        }

        System.out.println(
            "Deusi Bhailo program with "
            + numberOfPerformers
            + " performers planned for "
            + plannedRoutes.size()
            + " routes!"
        );
    }

    public static void main(String[] args) {

        DeusiBhailo d1 = new DeusiBhailo(
                10000,
                Arrays.asList("Baneshwor", "Koteshwor"),
                5);

        DeusiBhailo d2 = new DeusiBhailo(
                8000,
                new ArrayList<>(),
                5);

        DeusiBhailo d3 = new DeusiBhailo(
                5000,
                Arrays.asList("Kalanki"),
                2);

        DeusiBhailo[] programs = {d1, d2, d3};

        for (DeusiBhailo d : programs) {

            d.displayOverview();

            try {
                d.planActivity();
            }
            catch (NoRouteException e) {
                System.out.println("Route Error: " + e.getMessage());
            }
            catch (FestivalPlanningException e) {
                System.out.println("Planning Error: " + e.getMessage());
            }

            System.out.println();
        }
    }
}