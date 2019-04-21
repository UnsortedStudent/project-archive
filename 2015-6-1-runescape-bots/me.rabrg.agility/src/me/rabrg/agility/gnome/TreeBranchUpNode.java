package me.rabrg.agility.gnome;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class TreeBranchUpNode extends GnomeNode {

	private GameObject treeBranch;

	public TreeBranchUpNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return TREE_BRANCH_UP_AREA.contains(ctx.getLocalPlayer()) && (treeBranch = ctx.getGameObjects().closest("Tree branch")) != null;
	}

	@Override
	public int execute() {
		if (!treeBranch.isOnScreen()) {
			ctx.getCamera().rotateToEntity(treeBranch);
		} else if (treeBranch.interact()) {
			MethodProvider.sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return BALANCING_ROPE_AREA.contains(ctx.getLocalPlayer()); // AREA
				}
			}, Calculations.random(5400, 7200));
		}
		return Calculations.random(0, 225);
	}

	@Override
	public String getName() {
		return "Tree branch up";
	}

}
