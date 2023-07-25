import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    static Charset charset = Charset.forName("windows-1251");

    public static void main(String[] args) throws IOException {
        Scanner path = new Scanner(System.in);

        File oldLog = new File(path.nextLine());
        String newFileName = oldLog.getName();
        newFileName = newFileName.substring(0, newFileName.indexOf(".")) + "_new" +
                newFileName.substring(newFileName.indexOf("."), newFileName.length());
        File newLog = new File(oldLog.getParent() + "\\" + newFileName);

        StringBuilder oldContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(oldLog, charset))) {
            String line = reader.readLine();
            while (line != null) {
                oldContent.append(line).append("\n");
                line = reader.readLine();
            }
            String content = oldContent.toString();
            String newContent = content.replace(',', ';')
                    .replace('.', ',')
                    .replace(';', '.')
                    .replaceAll("Коэф,", "Коэф.");
            try (FileWriter writer = new FileWriter(newLog, charset)) {
                writer.write(newContent);
            }

            System.out.println("The conversion was successful!");
        } catch (FileNotFoundException e) {
            System.out.println("Error! File Not Found!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}