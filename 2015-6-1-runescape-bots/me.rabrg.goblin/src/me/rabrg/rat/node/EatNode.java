package me.rabrg.rat.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends Node {

	public EatNode(final MethodContext ctx) {
		super(ctx);
	}

	private int nextHeal = Calculations.random(7, 14);

	@Override
	public boolean validate() {
		return ctx.getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextHeal;
	}

	@Override
	public int execute() {
		if (!ctx.getTabs().isOpen(Tab.INVENTORY))
			ctx.getTabs().open(Tab.INVENTORY);
		final Item food = ctx.getInventory().get("Tuna");
		if (food != null) {
			food.interact("Eat");
		} else {
			ctx.getTabs().logout();
		}
		return Calculations.random(900, 1200);
	}

	@Override
	public String getName() {
		return "Eating food";
	}

}
