package logger;

import machine.Machine;
import selector.Selector;

import java.io.*;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Logger class that implement Logger interface with log data of machines to *.csv file.
 */
public class CsvLogger implements Logger{

    private String logFilePath;
    private final int machineCount;
    private final List<List<Integer>> machineGenerationData = new LinkedList<>(new LinkedList<>());
    private int generationCount;
    private final BitSet expectedValue;
    private File logFile;

    public CsvLogger(String logFilePath, int machineCount, BitSet expectedValue){
        this.logFilePath = logFilePath;
        this.machineCount = machineCount;
        this.generationCount = 0;
        this.expectedValue = expectedValue;
        this.createCsvFile();
    }

    @Override
    public void updateMachineData(List<Machine> machineList) {
        if (machineList.size() != this.machineCount){
            throw new ArrayIndexOutOfBoundsException("The machine list size is not equal to the machine count.");
        }
        else {
            List<Integer> currentMachineData = new LinkedList<>();
            for (Machine machine : machineList) {
                currentMachineData.add(Selector.countDifference(this.expectedValue, Selector.changeMachineResultToBitset(machine)));
            }
            this.machineGenerationData.add(currentMachineData);
            this.generationCount++;
        }
    }

    @Override
    public void logTotalData() throws IOException {
        BufferedWriter logWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.logFile)));
        // Write first line.
        logWriter.write("Generation,");
        for (int i = 1; i <= this.generationCount; i++){
            logWriter.write(String.valueOf(i));
            if (i != this.generationCount){
                logWriter.write(",");
            }
            else {
                logWriter.write("\n");
            }
        }

        for (int i = 1; i <= this.machineCount; i++){
            logWriter.write("machine" + i + ",");
            for (int j = 0; j < this.generationCount; j++){
                logWriter.write(String.valueOf(this.machineGenerationData.get(j).get(i - 1)));
                if (j != this.generationCount - 1){
                    logWriter.write(",");
                }
                else {
                    logWriter.write("\n");
                }
            }
        }

        logWriter.flush();
        logWriter.close();
    }

    /**
     * Create a new .csv log file.
     */
    void createCsvFile(){
        this.logFile = new File(this.logFilePath);
        try {
            String originalLogFileName = this.logFilePath;
            int sameFileCount = 1;
            while (!this.logFile.createNewFile()){
                int expandIndex = originalLogFileName.indexOf(".");
                sameFileCount++;
                this.logFilePath = originalLogFileName.substring(0, expandIndex) + "_" + sameFileCount + ".csv";
                this.logFile = new File(this.logFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
