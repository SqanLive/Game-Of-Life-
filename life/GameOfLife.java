package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife  extends JFrame {
    static int cellSize = 20;
    static int size = 40;
    static int width = cellSize * size - 24;
    static int labelHeight = 30;
    static int height = width + labelHeight - 34;
    static JLabel generationLabel;
    static JLabel aliveLabel;
    static JPanel fieldPanel;
    static JPanel topPanel;
    static JButton pauseButton;
    static JButton resetButton;
    private volatile boolean paused = false;

    public GameOfLife() {
        super("Game Of Life");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);

        JPanel labelPanel = new JPanel();
        labelPanel.setBounds(0, 0, width/2, 30);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, width/2, 30);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        topPanel = new JPanel();
        topPanel.setBounds(0, 0, width, 30);
        topPanel.setLayout(new GridLayout(1, 2,0,0));

        generationLabel = new JLabel("Generation #" );
        generationLabel.setBounds(0,0, 100, 15);
        generationLabel.setAlignmentX(CENTER_ALIGNMENT);
        generationLabel.setName("GenerationLabel");
        aliveLabel = new JLabel("Alive: " );
        aliveLabel.setBounds(0,0, 100, 15);
        aliveLabel.setAlignmentX(CENTER_ALIGNMENT);
        aliveLabel.setName("AliveLabel");

        pauseButton = new JButton("Pause/Resume");
        pauseButton.setBounds(0,0, 100, 15);
        pauseButton.setAlignmentX(CENTER_ALIGNMENT);
        pauseButton.setName("PlayToggleButton");

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(paused == false) {
                    paused = true;

                }
                else{
                    paused = false;
                }
            }
        });

        resetButton = new JButton("Reset");
        resetButton.setBounds(0,0, 100, 15);
        resetButton.setAlignmentX(CENTER_ALIGNMENT);
        resetButton.setName("ResetButton");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.generate();
                game.setGeneration();
                paused = false;
            }
        });

        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, resetButton.getMinimumSize().height));

        //labelPanel.setAlignmentX(LEFT_ALIGNMENT);
        labelPanel.add(generationLabel);
        labelPanel.add(aliveLabel);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        topPanel.add(labelPanel);
        topPanel.add(buttonPanel);

        fieldPanel = new JPanel();
        fieldPanel.setBounds(0, 0, width, height - labelHeight);
        fieldPanel.setLayout(new GridLayout(1, 1,0,0));

        game.generate();
        updateField(game.getCurrent_generation());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(topPanel);
        add(fieldPanel);

        setVisible(true);
        visualizeGUI();

    }

    public  void updateField(char[][] current_generation) {
        JPanel newFieldPanel = new JPanel();
        newFieldPanel.setBounds(0, 0, width, height - labelHeight);
        newFieldPanel.setLayout(new GridLayout(current_generation.length, current_generation.length,1,1));
        newFieldPanel.setBackground(Color.BLACK);
        for (int j = 0; j < current_generation.length; j++) {
            for (int i = 0; i < current_generation.length; i++) {
                JPanel cell = new JPanel();
                cell.setBounds(0, 0, width / current_generation.length, height / current_generation.length);
                Color color;
                if(current_generation[j][i] == 'O') {
                    color = Color.BLACK;
                }
                else {
                    color = Color.WHITE;
                }
                cell.setBackground(color);
                newFieldPanel.add(cell);
            }
        }
        fieldPanel.removeAll();
        fieldPanel.add(newFieldPanel);
        fieldPanel.revalidate();
    }

    public void updateLabels(int generation, int alive) {
        generationLabel.setText("Generation #" + generation);
        aliveLabel.setText("Alive: " + alive);
    }

    public void visualizeGUI() {
        while (true) {
            if(paused == false) {

                game.check_neighbours();
                updateLabels(game.getGeneration(), game.count_alive());
                updateField(game.getCurrent_generation());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
