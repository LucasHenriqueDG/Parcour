**Custom Events**
- PlayerLeaveParkourEvent: To remove a player from the parcour. If you want to teleport them to the exit location, use type = LeaveType.REGULAR, if not, use LeaveType.CANCELED
- PlayerStartParkourEvent: To make a player start the parcour. If you want to teleport them to the entrance location, use type = JoinType.COMMAND, if not, use JoinType.REGULAR

If you want to listen to them instead of calling the event itself, change "Event" to "Listener".

**Other custom Listeners available:**
- PlayerFinishParkourListener: called when a player finishes the parkour.
- PlayerReachCheckpointListener: Called when a player reaches a new Checkpoint.
- PlayerResetParkourListener: Called when a player resets its run.
- PlayerTeleportToCheckpointListener: Called when the player uses the item to go back to its previous checkpoint.
