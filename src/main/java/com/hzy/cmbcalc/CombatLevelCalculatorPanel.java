package com.hzy.cmbcalc;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CombatLevelCalculatorPanel extends PluginPanel {
    private final Client client;

    private final SkillIconManager iconManager;
    private GridBagConstraints c;
    private JLabel cmbLevel;
    private final CombatLevelCalculator levelCalculator;
    private final Skill[] skills;
    private final ArrayList<FlatTextField> skillFields = new ArrayList<>();
    private final EmptyBorder emptyBorder = new EmptyBorder(12, 4, 12, 4);

    CombatLevelCalculatorPanel(Client client, SkillIconManager iconManager) {
        this.client = client;
        this.iconManager = iconManager;

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        setBorder(new EmptyBorder(20, 10, 10, 10));

        skills = new Skill[] {Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.HITPOINTS, Skill.RANGED, Skill.MAGIC, Skill.PRAYER};

        levelCalculator = new CombatLevelCalculator();
        drawPanel();
    }

    void drawPanel() {
        setLayout(new GridBagLayout());
        c.gridx = 1;
        c.gridy = 0;
        addSkillInputs();
        EmptyBorder emptyBorder = new EmptyBorder(8, 1, 8, 1);
        cmbLevel = new JLabel();
        cmbLevel.setText("3.00");
        cmbLevel.setFont(FontManager.getRunescapeBoldFont());
        cmbLevel.setForeground(Color.WHITE);
        cmbLevel.setBorder(emptyBorder);
        c.gridx = 1;
        c.gridy = 9;
        add(cmbLevel, c);

        JButton button = new JButton("Calculate");
        button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        button.setFont(FontManager.getRunescapeFont());
        button.setForeground(Color.WHITE);
        button.setBorder(emptyBorder);
        button.setFocusPainted(false);
        c.gridx = 1;
        c.gridy = 11;
        MouseListener listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                calc();
                button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                e.consume();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
            }
        };
        button.addMouseListener(listener);
        add(button, c);
        ImageIcon icon = new ImageIcon(ImageUtil.loadImageResource(getClass(), "cmb.png"));
        c.gridx = 0;
        c.gridy = 9;
        JLabel cmbLabel = new JLabel(icon);
        cmbLabel.setBorder(emptyBorder);
        add(cmbLabel, c);

    }

    void addSkillInputs() {
        c.ipady = 6;
        c.ipadx = 6;
        KeyListener listener = new KeyAdapter() {
            boolean consumeKey = false;
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    consumeKey = true;
                }
                if(consumeKey) {
                    e.consume();
                }
            }
            @Override
            public void keyTyped(KeyEvent e ) {
                if(consumeKey) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                consumeKey = false;
            }
        };

        for (Skill skill : skills) {
            c.gridx = 1;
            c.ipady = 4;
            c.ipadx = 4;
            c.gridy++;
            FlatTextField field = new FlatTextField();
            field.setText("1");
            field.setPreferredSize(new Dimension(64, 32));
            field.setBorder(emptyBorder);
            field.setBackground(ColorScheme.DARKER_GRAY_COLOR);
            field.setHoverBackgroundColor(ColorScheme.DARKER_GRAY_HOVER_COLOR);
            field.setForeground(Color.WHITE);
            field.setFont(FontManager.getRunescapeSmallFont());


            field.addKeyListener(listener);

            add(field, c);
            skillFields.add(field);
            c.ipady = 0;
            c.ipadx = 0;
            ImageIcon skillIcon = new ImageIcon(iconManager.getSkillImage(skill));
            c.gridx = 0;
            JLabel skillLabel = new JLabel(skillIcon);
            skillLabel.setBorder(emptyBorder);
            add(skillLabel, c);
        }

    }
    void calc() {
        int[] lvls = {1,1,1,1,1,1,1};
        for(FlatTextField field : skillFields) {
            lvls[skillFields.indexOf(field)] = Integer.parseInt(field.getText());
        }
        double lvl = levelCalculator.calculateLevel(lvls);

        cmbLevel.setText("" + (lvl < 3 ? 3 : (lvl > 126.00 ? 126.00 : lvl)));
    }
}