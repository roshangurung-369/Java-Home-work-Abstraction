import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DataProcessingException extends Exception {

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

class EmptyDataException extends DataProcessingException {

    public EmptyDataException(String message) {
        super(message);
    }
}

interface DataProcessor {

    List<String> process(List<String> rawData)
            throws DataProcessingException;
}

public class UniqueVisitorCounter implements DataProcessor {

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
                        data.lastIndexOf(",")
                ).trim();
            }

            else if (data.contains("Guest:")) {
                name = data.substring(
                        data.indexOf("Guest:") + 7,
                        data.lastIndexOf(",")
                ).trim();
            }

            uniqueVisitors.add(name);
        }

        List<String> result = new ArrayList<>();
        result.add("Unique Visitors: " + uniqueVisitors.size());

        return result;
    }

    public static void main(String[] args) {

        UniqueVisitorCounter counter =
                new UniqueVisitorCounter();

        List<String> emptyList = new ArrayList<>();

        try {
            counter.process(emptyList);
        }

        catch (DataProcessingException e) {
            System.out.println(
                    "Error: " + e.getMessage());
        }

        List<String> data = new ArrayList<>();

        data.add("Visitor: John Doe, USA");
        data.add("Visitor: Emily White, UK");
        data.add("Guest: John Doe, USA");
        data.add("Guest: Ram Thapa, NP");

        try {

            List<String> result =
                    counter.process(data);

            for (String s : result) {
                System.out.println(s);
            }

        } catch (DataProcessingException e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }
    }
}