package org.rosettacode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class InvertedIndex {

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

    public void indexFile(File file) throws IOException {
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

    public void search(List<String> words) {
        Map <String,Integer> answer = new HashMap<String, Integer>();
        Map <String,Integer> allwords = new HashMap<String, Integer>();
//
        for (String f : files){
            allwords.put(f,0);

        }
//        for (String k : allwords.keySet()) {
//            if (idx.contains(k)){
//                Integer count = allwords.get(k);
//                allwords.put(k, count + 1);
//
//            }
//        }


        for (String _word : words) {
            String word = _word.toLowerCase();
            List<Tuple> idx = index.get(word);
            List<String> thisRound = new ArrayList<String>();
            if (idx != null) {
                for (Tuple t : idx) {
                    String cur_file = files.get(t.fileno);
                    Integer repeated = answer.get(files.get(t.fileno));
                    if (!thisRound.contains(cur_file)){
                        Integer g = allwords.get(cur_file);
                        allwords.put(cur_file, g + 1 );
                        thisRound.add(cur_file);
                    }

                    if (repeated == null){
                        answer.put(files.get(t.fileno), 1);                    }
                    else {
                        answer.put(files.get(t.fileno) , repeated + 1);
                    }


                }
            }

//            for (String f : answer) {
//                System.out.print(" " + f);
//            }
            System.out.println("");
        }
        System.out.println(answer);
        System.out.println(allwords);
    }

    public static void main(String[] args) throws IOException {
        // Enter data using BufferReader
        class Movie
        {
            public String name;
            public double rate;

            public Movie(String name, double rate) {
                this.name = name;
                this.rate = rate;
            }

            public double getRating() {
                return this.rate;
            }
            public String toString()
            {
                return this.name + ":" + this.rate;
            }
        }


        List<Movie> movies = Arrays.asList(
                new Movie("Lord of the rings", 8.8),
                new Movie("Back to the future", 8.5),
                new Movie("Carlito's way", 7.9),
                new Movie("Pulp fiction", 8.9));

        movies.sort(Comparator.comparingDouble(Movie::getRating)
                .reversed());

        movies.forEach(System.out::println);

        boolean condition = true;
        InvertedIndex idx = new InvertedIndex();

        try {
            System.out.println("Indexing is started...");

            List<File> filesInFolder = Files.walk(Paths.get(args[0]))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            for (File file : filesInFolder) {
                idx.indexFile(file);
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

            idx.search(Arrays.asList(name.split(" ")));

            }
//      }
//
//
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
