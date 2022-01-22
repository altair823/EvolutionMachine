package environment;

import machine.TypeAMachine;

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
    private final List<TypeAMachine> typeAMachines = new LinkedList<>();

    /**
     * Constructor for a new Environment object.
     * @param inputUnitCount the number of input units.
     * @param outputUnitCount the number of output units.
     * @param aTypeMachineCount the number of type A machines.
     * @param unitLayoutFilePath unit layout file path.
     * @throws FileSystemException wrong ULF file
     * @throws FileNotFoundException no ULF file
     */
    Environment(int inputUnitCount, int outputUnitCount, int aTypeMachineCount, String unitLayoutFilePath) throws FileSystemException, FileNotFoundException {
        for (int i = 0; i < aTypeMachineCount; i++){
            typeAMachines.add(new TypeAMachine(inputUnitCount, outputUnitCount, unitLayoutFilePath));
        }
    }

    /**
     * Method that makes a pulse and send it to all existing machines.
     */
    public void pulse(){
        for (TypeAMachine typeAMachine : typeAMachines){
            typeAMachine.pulse();
        }
    }

    /**
     * Getter for list of machines.
     * @return list of machines
     */
    public List<TypeAMachine> getTypeAMachines(){
        return this.typeAMachines;
    }
}
