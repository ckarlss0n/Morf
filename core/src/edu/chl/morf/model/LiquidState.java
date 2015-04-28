package edu.chl.morf.model;

/**
 * This class represents the liquid state of water.
 * It transforms to gas upon heating, and to ice (solid state of water) upon cooling.
 * <p>
 * <li><b>Responsible for: </b>
 * Representing the liquid state of water. Transforming to other forms upon interaction.
 * <p>
 * <li><b>Used by: </b>
 * <li>{@link GasState#cool()}
 * <li>{@link SolidState#heat()}
 * <li>{@link Water}
 * <p>
 * <li><b>Using: </b>
 * <li>{@link WaterState}
 * <li>{@link SolidState}
 * <li>{@link GasState}
 * <p>
 * @author Gustav Bergström
 */
public class LiquidState  implements WaterState{

    @Override
    public String toString(){
        return "liquid";
    }

    @Override
    public WaterState heat() {
        return new GasState();
    }

    @Override
    public WaterState cool() {
        return new SolidState();
    }
}
