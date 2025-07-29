package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class game extends JFrame implements ActionListener {
    private JButton imageGameButton;
    private JButton numberGameButton;
    private JButton numberGameButton2;

    public game() {
        setTitle("Game Menu");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        imageGameButton = new JButton("Picture Game");
        numberGameButton = new JButton("Letter Game");
        numberGameButton2 = new JButton("Number Game");

        imageGameButton.addActionListener(this);
        numberGameButton.addActionListener(this);
        numberGameButton2.addActionListener(this);

        add(imageGameButton);
        add(numberGameButton);
        add(numberGameButton2);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == imageGameButton) {
            dispose();
            new JFrame() {
                private List<String> cards;
                private List<JLabel> labels;
                private ImageIcon backImage;
                private String selectedCard1;
                private String selectedCard2;
                private int pairsFound;
                private boolean isProcessing;
                private int score;
                private JLabel scoreLabel;

                {
                    setTitle("Memory Game");
                    setSize(400, 450);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setLayout(new BorderLayout());

                    JPanel gamePanel = new JPanel(new GridLayout(4, 4));
                    add(gamePanel, BorderLayout.CENTER);

                    cards = new ArrayList<>();
                    labels = new ArrayList<>();
                    selectedCard1 = null;
                    selectedCard2 = null;
                    pairsFound = 0;
                    isProcessing = false;
                    score = 0;

                    backImage = new ImageIcon("image/back.jpg");

                    for (char c = '1'; c <= '8'; c++) {
                        cards.add("image/" + c + ".png");
                        cards.add("image/" + c + ".png");
                    }
                    Collections.shuffle(cards);

                    for (int i = 0; i < 16; i++) {
                        JLabel label = new JLabel(backImage);
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setVerticalAlignment(SwingConstants.CENTER);
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        label.setOpaque(true);
                        label.setPreferredSize(new Dimension(75, 75));
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (isProcessing)
                                    return;

                                JLabel clickedLabel = (JLabel) e.getSource();
                                int index = labels.indexOf(clickedLabel);
                                String cardValue = cards.get(index);

                                if (selectedCard1 == null) {
                                    selectedCard1 = cardValue;
                                    clickedLabel.setIcon(new ImageIcon(selectedCard1));
                                    clickedLabel.setEnabled(false);
                                } else if (selectedCard2 == null) {
                                    selectedCard2 = cardValue;
                                    clickedLabel.setIcon(new ImageIcon(selectedCard2));
                                    clickedLabel.setEnabled(false);
                                    isProcessing = true;

                                    if (selectedCard1.equals(selectedCard2)) {
                                        pairsFound++;
                                        score += 10;
                                        if (pairsFound == 8) {
                                            score += 50;
                                            JOptionPane.showMessageDialog(game.this, "Congratulations, completed the game!\nTotal Score: " + score);
                                            dispose();
                                        }
                                        selectedCard1 = null;
                                        selectedCard2 = null;
                                        isProcessing = false;
                                    } else {
                                        Timer timer = new Timer(1000, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                clickedLabel.setIcon(backImage);
                                                clickedLabel.setEnabled(true);
                                                for (JLabel label : labels) {
                                                    if (label.getIcon().toString().equals(selectedCard1) || label.getIcon().toString().equals(selectedCard2)) {
                                                        label.setIcon(backImage);
                                                        label.setEnabled(true);
                                                    }
                                                }
                                                selectedCard1 = null;
                                                selectedCard2 = null;
                                                isProcessing = false;
                                            }
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                    }
                                }
                                scoreLabel.setText("Score: " + score);
                            }
                        });
                        labels.add(label);
                        gamePanel.add(label);
                    }

                    scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
                    add(scoreLabel, BorderLayout.SOUTH);

                    setVisible(true);
                }
            };
        } else if (e.getSource() == numberGameButton) {
            dispose();
            new JFrame() {
                private List<String> cards;
                private List<JButton> buttons;
                private String selectedCard1;
                private String selectedCard2;
                private int pairsFound;
                private boolean isProcessing;

                {
                    setTitle("Memory Game");
                    setSize(400, 400);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setLayout(new GridLayout(4, 4));

                    cards = new ArrayList<>();
                    buttons = new ArrayList<>();
                    selectedCard1 = null;
                    selectedCard2 = null;
                    pairsFound = 0;
                    isProcessing = false;

                    for (char c = 'A'; c <= 'H'; c++) {
                        cards.add(String.valueOf(c));
                        cards.add(String.valueOf(c));
                    }
                    Collections.shuffle(cards);

                    for (int i = 0; i < 16; i++) {
                        JButton button = new JButton(" ");
                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (isProcessing)
                                    return;

                                JButton clickedButton = (JButton) e.getSource();
                                int index = buttons.indexOf(clickedButton);
                                String cardValue = cards.get(index);

                                if (selectedCard1 == null) {
                                    selectedCard1 = cardValue;
                                    clickedButton.setText(selectedCard1);
                                    clickedButton.setEnabled(false);
                                } else if (selectedCard2 == null) {
                                    selectedCard2 = cardValue;
                                    clickedButton.setText(selectedCard2);
                                    clickedButton.setEnabled(false);
                                    isProcessing = true;

                                    if (selectedCard1.equals(selectedCard2)) {
                                        pairsFound++;
                                        if (pairsFound == 8) {
                                            JOptionPane.showMessageDialog(game.this, "Congratulations, completed the game!");
                                            dispose();
                                        }
                                        selectedCard1 = null;
                                        selectedCard2 = null;
                                        isProcessing = false;
                                    } else {
                                        Timer timer = new Timer(1000, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                clickedButton.setText(" ");
                                                clickedButton.setEnabled(true);
                                                for (JButton button : buttons) {
                                                    if (button.getText().equals(selectedCard1) || button.getText().equals(selectedCard2)) {
                                                        button.setText(" ");
                                                        button.setEnabled(true);
                                                    }
                                                }
                                                selectedCard1 = null;
                                                selectedCard2 = null;
                                                isProcessing = false;
                                            }
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                    }
                                }
                            }
                        });
                        buttons.add(button);
                        add(button);
                    }

                    setVisible(true);
                }
            };
        } else if (e.getSource() == numberGameButton2) { 
            dispose();
            new JFrame() {
                private List<String> cards;
                private List<JButton> buttons;
                private String selectedCard1;
                private String selectedCard2;
                private int pairsFound;
                private boolean isProcessing;

                {
                    setTitle("Number Memory Game");
                    setSize(400, 400);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setLayout(new GridLayout(4, 4));

                    cards = new ArrayList<>();
                    buttons = new ArrayList<>();
                    selectedCard1 = null;
                    selectedCard2 = null;
                    pairsFound = 0;
                    isProcessing = false;

                    for (int i = 1; i <= 8; i++) {
                        cards.add(String.valueOf(i));
                        cards.add(String.valueOf(i));
                    }
                    Collections.shuffle(cards);

                    for (int i = 0; i < 16; i++) {
                        JButton button = new JButton(" ");
                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (isProcessing)
                                    return;

                                JButton clickedButton = (JButton) e.getSource();
                                int index = buttons.indexOf(clickedButton);
                                String cardValue = cards.get(index);

                                if (selectedCard1 == null) {
                                    selectedCard1 = cardValue;
                                    clickedButton.setText(selectedCard1);
                                    clickedButton.setEnabled(false);
                                } else if (selectedCard2 == null) {
                                    selectedCard2 = cardValue;
                                    clickedButton.setText(selectedCard2);
                                    clickedButton.setEnabled(false);
                                    isProcessing = true;

                                    if (selectedCard1.equals(selectedCard2)) {
                                        pairsFound++;
                                        if (pairsFound == 8) {
                                            JOptionPane.showMessageDialog(game.this, "Congratulations, completed the game!");
                                            dispose();
                                        }
                                        selectedCard1 = null;
                                        selectedCard2 = null;
                                        isProcessing = false;
                                    } else {
                                        Timer timer = new Timer(1000, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                clickedButton.setText(" ");
                                                clickedButton.setEnabled(true);
                                                for (JButton button : buttons) {
                                                    if (button.getText().equals(selectedCard1) || button.getText().equals(selectedCard2)) {
                                                        button.setText(" ");
                                                        button.setEnabled(true);
                                                    }
                                                }
                                                selectedCard1 = null;
                                                selectedCard2 = null;
                                                isProcessing = false;
                                            }
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                    }
                                }
                            }
                        });
                        buttons.add(button);
                        add(button);
                    }

                    setVisible(true);
                }
            };
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(game::new);
    }
}
