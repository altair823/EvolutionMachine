package selector;

import machine.TypeAMachine;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystemException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApproximateSelectorTest {

    private final List<TypeAMachine> typeAMachineList = new LinkedList<>();
    private ApproximateSelector approximateSelector;

    ApproximateSelectorTest() throws FileSystemException, FileNotFoundException {
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
        typeAMachineList.add(new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf"));
    }

    @Test
    void changeMachineResultToBitset() throws FileSystemException, FileNotFoundException {
        TypeAMachine typeAMachine1 = new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");

        BitSet bitSetResult = approximateSelector.changeMachineResultToBitset(typeAMachine1);
        for (int i = 0; i < typeAMachine1.getOutputUnits().size(); i++) {
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
        TypeAMachine typeAMachine = new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");

        // get result value of machine.
        BitSet bitSetResult = approximateSelector.changeMachineResultToBitset(typeAMachine);

        // compare
        assertEquals(3, approximateSelector.countDifference(bitSetResult));
    }

    @Test
    void sortMachinesByResultTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (TypeAMachine typeAMachine : typeAMachineList) {
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.pulse();
            }
        }

        // show unsorted list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }

        System.out.println("---------------------");

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(typeAMachineList);

        // show sorted list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
    }

    @Test
    void eliminateMachineTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (TypeAMachine typeAMachine : typeAMachineList) {
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.pulse();
            }
        }

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(typeAMachineList);

        // show sorted list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }

        // eliminate machines.
        approximateSelector.eliminateMachine(typeAMachineList, 4);

        System.out.println("---------------------");

        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
    }

    @Test
    void duplicateTest() {

        // mutate.
        for (int i = 0; i < 10; i++) {
            for (TypeAMachine typeAMachine : typeAMachineList) {
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.pulse();
            }
        }

        // sort.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.sortMachinesByResult(typeAMachineList);

        // eliminate machines.
        approximateSelector.eliminateMachine(typeAMachineList, 4);

        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }

        // duplicate machines.
        approximateSelector.duplicate(typeAMachineList, 8);

        System.out.println("---------------------");
        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
    }

    @Test
    void initializeUnits() {
        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
        System.out.println("---------------------");
        // mutate.
        for (int i = 0; i < 10; i++) {
            for (TypeAMachine typeAMachine : typeAMachineList) {
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.mutateEdge();
                typeAMachine.pulse();
            }
        }

        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
        System.out.println("---------------------");

        // initialize machines.
        approximateSelector = new ApproximateSelector(new BitSet(8));
        approximateSelector.initializeUnits(typeAMachineList);

        // show list.
        for (TypeAMachine typeAMachine : typeAMachineList) {
            System.out.println(typeAMachine);
        }
        System.out.println("---------------------");
    }

    @Test
    void selectTest() {

        TypeAMachine expectedMachine = null;

        // mutate.
        for (int i = 0; i < 1; i++) {
            for (TypeAMachine typeAMachine : typeAMachineList) {
                typeAMachine.mutateEdge();
                typeAMachine.pulse();
            }
        }
        for (int j = 0; j < 1000; j++) {


            // select approximately.
            approximateSelector = new ApproximateSelector(new BitSet(8));
            approximateSelector.select(typeAMachineList, 4);


            // mutate.
            for (int i = 0; i < 1; i++) {
                for (TypeAMachine typeAMachine : typeAMachineList) {
                    typeAMachine.mutateEdge();
                    typeAMachine.pulse();
                }
            }

            // show list.
            for (TypeAMachine typeAMachine : typeAMachineList) {
                System.out.println(typeAMachine);
            }
            System.out.println("---------------------");

            // check there is expected value appears.
            if (approximateSelector.isExpectedValueMachineExisted(typeAMachineList) != null){
                expectedMachine = approximateSelector.isExpectedValueMachineExisted(typeAMachineList);
                System.out.println("there is expected value appears!\nTrial: " + j);
                break;
            }
        }

        System.out.println("---------------------");
        System.out.println(expectedMachine);
    }
}