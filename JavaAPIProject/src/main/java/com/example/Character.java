package com.example;

public class Character {
    private double maxHealth;
    private double health;
    private double strength;
    private Species species;
    private Environment environment;
    private int[] moveSet;

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
                strength = species.getDensity() * 10;
            }
            if (species.getPhase().equals("l")) {
                strength = species.getHeatofCombustion() * 0.010;
            }
            if (species.getPhase().equals("g")) {
                strength = Math.abs(environment.getPressure() - species.getVP()) * 10;
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
    public void plainAttack(Character c) {
        c.takeDamage(strength);
        updateStatus();
    }


    // s is the solution that is getting diluted
    public void dilution(Character c) {
        if (species instanceof Solution) {
            double addedV = ((Solution) species).getVolume() * 0.25;
            ((Solution) c.getSpecies()).dilute(addedV);
            c.updateStrength();
            ((Solution) species).dilute(-1 * addedV);
            updateStrength();
        } else {
            System.out.println("This method is ineffective since " + species.getName() + " is not a solution!");
        }
        updateStatus();
    } 

    // combustion, s is the one taking the combustion
    public void combust(Character c, Environment e) {
        if (!(species instanceof Solution)) {
            if (species.getHeatofCombustion() > 0) {
                heal(species.getHeatofCombustion() * 0.08206);
            } if (species.getHeatofCombustion() < 0) {
                c.takeDamage(species.getHeatofCombustion() * 0.08206);
                
            }
            e.deltaT(species.getHeatofCombustion());
        } else {
            System.out.println("This solution cannot be combusted!");
        }
        updateStatus();
    }

    // added hydronium ions
    public void addAcid(Character c) {
        if (c.getSpecies() instanceof Solution) {
            if (((Solution) c.getSpecies()).getAcidity().equals("acidic")) {
                if (c.getSpecies() instanceof Solution) {
                    ((Solution) c.getSpecies()).updatePH(0.25, 0.1); 
                } else {
                    health -= c.getStrength() * 2;
                }
            } else {
                if (c.getSpecies() instanceof Solution) {
                    ((Solution) c.getSpecies()).updatePH(-0.25, 0.1); 
                } else {
                    health -= c.getStrength() * 1.5;
                }
            }
        }
        updateStatus();
    }

}
