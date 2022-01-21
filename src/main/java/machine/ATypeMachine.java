package machine;

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
 * Machine class that contains units and edges.
 * Those units and edges are connected to each other to form a machine.
 * @author altair823
 */
public class ATypeMachine {

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
     * Constructor for a new A Type Machine with unit layout file path.
     * This constructor makes a new A type machine with a new Control instance through read unit layout file.
     * Input units are assigned implicitly starting with unit that has ID 1,
     * and output units are assigned implicitly starting backward with unit that has last ID.
     * @param inputUnitCount the number of input units.
     * @param outputUnitCount the number of output units.
     * @param unitLayoutFile path of file containing layout data of units and edges.
     * @throws FileSystemException wrong file system
     * @throws FileNotFoundException there is no such file
     */
    public ATypeMachine(int inputUnitCount, int outputUnitCount, String unitLayoutFile) throws FileSystemException, FileNotFoundException {
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
     * Constructor for a new A type machine with existing Control instance.
     * This constructor used for copying existing A type machine.
     * Input units are assigned implicitly starting with unit that has ID 1,
     * and output units are assigned implicitly starting backward with unit that has last ID.
     * @param inputUnitCount the number of input units.
     * @param outputUnitCount the number of output units.
     * @param control existing Control instance.
     */
    public ATypeMachine(int inputUnitCount, int outputUnitCount, Control control){
        this.control = Control.copy(control);
        for (int i = 0; i < inputUnitCount; i++) {
            this.inputUnits.add(this.control.getUnitMap().get((long) (i + 1)));
        }
        for (int i = this.control.getUnitMap().size() - outputUnitCount + 1; i <= this.control.getUnitMap().size(); i++) {
            this.outputUnits.add(this.control.getUnitMap().get((long) (i)));
        }
    }

    /**
     * Method that make a new pulse to control instance.
     */
    public void pulse(){
        this.control.makePulse();
    }

    /**
     * Method that mutate the way a single edge in control delivering a state.
     */
    public void mutateEdge(){
        this.control.reverseSingleEdge();
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

    /**
     * Copy method that copy original A type machine to a new one.
     * @param originalATypeMachine original A type machine
     * @return new copied A type machine
     * @throws FileSystemException wrong ULF file
     * @throws FileNotFoundException no ULF file
     */
    public static ATypeMachine copy(ATypeMachine originalATypeMachine) throws FileSystemException, FileNotFoundException {
        return new ATypeMachine(originalATypeMachine.inputUnits.size(),
                originalATypeMachine.outputUnits.size(),
                originalATypeMachine.control);
    }
}
