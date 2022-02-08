package logger;

import environment.Environment;
import machine.TypeAMachine;
import org.junit.jupiter.api.Test;
import selector.ApproximateSelector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class CsvLoggerTest {
    private final CsvLogger csvLogger;
    private final Environment environment;

    CsvLoggerTest() throws FileSystemException, FileNotFoundException {
        BitSet target = new BitSet(8);
        target.set(1);
        target.set(4);
        this.csvLogger = new CsvLogger("/Users/altair823/IdeaProjects/EvolutionMachine/log/test_log.csv", 15, target);
        this.environment = Environment.builder()
                .unitLayoutFilePath("/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf")
                .selector(new ApproximateSelector(target))
                .machineCount(15)
                .inputUnitCount(8)
                .outputUnitCount(8)
                .eliminateCount(6)
                .machineBuilder(new TypeAMachine.TypeAMachineBuilder())
                .logger(this.csvLogger)
                .build();
    }

    @Test
    void createCsvFile() {
        assertDoesNotThrow(csvLogger::createCsvFile);
    }

    @Test
    void updateMachineDataTest() throws IOException {
        csvLogger.updateMachineData(this.environment.getMachines());
        this.environment.evolveIterate(100);
        csvLogger.updateMachineData(this.environment.getMachines());
    }

    @Test
    void logTotalDataTest() throws IOException {
        for (int i = 0; i < 1000; i++) {
            if (this.environment.evolveIterate(1) != -1){
                break;
            }
        }
    }
}