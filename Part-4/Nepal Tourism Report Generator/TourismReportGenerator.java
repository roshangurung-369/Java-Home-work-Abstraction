import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

class AuthenticationFailedException extends Exception {

    public AuthenticationFailedException(String message) {
        super(message);
    }
}

class DataProcessingException extends Exception {

    public DataProcessingException(String message) {
        super(message);
    }
}

class EmptyDataException extends DataProcessingException {

    public EmptyDataException(String message) {
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

class AirportArrivalsDataSource extends TouristDataSource {

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
                    "Airport access failed.", e);
        }
    }
}

class HotelRegistrationsDataSource extends TouristDataSource {

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
                    "Hotel access failed.", e);
        }
    }
}

interface DataProcessor {

    List<String> process(List<String> rawData)
            throws DataProcessingException;
}

class UniqueVisitorCounter implements DataProcessor {

    @Override
    public List<String> process(List<String> rawData)
            throws DataProcessingException {

        if (rawData.isEmpty()) {

            throw new EmptyDataException(
                    "No raw data to process! Did all tourists go missing?");
        }

        Set<String> uniqueVisitors = new HashSet<>();

        for (String data : rawData) {

            String name = "";

            if (data.contains("Visitor:")) {

                name = data.substring(
                        data.indexOf("Visitor:") + 9,
                        data.lastIndexOf(",")).trim();
            }

            else if (data.contains("Guest:")) {

                name = data.substring(
                        data.indexOf("Guest:") + 7,
                        data.lastIndexOf(",")).trim();
            }

            uniqueVisitors.add(name);
        }

        List<String> result = new ArrayList<>();
        result.add("Unique Visitors: "
                + uniqueVisitors.size());

        return result;
    }
}

public class TourismReportGenerator {

    public static void generateOverallReport(
            List<TouristDataSource> dataSources,
            DataProcessor processor) {

        System.out.println(
                "Generating overall tourism report...");

        for (TouristDataSource dataSource
                : dataSources) {

            List<String> rawData;

            try {

                rawData = dataSource.fetchData();

            } catch (DataSourceAccessException e) {

                System.out.println(
                        "Could not fetch data from "
                                + dataSource.sourceName
                                + ": "
                                + e.getMessage()
                                + ". Skipping this source.");

                if (e.getCause() != null) {

                    System.out.println(
                            "Reason: "
                                    + e.getCause()
                                    .getMessage());
                }

                continue;

            } finally {

                System.out.println(
                        "Data handling from "
                                + dataSource.sourceName
                                + " completed.");
            }

            try {

                List<String> processed =
                        processor.process(rawData);

                for (String result
                        : processed) {

                    System.out.println(result);
                }

            } catch (
                    DataProcessingException e) {

                System.out.println(
                        "Error processing data from "
                                + dataSource.sourceName
                                + ": "
                                + e.getMessage()
                                + ". Skipping this data.");
            }
        }
    }

    public static void main(String[] args) {

        List<TouristDataSource> dataSources =
                new ArrayList<>();

        dataSources.add(
                new AirportArrivalsDataSource());

        dataSources.add(
                new HotelRegistrationsDataSource());

        DataProcessor processor =
                new UniqueVisitorCounter();

        generateOverallReport(
                dataSources,
                processor);
    }
}