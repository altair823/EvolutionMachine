package environment;

import machine.ATypeMachine;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {
    private Environment environment;
    EnvironmentTest() throws FileSystemException, FileNotFoundException {
        this.environment = new Environment(8, 8, 10, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
    }

    @Test
    void pulseTest(){
        for (ATypeMachine aTypeMachine: this.environment.getaTypeMachines()){
            System.out.println(aTypeMachine);
        }
        this.environment.pulse();
        for (ATypeMachine aTypeMachine: this.environment.getaTypeMachines()){
            System.out.println(aTypeMachine);
        }
    }
}