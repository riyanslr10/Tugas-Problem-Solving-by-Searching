package Tugas;
import java.util.*;

public class PengirimanBarang {

    static class Graph {
        private Map<String, Map<String, Integer>> graph = new HashMap<>();

        public void tambahJarak(String kotaAsal, String kotaTujuan, int jarak) {
            graph.putIfAbsent(kotaAsal, new HashMap<>());
            graph.get(kotaAsal).put(kotaTujuan, jarak);
            graph.putIfAbsent(kotaTujuan, new HashMap<>());
            graph.get(kotaTujuan).put(kotaAsal, jarak);
        }

        public Map<String, Map<String, Integer>> getGraph() {
            return graph;
        }
    }

    public static List<String> cariRuteTerpendek(Map<String, Map<String, Integer>> graph, String kotaAwal) {
        Map<String, Integer> jarakMin = new HashMap<>();
        Map<String, String> kotaSebelumnya = new HashMap<>();
        Set<String> belumDikunjungi = new HashSet<>();

        for (String kota : graph.keySet()) {
            jarakMin.put(kota, Integer.MAX_VALUE);
            kotaSebelumnya.put(kota, null);
            belumDikunjungi.add(kota);
        }

        jarakMin.put(kotaAwal, 0);

        while (!belumDikunjungi.isEmpty()) {
            String kotaSekarang = null;
            for (String kota : belumDikunjungi) {
                if (kotaSekarang == null || jarakMin.get(kota) < jarakMin.get(kotaSekarang)) {
                    kotaSekarang = kota;
                }
            }

            belumDikunjungi.remove(kotaSekarang);

            for (String tetangga : graph.get(kotaSekarang).keySet()) {
                int jarakBaru = jarakMin.get(kotaSekarang) + graph.get(kotaSekarang).get(tetangga);
                if (jarakBaru < jarakMin.get(tetangga)) {
                    jarakMin.put(tetangga, jarakBaru);
                    kotaSebelumnya.put(tetangga, kotaSekarang);
                }
            }
        }

        List<String> ruteTerpendek = new ArrayList<>();
        String kotaSekarang = kotaAwal;
        while (kotaSekarang != null) {
            ruteTerpendek.add(kotaSekarang);
            kotaSekarang = kotaSebelumnya.get(kotaSekarang);
        }

        Collections.reverse(ruteTerpendek);
        return ruteTerpendek.size() == graph.size() ? ruteTerpendek : null;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.tambahJarak("Kota A", "Kota B", 10);
        graph.tambahJarak("Kota A", "Kota C", 15);
        graph.tambahJarak("Kota B", "Kota D", 12);
        graph.tambahJarak("Kota C", "Kota D", 10);
        graph.tambahJarak("Kota D", "Kota E", 5);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kota Awal: ");
        String kotaAwal = scanner.nextLine();

        List<String> ruteTerpendek = cariRuteTerpendek(graph.getGraph(), kotaAwal);

        if (ruteTerpendek != null) {
            System.out.println("Rute terpendek adalah: " + ruteTerpendek);
        } else {
            System.out.println("Tidak ada rute yang meliputi semua kota.");
        }
    }
}
