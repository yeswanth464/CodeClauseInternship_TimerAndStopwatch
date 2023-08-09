import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StopwatchAndTimer extends JFrame {
    private JLabel timerLabel;
    private JButton startStopButton;
    private JButton resetButton;
    private JButton lapButton;
    private JTextArea lapTextArea;
    private Timer timer;
    private boolean isTimerRunning;
    private long startTime;
    private long elapsedTime;
    private ArrayList<Long> lapTimes;

    public StopwatchAndTimer() {
        // Initialize components
        timerLabel = new JLabel("00:00:00");
        startStopButton = new JButton("Start");
        resetButton = new JButton("Reset");
        lapButton = new JButton("Lap");
        lapTextArea = new JTextArea(10, 15);
        isTimerRunning = false;
        lapTimes = new ArrayList<>();

        // Configure the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimerLabel();
            }
        });

        // Set up the layout
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(timerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startStopButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(lapButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        contentPane.add(new JScrollPane(lapTextArea), BorderLayout.SOUTH);

        // Add listeners to buttons
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStartStop();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReset();
            }
        });

        lapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLap();
            }
        });

        setContentPane(contentPane);
        setTitle("Stopwatch and Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void updateTimerLabel() {
        long currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startTime;
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
    }

    private void handleStartStop() {
        if (isTimerRunning) {
            timer.stop();
            isTimerRunning = false;
            startStopButton.setText("Start");
        } else {
            if (lapTimes.isEmpty()) {
                startTime = System.currentTimeMillis();
            } else {
                startTime = System.currentTimeMillis() - elapsedTime;
            }
            timer.start();
            isTimerRunning = true;
            startStopButton.setText("Stop");
        }
    }

    private void handleReset() {
        if (isTimerRunning) {
            startTime = System.currentTimeMillis();
        } else {
            elapsedTime = 0;
            lapTimes.clear();
            lapTextArea.setText("");
            updateTimerLabel();
        }
    }

    private void handleLap() {
        if (isTimerRunning) {
            lapTimes.add(elapsedTime);
            String lapTimeStr = String.format("%02d:%02d:%02d", elapsedTime / 3600000,
                    (elapsedTime % 3600000) / 60000, (elapsedTime % 60000) / 1000);
            lapTextArea.append("Lap " + lapTimes.size() + ": " + lapTimeStr + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StopwatchAndTimer().setVisible(true);
            }
        });
    }
}

