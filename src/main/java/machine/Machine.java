package machine;

import unorganized.machine.control.Control;
import unorganized.machine.units.Unit;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.List;

/**
 * Interface for machines that evolve in given environment.
 * @author altair823
 */
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

    /**
     * Interface for Builder of Machine instances.
     */
    interface MachineBuilder{

        /**
         * Setter for a number of input units.
         * @param inputUnitCount a number of input units
         * @return MachineBuilder instance
         */
        MachineBuilder setInputUnitCount(int inputUnitCount);

        /**
         * Setter for a number of output units.
         * @param outputUnitCount a number of output units
         * @return MachineBuilder instance
         */
        MachineBuilder setOutputUnitCount(int outputUnitCount);

        /**
         * Setter for a string that indicates path of unit layout file of machines.
         * @param unitLayoutFile unit layout file path
         * @return MachineBuilder instance
         */
        MachineBuilder setUnitLayoutFile(String unitLayoutFile);

        /**
         * Setter for a Control instance.
         * @param control Control instance
         * @return MachineBuilder instance
         */
        MachineBuilder setControl(Control control);

        /**
         * Method that builds a new Machine instance.
         * @return a new Machine instance
         * @throws FileSystemException wrong ULF file
         * @throws FileNotFoundException no ULF file
         */
        Machine build() throws FileSystemException, FileNotFoundException;
    }
}
