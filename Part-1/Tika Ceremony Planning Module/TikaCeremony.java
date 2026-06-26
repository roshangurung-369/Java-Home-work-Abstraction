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

abstract class FestivalActivity {
    protected String activityName;
    protected double estimatedCost;

    public FestivalActivity(String activityName, double estimatedCost) {
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

public class TikaCeremony extends FestivalActivity {

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
                "Not enough guests for a lively Tika! Is everyone on vacation?"
            );
        }

        if (estimatedCost > 50000) {
            throw new BudgetExceededException(
                "Tika budget too high! Is this for the whole village?"
            );
        }

        System.out.println(
            "Tika ceremony with "+ mainFamilyElder+ " planned successfully for "+ expectedGuests+ " guests!"
        );
    }

    public static void main(String[] args) {

        TikaCeremony t1 =
                new TikaCeremony(30000, 15, "Grandfather");

        TikaCeremony t2 =
                new TikaCeremony(25000, 3, "Grandmother");

        TikaCeremony t3 =
                new TikaCeremony(60000, 20, "Uncle");

        TikaCeremony[] ceremonies = {t1, t2, t3};

        for (TikaCeremony t : ceremonies) {

            t.displayOverview();

            try {
                t.planActivity();
            }
            catch (InvalidGuestCountException e) {
                System.out.println(
                        "Guest Warning: " + e.getMessage());
            }
            catch (BudgetExceededException e) {
                System.out.println(
                        "Budget Warning: " + e.getMessage());
            }
            catch (FestivalPlanningException e) {
                System.out.println(
                        "Planning Error: " + e.getMessage());
            }

            System.out.println();
        }
    }
}