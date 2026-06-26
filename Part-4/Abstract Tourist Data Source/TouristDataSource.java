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

public abstract class TouristDataSource {

    protected String sourceName;

    public TouristDataSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public abstract List<String> fetchData()
            throws DataSourceAccessException;

    public static void main(String[] args) {
        try {
            TouristDataSource source =
                    new TestDataSource("Test Tourism Source");

            System.out.println("Source: " + source.sourceName);

            List<String> data = source.fetchData();

            for (String visitor : data) {
                System.out.println(visitor);
            }

        } catch (DataSourceAccessException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class TestDataSource extends TouristDataSource {

    public TestDataSource(String sourceName) {
        super(sourceName);
    }

    @Override
    public List<String> fetchData() throws DataSourceAccessException {
        List<String> data = new ArrayList<>();

        data.add("Visitor: John Doe, USA");
        data.add("Visitor: Emily White, UK");

        return data;
    }
}