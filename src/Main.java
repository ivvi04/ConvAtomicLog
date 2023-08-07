import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    static Charset charset = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {
        Scanner path = new Scanner(System.in);

        File oldLog = new File(path.nextLine());

        StringBuilder oldContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(oldLog, charset))) {

            // Зачитываем данные из старого лога и производим замену
            String newContent = readAndReplaceContent(oldContent, reader);
            // Формируем новый лог и сохраняем туда
            if (newContent != null) {
                saveNewLog(genNewFile(oldLog), newContent);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error! File Not Found!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    private static String readAndReplaceContent(StringBuilder oldContent, BufferedReader reader) {
        String newContent = null;
        try {
            String line = reader.readLine();
            while (line != null) {
                oldContent.append(line).append("\n");
                line = reader.readLine();
            }
            newContent = oldContent.toString().replace(',', ';')
                    .replace('.', ',')
                    .replace(';', '.')
                    .replaceAll("Коэф,", "Коэф.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newContent;
    }

    private static File genNewFile(File oldLog) {
        String fileName = oldLog.getName().substring(0, oldLog.getName().indexOf(".")) + "_new" +
                oldLog.getName().substring(oldLog.getName().indexOf("."), oldLog.getName().length());
        return new File(oldLog.getParent() + "\\" + fileName);
    }

    private static void saveNewLog(File log, String content) {
        try (FileWriter writer = new FileWriter(log, charset)) {
            writer.write(content);
            System.out.println("The conversion was successful!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}