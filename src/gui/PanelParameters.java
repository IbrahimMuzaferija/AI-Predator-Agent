package gui;

import ga.geneticOperators.Mutation;
import ga.geneticOperators.MutationNormalDistribution;
import ga.geneticOperators.MutationRandom;
import ga.geneticOperators.Recombination;
import ga.geneticOperators.RecombinationOneCut;
import ga.geneticOperators.RecombinationUniform;
import ga.selectionMethods.RouletteWheel;
import ga.selectionMethods.SelectionMethod;
import ga.selectionMethods.Tournament;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import AICapture.PredatorIndividual;
import AICapture.AICaptureProblem;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;
    public static final String SEED = "1";
    public static final String POPULATION_SIZE = "200";
    public static final String GENERATIONS = "1000";
    public static final String TOURNAMENT_SIZE = "4";
    public static final String PROB_RECOMBINATION = "0.5";
    public static final String PROB_MUTATION = "0.5";
    public static final String MUTATION_PARAM = "0.04";
    JTextField textFieldSeed = new JTextField(SEED, TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE, TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS, TEXT_FIELD_LENGHT);
    String[] selectionMethods = {"Tournament", "Roulette"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField textFieldTournamentSize = new JTextField(TOURNAMENT_SIZE, TEXT_FIELD_LENGHT);

    String[] recombinationMethods = {"One cut", "Uniform"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION, TEXT_FIELD_LENGHT);

    String[] mutationMethods = {"Normal Fixed SD", "Random"};
    JComboBox comboBoxMutationMethods = new JComboBox(mutationMethods);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION, TEXT_FIELD_LENGHT);
    JTextField textFieldDelta = new JTextField(MUTATION_PARAM, TEXT_FIELD_LENGHT);

    public PanelParameters() {
        title = "Genetic algorithm parameters";

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);

        labels.add(new JLabel("Mutation method: "));
        valueComponents.add(comboBoxMutationMethods);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);

        labels.add(new JLabel("Mutation param: "));
        valueComponents.add(textFieldDelta);

        configure();
    }

    public void actionPerformedSelectionMethods(ActionEvent e) {
        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
    }

    public SelectionMethod<PredatorIndividual, AICaptureProblem> getSelectionMethod() {
        switch (comboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament<>(
                        Integer.parseInt(textFieldN.getText()),
                        Integer.parseInt(textFieldTournamentSize.getText()));
            case 1:
                return new RouletteWheel<>(Integer.parseInt(textFieldN.getText()));
        }
        return null;
    }

    public Recombination<PredatorIndividual> getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationOneCut<>(recombinationProb);
            case 1:
                return new RecombinationUniform<>(recombinationProb);
        }
        return null;
    }

    public Mutation<PredatorIndividual> getMutationMethod() {
        double mutationProbability = Double.parseDouble(textFieldProbMutation.getText());
        double mutationParam = Double.parseDouble(textFieldDelta.getText());
        switch (comboBoxMutationMethods.getSelectedIndex()) {
            case 0:
                return new MutationNormalDistribution<>(mutationProbability, mutationParam);
            case 1:
                return new MutationRandom<>(mutationProbability);
        }
        return null;
    }
}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.actionPerformedSelectionMethods(e);
    }
}

class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
