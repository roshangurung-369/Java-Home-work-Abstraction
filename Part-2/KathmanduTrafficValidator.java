class SameLocationException extends InvalidRouteException {

    public SameLocationException(String message) {
        super(message);
    }
}

public class KathmanduTrafficValidator implements RouteValidator {

    @Override
    public boolean isValidCommuteRoute(
            String origin,
            String destination,
            double distanceKm)
            throws InvalidRouteException {

        if (origin.equalsIgnoreCase(destination)) {
            throw new SameLocationException(
                "Origin and destination cannot be the same! Are you just spinning in circles, Damodar?"
            );
        }

        if (distanceKm < 0.1 || distanceKm > 30) {
            throw new InvalidRouteException(
                "Distance " + distanceKm +
                "km is unrealistic for Kathmandu commute!"
            );
        }

        return true;
    }

    public static void main(String[] args) {

        KathmanduTrafficValidator validator =
                new KathmanduTrafficValidator();

        try {
            System.out.println(
                    validator.isValidCommuteRoute(
                            "Thamel",
                            "Patan",
                            5.0));
        }
        catch (SameLocationException e) {
            System.out.println(
                    "Location Error: " + e.getMessage());
        }
        catch (InvalidRouteException e) {
            System.out.println(
                    "Route Error: " + e.getMessage());
        }

        try {
            validator.isValidCommuteRoute(
                    "Baneshwor",
                    "Baneshwor",
                    5.0);
        }
        catch (SameLocationException e) {
            System.out.println(
                    "Location Error: " + e.getMessage());
        }
        catch (InvalidRouteException e) {
            System.out.println(
                    "Route Error: " + e.getMessage());
        }

        try {
            validator.isValidCommuteRoute(
                    "Thamel",
                    "Patan",
                    40.0);
        }
        catch (SameLocationException e) {
            System.out.println(
                    "Location Error: " + e.getMessage());
        }
        catch (InvalidRouteException e) {
            System.out.println(
                    "Route Error: " + e.getMessage());
        }
    }
}