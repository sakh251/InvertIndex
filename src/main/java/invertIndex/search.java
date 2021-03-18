package invertIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class search {

    public static void main(String[] args) throws IOException {
        boolean condition = true;
        index_search idx = new InvertedIndex();
        try {
            System.out.println("Indexing is started...");

            List<File> filesInFolder = Files.walk(Paths.get(args[0]))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            for (File file : filesInFolder) {
                idx.indexFiles(file);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        while(condition == true) {
            //do something
            System.out.println("search>");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();

            if (name.equals("exit")) {
                break;
            }
            List<InvertedIndex.Result> result  = idx.search(Arrays.asList(name.split(" ")));
            result.forEach(i -> System.out.println("File:" + i.filepath + " Rate:" + i.rate));
        }
    }
}
