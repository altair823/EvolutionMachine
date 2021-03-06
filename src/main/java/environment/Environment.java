package environment;

import logger.Logger;
import lombok.Builder;
import machine.Machine;
import selector.Selector;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final Logger logger;

    /**
     * Constructor for a new Environment object.
     * @param inputUnitCount the number of input units
     * @param outputUnitCount the number of output units
     * @param machineCount the number of type A machines
     * @param eliminateCount the number of machines to eliminate
     * @param selector selector instance
     * @param unitLayoutFilePath unit layout file path
     * @param logger logger instance
     * @throws FileSystemException wrong ULF file
     * @throws FileNotFoundException no ULF file
     */
    @Builder
    private Environment(int inputUnitCount,
                        int outputUnitCount,
                        int machineCount,
                        int eliminateCount,
                        Selector selector,
                        String unitLayoutFilePath,
                        Logger logger)
            throws FileSystemException, FileNotFoundException {

        for (int i = 0; i < machineCount; i++){
            machines.add(Machine.builder()
                    .inputUnitCount(inputUnitCount)
                    .outputUnitCount(outputUnitCount)
                    .unitLayoutFile(unitLayoutFilePath)
                    .build());
        }
        this.selector = selector;
        this.eliminateCount = eliminateCount;
        this.logger = logger;
    }

    /**
     * Method that executes an evolve method a given number of times.
     * @param maxIterateCount a number that repeats the evolution of machines.
     * @return the number of trials that found the desired machine, or -1 if not found.
     */
    public int evolveIterate(int maxIterateCount) throws IOException {
        for (int i = 0; i < maxIterateCount; i++){
            if (this.evolve()){
                this.logger.logTotalData();
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
        this.pulse();
        this.pulse();
        this.pulse();
        this.logger.updateMachineData(this.getMachines());
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
}
