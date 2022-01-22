package selector;

import machine.TypeAMachine;

import java.util.BitSet;
import java.util.List;

/**
 * Selector that select machines based on how close the result value is to the expected value.
 */
public class ApproximateSelector implements Selector{

    private final BitSet expectedValue;

    /**
     * Constructor of approximate selector.
     * @param expectedValue expected value that represented by Bitset.
     */
    public ApproximateSelector(BitSet expectedValue){
        this.expectedValue = expectedValue;
    }

    @Override
    public TypeAMachine isExpectedValueMachineExisted(List<TypeAMachine> typeAMachineList){
        for (TypeAMachine typeAMachine: typeAMachineList){
            if (this.countDifference(this.changeMachineResultToBitset(typeAMachine)) == 0){
                return typeAMachine;
            }
        }
        return null;
    }

    @Override
    public void select(List<TypeAMachine> typeAMachines, int machineKillCount) {
        int originalListSize = typeAMachines.size();
        this.sortMachinesByResult(typeAMachines);
        this.eliminateMachine(typeAMachines, machineKillCount);
        this.initializeUnits(typeAMachines);
        this.duplicate(typeAMachines, originalListSize);
    }

    /**
     * Method that initialize all machines in given list.
     * @param typeAMachines list of machines
     */
    void initializeUnits(List<TypeAMachine> typeAMachines){
        for (TypeAMachine typeAMachine: typeAMachines){
            typeAMachine.initMachineUnits();
        }
    }

    /**
     * Method that duplicates the machines at the beginning of the list in order.
     * @param typeAMachines list of machines
     */
    void duplicate(List<TypeAMachine> typeAMachines, int maxMachineCount) {
        int originalListSize = typeAMachines.size();
        int existMachineIndex = 0;
        while (typeAMachines.size() < maxMachineCount){
            typeAMachines.add(TypeAMachine.copy(typeAMachines.get(existMachineIndex++ % originalListSize)));
        }
    }

    /**
     * Method that eliminates a given number of machines from the end of the list.
     * @param typeAMachines list of machines
     * @param machineKillCount number of machines that have to eliminate
     */
    void eliminateMachine(List<TypeAMachine> typeAMachines, int machineKillCount){
        for (int i = 0; i < machineKillCount; i++) {
            typeAMachines.remove(typeAMachines.size()-1);
        }
    }

    /**
     * Method that sort list of machines by comparator.
     * @param typeAMachines list of machines
     */
    void sortMachinesByResult(List<TypeAMachine> typeAMachines){
        typeAMachines.sort((machine1, machine2) -> {
            BitSet machine1Result = changeMachineResultToBitset(machine1);
            BitSet machine2Result = changeMachineResultToBitset(machine2);

            return this.countDifference(machine1Result) - this.countDifference(machine2Result);
        });
    }

    /**
     * Method that convert the output of machine to bitset.
     * @param typeAMachine Type A machine instance
     * @return Bitset object indicates the result of machine.
     */
    BitSet changeMachineResultToBitset(TypeAMachine typeAMachine){
        BitSet machineResult = new BitSet(typeAMachine.getOutputUnits().size());
        for (int i = 0; i < typeAMachine.getOutputUnits().size(); i++){
            if (typeAMachine.getOutputUnits().get(i).getCurrentState()) {
                machineResult.set(i);
            }
        }
        return machineResult;
    }

    /**
     * Method that count differences between the result value of machine and expected value.
     * @param machineResult result bitset value of machine
     * @return number of differences
     */
    int countDifference(BitSet machineResult){
        int maxBitCount = Math.max(machineResult.length(), this.expectedValue.length());
        int differenceCount = 0;
        for (int i = 0; i < maxBitCount; i++){
            if (this.expectedValue.get(i) != machineResult.get(i)){
                differenceCount++;
            }
        }
        return differenceCount;
    }
}
