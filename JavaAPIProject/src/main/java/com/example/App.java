package com.example;
import org.json.JSONObject;
import org.json.JSONArray;
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
            System.out.println("1. Ethanol\n2. Octane\n3. Sucrose\n4. Water\n");
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
        System.out.println(chosen);
        Species player = new Species(783, false);
        Environment env = new Environment();
        // options

        if (chosen == 1) {player = new Species(783, false);}
        if (chosen == 2) {player = new Species(977, false);}
        if (chosen == 3) {player = new Species(	5360545, false);}
        if (chosen == 4) {player = new Species(24526, false);}
        if (chosen == 5) {player = new Species(807, false);}
        if (chosen == 6) {player = new Species(702, true);}
        if (chosen == 7) {player = new Species(356, true);}
        if (chosen == 8) {player = new Species(5988, true);}
        if (chosen == 9) {player = new Species(962, false);}
        if (chosen == 10) {player = new Solution(176, 1.0, 1.0, "weakAcid");}
        if (chosen == 11) {player = new Solution(24530, 1.0, 1.0, "weakAcid");}
        if (chosen == 12) {player = new Solution(402, 1.0, 1.0, "weakAcid");}
        
        Character playerChar = new Character(player, env);
        System.out.println(player);
        System.out.println(playerChar);

        
      
    }
}
