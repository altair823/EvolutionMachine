package selector;

import machine.Machine;
import machine.TypeAMachine;

import java.util.List;

/**
 * Selector interface that selects machines running in environment by certain condition.
 * The implements of this interface have to implement selecting condition(comparator).
 * @author altair823
 * @see environment.Environment
 */
public interface Selector {

    /**
     * Method that check if the desired value has appeared.
     * @param typeAMachineList list of machines
     * @return whether the desired value has appeared
     */
    Machine isExpectedValueMachineExisted(List<Machine> typeAMachineList);

    /**
     * Machine selecting method.
     * @param typeAMachines list of type A machine instances
     * @param machineKillCount number of machines that have to eliminate
     */
    void select(List<Machine> typeAMachines, int machineKillCount);
}
