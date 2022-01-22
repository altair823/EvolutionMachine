package environment;

import machine.TypeAMachine;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

class EnvironmentTest {
    private final Environment environment;
    EnvironmentTest() throws FileSystemException, FileNotFoundException {
        this.environment = new Environment(8, 8, 10, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
    }

    @Test
    void pulseTest(){
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        this.environment.pulse();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
    }
}