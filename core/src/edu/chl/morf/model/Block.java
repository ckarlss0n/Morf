package edu.chl.morf.model;

/**
 * Common interface for the different states of water with a heating and a cooling method.
 * <p>
 * @author Lage Bergman
 */
public interface Block {

    public Block heat();
    public Block cool();
}
