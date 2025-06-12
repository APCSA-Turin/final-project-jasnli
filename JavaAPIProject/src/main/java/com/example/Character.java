package com.example;

public class Character {

    // initialize character stats
    private double maxHealth; // maximum health
    private double health; // current health
    private double strength; //  strength
    private Species species; // the Species class of the character
    private Environment environment; //  the Environment the character is in

    // character constructor
    public Character(Species s, Environment env) {
        this.species = s; // species
        health = (10.0 * Math.sqrt((species.getMolecularWeight()) * 8.314)) + (species.getBoilingPoint() +273.15) * 0.08206; // max health calculation
        maxHealth = health; // set max health
        environment = env; // environment
        updateStrength(); // strength is based off of several factors
    }

    // update strength (based off of factors, adds some nuance to the game)
    public void updateStrength() {
        // if it is a solution
        if (species instanceof Solution) {
                // strength will be based on concentration and pH
                strength = ((Solution) species).getConcentration() * (7 - ((Solution) species).getPH()) * Math.PI + ((Solution) species).getpKa();
            } else {
                // if it is a solid, it will be based on density
                if (species.getPhase().equals("s")) {
                    strength = species.getDensity() * 25;
                }
                // if it is a liquid, it will be based on heat of combustion and density
                if (species.getPhase().equals("l")) {
                    strength =  species.getHeatofCombustion() * 0.010 + species.getDensity() * 10.0;
                }
                // if it is a gas, it will be based on vapor pressure and atmospheric pressure
                if (species.getPhase().equals("g")) {
                    strength = Math.abs(environment.getPressure() * 1.2 - (species.getVP())) * 10;
                }
            }   
    }
    

    // getter methods
    public double getMaxHealth() {return maxHealth;}
    public Species getSpecies() {return species;}
    public double getHealth() {return health;}
    public double getStrength() {return strength;}

    // print method
    public String toString() {
        String str = "";
        str += "Health: " + Science.round(health, 2) +"\n";
        str += "Strength: " + Science.round(strength, 2) +"\n";
        return str;
    }

    // taking damage
    public void takeDamage(double dmg) {
        // solids will take less damage
        if (species.getPhase().equals("s")) {
            dmg *= 0.80;
        }
        health -= dmg;

        // when health drops below 0, it goes to 0 (no negatives)
        if (health <= 0) {
            health = 0;
        }
        updateStatus();
    }

    // used after most methods
    public void updateStatus() {
        // checks and updates the phase based on the environment
        species.setPhase(environment.getTemperature());
    }
    
    // basic attack based on strength, returns true if successful
    public boolean plainAttack(Character c) {
        c.takeDamage(strength);
        return true;
    }


    // basic dilution ONLY if both characters are solutions, returns true if dilutes successfully, otherwise return false
    public boolean dilution(Character c) {
        if (species instanceof Solution && c.getSpecies() instanceof Solution) {
            // adds 25% of the current volume, which will increase your concentration and decrease theirs, making you stronger and them weaker
            double addedV = ((Solution) species).getVolume() * 0.25;
            ((Solution) c.getSpecies()).dilute(addedV);
            c.updateStrength();
            ((Solution) species).dilute(-1 * addedV);
            updateStrength();
            return true;
        } else {
            // if either character is not a solution, it is ineffective and false
            return false;
        }
    } 

    // combustion, for organic substances only
    public int combust(Character c, Environment e) {
        if (species.combustable()) {
                // 5050 chance (random 1-2)
                int rtn = 0;
                int randomNum = (int) (Math.random() * 2) + 1;
                // get the damage taken first, based off of heat of combustion
                double damageTaken = species.getHeatofCombustion() * 0.08206;

                // 1 fore incomplete combustion (harming self)
                if (randomNum == 1) {
                    takeDamage(damageTaken * 0.20);
                    c.takeDamage(damageTaken * 0.80);
                    // return of 1 implemented for the GUI text
                    rtn = 1;
                } else {
                    // 2 for complete combustion (harming enemy)
                    c.takeDamage(damageTaken);
                    // return of 2 implemented for the GUI text
                    rtn = 2;
                }
                // changing temperature based off of the heat and the heat capacity of air
                e.deltaT(species.getHeatofCombustion() / (41.8)); 
                // return value for GUI
                return rtn;
        } else {
            // 0 means failure
            return 0;
        }
    }

    // adds hydronium ions to the enemy, only if you are a solution
    // changes pH if enemy is also a solution
    // deals double damage if enemy is not a solution
    public boolean addAcid(Character c) {
        // if you are a solution, continue
        if (species instanceof Solution) {
            if (((Solution) species).getAcidity().equals("acidic")) {
                // acid adding (implemented most of the time)
                if (c.getSpecies() instanceof Solution) {
                    // if enemy is also a solution, change their pH
                    ((Solution) c.getSpecies()).updatePH(1/((Solution) species).getpKa(), 0.1); 
                    return true;
                } else {
                    // otherwise deal double damage
                    c.takeDamage(strength * 2);
                    return true;
                }
            } else {
                // NOT USED (for now)
                if (c.getSpecies() instanceof Solution) {
                    double initialPH = ((Solution) c.getSpecies()).getPH();
                    ((Solution) c.getSpecies()).updatePH(-1/((Solution) species).getpKa(), 0.1); 
                    return true;
                } else {
                    c.takeDamage(strength * 2);
                    return true;
                }
            }
        // if you are not a solution, you cannot use this move
        } else {
            return false;
        }
    }

    // pressurize
    public boolean pressurize() {
        // only if the character is a gas
        if (species.getPhase().equals("g")) {
            environment.deltaP(species.getVP());
            return true;
        } else {
            return false;
        }   
    }

    // end turn
    public String endTurn() {
        // updates the status and strength
        updateStatus();
        updateStrength();
        String str = "";

        // if it is a gas, it will take damage based on the pressure
        if (species.getPhase().equals("g")) {
            takeDamage(Math.abs(environment.getPressure() - 1.0) * species.getMolecularWeight() * 0.5);
            str += species.getName() + " took " + Science.round((Math.abs(environment.getPressure() - 1.0) * species.getMolecularWeight() * 0.52), 2) + " damage from the atmospheric pressure!\n";
        }

        // if it is a solution, it will take damage based on the pH
        if (species instanceof Solution) {
            double originalHP = health;
            takeDamage(Math.abs((((Solution) species).getOriginalPH() - ((Solution) species).getPH())) * ((Solution) species).getConcentration() * ((Solution) species).getpKa());
            str += species.getName() + " took " + Science.round(originalHP - health, 2) + " damage from the pH imbalance!";
        }

        // if it is neither, return the following messageS
        if (str.equals("")) {
            str = "No damage was taken by " + species.getName() + " from a pH imbalance nor atmospheric pressure";
        }
        return str;

    }

}
