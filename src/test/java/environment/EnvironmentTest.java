package environment;

import logger.CsvLogger;
import machine.Machine;
import org.junit.jupiter.api.Test;
import selector.ApproximateSelector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.sql.*;
import java.util.BitSet;

class EnvironmentTest {
    private final Environment environment;
    EnvironmentTest() throws FileSystemException, FileNotFoundException {
        this.environment = Environment.builder()
                .unitLayoutFilePath("/Users/altair823/IdeaProjects/EvolutionMachine/layout/machine1.ulf")
                .inputUnitCount(8)
                .outputUnitCount(8)
                .machineCount(10)
                .selector(new ApproximateSelector(new BitSet(8)))
                .eliminateCount(5)
                .logger(new CsvLogger("/Users/altair823/IdeaProjects/EvolutionMachine/log/test_log.csv", 10, new BitSet(8)))
                .build();
    }

    @Test
    void pulseTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void mutateTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void selectTest(){
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        this.environment.select();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
    }

    @Test
    void isSatisfyExpectedValueTest(){

        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("------------------");
        for (int i = 0; i < 50; i++) {
            this.environment.mutate();
        }
        this.environment.pulse();
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
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
        for (Machine machine : this.environment.getMachines()){
            System.out.println(machine);
        }
        System.out.println("cycle count: " + cycleCount);
    }

    @Test
    void evolveIterateTest() throws IOException {
        System.out.println("cycle count: " + this.environment.evolveIterate(1000));
    }
    @Test
    void dbTest() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mariadb://61.73.209.90:3306/unorganized_machine",
                    "altair823",
                    "823eric!@");

            pstmt = con.prepareStatement("Select version()");

            rs = pstmt.executeQuery();


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(con != null) {
                    con.close(); // ?????? ??????
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
