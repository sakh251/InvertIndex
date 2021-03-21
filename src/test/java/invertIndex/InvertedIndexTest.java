package invertIndex;




import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import search.InvertedIndex;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InvertedIndexTest {

    private static InvertedIndex idx = new InvertedIndex();


    @BeforeAll
    public static void test_index() throws IOException {

        String text1 = "here's an \"edit \" item on the Run menu, and on the pull-down to the left of the two green \"run\" and \"debug\" arrows on the toolbar. In that panel, you create a configuration with the \"+\" button in the top left, and then you can choose the Class containing main(), add VM parameters and command-line args, specify the working directory and any environment variables.\n" +
                "\n" +
                "There are other options there as well: code coverage, logging, build, JRE, etc. item item salam";
        String text2 = "here's an \"edit configurations\" item on the Run menu, and on the pull-down to the left of the two green \"run\" and \"debug\" arrows on the toolbar.\n" +
                "\n" +
                "item item item";
        Files.write(Paths.get("1.txt"), text1.getBytes());
        Files.write(Paths.get("2.txt"), text2.getBytes());

        List<File> filesInFolder = Files.list(Paths.get("."))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .map(Path::toFile)
                .collect(Collectors.toList());
        assert (filesInFolder.size() >=1);
        for (File file : filesInFolder) {
            idx.indexFiles(file);
        }
    }

    @Test
    public void test_query1(){
        String test_query1 = new String("salam item");
        List<InvertedIndex.Result> result  = idx.search(Arrays.asList(test_query1.split(" ")));
        assert (result.size() >= 1) : "Result is null";
        assert (result.get(0).rate == 100): "Invalid rate for query 1 in Doc 1";
        assert (result.get(1).rate == 50) : "Invalid rate for query 1 in Doc 2";
    }

    @Test
    public void test_query2(){
        String test_query2 = new String("configurations item");
        List<InvertedIndex.Result> result  = idx.search(Arrays.asList(test_query2.split(" ")));
        assert (result.size() >= 1) : "Result is null";
        assert (result.get(0).rate == 100): "Invalid rate for query 2 in Doc 2";
        assert (result.get(1).rate == 50) : "Invalid rate for query 2 in Doc 1";

    }

    private void assertEquals(boolean b) {
    }

}