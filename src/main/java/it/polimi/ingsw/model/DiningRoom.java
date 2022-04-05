package it.polimi.ingsw.model;

public class DiningRoom {
    private int[] studentCounters;

    public int getStudentCounters(Color color) {
        return studentCounters[color.ordinal()];
    }

    private void setStudentCounters(int n) {
        for (Color color : Color.values()) {
            setStudentCounters(color, n);
        }
    }

    private void setStudentCounters(Color color, int n) {
        studentCounters[color.ordinal()] = n;
    }
}
