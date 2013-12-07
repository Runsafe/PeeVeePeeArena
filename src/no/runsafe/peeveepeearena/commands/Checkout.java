package no.runsafe.peeveepeearena.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.peeveepeearena.Purchase;
import no.runsafe.peeveepeearena.ShopItemSet;
import no.runsafe.peeveepeearena.repositories.PurchasedRepository;
import no.runsafe.peeveepeearena.repositories.ShopRepository;

import java.util.List;
import java.util.Map;

public class Checkout extends PlayerCommand
{
	public Checkout(PurchasedRepository purchasedRepository, ShopRepository shopRepository)
	{
		super("checkout", "Retrieve all the items from your shop checkout", "runsafe.peeveepee.checkout");

		this.purchasedRepository = purchasedRepository;
		this.shopRepository = shopRepository;
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		List<Purchase> purchases = this.purchasedRepository.getPurchases(executor.getName());
		if (purchases.isEmpty())
			return "&cYour checkout is empty, buy some things at the shop first!";

		for (Purchase purchase : purchases)
		{
			ShopItemSet itemSet = this.shopRepository.getItemSet(purchase.getSetID());

			if (itemSet == null)
			{
				executor.sendColouredMessage(String.format("&cOne of your purchases is longer valid and could not be retrieved."));
				this.purchasedRepository.deletePurchase(purchase.getPurchaseID());
				continue;
			}

			if (!itemSet.giveToPlayer(executor))
				return "&cYour inventory is too full for the next purchase, aborting.";

			executor.sendColouredMessage(String.format("&3Added %s to your inventory.", itemSet.getName()));
			this.purchasedRepository.deletePurchase(purchase.getPurchaseID());
		}

		return "&2Retrieved all items from your checkout.";
	}

	private final PurchasedRepository purchasedRepository;
	private final ShopRepository shopRepository;
}
