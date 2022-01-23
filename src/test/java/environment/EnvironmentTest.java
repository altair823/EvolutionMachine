package environment;

import machine.TypeAMachine;
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
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        this.environment.pulse();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
    }

    @Test
    void mutateTest(){
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
    }

    @Test
    void selectTest(){
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        System.out.println("------------------");
        this.environment.select();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
    }

    @Test
    void isSatisfyExpectedValueTest(){

        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
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
        for (TypeAMachine typeAMachine : this.environment.getTypeAMachines()){
            System.out.println(typeAMachine);
        }
        System.out.println("cycle count: " + cycleCount);
    }

    @Test
    void evolveIterateTest(){
        System.out.println("cycle count: " + this.environment.evolveIterate(1000));
    }
}