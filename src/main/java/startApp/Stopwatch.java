package startApp;

public class Stopwatch {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            stopTime = System.currentTimeMillis();
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public String getElapsedTimeFormatted() {
        long elapsedMillis;
        if (running) {
            elapsedMillis = System.currentTimeMillis() - startTime;
        } else {
            elapsedMillis = stopTime - startTime;
        }

        if (elapsedMillis == 0) {
            return "0:00:000";
        }

        long minutes = elapsedMillis / (60 * 1000);
        long seconds = (elapsedMillis % (60 * 1000)) / 1000;
        long milliseconds = elapsedMillis % 1000;

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }

    public void reset() {
        stop();
        startTime = 0;
        stopTime = 0;
        running = false;
    }
}
