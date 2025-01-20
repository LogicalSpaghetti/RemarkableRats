package spaghetti.remarkablerats.entity.enums

enum class RatActionType(val type: String) {
    MOVE_TO_BLOCKSTATE("BlockState"),
    PLACE_BLOCK("PlaceBlock"),
    WAIT("Wait"),
    TAKE_FROM_CHEST("TakeFromChest"),
    ADD_TO_CHEST("AddToChest")
}