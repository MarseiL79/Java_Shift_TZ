import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        FileManager fileManager = new FileManager();

        ArrayList<String> files = new ArrayList<>();

        boolean appendInFile = false;
        boolean shortStats   = false;
        boolean fullStats    = false;

        String result_path = "";
        String prefix = "";

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-o" -> {
                    result_path = args[i + 1];
                    ++i;
                }
                case "-p" -> {
                    prefix = args[i + 1];
                    ++i;
                }
                case "-a" -> {
                    appendInFile = true;
                }
                case "-f" -> {
                    fullStats = true;
                }
                case "-s" -> {
                    shortStats = true;
                }
                default -> files.add(args[i]);
            }
        }

        fileManager.processFiles(files, result_path, prefix, appendInFile, shortStats, fullStats);
    }
}