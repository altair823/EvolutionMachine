package environment;

import machine.Machine;
import org.junit.jupiter.api.Test;
import selector.ApproximateSelector;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.BitSet;

class EnvironmentTest {
    private final Environment environment;
    EnvironmentTest() throws FileSystemException, FileNotFoundException {
        this.environment = new Environment.EnvironmentBuilder("/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf")
                .setInputUnitCount(8)
                .setOutputUnitCount(8)
                .setMachineCount(10)
                .setSelector(new ApproximateSelector(new BitSet(8)))
                .setEliminateCount(5)
                .build();
    }

    @Test
    void pulseTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void mutateTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void selectTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        this.environment.select();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void isSatisfyExpectedValueTest(){

        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        System.out.println(this.environment.isSatisfyExpectedValue());
        this.environment.select();
    }

    @Test
    void evolveTest(){
        int cycleCount = 0;
        while (!this.environment.evolve()){
            cycleCount++;
        }
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("cycle count: " + cycleCount);
    }

    @Test
    void evolveIterateTest(){
        System.out.println("cycle count: " + this.environment.evolveIterate(1000));
    }
}