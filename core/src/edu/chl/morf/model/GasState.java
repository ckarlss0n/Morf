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
 * <li>{@link Block}
 * <li>{@link LiquidState}
 * <p>
 * @author Gustav Bergstr�m
 */
public class GasState implements Block {

    @Override
    public String toString(){
        return "gas";
    }

    @Override
    public Block heat() {
        return null;
    }

    @Override
    public Block cool() {
        return new LiquidState();
    }
}
