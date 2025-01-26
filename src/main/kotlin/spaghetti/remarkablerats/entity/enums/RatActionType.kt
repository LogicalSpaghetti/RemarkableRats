package spaghetti.remarkablerats.entity.enums

enum class RatActionType(val type: String) {
    TELEPORT_TO_BLOCKSTATE("BlockState"),
    WALK_TO_BLOCKSTATE("WalkToBlock"),
    PLACE_BLOCK("PlaceBlock"),
    BREAK_BLOCK("BreakBlock"),
    WAIT("Wait"),
    TAKE_FROM_CHEST("TakeFromChest"),
    ADD_TO_CHEST("AddToChest"),
    DEFEND_AREA("DefendArea"),

    ;
    companion object {
        fun fromString(type: String): RatActionType? {
            for (action in RatActionType.entries) {
                if (action.type == type) return action
            }
            return null
        }
    }
}
