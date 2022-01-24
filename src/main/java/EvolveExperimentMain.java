import environment.Environment;
import logger.CsvLogger;
import machine.TypeAMachine;
import selector.ApproximateSelector;

import java.io.IOException;
import java.util.BitSet;

public class EvolveExperimentMain {
    public static void main(String[] args) throws IOException {

        BitSet targetBit = new BitSet(8);
        int machineCount = 10;
        int eliminateCount = 5;
        int outputUnitCount = 8;
        int inputUnitCount = 8;
        int maxIterateCount = 1000;

        String logFilePath = "/Users/altair823/IdeaProjects/EvolutionMachine/log/test_log.csv";
        String unitLayoutFilePath = "/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf";
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
