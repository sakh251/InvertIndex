package search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class InvertedIndex implements index_search{

    List<String> stopwords = Arrays.asList("a", "able", "about",
            "across", "after", "all", "almost", "also", "am", "among", "an",
            "and", "any", "are", "as", "at", "be", "because", "been", "but",
            "by", "can", "cannot", "could", "dear", "did", "do", "does",
            "either", "else", "ever", "every", "for", "from", "get", "got",
            "had", "has", "have", "he", "her", "hers", "him", "his", "how",
            "however", "i", "if", "in", "into", "is", "it", "its", "just",
            "least", "let", "like", "likely", "may", "me", "might", "most",
            "must", "my", "neither", "no", "nor", "not", "of", "off", "often",
            "on", "only", "or", "other", "our", "own", "rather", "said", "say",
            "says", "she", "should", "since", "so", "some", "than", "that",
            "the", "their", "them", "then", "there", "these", "they", "this",
            "tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
            "what", "when", "where", "which", "while", "who", "whom", "why",
            "will", "with", "would", "yet", "you", "your");

    Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
    List<String> files = new ArrayList<String>();


    public void indexFiles (File file)  throws IOException {
        int fileno = files.indexOf(file.getPath());
        if (fileno == -1) {
            files.add(file.getPath());
            fileno = files.size() - 1;
        }

        int pos = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        for (String line = reader.readLine(); line != null; line = reader
                .readLine()) {
            for (String _word : line.split("\\W+")) {
                String word = _word.toLowerCase();
                pos++;
                if (stopwords.contains(word))
                    continue;
                List<Tuple> idx = index.get(word);
                if (idx == null) {
                    idx = new LinkedList<Tuple>();
                    index.put(word, idx);
                }
                idx.add(new Tuple(fileno, pos));
            }
        }
        System.out.println("indexed " + file.getPath() + " " + pos + " words");
    }

    public List<Result> search(List<String> words) {

        Map <String, Double> foundwords = new HashMap<String, Double>();
        for (String f : files){
            foundwords.put(f,0.0);
        }
        Integer totalWords = words.size();
        for (String _word : words) {
            String word = _word.toLowerCase();
            List<Tuple> idx = index.get(word);
            List<String> thisRound = new ArrayList<String>();
            if (idx != null) {
                for (Tuple t : idx) {
                    String cur_file = files.get(t.fileno);
                    if (!thisRound.contains(cur_file)){
                        Double count = foundwords.get(cur_file)/100;
                        foundwords.put(cur_file, ((count + 1.0/totalWords)) * 100);
                        thisRound.add(cur_file);
                    }
                }
            }
            System.out.println("");
        }
        List<Result> result = new ArrayList<Result>();

        foundwords.forEach((k,v) -> result.add(new Result(k,v)));
        result.sort(Comparator.comparingDouble(Result::getRating).reversed());

        return result;
    }

    public class Result
    {
        public String filepath;
        public double rate;

        public Result(String filepath, double rate) {
            this.filepath = filepath;
            this.rate = rate;
        }

        public double getRating() {
            return this.rate;
        }
        public String toString()
        {
            return this.filepath + ":" + this.rate;
        }
    }

    private class Tuple {
        private int fileno;
        private int position;

        public Tuple(int fileno, int position) {
            this.fileno = fileno;
            this.position = position;
        }
    }
}
