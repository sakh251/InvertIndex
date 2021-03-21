package search;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface index_search {
    public void indexFiles(File file) throws IOException;
    public List<InvertedIndex.Result> search(List<String> words);
}
