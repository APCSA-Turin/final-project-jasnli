package com.example;

public class Character {
    private double maxHealth;
    private double health;
    private double strength;
    private Species species;
    private Environment environment;

    public Character(Species s, Environment env) {
        this.species = s;
        health = (10.0 * Math.sqrt((species.getMolecularWeight()) * 8.314)) + (species.getBoilingPoint() +273.15) * 0.08206;
        maxHealth = health;
        environment = env;
        updateStrength();
    }

    // update strength
    public void updateStrength() {
     if (species instanceof Solution) {
            strength = ((Solution) species).getConcentration() * (7 - ((Solution) species).getPH()) * Math.PI + ((Solution) species).getpKa();
        } else {
            if (species.getPhase().equals("s")) {
                strength = species.getDensity() * 25;
            }
            if (species.getPhase().equals("l")) {
                strength =  species.getHeatofCombustion() * 0.010 + species.getDensity() * 10.0;
            }
            if (species.getPhase().equals("g")) {
                strength = Math.abs(environment.getPressure() * 1.2 - (species.getVP())) * 10;
            }
        }   
    }
    

    // getter
    public Species getSpecies() {return species;}
    public double getHealth() {return health;}
    public double getStrength() {return strength;}

    public String toString() {
        String str = "";
        str += "Health: " + health +"\n";
        str += "Strength: " + strength +"\n";
        return str;
    }

    // damaging & healing
    public void takeDamage(double dmg) {
        if (species.getPhase().equals("s")) {
            dmg *= 0.80;
        }
        health -= dmg;
        if (health <= 0) {
            health = 0;
        }
        updateStatus();
    }
    public void heal(double health) {
        if (health <= maxHealth) {
            this.health += health;
        } if (health > maxHealth) {
            this.health = maxHealth;
        }
        updateStatus();
    }
    public void updateStatus() {
        species.setPhase(environment.getTemperature());
        if (species instanceof Solution) {
            ((Solution) species).getPH();
        }
    }

    // moves : 
    
    // attack character c
    public boolean plainAttack(Character c) {
        c.takeDamage(strength);
        System.out.println("You deal " + strength + " damage to " + c.getSpecies().getName());
        return true;
    }


    // s is the solution that is getting diluted
    public boolean dilution(Character c) {
        if (species instanceof Solution) {
            double addedV = ((Solution) species).getVolume() * 0.25;
            ((Solution) c.getSpecies()).dilute(addedV);
            c.updateStrength();
            ((Solution) species).dilute(-1 * addedV);
            updateStrength();
            System.out.println("This solution has diluted the other solution with " + addedV + " L of solution, lowering their strength and increasing our strength!");
            System.out.println("New Strength: " + strength);
            System.out.println("New Concentration: " + ((Solution) species).getConcentration());
            return true;
        } else {
            System.out.println("This method is ineffective since " + species.getName() + " is not a solution!");
            return false;
        }
    } 

    // combustion, s is the one taking the combustion
    public boolean combust(Character c, Environment e) {
        if (species.combustable()) {
                int randomNum = (int) (Math.random() * 2) + 1;
                double damageTaken = species.getHeatofCombustion() * 0.08206;
                if (randomNum == 1) {
                    takeDamage(damageTaken * 0.20);
                    c.takeDamage(damageTaken * 0.80);
                    System.out.println("Incomplete Combustion! Dealt " + (damageTaken * 0.20) + " damage to self and " + (damageTaken * 0.80) + " damage to " + c.getSpecies().getName());
                } else {
                    c.takeDamage(damageTaken);
                    System.out.println("Complete Combustion! Dealt " + (damageTaken) + " damage to " + c.getSpecies().getName());
                }
                e.deltaT(species.getHeatofCombustion() / (41.8)); 
                System.out.println("The temperature has increased by " + (species.getHeatofCombustion() / (41.8)) + ", the current temperature is " + environment.getTemperature() + "°C.");
                return true;
        } else {
            System.out.println("This solution cannot be combusted!");
            return false;
        }
    }

    // added hydronium ions
    public boolean addAcid(Character c) {
        if (species instanceof Solution) {
            if (((Solution) species).getAcidity().equals("acidic")) {
                // acid adding
                if (c.getSpecies() instanceof Solution) {
                    double initialPH = ((Solution) c.getSpecies()).getPH();
                    ((Solution) c.getSpecies()).updatePH(1/((Solution) species).getpKa(), 0.1); 
                    System.out.println("Added acid to " + c.getSpecies().getName() + " changing their pH by " + (((Solution) c.getSpecies()).getPH() - initialPH));
                    return true;
                } else {
                    c.takeDamage(strength * 1.25);
                    System.out.println(c.getSpecies().getName() + " is not a solution, so " + c.getSpecies().getName() + " takes " + (strength * 1.25) + " damage");
                    return true;
                }
            } else {
                // base adding
                if (c.getSpecies() instanceof Solution) {
                    double initialPH = ((Solution) c.getSpecies()).getPH();
                    ((Solution) c.getSpecies()).updatePH(-1/((Solution) species).getpKa(), 0.1); 
                    System.out.println("Added base to " + c.getSpecies().getName() + " changing their pH by " + (((Solution) c.getSpecies()).getPH() - initialPH));
                    return true;
                } else {
                    c.takeDamage(strength * 1.25);
                    System.out.println(c.getSpecies().getName() + " is not a solution, so " + c.getSpecies().getName() + " takes " + (strength * 1.25) + " damage");
                    return true;
                }
            }
        } else {
            System.out.println(species.getName() + " is not a solution, you cannot use this move!");
            return false;
        }
    }

    // pressurize
    public boolean pressurize() {
        if (species.getPhase().equals("g")) {
            environment.deltaP(species.getVP());
            System.out.println("The atmospheric pressure has increased by " + species.getVP() * 0.2 + " atm. The current pressure is " + environment.getPressure() + " atm");
            return true;
        } else {
            return false;
        }   
    }

    // end turn
    public void endTurn() {
        updateStatus();
        updateStrength();
        if (species.getPhase().equals("g")) {
            takeDamage(Math.abs(environment.getPressure() - 1.0) * species.getMolecularWeight() * 0.5);
            System.out.println(Science.round((Math.abs(environment.getPressure() - 1.0) * species.getMolecularWeight() * 0.52), 2) + " damage was taken from the atmospheric pressure!");
        }
        if (species instanceof Solution) {
            takeDamage(Math.abs((((Solution) species).getOriginalPH() - ((Solution) species).getPH())) * ((Solution) species).getConcentration() * ((Solution) species).getpKa());
            System.out.println(Science.round((Math.abs((((Solution) species).getOriginalPH() - ((Solution) species).getPH())) * ((Solution) species).getConcentration() * ((Solution) species).getpKa()), 2) + " damage was taken from the pH imbalance!");
        }

    }

}
