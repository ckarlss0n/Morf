package edu.chl.morf.model;

/**
 * This class represents the solid state of water (ice).
 * It transforms to water (liquid state of water) upon heating.
 * <p>
 * <li><b>Responsible for: </b>
 * Representing the solid state of water. Transforming to other forms upon interaction.
 * <p>
 * <li><b>Used by: </b>
 * {@link LiquidState#cool()}
 * <p>
 * <li><b>Using: </b>
 * <li>{@link Block}
 * <li>{@link LiquidState}
 * <p>
 * @author Gustav Bergstr�m
 */
public class SolidState implements Block {

    @Override
    public String toString(){
        return "solid";
    }

    @Override
    public Block heat() {
        return new LiquidState();
    }

    @Override
    public Block cool() {
        return null;
    }
}
