package games.sixknights.state;

public enum State {
    /**
     * Biztonságos állapot, nincs ütésben egyik huszár által sem.
     */
    SAFE,
    /**
     * Fehér huszár áll itt.
     */
    WHITE,
    /**
     * Fekete huszár áll itt.
     */
    BLACK,
    /**
     * Fehér huszár által van ütésben a mező.
     */
    WHITE_HIT,
    /**
     * Fekete huszár által van ütésben a mező.
     */
    BLACK_HIT,
    /**
     * "Halott" mező, mindkét oldal által ütésben van.
     */
    DEAD
}
