import environment.Environment;
import logger.CsvLogger;
import machine.TypeAMachine;
import selector.ApproximateSelector;

import java.io.IOException;
import java.util.BitSet;
import java.util.Objects;

public class EvolveMachine {
    public static void main(String[] args) throws IOException {

        if (Objects.equals(args[0], "help")
                || Objects.equals(args[0], "h")
                || Objects.equals(args[0], "Help")
                || Objects.equals(args[0], "H")){
            System.out.println("Argument list: machineCount eliminateCount outputUnitCount inputUnitCount maxIterateCount unitLayoutFilePath");
            System.exit(0);
        }
        else if(args.length != 6){
            System.out.println("Wrong argument count!");
            System.out.println("Argument list: machineCount eliminateCount outputUnitCount inputUnitCount maxIterateCount unitLayoutFilePath");
            System.exit(0);
        }

        BitSet targetBit = new BitSet(8);
        int machineCount = Integer.parseInt(args[0]);
        int eliminateCount = Integer.parseInt(args[1]);
        int outputUnitCount = Integer.parseInt(args[2]);
        int inputUnitCount = Integer.parseInt(args[3]);
        int maxIterateCount = Integer.parseInt(args[4]);
        String unitLayoutFilePath = args[5];

        String logFilePath = "test_log.csv";
        Environment environment = new Environment.EnvironmentBuilder(unitLayoutFilePath)
                .setLogger(new CsvLogger(logFilePath, machineCount, targetBit))
                .setOutputUnitCount(outputUnitCount)
                .setInputUnitCount(inputUnitCount)
                .setEliminateCount(eliminateCount)
                .setMachineCount(machineCount)
                .setSelector(new ApproximateSelector(targetBit))
                .setMachineBuilder(new TypeAMachine.TypeAMachineBuilder())
                .build();

        environment.evolveIterate(maxIterateCount);
    }
}
