package edu.chl.morf.model;

/**
 * Common interface for the different states of water with a heating and a cooling method.
 * <p>
 * @author Lage Bergman
 */
public interface WaterState {

    public WaterState heat();
    public WaterState cool();
}
