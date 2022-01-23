package machine;

import unorganized.machine.control.Control;
import unorganized.machine.units.Unit;

import java.util.List;

public interface Machine {

    /**
     * Method that make a new pulse to control instance.
     */
    void pulse();

    /**
     * Method that mutate the way a single edge in control delivering a state.
     */
    void mutateEdge();

    /**
     * Method that initialize its unit states but not edges.
     */
    void initMachineUnits();

    /**
     * Getter for input unit list.
     * @return input unit list
     */
    List<Unit> getInputUnits();

    /**
     * Getter for output unit list.
     * @return output unit list
     */
    List<Unit> getOutputUnits();

    /**
     * Getter for Control instance.
     * @return control instance
     */
    Control getControl();

    /**
     * Copy method that copy original machine to a new one.
     * @param originalMachine original machine
     * @return new copied type A machine
     */
    static TypeAMachine copy(Machine originalMachine) {
        return new TypeAMachine(originalMachine.getInputUnits().size(),
                originalMachine.getOutputUnits().size(),
                Control.copy(originalMachine.getControl()));
    }
}
