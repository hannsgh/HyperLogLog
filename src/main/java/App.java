
import java.io.*;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws Exception {

        HyperLogLog hyperLogLog = new HyperLogLog(16);
        final long before = System.currentTimeMillis();

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/properties.properties");
            property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        String paths = property.getProperty("paths");
        System.out.println(" Идет анализ файла : " + property.getProperty("paths"));
        try (BufferedReader br = new BufferedReader(new FileReader(new File(paths)))) {

            String line = br.readLine();
            String[] sArr = null;

            while (line != null) {
                sArr = line.split("/n");
                for (String s : sArr) {
                    hyperLogLog.update(s);
                }
                line = br.readLine();


            }
        }
        final long after = System.currentTimeMillis();

        System.out.println("Вероятное количество уникальных значений ~ " + (int) hyperLogLog.get());
        System.out.println("Время работы приложения : " + (after - before) / 1000);

    }
}
