package logger;

import machine.Machine;

import java.util.List;

/**
 * Logger class that implement Logger interface with log data of machines to *.csv file.
 */
public class CsvLogger implements Logger{

    public CsvLogger(String logFilePath){

    }

    @Override
    public void logInitialEnvironment(List<Machine> machineList) {

    }

    @Override
    public void updateMachineData(List<Machine> machineList) {

    }

    @Override
    public void logAllMachines(List<Machine> machineList) {

    }

    void createCsvFile(String logFilePath){

    }
}
