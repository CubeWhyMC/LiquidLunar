package org.cubewhy.lunarcn.module.impl.client

import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.IntValue

@ModuleInfo(name="ChatConfig", description="Change configs of chat", category=ModuleCategory.CLIENT, canEnabled = false)
class ChatConfig : Module() {
    val limit = IntValue("limit", 100, 100, 1000)
    val copy = BooleanValue("Copy button", false)
}