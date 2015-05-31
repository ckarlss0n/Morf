package edu.chl.morf.model.blocks;

/**
 * Common interface for blocks with a heat and a cool method.
 * 
 * @author Lage Bergman
 */

public interface Block {
	//Methods for heating and cooling a block.
    void heat();
    void cool();
}
