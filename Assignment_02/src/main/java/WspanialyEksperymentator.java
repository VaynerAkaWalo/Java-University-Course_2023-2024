import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.IntStream;

public class WspanialyEksperymentator implements Eksperymentator{

    private KostkaDoGry kostkaDoGry;
    private long time = 0;

    @Override
    public void użyjKostki(KostkaDoGry kostka) {
        kostkaDoGry = kostka;
    }

    @Override
    public void czasJednegoEksperymentu(long czasEksperymentu) {
        time = czasEksperymentu;
    }

    @Override
    public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek) {
        Map<Integer, Double> map = new HashMap<>();
        int counter = 0;

        Instant end = Instant.now().plus(time, ChronoUnit.MILLIS);
        while (Instant.now().isBefore(end)) {
            counter++;
            int sum = IntStream.range(0, liczbaKostek).map(x -> kostkaDoGry.rzut()).sum();
            map.merge(sum, 1d, Double::sum);
        }

        for (Integer i : map.keySet()) {
            map.put(i, map.get(i) / counter);
        }

        return map;
    }

    @Override
    public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja) {
        int counter = 0;
        int total = 0;
        int[] array = new int[sekwencja.size()];

        Instant end = Instant.now().plus(time, ChronoUnit.MILLIS);
        while (Instant.now().isBefore(end)) {
            for (int i = 0; i < sekwencja.size(); i++) {
                array[i] = kostkaDoGry.rzut();
            }
            if (equal(sekwencja, array)) counter++;
            total++;
        }

        return (double) counter / total;
    }

    @Override
    public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka) {
        int counter = 0;
        int total = 0;
        Set<Integer> set = new HashSet<>();

        Instant end = Instant.now().plus(time, ChronoUnit.MILLIS);
        while (Instant.now().isBefore(end)) {
            for (int i = 0; i < oczka.size(); i++) {
                set.add(kostkaDoGry.rzut());
            }
            if (set.size() == oczka.size() && set.containsAll(oczka)) counter++;
            total++;
            set.clear();
        }

        return (double) counter / total;
    }

    private boolean equal(List<Integer> list, int[] array) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != array[i]) return false;
        }
        return true;
    }

}
