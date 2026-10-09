
package S2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main extends JFrame {

    private final JTextField inputField = new JTextField("10000", 10);
    private final JButton runButton = new JButton("Run");
    private final GraphPanel graphPanel = new GraphPanel();

    public Main() {
        setTitle("Sorting Algorithm Time Comparison");
        setSize(900, 600);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();

        controlPanel.add(new JLabel("Number of data:"));
        controlPanel.add(inputField);
        controlPanel.add(runButton);

        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        runButton.addActionListener(event -> runTest());
    }

    private void runTest() {
        final int maximumSize;

        try {
            maximumSize = Integer.parseInt(inputField.getText().trim());

            if (maximumSize < 100) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter at least 100."
                );
                return;
            }

            if (maximumSize > 50000) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter no more than 50000."
                );
                return;
            }

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );
            return;
        }

        runButton.setEnabled(false);
        inputField.setEnabled(false);
        runButton.setText("Running...");

        // ใช้ Thread แยกเพื่อไม่ให้หน้าต่างค้าง
        new Thread(() -> {
            warmUp();

            int numberOfPoints = 5;
            int repeatCount = 3;

            int[] sizes = new int[numberOfPoints];
            double[][] times = new double[4][numberOfPoints];

            Random random = new Random();

            for (int point = 0; point < numberOfPoints; point++) {
                sizes[point] =
                        maximumSize * (point + 1) / numberOfPoints;

                double bubbleTotal = 0;
                double selectionTotal = 0;
                double insertionTotal = 0;
                double quickTotal = 0;

                for (int repeat = 0; repeat < repeatCount; repeat++) {
                    int[] original =
                            createRandomArray(sizes[point], random);

                    bubbleTotal += measureTime(
                            original,
                            SortingAlgorithms::bubbleSort
                    );

                    selectionTotal += measureTime(
                            original,
                            SortingAlgorithms::selectionSort
                    );

                    insertionTotal += measureTime(
                            original,
                            SortingAlgorithms::insertionSort
                    );

                    quickTotal += measureTime(
                            original,
                            SortingAlgorithms::quickSort
                    );
                }

                times[0][point] = bubbleTotal / repeatCount;
                times[1][point] = selectionTotal / repeatCount;
                times[2][point] = insertionTotal / repeatCount;
                times[3][point] = quickTotal / repeatCount;
            }

            SwingUtilities.invokeLater(() -> {
                graphPanel.setData(sizes, times);

                runButton.setEnabled(true);
                inputField.setEnabled(true);
                runButton.setText("Run");
            });

        }).start();
    }

    /*
     * รับ Method สำหรับเรียงข้อมูลเข้ามา
     * จากนั้น Clone ข้อมูลและวัดเวลา
     */
    private static double measureTime(
            int[] original,
            SortMethod sortMethod) {

        int[] array = original.clone();

        long startTime = System.nanoTime();

        sortMethod.sort(array);

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

    /*
     * Warm-up ก่อนจับเวลาจริง
     * ช่วยลดปัญหาเวลาในการ Run ครั้งแรกเพี้ยน
     */
    private static void warmUp() {
        Random random = new Random();

        for (int round = 0; round < 5; round++) {
            int[] data = createRandomArray(2000, random);

            SortingAlgorithms.bubbleSort(data.clone());
            SortingAlgorithms.selectionSort(data.clone());
            SortingAlgorithms.insertionSort(data.clone());
            SortingAlgorithms.quickSort(data.clone());
        }
    }

    private static int[] createRandomArray(
            int size,
            Random random) {

        int[] array = new int[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1_000_000);
        }

        return array;
    }

    /*
     * Interface สำหรับส่ง Sorting Method
     * เข้าไปใน measureTime()
     */
    @FunctionalInterface
    private interface SortMethod {
        void sort(int[] array);
    }

    /*
     * Panel สำหรับวาดกราฟ
     */
    private static class GraphPanel extends JPanel {

        private int[] sizes;
        private double[][] times;

        private final Color[] colors = {
                Color.RED,
                Color.BLUE,
                new Color(0, 150, 70),
                new Color(245, 170, 0)
        };

        private final String[] algorithmNames = {
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Quick Sort"
        };

        public GraphPanel() {
            setBackground(Color.WHITE);
        }

        public void setData(int[] sizes, double[][] times) {
            this.sizes = sizes;
            this.times = times;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D g2 = (Graphics2D) graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int left = 90;
            int right = getWidth() - 180;
            int top = 65;
            int bottom = getHeight() - 75;

            drawTitle(g2);
            drawAxes(g2, left, right, top, bottom);

            if (sizes == null || times == null) {
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(
                        "Enter the number of data and click Run",
                        left + 100,
                        top + 100
                );

                g2.dispose();
                return;
            }

            double maximumTime = findMaximumTime() * 1.10;

            if (maximumTime <= 0) {
                maximumTime = 1;
            }

            drawGrid(
                    g2,
                    left,
                    right,
                    top,
                    bottom,
                    maximumTime
            );

            drawXAxisValues(g2, left, right, bottom);

            for (int algorithm = 0;
                 algorithm < times.length;
                 algorithm++) {

                drawSeries(
                        g2,
                        algorithm,
                        left,
                        right,
                        top,
                        bottom,
                        maximumTime
                );
            }

            drawLegend(g2, right + 20, top);

            g2.dispose();
        }

        private void drawTitle(Graphics2D g2) {
            g2.setColor(Color.BLACK);
            g2.setFont(
                    new Font("SansSerif", Font.BOLD, 18)
            );

            String title =
                    "Sorting Algorithm Time Comparison";

            FontMetrics metrics = g2.getFontMetrics();

            int titleX =
                    (getWidth() - metrics.stringWidth(title)) / 2;

            g2.drawString(title, titleX, 30);
        }

        private void drawAxes(
                Graphics2D g2,
                int left,
                int right,
                int top,
                int bottom) {

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));

            // แกน X
            g2.drawLine(left, bottom, right, bottom);

            // แกน Y
            g2.drawLine(left, bottom, left, top);

            g2.setFont(
                    new Font("SansSerif", Font.PLAIN, 13)
            );

            g2.drawString("Time (ms)", 20, top - 10);

            g2.drawString(
                    "Number of data",
                    (left + right) / 2 - 45,
                    getHeight() - 20
            );
        }

        private void drawGrid(
                Graphics2D g2,
                int left,
                int right,
                int top,
                int bottom,
                double maximumTime) {

            g2.setFont(
                    new Font("SansSerif", Font.PLAIN, 12)
            );

            for (int i = 0; i <= 5; i++) {
                int y = bottom - i * (bottom - top) / 5;
                double value = maximumTime * i / 5;

                g2.setColor(new Color(210, 210, 210));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(left, y, right, y);

                String label = String.format("%.1f", value);

                g2.setColor(Color.BLACK);

                FontMetrics metrics = g2.getFontMetrics();

                g2.drawString(
                        label,
                        left - metrics.stringWidth(label) - 10,
                        y + 5
                );
            }
        }

        private void drawXAxisValues(
                Graphics2D g2,
                int left,
                int right,
                int bottom) {

            g2.setColor(Color.BLACK);

            for (int i = 0; i < sizes.length; i++) {
                int x = calculateX(i, left, right);

                String value = String.valueOf(sizes[i]);
                FontMetrics metrics = g2.getFontMetrics();

                g2.drawString(
                        value,
                        x - metrics.stringWidth(value) / 2,
                        bottom + 22
                );
            }
        }

        private void drawSeries(
                Graphics2D g2,
                int algorithm,
                int left,
                int right,
                int top,
                int bottom,
                double maximumTime) {

            g2.setColor(colors[algorithm]);
            g2.setStroke(new BasicStroke(2.5f));

            for (int i = 0; i < sizes.length; i++) {
                int x = calculateX(i, left, right);

                int y = calculateY(
                        times[algorithm][i],
                        top,
                        bottom,
                        maximumTime
                );

                // วาดจุด
                g2.fillOval(x - 4, y - 4, 8, 8);

                // วาดเส้นเชื่อม
                if (i > 0) {
                    int previousX =
                            calculateX(i - 1, left, right);

                    int previousY = calculateY(
                            times[algorithm][i - 1],
                            top,
                            bottom,
                            maximumTime
                    );

                    g2.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }
            }
        }

        private int calculateX(
                int index,
                int left,
                int right) {

            if (sizes.length == 1) {
                return (left + right) / 2;
            }

            return left
                    + index
                    * (right - left)
                    / (sizes.length - 1);
        }

        private int calculateY(
                double time,
                int top,
                int bottom,
                double maximumTime) {

            return bottom - (int) (
                    time / maximumTime * (bottom - top)
            );
        }

        private double findMaximumTime() {
            double maximum = 0;

            for (double[] algorithmTimes : times) {
                for (double time : algorithmTimes) {
                    maximum = Math.max(maximum, time);
                }
            }

            return maximum;
        }

        private void drawLegend(
                Graphics2D g2,
                int x,
                int y) {

            g2.setFont(
                    new Font("SansSerif", Font.PLAIN, 13)
            );

            for (int i = 0; i < algorithmNames.length; i++) {
                int legendY = y + i * 30;

                g2.setColor(colors[i]);
                g2.setStroke(new BasicStroke(2.5f));

                g2.drawLine(
                        x,
                        legendY,
                        x + 25,
                        legendY
                );

                g2.fillOval(
                        x + 9,
                        legendY - 4,
                        8,
                        8
                );

                g2.setColor(Color.BLACK);

                g2.drawString(
                        algorithmNames[i],
                        x + 35,
                        legendY + 5
                );
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main application = new Main();
            application.setVisible(true);
        });
    }
}