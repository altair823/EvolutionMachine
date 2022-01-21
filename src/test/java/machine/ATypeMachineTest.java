package machine;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

class ATypeMachineTest {

    private final ATypeMachine aTypeMachine;

    ATypeMachineTest() throws FileSystemException, FileNotFoundException {
        this.aTypeMachine = new ATypeMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
    }

    @Test
    void pulseTest() {
        System.out.println(this.aTypeMachine);
        this.aTypeMachine.pulse();
        System.out.println(this.aTypeMachine);
    }

    @Test
    void mutateEdgeTest() {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.aTypeMachine);
            this.aTypeMachine.pulse();
            this.aTypeMachine.mutateEdge();
            this.aTypeMachine.mutateEdge();
            this.aTypeMachine.mutateEdge();
            this.aTypeMachine.mutateEdge();
            this.aTypeMachine.mutateEdge();
        }
    }

    @Test
    void copyTest() throws FileSystemException, FileNotFoundException {
        ATypeMachine aTypeMachine1 = new ATypeMachine(8, 8, "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf");
        ATypeMachine aTypeMachine2 = ATypeMachine.copy(aTypeMachine1);

        System.out.println(aTypeMachine1);
        System.out.println(aTypeMachine2);

        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.mutateEdge();
        aTypeMachine1.pulse();
        aTypeMachine2.pulse();
        aTypeMachine1.pulse();
        aTypeMachine2.pulse();
        aTypeMachine1.pulse();
        aTypeMachine2.pulse();
        aTypeMachine1.pulse();
        aTypeMachine2.pulse();


        System.out.println(aTypeMachine1);
        System.out.println(aTypeMachine2);
    }
}