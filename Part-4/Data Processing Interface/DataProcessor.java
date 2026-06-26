import java.util.ArrayList;
import java.util.List;

class DataProcessingException extends Exception {

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

public interface DataProcessor {

    List<String> process(List<String> rawData)
            throws DataProcessingException;

    static void main(String[] args) {

        DataProcessor processor = new TestProcessor();

        List<String> rawData = new ArrayList<>();
        rawData.add("Visitor: John Doe, USA");
        rawData.add("Visitor: Emily White, UK");

        try {

            List<String> processed = processor.process(rawData);

            System.out.println("Processed Data:");

            for (String data : processed) {
                System.out.println(data);
            }

        } catch (DataProcessingException e) {
            System.out.println(
                    "Processing Error: " + e.getMessage());
        }
    }
}

class TestProcessor implements DataProcessor {

    @Override
    public List<String> process(List<String> rawData)
            throws DataProcessingException {

        if (rawData == null) {
            throw new DataProcessingException(
                    "Raw data cannot be null.");
        }

        List<String> processedData = new ArrayList<>();

        for (String data : rawData) {
            processedData.add(data.toUpperCase());
        }

        return processedData;
    }
}