package com.sudhs.entities;

import lombok.Getter;
import lombok.Setter;

//TODO: All this looks super weird. Fix it. Right now too sleepy to do anything sensible
@Getter
@Setter
public class FloorPanel {
    private PanelButton upButton = new PanelButton(Direction.UP);
    private PanelButton downButton = new PanelButton(Direction.DOWN);
}

