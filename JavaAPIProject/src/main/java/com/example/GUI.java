package com.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI{
    // The window in which my project will be in...
    private JFrame frame;

    //// Instantiation of cardLayout and mainPanel
    // cardLayout - to switch between the different panels
    // mainScreen - the panel for everything
    private CardLayout cardLayout;
    private JPanel mainScreen;

    // selection screen display selection
    private JLabel currentSelection;

    // private instance variables for the game
    private Environment environment;
    private Character player;
    private Character opponent;


    public GUI() {
        // Creating the Main Window
        frame = new JFrame("Chemistry Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);

        // Creating the Main Panel (Start Screen)
        cardLayout = new CardLayout();
        mainScreen = new JPanel(cardLayout);

        // Adding different pages to my cardLayout
        mainScreen.add(createStartScreen(), "StartMenu");
        mainScreen.add(createSelectionScreen(), "SelectionScreen");

        // Initializing game elements
        environment = new Environment();
        mainScreen.add(createElementsPanel(), "elementSelection");
        mainScreen.add(createCompoundsPanel(), "compoundSelection");
        mainScreen.add(createSolutionsPanel(), "solutionSelection");

        // Adding the mainScreen to the frame
        frame.add(mainScreen);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // Method for Creating the Start Menu
    private JPanel createStartScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // text for display
        JLabel titleText = new JLabel("Welcome to the Chemistry Game!");
        titleText.setFont(new Font("Helvetica", Font.BOLD, 48));
        panel.add(titleText, BorderLayout.CENTER);

        // start button
        JButton startButton = new JButton("Start");
        panel.add(startButton, BorderLayout.SOUTH);

        // the functionality of the button
        startButton.addActionListener(new ActionListener() {
            // if the button is pressed, go to the next screen
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        return panel;
    }

    // Method for Creating the Second Page to Select Element/Compound/Solution
    private JPanel createSelectionScreen() {
        // border panel for the text
        JPanel panel = new JPanel(new BorderLayout());

        // grid panel for the selection
        JPanel gridPanel = new JPanel(new GridLayout(3,0));

        // position panel for text placement and back button
        JPanel positionPanel = new JPanel(null);

        // text for the selection display
        JLabel titleText = new JLabel("Select Your Category", SwingConstants.CENTER);
        titleText.setFont(new Font("Helvetica", Font.PLAIN, 48));
        titleText.setForeground(Color.BLACK);

        // text for the current selection chosen 
        currentSelection = new JLabel("No Current Selection!", SwingConstants.RIGHT);
        currentSelection.setLocation(200, 200);
        currentSelection.setSize(currentSelection.getPreferredSize());

        // buttons
        JButton elements = new JButton("Elements");
        JButton compounds =  new JButton("Compounds");
        JButton solutions = new JButton("Solutions");

        // back button 
        JButton back = new JButton("Back");
        back.setBounds(0, 0, 100, 100);

        // add text to the border panel
        panel.add(titleText, BorderLayout.NORTH);

        // add button to the position panel
        positionPanel.add(back);
        positionPanel.add(currentSelection);

        // add buttons to the grid panel
        gridPanel.add(elements);
        gridPanel.add(compounds);
        gridPanel.add(solutions);
        panel.add(positionPanel, BorderLayout.CENTER);
        panel.add(gridPanel, BorderLayout.SOUTH);


        // actions for each button

        // elements button
        elements.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainScreen, "elementSelection");
            }
        });

        // compounds button
        compounds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainScreen, "compoundSelection");
            }
        });

        // solutions button
        solutions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainScreen, "solutionSelection");
            }
        });

        // back button
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainScreen, "StartMenu");
            }
        });

        return panel;

    }

    // Method for Creating the Selection Screen for Elements
    private JPanel createElementsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        // create the selection grid
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5));

        // create the buttons for each element
        JButton bH = new JButton("Hydrogen");
        JButton bO = new JButton("Oxygen");
        JButton bNa = new JButton("Sodium");
        JButton bCl = new JButton("Chlorine");
        JButton bI = new JButton("Iodine");

        // add buttons to the selection grid
        selectionPanel.add(bH);
        selectionPanel.add(bO);
        selectionPanel.add(bNa);
        selectionPanel.add(bCl);
        selectionPanel.add(bI);

        //// functionality for each button

        // select hydrogen
        bH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(783);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select oxygen
        bO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(977);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select sodium
        bNa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(5360545);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select chlorine
        bCl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(24526);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select iodine
        bI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(807);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // text for the selection panel
        JLabel text = new JLabel("Select your Element", SwingConstants.CENTER);
        text.setFont(new Font("Helvetica", Font.PLAIN, 48));

        // add the selection grid to the panel
        panel.add(text);
        panel.add(selectionPanel);

        return panel;

    }

    // Method for Creating the Selection Screen for Elements
    private JPanel createCompoundsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        // create the selection grid
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5));

        // create the buttons for each element
        JButton bEth = new JButton("Ethanol");
        JButton bOct = new JButton("Octane");
        JButton bAce = new JButton("Acetone");
        JButton bWater = new JButton("Water");

        // add buttons to the selection grid
        selectionPanel.add(bEth);
        selectionPanel.add(bOct);
        selectionPanel.add(bAce);
        selectionPanel.add(bWater);

        //// functionality for each button

        // select hydrogen
        bEth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(702);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select oxygen
        bOct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(356);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select sodium
        bAce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(180);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select chlorine
        bWater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Species(962);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // text for the selection panel
        JLabel text = new JLabel("Select your Element", SwingConstants.CENTER);
        text.setFont(new Font("Helvetica", Font.PLAIN, 48));

        // add the selection grid to the panel
        panel.add(text);
        panel.add(selectionPanel);

        return panel;

    }

    // Method for Creating the Selection Screen for Elements
    private JPanel createSolutionsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        // create the selection grid
        JPanel selectionPanel = new JPanel(new GridLayout(1, 5));

        // create the buttons for each element
        JButton bAcetic = new JButton("Acetic Acid");
        JButton bHydrazoic = new JButton("Hydrazoic Acid");
        JButton bHydrosulfuric = new JButton("Hydrosulfuric Acid");

        // add buttons to the selection grid
        selectionPanel.add(bAcetic);
        selectionPanel.add(bHydrazoic);
        selectionPanel.add(bHydrosulfuric);

        //// functionality for each button

        // select hydrogen
        bAcetic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Solution(176, 1.0, 1.0);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select oxygen
        bHydrazoic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Solution(24530, 1.0, 1.0);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // select sodium
        bHydrosulfuric.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Species s = new Solution(402, 1.0, 1.0);
                player = new Character(s, environment);
                updateCurrentSelection();
                cardLayout.show(mainScreen, "SelectionScreen");
            }
        });

        // text for the selection panel
        JLabel text = new JLabel("Select your Element", SwingConstants.CENTER);
        text.setFont(new Font("Helvetica", Font.PLAIN, 48));

        // add the selection grid to the panel
        panel.add(text);
        panel.add(selectionPanel);

        return panel;

    }

    // Method that updates the text of current selection
    private void updateCurrentSelection() {
        if (currentSelection != null) {
            if (player == null) {
                currentSelection.setText("No Current Selection");
            } else {
                currentSelection.setText("<html>Current Selection: <b>" + player.getSpecies().getName() +
                "</b><br>Stats:<br>" + player.getSpecies().toString().replaceAll("\n", "<br>") + "</html>");
            }
        }
        currentSelection.setSize(currentSelection.getPreferredSize());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI();
        });
    }
}
