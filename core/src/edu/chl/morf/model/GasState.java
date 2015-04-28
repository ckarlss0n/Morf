package edu.chl.morf.model;

/**
 * This class represents the gas state of water.
 * It transforms to water (liquid state of water) upon cooling.
 * <p>
 * <li><b>Responsible for: </b>
 * Representing the gas state of water. Transforming to other forms upon interaction.
 * <p>
 * <li><b>Used by: </b>
 * {@link LiquidState#heat()}
 * <p>
 * <li><b>Using: </b>
 * <li>{@link WaterState}
 * <li>{@link LiquidState}
 * <p>
 * @author Gustav Bergström
 */
public class GasState implements WaterState{

    @Override
    public String toString(){
        return "gas";
    }

    @Override
    public WaterState heat() {
        return null;
    }

    @Override
    public WaterState cool() {
        return new LiquidState();
    }
}
