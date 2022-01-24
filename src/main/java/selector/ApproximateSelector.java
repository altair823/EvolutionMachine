package selector;

import machine.Machine;

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
    public Machine isExpectedValueMachineExisted(List<Machine> machineList){
        for (Machine machine: machineList){
            if (Selector.countDifference(this.expectedValue, Selector.changeMachineResultToBitset(machine)) == 0){
                return machine;
            }
        }
        return null;
    }

    @Override
    public void select(List<Machine> machines, int machineKillCount) {
        int originalListSize = machines.size();
        this.sortMachinesByResult(machines);
        this.eliminateMachine(machines, machineKillCount);
        this.initializeUnits(machines);
        this.duplicate(machines, originalListSize);
    }

    /**
     * Method that initialize all machines in given list.
     * @param machines list of machines
     */
    void initializeUnits(List<Machine> machines){
        for (Machine machine: machines){
            machine.initMachineUnits();
        }
    }

    /**
     * Method that duplicates the machines at the beginning of the list in order.
     * @param machines list of machines
     */
    void duplicate(List<Machine> machines, int maxMachineCount) {
        int originalListSize = machines.size();
        int existMachineIndex = 0;
        while (machines.size() < maxMachineCount){
            machines.add(Machine.copy(machines.get(existMachineIndex++ % originalListSize)));
        }
    }

    /**
     * Method that eliminates a given number of machines from the end of the list.
     * @param machines list of machines
     * @param machineKillCount number of machines that have to eliminate
     */
    void eliminateMachine(List<Machine> machines, int machineKillCount){
        for (int i = 0; i < machineKillCount; i++) {
            machines.remove(machines.size()-1);
        }
    }

    /**
     * Method that sort list of machines by its result.
     * @param machines list of machines
     */
    void sortMachinesByResult(List<Machine> machines){
        machines.sort((machine1, machine2) -> {
            BitSet machine1Result = Selector.changeMachineResultToBitset(machine1);
            BitSet machine2Result = Selector.changeMachineResultToBitset(machine2);

            return Selector.countDifference(this.expectedValue, machine1Result)
                    - Selector.countDifference(this.expectedValue, machine2Result);
        });
    }
}
