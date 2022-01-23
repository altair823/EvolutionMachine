package environment;

import machine.Machine;
import selector.Selector;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.LinkedList;
import java.util.List;

/**
 * Environment class that operates multiple Control instances and delete it in certain condition.
 * In running situation, the machine with the state furthest from the desired condition disappears
 * and the number of the rest is increased instead.
 */
public class Environment {
    private final List<Machine> machines = new LinkedList<>();
    private final Selector selector;
    private final int eliminateCount;

    /**
     * Constructor for a new Environment object.
     * @param inputUnitCount the number of input units
     * @param outputUnitCount the number of output units
     * @param aTypeMachineCount the number of type A machines
     * @param eliminateCount the number of machines to eliminate
     * @param selector selector instance
     * @param unitLayoutFilePath unit layout file path
     * @throws FileSystemException wrong ULF file
     * @throws FileNotFoundException no ULF file
     */
    private Environment(int inputUnitCount,
                        int outputUnitCount,
                        int aTypeMachineCount,
                        int eliminateCount,
                        Selector selector,
                        String unitLayoutFilePath,
                        Machine.MachineBuilder machineBuilder)
            throws FileSystemException, FileNotFoundException {

        for (int i = 0; i < aTypeMachineCount; i++){
            machines.add(machineBuilder
                    .setInputUnitCount(inputUnitCount)
                    .setOutputUnitCount(outputUnitCount)
                    .setUnitLayoutFile(unitLayoutFilePath)
                    .build());
        }
        this.selector = selector;
        this.eliminateCount = eliminateCount;
    }

    /**
     * Method that executes an evolve method a given number of times.
     * @param maxIterateCount a number that repeats the evolution of machines.
     * @return the number of trials that found the desired machine, or -1 if not found.
     */
    public int evolveIterate(int maxIterateCount){
        for (int i = 0; i < maxIterateCount; i++){
            if (this.evolve()){
                return i;
            }
        }
        return -1;
    }

    /**
     * Method that executes a single evolution of machines.
     * @return true if a machine that meets the condition appears, otherwise false.
     */
    boolean evolve(){
        this.mutate();
        this.pulse();
        for (Machine machine : this.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        if (this.isSatisfyExpectedValue() != null){
            return true;
        }
        this.select();
        return false;
    }

    /**
     * Method that makes a pulse and send it to all existing machines.
     */
    void pulse(){
        for (Machine machine : this.machines){
            machine.pulse();
        }
    }

    /**
     * Method that reverses a single edge of each machine.
     */
    void mutate(){
        for (Machine typeAMachine: this.machines){
            typeAMachine.mutateEdge();
        }
    }

    /**
     * Method that increases the number of machines by selecting the machines closest to the desired condition.
     * Eliminate a set number of distant machines under the desired conditions,
     * and increase the number of nearby machines under the desired conditions by that amount.
     */
    void select(){
        this.selector.select(this.machines, this.eliminateCount);
    }

    /**
     * Method that checks whether each machine satisfies the conditions of the environment
     * @return the machine instance that satisfies the conditions. null if not.
     */
    Machine isSatisfyExpectedValue(){
        return this.selector.isExpectedValueMachineExisted(this.machines);
    }

    /**
     * Getter for list of machines.
     * @return list of machines
     */
    public List<Machine> getMachines(){
        return this.machines;
    }

    /**
     * Builder class that create Environment class.
     */
    public static class EnvironmentBuilder{
        private int inputUnitCount;
        private int outputUnitCount;
        private int machineCount;
        private int eliminateCount;
        private Selector selector;
        private final String unitLayoutFilePath;
        private Machine.MachineBuilder machineBuilder;

        /**
         * Constructor for Environment Builder.
         * @param unitLayoutFilePath unit layout file path
         */
        public EnvironmentBuilder(String unitLayoutFilePath){
            this.unitLayoutFilePath = unitLayoutFilePath;
        }

        /**
         * Setter for inputUnitCount.
         * @param inputUnitCount number of input units.
         * @return Environment builder instance
         */
        public EnvironmentBuilder setInputUnitCount(int inputUnitCount){
            this.inputUnitCount = inputUnitCount;
            return this;
        }

        /**
         * Setter for outputUnitCount.
         * @param outputUnitCount number of output units.
         * @return Environment builder instance
         */
        public EnvironmentBuilder setOutputUnitCount(int outputUnitCount){
            this.outputUnitCount = outputUnitCount;
            return this;
        }

        /**
         * Setter for machine count.
         * @param machineCount number of machines
         * @return Environment builder instance
         */
        public EnvironmentBuilder setMachineCount(int machineCount){
            this.machineCount = machineCount;
            return this;
        }

        /**
         * Setter for selector of environment.
         * @param selector new selector
         * @return Environment builder instance
         */
        public EnvironmentBuilder setSelector(Selector selector){
            this.selector = selector;
            return this;
        }

        /**
         * Setter for count of machines to eliminate.
         * @param eliminateCount number of machines to eliminate.
         * @return Environment builder instance
         */
        public EnvironmentBuilder setEliminateCount(int eliminateCount){
            this.eliminateCount = eliminateCount;
            return this;
        }

        /**
         * Setter for builder of a new Machine.
         * @param machineBuilder MachineBuilder instance
         * @return Environment builder instance
         */
        public EnvironmentBuilder setMachineBuilder(Machine.MachineBuilder machineBuilder){
            this.machineBuilder = machineBuilder;
            return this;
        }

        /**
         * Method that build a new Environment instance.
         * @return new Environment instance
         * @throws FileSystemException wrong ULF file
         * @throws FileNotFoundException no ULF file
         */
        public Environment build() throws FileSystemException, FileNotFoundException {
            return new Environment(this.inputUnitCount,
                    this.outputUnitCount,
                    this.machineCount,
                    this.eliminateCount,
                    this.selector,
                    this.unitLayoutFilePath,
                    this.machineBuilder);
        }
    }
}
