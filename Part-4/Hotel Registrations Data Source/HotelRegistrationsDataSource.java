import java.util.ArrayList;
import java.util.List;

class DataSourceAccessException extends Exception {

    public DataSourceAccessException(String message) {
        super(message);
    }

    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

class AuthenticationFailedException extends Exception {

    public AuthenticationFailedException(String message) {
        super(message);
    }
}

abstract class TouristDataSource {

    protected String sourceName;

    public TouristDataSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData()
            throws DataSourceAccessException;
}

public class HotelRegistrationsDataSource extends TouristDataSource {

    public HotelRegistrationsDataSource() {
        super("Kathmandu Hotels Registrations");
    }

    @Override
    public List<String> fetchData()
            throws DataSourceAccessException {

        try {

            if (sourceName.contains("Hotels")
                    && Math.random() < 0.2) {

                throw new AuthenticationFailedException(
                        "Hotel API authentication failed! Did someone forget the password again?");
            }

            List<String> data = new ArrayList<>();

            data.add("Hotel: Yak & Yeti, Guest: Ram Thapa, NP");
            data.add("Hotel: Annapurna, Guest: Alice Smith, AU");

            return data;

        } catch (AuthenticationFailedException e) {

            throw new DataSourceAccessException(
                    "Could not access hotel registration data.", e);
        }
    }

    public static void main(String[] args) {

        HotelRegistrationsDataSource hotel =
                new HotelRegistrationsDataSource();

        try {

            List<String> data = hotel.fetchData();

            System.out.println("Data fetched successfully:");

            for (String record : data) {
                System.out.println(record);
            }

        } catch (DataSourceAccessException e) {

            System.out.println(
                    "Error fetching data: "
                            + e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Reason: "
                                + e.getCause().getMessage());
            }
        }
    }
}