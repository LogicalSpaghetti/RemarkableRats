@file:Suppress("DEPRECATION")

package spaghetti.remarkablerats.entity.abstracts

import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.registry.tag.FluidTags
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent
import spaghetti.remarkablerats.entity.entities.RatEntity
import spaghetti.remarkablerats.entity.enums.RatActionType
import spaghetti.remarkablerats.logger

abstract class CommandedEntity protected constructor(entityType: EntityType<out TameableEntity>, world: World) :
        TameableEntity(entityType, world) {

    // TODO: not saved properly?
    var targetTypeList: List<String> = listOf()
    var targetedDataList: List<Int> = listOf()

    companion object {
        private val actionDelay: TrackedData<Int> =
                DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val instruction_stage: TrackedData<Int> =
                DataTracker.registerData(RatEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
    }

    private fun getActionDelay(): Int = dataTracker.get(actionDelay)
    private fun reduceActionDelay() {
        dataTracker.set(actionDelay, dataTracker.get(actionDelay) - 1)
    }
    private fun resetActionDelay() {
        dataTracker.set(actionDelay, getMaxDelayBetweenActions())
    }

    private fun getMaxDelayBetweenActions(): Int = 10

    fun getInstructionStage(): Int = this.dataTracker.get(instruction_stage)
    private fun incrementInstructionStage() {
        this.dataTracker.set(instruction_stage, (getInstructionStage() + 1))
    }
    fun setInstructionStage(stage: Int) {
        this.dataTracker.set(instruction_stage, (stage))
    }

    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder)
        builder.add(actionDelay, 0)
        builder.add(instruction_stage, 0)
    }

    fun getCurrentInstructionType(): RatActionType? {
        if (targetTypeList.isEmpty()) return null
        return RatActionType.fromString(targetTypeList[getInstructionStage() % targetTypeList.size])
    }

    fun getCurrentInstructionData(): Int {
        if (targetedDataList.isEmpty()) return 0
        return targetedDataList[getInstructionStage() % targetedDataList.size]
    }

    fun goalCompleted() {
        incrementInstructionStage()
        resetActionDelay()
        logger.info("Completed goal: ${this.getCurrentInstructionType()}, Stage: ${getInstructionStage()}")
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("InstructionStage", getInstructionStage())

        // Targets
        val targetsArray = NbtList()
        for (i in targetedDataList.indices) {
            val nbtCompound = NbtCompound()
            nbtCompound.putString("TargetType", targetTypeList[i])
            nbtCompound.putInt("TargetData", targetedDataList[i])
            targetsArray.add(nbtCompound)
        }
        nbt.put("TargetsArray", targetsArray)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        if (nbt.contains("InstructionStage")) this.dataTracker.set(instruction_stage, nbt.getInt("InstructionStage"))

        if (nbt.contains("TargetsArray")) {
            val targetsArray = nbt.getList("TargetsArray", NbtElement.COMPOUND_TYPE.toInt())
            val targetTypeArrayList: ArrayList<String> = arrayListOf()
            val targetedDataArrayList: ArrayList<Int> = arrayListOf()
            for (i in 0..< targetsArray.size) {
                val nbtCompound = targetsArray.getCompound(i)
                targetTypeArrayList.add(nbtCompound.getString("TargetType"))
                targetedDataArrayList.add(nbtCompound.getInt("TargetData"))
            }
            targetTypeList = targetTypeArrayList
            targetedDataList = targetedDataArrayList
            nbt.put("TargetsArray", targetsArray)
        }

    }

    override fun tick() {
        super.tick()
        if (getActionDelay() > 0) {
            reduceActionDelay()
        }
    }

    fun teleportOnTopOfBlock(destPos: BlockPos): Boolean { return teleportTo(destPos.x + 0.5, destPos.y.toDouble() + 1, destPos.z + 0.5) }

    private fun teleportTo(x: Double, y: Double, z: Double): Boolean {
        logger.info("Teleporting to: $x, $y, $z")
        val mutable = BlockPos.Mutable(x, y, z)

        while (mutable.y > world.bottomY && !world.getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN)
        }

        val blockState = world.getBlockState(mutable)
        val bl = blockState.blocksMovement()
        val bl2 = blockState.fluidState.isIn(FluidTags.WATER)
        if (bl && !bl2) {
            val vec3d = this.pos
            val bl3 = this.teleport(x, y, z, true)
            if (bl3) {
                world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this))
                if (!this.isSilent) {
                    world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                            this.soundCategory, 1.0f, 1.0f)
                    this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f)
                }
            }

            return bl3
        } else {
            return false
        }
    }
}
