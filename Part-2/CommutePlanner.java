public class CommutePlanner {

    public static void planMyCommute(
            String origin,
            String destination,
            RouteValidator validator,
            NavigationService navigator) {

        System.out.println(
                "Planning your commute from "
                        + origin
                        + " to "
                        + destination
                        + "...");

        try {
            navigator.navigate(
                    origin,
                    destination,
                    validator);
        }
        catch (NavigationFailedException e) {

            if (e.getCause() != null) {
                System.out.println(
                        "Cannot plan: Invalid route details.");

                System.out.println(
                        "Original Error: "
                                + e.getCause().getMessage());
            }
            else {
                System.out.println(
                        "Cannot plan: GPS issue.");

                System.out.println(
                        e.getMessage());
            }
        }
        finally {
            System.out.println(
                    "Commute planning for "
                            + origin
                            + " to "
                            + destination
                            + " completed.");
        }

        System.out.println();
    }

    public static void main(String[] args) {

        KathmanduTrafficValidator validator =
                new KathmanduTrafficValidator();

        GPSNavigationModule navigator =
                new GPSNavigationModule();

        planMyCommute(
                "Thamel",
                "Patan",
                validator,
                navigator);

        planMyCommute(
                "Baneshwor",
                "Baneshwor",
                validator,
                navigator);

        planMyCommute(
                "Kalanki",
                "Balaju",
                validator,
                navigator);
    }
}