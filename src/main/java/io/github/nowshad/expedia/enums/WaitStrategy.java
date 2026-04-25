package io.github.nowshad.expedia.enums;

/**
 * Defines the type of explicit wait to apply when locating elements.
 * Always choose the most specific strategy for each interaction.
 */
public enum WaitStrategy {

    /** Element exists in DOM and is visible on screen — use for most interactions */
    VISIBLE,

    /** Element exists in DOM and is enabled — use for buttons, inputs */
    CLICKABLE,

    /** Element exists anywhere in DOM — use only for hidden/metadata elements */
    PRESENCE,

    /** All matching elements are visible — use for lists, search results */
    VISIBLE_ALL
}
