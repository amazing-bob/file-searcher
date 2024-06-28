public class Main {
    public static void main(String[] args) {
        System.out.println("START search files!!");

        String folderPath = "C:\\wings3-Application\\workspace\\wings-application\\wings-application-bos\\src\\main\\webapp\\WEB-INF\\jsp\\biz\\bo";
        String searchString = "/js/comm/ws.comm.util.js";
        FileSearcher.SearchResult result = FileSearcher.searchFiles(folderPath, searchString);

        System.out.println("targetFile Count: " + result.getTargetFiles().size());
        System.out.println("matchedFile Count: " + result.getMatchedFiles().size());
        System.out.println("unmatchedFile Count: " + result.getUnmatchedFiles().size());

//        System.out.println("-------------------- targetFiles --------------------");
//        for (String targetFile : result.getTargetFiles()) {
//            System.out.println(targetFile);
//        }

        System.out.println("-------------------- matchedFiles --------------------");
        for (String matchedFile : result.getMatchedFiles()) {
            System.out.println(matchedFile);
        }

//        System.out.println("-------------------- unmatchedFiles --------------------");
//        for (String unmatchedFile : result.getUnmatchedFiles()) {
//            System.out.println(unmatchedFile);
//        }

    }
}