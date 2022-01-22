package machine;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

class TypeAMachineTest {

    private final TypeAMachine typeAMachine;

    TypeAMachineTest() throws FileSystemException, FileNotFoundException {
        this.typeAMachine = new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
    }

    @Test
    void pulseTest() {
        System.out.println(this.typeAMachine);
        this.typeAMachine.pulse();
        System.out.println(this.typeAMachine);
    }

    @Test
    void mutateEdgeTest() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.typeAMachine);
            this.typeAMachine.pulse();
            this.typeAMachine.mutateEdge();
            this.typeAMachine.mutateEdge();
            this.typeAMachine.mutateEdge();
            this.typeAMachine.mutateEdge();
            this.typeAMachine.mutateEdge();
        }
    }

    @Test
    void copyTest() throws FileSystemException, FileNotFoundException {
        TypeAMachine typeAMachine1 = new TypeAMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
        TypeAMachine typeAMachine2 = TypeAMachine.copy(typeAMachine1);

        System.out.println(typeAMachine1);
        System.out.println(typeAMachine2);

        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.mutateEdge();
        typeAMachine1.pulse();
        typeAMachine2.pulse();
        typeAMachine1.pulse();
        typeAMachine2.pulse();
        typeAMachine1.pulse();
        typeAMachine2.pulse();
        typeAMachine1.pulse();
        typeAMachine2.pulse();


        System.out.println(typeAMachine1);
        System.out.println(typeAMachine2);
    }
}