package homework_11_inc;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PebbleFetchingCompetition {

    private final int nRounds;
    private final int nPlayers;
    private Thread[] participants;
    private static final HashMap<String, Integer> results = new HashMap<>();
    private static volatile boolean startLocked = true;
    private static final Object pebble = new Object();

    static class Participant extends Thread {

        private final String id;
        private final HashMap<String, Integer> results;

        public Participant(String _id, HashMap<String, Integer> res) {
            id = _id;
            results = res;
            results.put(id, 0);
        }

        @Override
        public String toString() {
            return String.format(
                    "%s has grabbed the pebble %d times.",
                    id, results.get(id));
        }

        @Override
        public void run() {
            while (true) {
                synchronized (pebble) {
                    try {
                        while (startLocked) {
                            pebble.wait();
                        }
                        results.put(id, results.get(id) + 1);
                        startLocked = true;
                        pebble.notifyAll();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }
    }

    public PebbleFetchingCompetition(Integer numPlayers, Integer numRounds) {
        nRounds = numRounds;
        nPlayers = numPlayers;
    }

    private static HashMap<String, Integer> parseArgs(String[] args) {
        if(args.length != 4) {
            throw new IllegalArgumentException("Incorrect number of arguments");
        }

        HashMap<String, Integer> runOpts = new HashMap<>();

        for(int i=0; i < 4; i += 2) {
            if(args[i].equals("-nRounds") || args[i].equals("-nPlayers")) {
                if(Pattern.matches("^\\d+$", args[i+1]) &&
                        Integer.parseInt(args[i+1]) > 0) {
                    runOpts.put(args[i], Integer.parseInt(args[i+1]));
                }
            } else {
                throw new IllegalArgumentException(
                        String.format(
                                "Arg '%s' has to be an integer greater than 0" +
                                        ". Received: %s",
                                args[i], args[i+1]));
            }
        }
        return runOpts;
    }

    private void createParticipants() {
        participants = new Thread[nPlayers+1];

        for(int i=0; i < nPlayers; i++) {
            participants[i] =
                    new Participant("student_" + i, results);
            participants[i].start();
        }

        participants[participants.length-1] =
                new Participant("teacher", results);
        participants[participants.length-1].start();
    }

    private void displayResults() {
        for(String key : results.keySet()) {
            System.out.printf(
                    "%s grabbed the pebble %d times.\n",
                    key.toUpperCase(), results.get(key));
        }
    }

    public void play() {
        createParticipants();

        for(int round = 1; round <= nRounds; round++) {
            synchronized (pebble) {
                try {
                    startLocked = false;
                    pebble.notifyAll();
                    while (!startLocked) {
                        pebble.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for(Thread p : participants) p.interrupt();
        displayResults();
    }

    public static void main(String[] args) {
        HashMap<String, Integer> opts = parseArgs(args);
        Integer numPlayers = opts.get("-nPlayers");
        Integer numRounds = opts.get("-nRounds");
        new PebbleFetchingCompetition(numPlayers, numRounds).play();
    }
}
