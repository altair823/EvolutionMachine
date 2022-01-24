package logger;

import machine.Machine;

import java.io.IOException;
import java.util.List;

/**
 * Logger interface that log current Environment status, like output value of machines.
 */
public interface Logger {

    /**
     * Update data of machines to log buffer.
     * @param machineList list of machines.
     */
    void updateMachineData(List<Machine> machineList);

    /**
     * Log the states of the machines so far.
     * This method should be executed only once at the end.
     */
    void logTotalData() throws IOException;
}
