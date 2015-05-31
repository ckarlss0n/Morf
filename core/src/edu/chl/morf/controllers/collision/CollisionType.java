package edu.chl.morf.controllers.collision;

/**
 * This Enum is used in CollisionData and is used to keep track of
 * what type of fixture (Box2D) the CollisionData is attached to.
 * The CollisionType is checked in CollisionListener to determine
 * what should be done.
 *
 * @author Harald Brorsson
 */
public enum CollisionType {
    PLAYERCHARACTER,
    GHOST_LEFT,
    GHOST_RIGHT,
    GHOST_BOTTOM,
    GHOST_BOTTOM_ICE,
    GHOST_CORE,
    ACTIVE_BLOCK_RIGHT,
    ACTIVE_BLOCK_LEFT,
    ACTIVE_BLOCK_BOTTOM_RIGHT,
    ACTIVE_BLOCK_BOTTOM_LEFT,
    SPIKE,
    WATER,
    WATER_SENSOR,
    WATER_FLOWER_INTERSECTION,
    FLOWER,
    OTHER
}
