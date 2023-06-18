package org.cubewhy.lunarcn.module.modules.cheats

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.TickEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.value.StringValue

@ModuleInfo(name = "TestModule", document = "aTest", category = ModuleCategory.CHEATS)
class TestModule : Module() {
    private val aStringValue: StringValue = StringValue("aString", "string")

    @EventTarget
    fun onTick(event: TickEvent) {
        println(aStringValue.value)
    }
}