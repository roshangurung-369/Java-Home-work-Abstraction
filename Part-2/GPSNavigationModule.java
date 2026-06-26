public class GPSNavigationModule implements NavigationService {

    @Override
    public void navigate(
            String startPoint,
            String endPoint,
            RouteValidator validator)
            throws NavigationFailedException {

        System.out.println(
                "Attempting to navigate from "
                        + startPoint
                        + " to "
                        + endPoint
                        + "...");

        try {

            validator.isValidCommuteRoute(
                    startPoint,
                    endPoint,
                    simulatedDistance(startPoint, endPoint));

        }
        catch (InvalidRouteException e) {
            throw new NavigationFailedException(
                    "Route validation failed!",
            e);
        }

        if (startPoint.equalsIgnoreCase("Kalanki")) {
            throw new NavigationFailedException(
                    "GPS signal lost near Kalanki! Welcome to Kathmandu traffic!");
        }

        System.out.println(
                "Navigation successful! Estimated time: 20 minutes (or 2 hours depending on traffic).");
    }

    private double simulatedDistance(
            String start,
            String end) {

        return 5.0;
    }

    public static void main(String[] args) {

        KathmanduTrafficValidator validator =
                new KathmanduTrafficValidator();

        GPSNavigationModule gps =
                new GPSNavigationModule();

        try {
            gps.navigate(
                    "Thamel",
                    "Patan",
                    validator);
        }
        catch (NavigationFailedException e) {
            System.out.println(e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Original Error: "
                                + e.getCause().getMessage());
            }
        }

        System.out.println();

        try {
            gps.navigate(
                    "Baneshwor",
                    "Baneshwor",
                    validator);
        }
        catch (NavigationFailedException e) {
            System.out.println(e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Original Error: "
                                + e.getCause().getMessage());
            }
        }

        System.out.println();

        try {
            gps.navigate(
                    "Kalanki",
                    "Balaju",
                    validator);
        }
        catch (NavigationFailedException e) {
            System.out.println(e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Original Error: "
                                + e.getCause().getMessage());
            }
        }
    }
}