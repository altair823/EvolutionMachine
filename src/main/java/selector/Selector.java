package selector;

import machine.Machine;
import machine.TypeAMachine;

import java.util.BitSet;
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

    /**
     * Method that count differences between the result value of machine and expected value.
     * @param expectedValue expected bitset value
     * @param machineResult result bitset value of machine
     * @return number of differences
     */
    static int countDifference(BitSet expectedValue, BitSet machineResult){
        int maxBitCount = Math.max(machineResult.length(), expectedValue.length());
        int differenceCount = 0;
        for (int i = 0; i < maxBitCount; i++){
            if (expectedValue.get(i) != machineResult.get(i)){
                differenceCount++;
            }
        }
        return differenceCount;
    }
    /**
     * Method that convert the output of machine to bitset.
     * @param machine Type A machine instance
     * @return Bitset object indicates the result of machine.
     */
    static BitSet changeMachineResultToBitset(Machine machine){
        BitSet machineResult = new BitSet(machine.getOutputUnits().size());
        for (int i = 0; i < machine.getOutputUnits().size(); i++){
            if (machine.getOutputUnits().get(i).getCurrentState()) {
                machineResult.set(i);
            }
        }
        return machineResult;
    }
}
