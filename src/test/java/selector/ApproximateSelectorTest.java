package selector;

import machine.Machine;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApproximateSelectorTest {

    private final List<Machine> machineList = new LinkedList<>();
    private ApproximateSelector approximateSelector;

    ApproximateSelectorTest() throws FileSystemException, FileNotFoundException {
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        machineList.add(new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
    }

    @Test
    void changeMachineResultToBitset() throws FileSystemException, FileNotFoundException {
        Machine machine1 = new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");

        BitSet bitSetResult = Selector.changeMachineResultToBitset(machine1);
        for (int i = 0; i < machine1.getOutputUnits().size(); i++) {
            System.out.print(bitSetResult.get(i) + " ");
        }
        System.out.println();
    }

    @Test
    void countDifferenceTest() throws FileSystemException, FileNotFoundException {
        // set expected value.
        BitSet expectedValue = new BitSet(8);
        expectedValue.set(0);
        expectedValue.set(3);
        expectedValue.set(6);
        approximateSelector = new ApproximateSelector(expectedValue);

        // create machine.
        Machine machine = new Machine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");

        // get result value of machine.
        BitSet bitSetResult = Selector.changeMachineResultToBitset(machine);

        // compare
        assertEquals(3, Selector.countDifference(expectedValue, bitSetResult));
    }

    @Test
    void sortMachinesByResultTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (Machine machine : machineList) {
                machine.mutateEdge();
                machine.mutateEdge();
                machine.mutateEdge();
                machine.pulse();
            }
        }

        // show unsorted list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }

        System.out.println("---------------------");

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(machineList);

        // show sorted list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
    }

    @Test
    void eliminateMachineTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (Machine machine : machineList) {
                machine.mutateEdge();
                machine.mutateEdge();
                machine.mutateEdge();
                machine.pulse();
            }
        }

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(machineList);

        // show sorted list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }

        // eliminate machines.
        approximateSelector.eliminateMachine(machineList, 4);

        System.out.println("---------------------");

        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
    }

    @Test
    void duplicateTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (Machine machine : machineList) {
                machine.mutateEdge();
                machine.mutateEdge();
                machine.mutateEdge();
                machine.pulse();
            }
        }

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(machineList);

        // eliminate machines.
        approximateSelector.eliminateMachine(machineList, 4);

        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }

        // duplicate machines.
        approximateSelector.duplicate(machineList, 8);

        System.out.println("---------------------");
        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
    }

    @Test
    void initializeUnits() {
        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
        System.out.println("---------------------");
        // mutate.
        for (int i = 0; i < 10; i++) {
            for (Machine machine : machineList) {
                machine.mutateEdge();
                machine.mutateEdge();
                machine.mutateEdge();
                machine.pulse();
            }
        }

        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
        System.out.println("---------------------");

        // initialize machines.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.initializeUnits(machineList);

        // show list.
        for (Machine machine : machineList) {
            System.out.println(machine);
        }
        System.out.println("---------------------");
    }

    @Test
    void selectTest() {

        Machine expectedMachine = null;

        // mutate.
        for (int i = 0; i < 1; i++) {
            for (Machine machine : machineList) {
                machine.mutateEdge();
                machine.pulse();
            }
        }
        for (int j = 0; j < 1000; j++) {


            // select approximately.
            approximateSelector = new ApproximateSelector(new BitSet(8));
            approximateSelector.select(machineList, 4);


            // mutate.
            for (int i = 0; i < 1; i++) {
                for (Machine machine : machineList) {
                    machine.mutateEdge();
                    machine.pulse();
                }
            }

            // show list.
            for (Machine machine : machineList) {
                System.out.println(machine);
            }
            System.out.println("---------------------");

            // check there is expected value appears.
            if (approximateSelector.isExpectedValueMachineExisted(machineList) != null){
                expectedMachine = approximateSelector.isExpectedValueMachineExisted(machineList);
                System.out.println("there is expected value appears!\nTrial: " + j);
                break;
            }
        }

        System.out.println("---------------------");
        System.out.println(expectedMachine);
    }
}