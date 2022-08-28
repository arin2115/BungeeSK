package fr.zorg.bungeesk.bukkit.skript.expressions.bungeeping;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.zorg.bungeesk.bukkit.skript.events.bukkit.BungeePingEvent;
import org.bukkit.event.Event;

@Name("Ping's maximum size of connected players")
@Description("The maximum size of the connected players in a proxy ping event")
@Examples("on proxy ping:\n\tset max players to 100")
@Since("2.0.0")
public class ExprMaxPlayers extends SimpleExpression<Long> {

    static {
        Skript.registerExpression(ExprMaxPlayers.class, Long.class, ExpressionType.SIMPLE, "max players [size]");
    }

    private boolean isBungeePingEvent;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.isBungeePingEvent = super.getParser().isCurrentEvent(BungeePingEvent.class);
        if (!this.isBungeePingEvent) {
            Skript.error("The max players size can only be used in a BungeePingEvent");
            return false;
        }
        return true;
    }

    @Override
    protected Long[] get(Event e) {
        if (e instanceof BungeePingEvent) {
            return new Long[]{(long) ((BungeePingEvent) e).getPing().getMaxPlayers()};
        }
        return new Long[0];
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Long> getReturnType() {
        return Long.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET)
            return CollectionUtils.array(Long.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if ((!(e instanceof BungeePingEvent)) || mode != Changer.ChangeMode.SET)
            return;

        ((BungeePingEvent) e).getPing().setMaxPlayers((int) (long) delta[0]);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "maximum of connected players in a bungee ping event";
    }
}