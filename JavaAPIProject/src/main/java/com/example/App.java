package com.example;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Scanner;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        // BP, MP, DENSITY API : https://commonchemistry.cas.org/api-overview?utm_campaign=GLO_GEN_ANY_CCH_LDG&utm_medium=EML_CAS%20_ORG&utm_source=EMCommon%20Chemistry%20API
        // PUBCHEM API : https://pubchem.ncbi.nlm.nih.gov/docs/pug-rest#section=Output
        // FORMAT https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/(CIS)/JSON?heading=(PROPERTY)
        Scanner s = new Scanner(System.in);
        
        System.out.println("Welcome, pick a compound to start with!");
        System.out.println("---------------------------------------");
        System.out.println("Select Category: ");
        System.out.println("1. Elements\n2. Compound\n3. Solutions\n");
        int result = 0;
        while (result != 1 && result != 2 && result != 3) {
            result = s.nextInt();
            s.nextLine();
        }

        int chosen = 0;
        // selected elements
        if (result == 1) {
            System.out.println("---------------------------------------");
            System.out.println("Select Element: ");
            System.out.println("1. Hydrogen\n2. Oxygen\n3. Sodium\n4. Chlorine\n5. Iodine\n");
            chosen += s.nextInt();
            s.nextLine(); 
            while (chosen < 1 || chosen > 5) {
                chosen = 0;
                chosen += s.nextInt();
                s.nextLine(); 
            }
        }

        // selected compounds
        if (result == 2) {
            chosen = 5;
            System.out.println("---------------------------------------");
            System.out.println("Select Element: ");
            System.out.println("1. Ethanol\n2. Octane\n3. Acetone\n4. Water\n");
            chosen += s.nextInt();
            s.nextLine(); 
            while (chosen < 5 || chosen > 9) {
                chosen = 5;
                chosen += s.nextInt();
                s.nextLine(); 
            }
        }

        // selected solutions
        if (result == 3) {
            chosen = 9;
            System.out.println("---------------------------------------");
            System.out.println("Select Element: ");
            System.out.println("1. Acetic Acid\n2. Hydrazoic Acid\n3. Hydrosulfuric Acid\n");
            chosen += s.nextInt();
            s.nextLine(); 
            while (chosen < 9 || chosen > 12) {
                chosen = 9;
                chosen += s.nextInt();
                s.nextLine();
            }
        }

        // assigning
        Species player = chooseSpecies(chosen);
        Environment env = new Environment();
        // options
        
        Character playerChar = new Character(player, env);
        System.out.println(player);
        System.out.println(playerChar);


        chosen = (int) (Math.random() * 12) + 1;
        Species enemy = chooseSpecies(chosen);
        Character enemyChar = new Character(enemy, env);
        System.out.println(enemy);
        System.out.println(enemyChar);
        
      


        // combat
        boolean inCombat = true;
        while (inCombat) {
            // player's turn
            int move = 0;
            boolean successful = false;
            // player goes
            System.out.println("Player's Turn");
            while (!successful) {
                while (move < 1 || move > 5) {
                    System.out.println("Moves: \n1. Basic Collision\n2. Dilute (Solutions Only)\n3. Combust (Organic Compounds Only)\n4. Pressurize (Gases Only)\n5. Acidify (Solutions Only)\n");
                    move = s.nextInt();
                    s.nextLine();
                }
                successful = makeMove(move, playerChar, enemyChar, env);
                move = 0;
            }
            playerChar.endTurn();
            System.out.println("----------------------");
            System.err.println("Enemy's Stats: ");
            System.out.println(enemyChar);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
                e.getStackTrace();
            }
            System.out.println("----------------------");
            System.out.println("Enemy's Turn");
            successful = false;
            // enemy goes
            while (!successful) {
                move = (int) (Math.random() * 5) + 1;
                successful = makeMove(move, enemyChar, playerChar, env);
            }
            enemyChar.endTurn();
            System.out.println("----------------------");
            System.err.println("Player Stats: ");
            System.out.println(playerChar);
            System.out.println("----------------------");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
                e.getStackTrace();
            }
            if (playerChar.getHealth() == 0) {
                System.out.println("Player Loses! Play Again? (y/n)");
                String response = s.nextLine();
                if (response.equals("y")) {
                    inCombat = true;
                    playerChar = new Character(player, new Environment());
                    chosen = (int) (Math.random() * 12) + 1;
                    enemy = chooseSpecies(chosen);
                    enemyChar = new Character(enemy, env);
                    System.out.println(enemy);
                    System.out.println(enemyChar);
                } else {
                    inCombat = false;
                }
                
            }
            if (enemyChar.getHealth() == 0) {
                System.out.println("Player Wins! Play Again? (y/n)");
                String response = s.nextLine();
                if (response.equals("y")) {
                    inCombat = true;
                    playerChar = new Character(player, new Environment());                    
                    chosen = (int) (Math.random() * 12) + 1;
                    enemy = chooseSpecies(chosen);
                    enemyChar = new Character(enemy, env);
                    System.out.println(enemy);
                    System.out.println(enemyChar);
                } else {
                    inCombat = false;

                }
            }
        }
    }

    public static Species chooseSpecies(int num) throws IOException {
        Species player = new Species(783);
        int chosen = num;
        if (chosen == 1) {player = new Species(783);}
        if (chosen == 2) {player = new Species(977);}
        if (chosen == 3) {player = new Species(	5360545);}
        if (chosen == 4) {player = new Species(24526);}
        if (chosen == 5) {player = new Species(807);}
        if (chosen == 6) {player = new Species(702);}
        if (chosen == 7) {player = new Species(356);}
        if (chosen == 8) {player = new Species(	180);}
        if (chosen == 9) {player = new Species(962);}
        if (chosen == 10) {player = new Solution(176, 1.0, 1.0);}
        if (chosen == 11) {player = new Solution(24530, 1.0, 1.0);}
        if (chosen == 12) {player = new Solution(402, 1.0, 1.0);}
        return player;
    }

    public static boolean makeMove(int moveNum, Character c1, Character c2, Environment env) {
        boolean successfulMove = false;
        if (moveNum == 1) {
            successfulMove = c1.plainAttack(c2);
        }
        if (moveNum == 2) {
            successfulMove = c1.dilution(c2);
        }
        if (moveNum == 3) {
            successfulMove = c1.combust(c2, env);
        }
        if (moveNum == 4) {
            successfulMove = c1.pressurize();
        }
        if (moveNum == 5) {
            successfulMove = c1.addAcid(c2);
        }
        return successfulMove;
    }
}
