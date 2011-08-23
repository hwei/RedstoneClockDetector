RedstoneClockDetector
=====================

A plugin for bukkit. Detect redstone clock and teleport to it.


Features
--------

* Detect redstone clock and teleport to it.
* Support command in console.


Requirements
------------

* Minecraft server that runs CraftBukkit
* PermissionBukkit (Optional)


Usage
-----

1. Type command /rcd [sec] [player].
2. RedstoneClockDetector plugin will monitor redstone activities for [sec] seconds.
3. Player [player] will be teleported to the place where most redstone activities happened.
4. Let [player] turn off the circuit and warn the owner not to leave the circuit on again.


Tips
----

The player being teleported had better be in god mode.


Commands
--------

    /rcd [sec] [player] Select the most active block in [sec] seconds (Default: 5). And teleport [player] to that position (Default: yourself).


Permissions
-----------

    redstoneclockdetector.*:
        description: Gives access to use this plugin.
        default: op


Install
-------

1. Put RedstoneClockDetector.jar in your plugins directory.
2. Set permissions in PermissionBukkit. (Optional)
3. Restart your server.




