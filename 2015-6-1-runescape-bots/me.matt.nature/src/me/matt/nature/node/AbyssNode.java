package me.matt.nature.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

public final class AbyssNode extends Node {

	private GameObject natureRift;
	private GameObject obstacle;
	private NPC darkMage;

	public AbyssNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(ctx.getLocalPlayer());
	}

	@Override
	public int execute() {
		if ((natureRift = ctx.getGameObjects().closest("Nature rift")) == null || !ctx.getMap().canReach(natureRift)) {
			obstacle = ctx.getGameObjects().closest("Passage", "Rock", "Gap");
			if (obstacle != null && obstacle.getName() != null) {
				if (obstacle.getName().equals("Rock")) {
					obstacle.interact("Mine");
				} else if (obstacle.getName().equals("Gap")) {
					obstacle.interact("Squeeze-through");
				} else if (obstacle.getName().equals("Passage")) {
					obstacle.interact("Go-through");
				}
				return Calculations.random(50, 1200);
			}
		} else if (ctx.getInventory().contains(5515, 5513, 5511)) {
			darkMage = ctx.getNpcs().closest("Dark mage");
			if (darkMage != null) {
				if (darkMage.interact("Repairs")) {
					MethodProvider.sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !ctx.getInventory().contains(5515, 5513, 5511);
						}
					}, Calculations.random(15000, 30000));
				}
			}
		} else if (natureRift.distance() > 7) {
			ctx.getWalking().walk(natureRift);
			return Calculations.random(600, 1800);
		} else if (!natureRift.isOnScreen()) {
			ctx.getCamera().rotateToEntity(natureRift);
		} else if (natureRift.interact("Exit-through")) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return !ABYSS_AREA.contains(ctx.getLocalPlayer());
				}
			}, Calculations.random(6400, 7200));
		}
		return Calculations.random(25, 125);
	}

	@Override
	public String getName() {
		return "abyss";
	}

}
