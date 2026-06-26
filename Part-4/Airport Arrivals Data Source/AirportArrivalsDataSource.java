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

class ConnectionLostException extends Exception {

    public ConnectionLostException(String message) {
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

public class AirportArrivalsDataSource extends TouristDataSource {

    public AirportArrivalsDataSource() {
        super("Tribhuvan Airport Arrivals");
    }

       @Override
    public List<String> fetchData()
            throws DataSourceAccessException {
    
        try {
    
            if (sourceName.contains("Tribhuvan")
                    && Math.random() < 0.3) {
    
                throw new ConnectionLostException(
                    "Airport data connection lost! Maybe a pigeon sat on the antenna?");
            }
    
            List<String> data = new ArrayList<>();
    
            data.add("Visitor: John Doe, USA");
            data.add("Visitor: Emily White, UK");
    
            return data;
    
        } catch (ConnectionLostException e) {
    
            throw new DataSourceAccessException(
                "Could not access airport data.", e);
        }
    }

    public static void main(String[] args) {

        AirportArrivalsDataSource airport =
                new AirportArrivalsDataSource();

        try {
            List<String> data = airport.fetchData();

            System.out.println("Data fetched successfully:");

            for (String visitor : data) {
                System.out.println(visitor);
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