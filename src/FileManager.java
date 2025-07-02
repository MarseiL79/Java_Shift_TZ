import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class FileManager {
    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter strWriter;
    private final NumStats<BigInteger> intStats   = new NumStats<>();
    private final NumStats<BigDecimal> floatStats = new NumStats<>();
    private final StringStats          strStats   = new StringStats();


    public void processFiles(
            ArrayList<String> files,
            String result_path, String prefix, boolean appendInFile, boolean shortStats, boolean fullStats) {

        if(!new File(result_path).exists()) {
            System.out.println("Указанный путь не найден, запись в папку проекта.");
            result_path = "";
        }

        for (String file : files) {
            readFile(file, result_path, prefix, appendInFile);
        }
        closeWriters();
        printStats(shortStats, fullStats);
    }

    private void readFile(String file, String result_path, String prefix, boolean appendInFile) {

        if(!result_path.isEmpty()) { result_path += "/"; }


        System.out.println("#### " + file + " ####");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                try {
                    new BigInteger(line);
                    writeLine(getIntWriter(result_path, prefix, appendInFile), line);
                    intStats.add(new BigInteger(line));
                }
                catch (NumberFormatException e1) {
                    try {
                        new BigDecimal(line);
                        writeLine(getFloatWriter(result_path, prefix, appendInFile), line);
                        floatStats.add(new BigDecimal(line));
                    } catch (NumberFormatException e2) {
                        writeLine(getStrWriter(result_path, prefix, appendInFile), line);
                        strStats.add(line);
                    }
                }
            }
            System.out.println();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedWriter getIntWriter(String path, String prefix, boolean appendInFile) throws IOException {
        if (intWriter == null) {
            intWriter = new BufferedWriter(
                    new FileWriter(path + prefix + "integers.txt", appendInFile)
            );
        }
        return intWriter;
    }

    private BufferedWriter getFloatWriter(String path, String prefix, boolean appendInFile) throws IOException {
        if (floatWriter == null) {
            floatWriter = new BufferedWriter(
                    new FileWriter(path + prefix + "floats.txt", appendInFile)
            );
        }
        return floatWriter;
    }

    private BufferedWriter getStrWriter(String path, String prefix, boolean appendInFile) throws IOException {
        if (strWriter == null) {
            strWriter = new BufferedWriter(
                    new FileWriter(path + prefix + "strings.txt", appendInFile)
            );
        }
        return strWriter;
    }

    private void writeLine(BufferedWriter w, String line) throws IOException {
        w.write(line);
        w.newLine();
    }

    private void closeWriters() {
        try { if (intWriter   != null) intWriter.close();   } catch (IOException ignored) {}
        try { if (floatWriter != null) floatWriter.close(); } catch (IOException ignored) {}
        try { if (strWriter   != null) strWriter.close();   } catch (IOException ignored) {}
        intWriter   = null;
        floatWriter = null;
        strWriter   = null;
    }

    private void printStats(boolean shortStats, boolean fullStats) {
        if (fullStats) {
            System.out.println("\n=== Полная статистика ===");
            System.out.printf("Integers: count=%d, min=%s, max=%s, sum=%s, avg=%s\n",
                    intStats.getCount(),
                    intStats.getMin(),
                    intStats.getMax(),
                    intStats.getSum(),
                    intStats.getAverage());
            System.out.printf("Floats:   count=%d, min=%s, max=%s, sum=%s, avg=%s\n",
                    floatStats.getCount(),
                    floatStats.getMin(),
                    floatStats.getMax(),
                    floatStats.getSum(),
                    floatStats.getAverage());
            System.out.printf("Strings:  count=%d, minLen=%d, maxLen=%d\n",
                    strStats.getCount(),
                    strStats.getMinLen(),
                    strStats.getMaxLen());
        }
        else if(shortStats) {
            System.out.println("\n=== Краткая статистика ===");
            System.out.printf("Integers: %d\n", intStats.getCount());
            System.out.printf("Floats:   %d\n", floatStats.getCount());
            System.out.printf("Strings:  %d\n", strStats.getCount());
        }
    }
}
