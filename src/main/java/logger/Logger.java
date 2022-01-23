package logger;

import machine.Machine;

import java.util.List;

/**
 * Logger interface that log current Environment status, like output value of machines.
 */
public interface Logger {

    /**
     * Log initial status of environment.
     * @param machineList list of machines
     */
    void logInitialEnvironment(List<Machine> machineList);

    /**
     * Update data of machines to log buffer.
     * @param machineList list of machines.
     */
    void updateMachineData(List<Machine> machineList);

    /**
     * Log list of current machines.
     * @param machineList list of machines
     */
    void logAllMachines(List<Machine> machineList);
}
