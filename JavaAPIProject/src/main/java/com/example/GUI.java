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

    // texts used for later
    private JLabel currentSelection; // to show the current selection
    private JLabel environmentText; // display the environment statistics
    private JLabel playerStatistics; // displays the player's stats
    private JLabel enemyStatistics; // displays the enemy's stats
    private JLabel moveUsed; // to show the moves used and its effects
    

    // health bar that can be accessed anywhere in this class
    private JProgressBar playerHP;
    private JProgressBar enemyHP;

    // private instance variables for the game
    private Environment environment;
    private Character player;
    private Character opponent;

    // turns
    private boolean isPlayerTurn;

    // timer
    Timer timer;


    public GUI() {
        // Creating the Main Window
        frame = new JFrame("Chemistry Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);

        // Creating the Main Panel (Start Screen)
        cardLayout = new CardLayout();
        mainScreen = new JPanel(cardLayout);

        // Creating Empty Statistics
        playerStatistics = new JLabel();
        enemyStatistics = new JLabel();

        // Adding different pages to my cardLayout
        mainScreen.add(createStartScreen(), "StartMenu");
        mainScreen.add(createSelectionScreen(), "SelectionScreen");

        // Initializing game elements
        environment = new Environment();
        environmentText = new JLabel("<html>" + environment.toString().replace("\n", "<br>") + "</html>");
        isPlayerTurn = true;
        generateEnemy();
        mainScreen.add(createElementsPanel(), "elementSelection");
        mainScreen.add(createCompoundsPanel(), "compoundSelection");
        mainScreen.add(createSolutionsPanel(), "solutionSelection");
        mainScreen.add(createBattlePanel(), "GameMenu");

        // Adding the mainScreen to the frame
        frame.add(mainScreen);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Timer to Continually Update Elements
        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateHealth();
                updateStats();
            }
        });
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

        // selection buttons
        JButton elements = new JButton("Elements");
        JButton compounds =  new JButton("Compounds");
        JButton solutions = new JButton("Solutions");

        // back button 
        JButton back = new JButton("Back");
        back.setBounds(0, 0, 100, 100);

        // start game button
        JButton start = new JButton("Start Game");
        start.setBounds(0,100, 100, 100);

        // add text to the border panel
        panel.add(titleText, BorderLayout.NORTH);

        // add button to the position panel
        positionPanel.add(back);
        positionPanel.add(currentSelection);
        positionPanel.add(start);

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

        // start game button
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player != null) {
                    playerStatistics.setText("<html> Player Atomic Statistics <br>" + player.getSpecies().toString().replace("\n", "<br>") + "<br>" + "---------<br>Player Combat Stats: <br>" + player.toString().replace("\n", "<br>") + "</html");
                    cardLayout.show(mainScreen, "GameMenu");
                    
                    timer.start();
                } else {
                    currentSelection.setText("You need to make a selection first!");
                    currentSelection.setSize(currentSelection.getPreferredSize());
                    // wait 2.5s
                    Timer t = (new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (currentSelection.getText().equals("You need to make a selection first!")) {
                                currentSelection.setText("No Current Selection");
                                currentSelection.setSize(currentSelection.getPreferredSize());
                            }                      
                        }
                    }));   
                    t.setRepeats(false);
                    t.start();
                    // changes the text back
                }
                
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

    // Method for Creating the Battle Screen that controls the Fighting
    private JPanel createBattlePanel() {
        // Main Frame for Battle
        JPanel panel = new JPanel(new BorderLayout());

        // Top Panel w/ Enemy Health & Environment
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        enemyHP = new JProgressBar(0, 100);
        enemyHP.setForeground(Color.RED);
        enemyHP.setValue(100);
        JLabel textEnemyHealth = new JLabel("Enemy Health");

        // Left Panel
        JPanel leftStats = new JPanel();
        leftStats.setLayout(new BoxLayout(leftStats, BoxLayout.Y_AXIS));
        playerStatistics.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerStatistics.setAlignmentY(Component.CENTER_ALIGNMENT);
        leftStats.add(Box.createVerticalGlue());
        leftStats.add(playerStatistics);
        leftStats.add(Box.createVerticalGlue());

        // Right Panel
        JPanel rightStats = new JPanel();
        rightStats.setLayout(new BoxLayout(rightStats, BoxLayout.Y_AXIS));
        enemyStatistics.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyStatistics.setAlignmentY(Component.CENTER_ALIGNMENT);
        rightStats.add(Box.createVerticalGlue());
        rightStats.add(enemyStatistics);
        rightStats.add(Box.createVerticalGlue());

        // Health Bar
        playerHP = new JProgressBar(0,100);
        playerHP.setForeground(Color.RED);
        playerHP.setValue(100);

        // Bottom Panel
        JLabel textPlayerHealth = new JLabel("Player Health");
        moveUsed = new JLabel("Click a move to use it!");
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
        JPanel healthPanel = new JPanel(new GridLayout(1, 1));
        JPanel attackPanel = new JPanel(new GridLayout(2, 3));

        // Bottom Panel Buttons
        JButton basicAttack = new JButton("Basic Collision");
        JButton dilute = new JButton("Dilute");
        JButton combust = new JButton("Combust");
        JButton pressurize = new JButton("Pressurize");
        JButton acidify = new JButton("Acidify");

        // Button Functionality

        // timer for delay
        Timer b = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turnEnd();
            }
        });

        // timer for delay
        Timer a = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {
                    enemyTurn();
                    b.setRepeats(false);
                    b.start();
                    isPlayerTurn = true;
                }
            }
        });

        // basic collision
        basicAttack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {return;}
                playerTurn(1);
                a.setRepeats(false);
                a.start();
            }
        });

        // dilute
        dilute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {return;}
                playerTurn(2);
                a.setRepeats(false);
                a.start();
            }
        });

        // combust
        combust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {return;}
                playerTurn(3);
                a.setRepeats(false);
                a.start();
            }
        });

        // pressurize
        pressurize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {return;}
                playerTurn(4);
                a.setRepeats(false);
                a.start();
            }
        });

        // acidify
        acidify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn) {return;}
                playerTurn(5);
                a.setRepeats(false);
                a.start();
            }
        });


        // Adding to the Bottom Panel
        bottomPanel.add(textPlayerHealth);
        healthPanel.add(playerHP);
        bottomPanel.add(healthPanel);
        bottomPanel.add(moveUsed);
        attackPanel.add(basicAttack);
        attackPanel.add(dilute);
        attackPanel.add(combust);
        attackPanel.add(pressurize);
        attackPanel.add(acidify);
        bottomPanel.add(attackPanel);
        
        // Adding to the Top Panel
        topPanel.add(textEnemyHealth);
        topPanel.add(enemyHP);
        topPanel.add(environmentText);

        // Middle Panel w/ Images
        JPanel middlePanel = new JPanel(null);

        // Adding Panels to the Main Panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(leftStats, BorderLayout.WEST);
        panel.add(rightStats, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(middlePanel, BorderLayout.CENTER);

        return panel;
    }

    // Method that updates the text of current selection
    private void updateCurrentSelection() {
        if (currentSelection != null) {
            if (player == null) {
                currentSelection.setText("No Current Selection!");
            } else {
                currentSelection.setText("<html>Current Selection: <b>" + player.getSpecies().getName() +
                "</b><br>Stats:<br>" + player.getSpecies().toString().replaceAll("\n", "<br>") + "</html>");
            }
        }
        currentSelection.setSize(currentSelection.getPreferredSize());
    }

    // Generate an Enemy
    private void generateEnemy() {
        int chosen = (int) (Math.random() * 12) + 1;
        Species s = null;
        if (chosen == 1) {s = new Species(783);}
        if (chosen == 2) {s = new Species(977);}
        if (chosen == 3) {s = new Species(	5360545);}
        if (chosen == 4) {s = new Species(24526);}
        if (chosen == 5) {s = new Species(807);}
        if (chosen == 6) {s = new Species(702);}
        if (chosen == 7) {s = new Species(356);}
        if (chosen == 8) {s = new Species(	180);}
        if (chosen == 9) {s = new Species(962);}
        if (chosen == 10) {s = new Solution(176, 1.0, 1.0);}
        if (chosen == 11) {s = new Solution(24530, 1.0, 1.0);}
        if (chosen == 12) {s = new Solution(402, 1.0, 1.0);}
        Character c = new Character(s, environment);
        opponent = c;
        enemyStatistics.setText("<html> Enemy Atomic Statistics <br>" + opponent.getSpecies().toString().replace("\n", "<br>") + "<br>" + "---------<br>Enemy Combat Stats: <br>" + opponent.toString().replace("\n", "<br>") + "</html");
    }

    // Updates the Health Bar Constantly
    private void updateHealth() {
        // updates player health
        double playerMAX = player.getMaxHealth();
        double playerCurrent = player.getHealth();
        int playerPercentage = (int) ((playerCurrent / playerMAX) * 100);
        playerHP.setValue(playerPercentage);
        
        // updates enemy health
        double enemyMAX = opponent.getMaxHealth();
        double enemyCurrent = opponent.getHealth();
        int enemyPercentage = (int) ((enemyCurrent / enemyMAX) * 100);
        enemyHP.setValue(enemyPercentage);
    }

    // Logic to make an Enemy make a Random Move
    private void enemyTurn() {
        boolean success = false;
        while (!success) {
            int randomNum = (int) (Math.random() * 5) + 1;

            // plain collision just deals regular damage to the other player
            if (randomNum == 1) {
                double HPBefore = player.getHealth();
                success = opponent.plainAttack(player);

                // check for success
                if (success) {
                    moveUsed.setText(opponent.getSpecies().getName() + " (Enemy) used plain attack on you and dealt " + (HPBefore - player.getHealth()) + " damage!");
                } else {
                    moveUsed.setText(opponent.getSpecies().getName() + " (Enemy) used plain attack on you and was unsuccessful!");
                }
            } 

            // dilute will increase the user's strength (if they are a solution) and decrease the enemy's strength (if they are also a solution)
            if (randomNum == 2) {
                double oldOpponentStrength = opponent.getStrength();
                double oldPlayerStrength = player.getStrength();

                success = opponent.dilution(player);

                // check for success
                if (success) {
                    moveUsed.setText("<html>" +
                    opponent.getSpecies().getName() + " (Enemy) used dilution on you, making you weaker and them stronger:<br>" +
                    "Opponent Strength: +" + (opponent.getStrength() - oldOpponentStrength) + "<br>" +
                    "Player Strength: -" + (oldPlayerStrength - player.getStrength()) +
                    "</html>");
                } else {
                    moveUsed.setText("Dilution was unsuccessful since one of you weren't a solution!");
                }
            }

            // combustion will deal heavy damage with a chance to damage themselves, only for things with a heat of combustion
            if (randomNum == 3) {
                // variables for text
                double playerHealth = player.getHealth();
                double enemyHealth = opponent.getHealth();
                double initialT = environment.getTemperature();

                int rtn = opponent.combust(player, environment);
                success = rtn != 0;

                // check for success
                if (rtn == 1) {
                    moveUsed.setText("<html>" + opponent.getSpecies().getName() + " (Enemy) used incomplete combustion, dealing " + (enemyHealth - opponent.getHealth()) + " damage to itself and " + (playerHealth - player.getHealth()) + " damage to you!" +
                    "<br>The temperature has increased by " + (environment.getTemperature() - initialT) + "°C" + "</html>");
                }
                if (rtn == 2) {
                    moveUsed.setText("<html>" + opponent.getSpecies().getName() + " (Enemy) used complete combustion, dealing " + (playerHealth - player.getHealth()) + " damage to you!" +
                    "<br>The temperature has increased by " + (environment.getTemperature() - initialT) + "°C" + "</html>");
                }
                if (rtn == 0) {
                    moveUsed.setText("Combustion cannot be used with " + opponent.getSpecies().getName());
                }
            }

            // pressurize is only for gases and will increase atmospheric pressure, dealing damage over time to other gases
            if (randomNum == 4) {
                double initialP = environment.getPressure();
                success = opponent.pressurize();

                // check for success
                if (success) {
                    moveUsed.setText("The atmospheric pressure changed by " + (environment.getPressure() - initialP) + " atm and is now " + environment.getPressure() + " atm");
                } else {
                    moveUsed.setText(opponent.getSpecies().getName() + " is not a gas and cannot use this move!");
                }
            }

            // acidify can only be used by acids on anything, adding acid and changing pH causing damage over time if the other substance is a solution, otherwise dealing double damage!
            if (randomNum == 5) {
                double playerHealth = player.getHealth();
                double originalpH;
                if (player.getSpecies() instanceof Solution) {
                    originalpH = ((Solution) player.getSpecies()).getPH();
                } else {
                    originalpH = 0;
                }
                

                success = opponent.addAcid(player);

                // check for success
                if (success) {
                    if (player.getSpecies() instanceof Solution) {
                        moveUsed.setText(opponent.getSpecies().getName() + " (Enemy) added acid to " + player.getSpecies().getName() + " changing the pH by " + (((Solution) player.getSpecies()).getPH() - originalpH));
                    } else {
                        moveUsed.setText(opponent.getSpecies().getName() + " (Enemy) dropped acid on " + player.getSpecies().getName() + " dealing " + (playerHealth - player.getHealth()) + "damage");
                    }
                } else {
                    moveUsed.setText(opponent.getSpecies().getName() + " (Enemy) is not a solution, so it cannot use this move!");
                }
            }

        }
    }

    // Logic to allow Player to make a Move
    private void playerTurn(int choice) {
        boolean success = false;

        // plain collision just deals regular damage to the other player
        if (choice == 1) {
            double HPBefore = opponent.getHealth();
            success = player.plainAttack(opponent);

            // check for success
            if (success) {
                moveUsed.setText("You used plain attack on " + opponent.getSpecies().getName() + " and dealt " + (HPBefore - opponent.getHealth()) + " damage!");
            } else {
                moveUsed.setText("You used plain attack on " + opponent.getSpecies().getName() + " and was unsuccessful!");
            }
        } 

        // dilute will increase the user's strength (if they are a solution) and decrease the enemy's strength (if they are also a solution)
        if (choice == 2) {
            double oldOpponentStrength = opponent.getStrength();
            double oldPlayerStrength = player.getStrength();

            success = player.dilution(opponent);

            // check for success
            if (success) {
                moveUsed.setText("<html>" +
                "You used dilution on " +  opponent.getSpecies().getName() + ", making you weaker and them stronger:<br>" +
                "Player Strength: +" + (player.getStrength() - oldPlayerStrength) + "<br>" +
                "Opponent Strength: -" + (oldOpponentStrength - opponent.getStrength()) +
                "</html>");
            } else {
                moveUsed.setText("Dilution was unsuccessful since one of you weren't a solution!");
            }
        }

        // combustion will deal heavy damage with a chance to damage themselves, only for things with a heat of combustion
        if (choice == 3) {
            // variables for text
            double playerHealth = player.getHealth();
            double enemyHealth = opponent.getHealth();
            double initialT = environment.getTemperature();

            int rtn = player.combust(opponent, environment);
            success = rtn != 0;

            // check for success
            if (rtn == 1) {
                moveUsed.setText("<html>" + "You used incomplete combustion, dealing " + (playerHealth - player.getHealth()) + " damage to yourself and " + (enemyHealth - opponent.getHealth()) + " damage to" + opponent.getSpecies().getName() + "!" +
                "<br>The temperature has increased by " + (environment.getTemperature() - initialT) + "°C" + "</html>");
            }
            if (rtn == 2) {
                moveUsed.setText("<html>" + "You used complete combustion, dealing " + (enemyHealth - opponent.getHealth()) + " damage to " + opponent.getSpecies().getName() + "!" +
                "<br>The temperature has increased by " + (environment.getTemperature() - initialT) + "°C" + "</html>");
            }
            if (rtn == 0) {
                moveUsed.setText("Combustion cannot be used with " + player.getSpecies().getName());
            }
        }

        // pressurize is only for gases and will increase atmospheric pressure, dealing damage over time to other gases
        if (choice == 4) {
            double initialP = environment.getPressure();
            success = player.pressurize();

            // check for success
            if (success) {
                moveUsed.setText("The atmospheric pressure changed by " + (environment.getPressure() - initialP) + " atm and is now " + environment.getPressure() + " atm");
            } else {
                moveUsed.setText("You are not a gas, you cannot use this move!");
            } 
        }

        // acidify can only be used by acids on anything, adding acid and changing pH causing damage over time if the other substance is a solution, otherwise dealing double damage!
        if (choice == 5) {
            double enemyHealth = opponent.getHealth();
            double originalpH;
            if (opponent.getSpecies() instanceof Solution) {
                originalpH = ((Solution) opponent.getSpecies()).getPH();
            } else {
                originalpH = 0;
            }
            
            success = player.addAcid(opponent);

            // check for success
            if (success) {
                if (opponent.getSpecies() instanceof Solution) {
                    moveUsed.setText("You added acid to " + opponent.getSpecies().getName() + " changing the pH by " + (((Solution) opponent.getSpecies()).getPH() - originalpH));
                } else {
                    moveUsed.setText("You dropped acid on " + opponent.getSpecies().getName() + " dealing " + (enemyHealth - opponent.getHealth()) + "damage");
                }
            } else {
                moveUsed.setText("You are not a solution, so you cannot use this move!");
            }
        }

        if (success) {
           isPlayerTurn = false; 
        } else {
            isPlayerTurn = true;
        }
        
    }

    // Logic to take damage from atmosphere or PH
    private void turnEnd() {
        moveUsed.setText("<html>" + player.endTurn().replace("\n", "<br>") + "<br>" + opponent.endTurn().replace("\n", "<br>") + "</html>");
    }

    // Updates the Statistics about the Players & Environment
    private void updateStats() {
        playerStatistics.setText("<html> Player Atomic Statistics <br>" + player.getSpecies().toString().replace("\n", "<br>") + "<br>" + "---------<br>Player Combat Stats: <br>" + player.toString().replace("\n", "<br>") + "</html");
        playerStatistics.setSize(playerStatistics.getPreferredSize());
        enemyStatistics.setText("<html> Enemy Atomic Statistics <br>" + opponent.getSpecies().toString().replace("\n", "<br>") + "<br>" + "---------<br>Enemy Combat Stats: <br>" + opponent.toString().replace("\n", "<br>") + "</html");
        enemyStatistics.setSize(enemyStatistics.getPreferredSize());
        environmentText.setText("<html>" + environment.toString().replace("\n", "<br>") + "</html>");
        environmentText.setSize(environmentText.getPreferredSize());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI();
        });
    }
}
