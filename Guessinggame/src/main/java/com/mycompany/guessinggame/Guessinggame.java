package com.mycompany.guessinggame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Guessinggame extends JFrame implements ActionListener {

    private JTextField inputField;
    private JButton guessButton, restartButton;
    private JLabel hintLabel, attemptsLabel, messageLabel, levelLabel;
    private int targetNumber, maxGuesses, guessCount, level;
    private Random random;

    public Guessinggame() {
        super(" Multi-Level Number Guessing Game");

        random = new Random();
        level = 1;

        // GUI Components
        inputField = new JTextField();
        guessButton = new JButton("Guess");
        restartButton = new JButton("Restart");

        hintLabel = new JLabel("Enter a number between 1 and 1000.");
        attemptsLabel = new JLabel("Attempts Left: ");
        messageLabel = new JLabel(" ");
        levelLabel = new JLabel("Level: 1");

        setLayout(null);
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.CYAN);

        inputField.setBounds(100, 150, 180, 30);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(inputField);

        guessButton.setBounds(300, 150, 90, 30);
        guessButton.addActionListener(this);
        add(guessButton);

        restartButton.setBounds(170, 200, 100, 30);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);

        levelLabel.setBounds(20, 10, 200, 25);
        levelLabel.setForeground(Color.ORANGE);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(levelLabel);
hintLabel.setBounds(20, 50, 400, 25);
        hintLabel.setForeground(Color.BLACK);
        add(hintLabel);

        attemptsLabel.setBounds(20, 100, 200, 25);
        attemptsLabel.setForeground(Color.BLACK);
        add(attemptsLabel);

        messageLabel.setBounds(20, 120, 400, 25);
        messageLabel.setForeground(Color.BLACK);
        add(messageLabel);

        inputField.addActionListener(e -> guessButton.doClick());

        startNewLevel();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startNewLevel() {
        int max = Math.max(1000 - (level - 1) * 200, 100);
        targetNumber = random.nextInt(max) + 1;
        maxGuesses = Math.max(3 - level + 1, 3);
        guessCount = 0;

        levelLabel.setText("Level: " + level);
        hintLabel.setText("Guess the number between 1 and " + max);
        attemptsLabel.setText("Attempts Left: " + maxGuesses);
        messageLabel.setText("");
        inputField.setText("");
        inputField.setEditable(true);
        guessButton.setEnabled(true);
        restartButton.setVisible(false);
    }

    private void restartGame() {
        level = 1;
        startNewLevel();
    }

    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText();
        int guess;

        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            messageLabel.setText(" Enter a valid number!");
            return;
        }

        guessCount++;
        int remaining = maxGuesses - guessCount;

        if (guess == targetNumber) {
            String award = switch (guessCount) {
                case 1 -> "Excellent!! You got in First try!";
                case 2 -> "Very Good!! You got in Second try!";
                case 3 -> "Good!! You got Third try!";
                default -> "Nice!";
            };

            JOptionPane.showMessageDialog(this,
                    award + "\nYou've passed level " + level,
                    "Winner", JOptionPane.INFORMATION_MESSAGE);

            level++;
            startNewLevel();
            return;
        }

        if (remaining > 0) {
            int interval = Math.abs(targetNumber - guess) / 2 + 10;
            int lower = Math.max(1, targetNumber - interval);
            int upper = Math.min(1000, targetNumber + interval);

            messageLabel.setText("❌ Incorrect! Try between " + lower + " and " + upper);
            attemptsLabel.setText("Attempts Left: " + remaining);
        } else {
            JOptionPane.showMessageDialog(this,
                    "💀 Game Over! The number was: " + targetNumber,
                    "Game Over", JOptionPane.ERROR_MESSAGE);

            inputField.setEditable(false);
            guessButton.setEnabled(false);
            restartButton.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Guessinggame();
    }
}
