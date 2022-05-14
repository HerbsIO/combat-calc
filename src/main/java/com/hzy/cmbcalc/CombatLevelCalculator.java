package com.hzy.cmbcalc;

public class CombatLevelCalculator {
    private final double xratio;
    private double base, melee, range, mage, level;
    CombatLevelCalculator() {
        xratio = 0.325; // Base multiplier for each style
    }
    // [Atk, str, def, hp, range, mage, pray]
    public double calculateLevel(int[] levels) {
        base = 0.25 * (levels[2] + levels[3] + Math.floor(levels[6] / 2));
        melee = xratio * (levels[0] + levels[1]);
        range = xratio * (Math.floor(levels[4] / 2) + levels[4]);
        mage = xratio * (Math.floor(levels[5] / 2) + levels[5]);
        level = base + Math.max(Math.max(range, mage), melee);
        return level;
    }
}
