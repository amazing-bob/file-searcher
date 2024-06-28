import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FileSearcher {

    public static SearchResult searchFiles(String folderPath, String searchString) {
        if (folderPath == null || searchString == null) {
            System.out.println("Usage: java FileSearcher <folder-path> <search-string>");
            return null;
        }
        System.out.println("folderPath: " + folderPath);
        System.out.println("searchString: " + searchString);

        List<String> targetFiles = new ArrayList<>();
        List<String> matchedFiles = new ArrayList<>();
        List<String> unmatchedFiles = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(file)) {
                        targetFiles.add(file.toString());
                        try {
                            List<String> lines = Files.readAllLines(file);
                            for (String line : lines) {
                                if (line.contains(searchString)) {
                                    matchedFiles.add(file.toString());
                                    break;
                                }
                            }
                        } catch (MalformedInputException e) {
                            System.err.println("MalformedInputException: Cannot read file " + file + " due to encoding issues.");
                        } catch (IOException e) {
                            System.err.println("IOException: Failed to read file " + file);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("Failed to read file: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });

            unmatchedFiles.addAll(targetFiles);
            unmatchedFiles.removeAll(matchedFiles);
            return new SearchResult(targetFiles, matchedFiles, unmatchedFiles);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class SearchResult {
        private List<String> targetFiles;
        private List<String> matchedFiles;
        private List<String> unmatchedFiles;

        public SearchResult(List<String> targetFiles, List<String> matchedFiles, List<String> unmatchedFiles) {
            this.targetFiles = targetFiles;
            this.matchedFiles = matchedFiles;
            this.unmatchedFiles = unmatchedFiles;
        }

        public List<String> getTargetFiles() {
            return targetFiles;
        }

        public List<String> getMatchedFiles() {
            return matchedFiles;
        }

        public List<String> getUnmatchedFiles() {
            return unmatchedFiles;
        }
    }
}