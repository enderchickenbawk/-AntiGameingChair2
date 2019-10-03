package club.mineman.antigamingchair.check.checks;

import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;

public abstract class PositionCheck extends AbstractCheck<PlayerUpdatePositionEvent>
{
    public PositionCheck(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, PlayerUpdatePositionEvent.class);
    }
}
