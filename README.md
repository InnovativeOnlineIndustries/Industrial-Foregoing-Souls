![Logo](https://i.imgur.com/kQtCOE8.png)

![Discord](https://imgur.com/A7U3oXE.png) [Join my Discord](https://discord.gg/4tPfwjn)![Patreon](https://imgur.com/m9xyJSE.png) [Support me on Patreon](https://www.patreon.com/buuz135)![Twitter](https://imgur.com/A7U3oXE.png) [Follow me on twitter](https://twitter.com/Buuz135mods)

<strong>Industrial Foregoing: Souls</strong> uses souls extracted from Wardens to accelerate your machines. To do it you
need to extract souls from a <strong>Warden</strong> using a <strong>Soul Laser Base</strong> with a **Blue Lens**. As
always the Stasis Chamber will be your friend.

![Setup](https://i.imgur.com/oxRZtIm.gif)

**Don't Forget to add a lens!**

![Setup](https://i.imgur.com/FECPKSB.gif)

Once you have some souls stored now you need to place a **Soul Surge** pointing at the block you want to accelerate and
connect it using **Soul Pipes** to the **Soul Laser Base**. You can stop the Soul Surge from working by applying a *
*redstone signal** to it.

![Setup](https://imgur.com/vKGRR6G.png)

Each soul lasts for 15 seconds and makes the machine 4 times faster by default (Can be changed in the config)

You can prevent blocks from being accelerated by adding them to the block tag `industrialforegoingsouls:cant_accelerate`

This mod also adds a way to get more **Echo Shards** as they are used a bunch in the mod.

## Default Config

	[IFSoulsMachines.ConfigSoulLaserBase]
		#Max soul storage tank amount
		#Range: > 1
		SOUL_STORAGE_AMOUNT = 1350
		#Max progress of the machine
		#Range: > 1
		MAX_PROGRESS = 20
		#Kill the warden when it's life reaches near to 0 or keep it alive
		KILL_WARDEN = false
		#Damage done to the warden when an operation is done
		#Range: > 0
		DAMAGE_PER_OPERATION = 4
		#Souls generated when an operation is done
		#Range: > 1
		SOULS_PER_OPERATION = 3

	[IFSoulsMachines.ConfigSoulSurge]
		#How long in ticks a soul last to accelerate ticks
		#Range: > 1
		SOUL_TIME = 300
		#How many extra ticks the surge will accelerate
		#Range: > 1
		ACCELERATION_TICK = 4

