package invertIndex;




import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


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

    private static String testDocPath = new  String("C:\\Users\\salman\\IdeaProjects\\test\\src\\main\\resources\\");

    @BeforeAll
    public static void test_index() throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(testDocPath))
                .filter(Files::isRegularFile)
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