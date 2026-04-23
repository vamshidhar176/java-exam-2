import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class ActivityAggregator {
    private ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

    public void add(String user) {
        map.computeIfAbsent(user, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public Map<String, AtomicInteger> get() {
        return map;
    }
}

class LogProcessor implements Runnable {
    private File file;
    private ActivityAggregator agg;

    LogProcessor(File f, ActivityAggregator a) {
        file = f;
        agg = a;
    }

    public void run() {
        if (file == null || !file.exists() || file.length() == 0) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.trim().split(" ");
                if (p.length != 3) continue;

                String user = p[1];
                agg.add(user);
            }

        } catch (Exception e) {
            System.out.println("Error reading " + file.getName());
        }
    }
}

class ReportGenerator {
    public static void generate(Map<String, AtomicInteger> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("report.txt"))) {

            bw.write("===== USER ACTIVITY REPORT =====\n");

            if (data.isEmpty()) {
                bw.write("No data available\n");
                return;
            }

            data.entrySet().stream()
                .sorted((a, b) -> b.getValue().get() - a.getValue().get())
                .forEach(e -> {
                    try {
                        bw.write(e.getKey() + ": " + e.getValue().get() + " actions\n");
                    } catch (Exception ex) {}
                });

        } catch (Exception e) {
            System.out.println("Write error");
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        File dir = new File("logs");

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory not found");
            return;
        }

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files in directory");
            return;
        }

        ActivityAggregator agg = new ActivityAggregator();
        List<Thread> threads = new ArrayList<>();

        for (File f : files) {
            if (f.isFile()) {
                Thread t = new Thread(new LogProcessor(f, agg));
                threads.add(t);
                t.start();
            }
        }

        for (Thread t : threads) t.join();

        ReportGenerator.generate(agg.get());

        System.out.println("Report generated");
    }
}