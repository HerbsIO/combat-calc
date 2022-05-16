package com.hzy.cmbcalc;

public class CombatLevelCalculator {
    private final double xratio;

    CombatLevelCalculator() {
        xratio = 0.325; // Base multiplier for each style
    }
    // [Atk, str, def, hp, range, mage, pray]
    public double calculateLevel(int[] levels) {
        double base = 0.25 * (levels[2] + levels[3] + (levels[6] / 2));
        double melee = xratio * (levels[0] + levels[1]);
        double range = xratio * ((levels[4] / 2) + levels[4]);
        double mage = xratio * ((levels[5] / 2) + levels[5]);
        double level = base + Math.max(Math.max(range, mage), melee);
        return level;
    }
}
