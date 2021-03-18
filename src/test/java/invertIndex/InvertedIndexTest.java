package invertIndex;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InvertedIndexTest {

    private InvertedIndex idx = new InvertedIndex();

    String testDocPath = new  String("C:\\Users\\salman\\IdeaProjects\\test\\src\\main\\resources\\");
    @Rule
    public TestName testName = new TestName();


    public void invertedIndexTest(){

    }

    @Before
    public void test_index() throws IOException {

        List<File> filesInFolder = Files.walk(Paths.get(testDocPath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        assert (filesInFolder.size() >=1);
        for (File file : filesInFolder) {
            this.idx.indexFiles(file);
        }

    }
    @Test
    public void test_search(){
        String test_query1 = new String("salam item");
        String test_query2 = new String("configuration item");


        List<InvertedIndex.Result> result  = this.idx.search(Arrays.asList(test_query1.split(" ")));
        assert (result.size() >= 1);
        assert (result.get(0).rate == 100);
        assert (result.get(1).rate == 50);
        result  = idx.search(Arrays.asList(test_query2.split(" ")));

    }

    private void assertEquals(boolean b) {
    }

}