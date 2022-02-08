package machine;

import lombok.Builder;
import unorganized.machine.control.Control;
import unorganized.machine.mapper.ATypeMapper;
import unorganized.machine.reader.UnitLayoutReader;
import unorganized.machine.units.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;


/**
 * Machine class that contains type A units and edges.
 * @author altair823
 */
public class TypeAMachine implements Machine {

    /**
     * Unit list that receive input for the machine.
     */
    private final List<Unit> inputUnits = new ArrayList<>();

    /**
     * Unit list that transmit result of the machine.
     */
    private final List<Unit> outputUnits = new ArrayList<>();

    /**
     * Control instance that control all unit and edges by pulse.
     */
    private final Control control;

    /**
     * Constructor for a new type A machine with unit layout file path.
     * This constructor makes a new type A machine with a new Control instance through read unit layout file.
     * Input units are assigned implicitly starting with unit that has ID 1,
     * and output units are assigned implicitly starting backward with unit that has last ID.
     * @param inputUnitCount the number of input units.
     * @param outputUnitCount the number of output units.
     * @param unitLayoutFile path of file containing layout data of units and edges.
     * @throws FileSystemException wrong file system
     * @throws FileNotFoundException there is no such file
     */
    public TypeAMachine(int inputUnitCount, int outputUnitCount, String unitLayoutFile) throws FileSystemException, FileNotFoundException {
        this.control = new Control();
        this.control.addMapper("A", new ATypeMapper());
        this.control.readLayout(new UnitLayoutReader(new File(unitLayoutFile)));
        for (int i = 0; i < inputUnitCount; i++) {
            this.inputUnits.add(this.control.getUnitMap().get((long) (i + 1)));
        }
        for (int i = this.control.getUnitMap().size() - outputUnitCount + 1; i <= this.control.getUnitMap().size(); i++) {
            this.outputUnits.add(this.control.getUnitMap().get((long) (i)));
        }
    }

    /**
     * Constructor for a new type A machine with existing Control instance.
     * This constructor used for copying existing A type machine.
     * Input units are assigned implicitly starting with unit that has ID 1,
     * and output units are assigned implicitly starting backward with unit that has last ID.
     * @param inputUnitCount the number of input units.
     * @param outputUnitCount the number of output units.
     * @param control existing Control instance.
     */
    public TypeAMachine(int inputUnitCount, int outputUnitCount, Control control){
        this.control = Control.copy(control);
        for (int i = 0; i < inputUnitCount; i++) {
            this.inputUnits.add(this.control.getUnitMap().get((long) (i + 1)));
        }
        for (int i = this.control.getUnitMap().size() - outputUnitCount + 1; i <= this.control.getUnitMap().size(); i++) {
            this.outputUnits.add(this.control.getUnitMap().get((long) (i)));
        }
    }

    @Override
    public void pulse(){
        this.control.makePulse();
    }

    @Override
    public void mutateEdge(){
        this.control.reverseSingleEdge();
    }

    @Override
    public void initMachineUnits(){
        this.control.initUnitStates();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Input Units states:  ");
        for (Unit unit: this.inputUnits){
            stringBuilder.append(unit.getCurrentState() ? "1 ": "0 ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Output Units states: ");
        for (Unit outputUnit: this.outputUnits){
            stringBuilder.append(outputUnit.getCurrentState() ? "1 ": "0 ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public List<Unit> getInputUnits(){
        return this.inputUnits;
    }

    @Override
    public List<Unit> getOutputUnits(){
        return this.outputUnits;
    }

    @Override
    public Control getControl(){
        return this.control;
    }

    /**
     * Builder class for A type machine implementing MachineBuilder.
     */
    public static class TypeAMachineBuilder implements MachineBuilder{
        private int inputUnitCount;
        private int outputUnitCount;
        private String unitLayoutFile = null;
        private Control control = null;

        @Override
        public MachineBuilder setInputUnitCount(int inputUnitCount) {
            this.inputUnitCount = inputUnitCount;
            return this;
        }

        @Override
        public MachineBuilder setOutputUnitCount(int outputUnitCount) {
            this.outputUnitCount = outputUnitCount;
            return this;
        }

        @Override
        public MachineBuilder setUnitLayoutFile(String unitLayoutFile) {
            if (this.control != null){
                throw new IllegalArgumentException("Control argument is already existing!");
            }
            this.unitLayoutFile = unitLayoutFile;
            return this;
        }

        @Override
        public MachineBuilder setControl(Control control) {
            if (this.unitLayoutFile != null){
                throw new IllegalArgumentException("Unit layout file string argument is already existing!");
            }
            this.control = control;
            return this;
        }

        @Override
        public Machine build() throws FileSystemException, FileNotFoundException {
            if (this.control != null && this.unitLayoutFile == null){
                return new TypeAMachine(this.inputUnitCount,
                        this.outputUnitCount,
                        this.control);
            }
            else if (this.control == null && this.unitLayoutFile != null){
                return new TypeAMachine(this.inputUnitCount,
                        this.outputUnitCount,
                        this.unitLayoutFile);
            }
            else {
                throw new IllegalArgumentException("Wrong arguments!");
            }
        }
    }
}
