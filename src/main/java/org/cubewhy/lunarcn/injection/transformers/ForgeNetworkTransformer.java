package org.cubewhy.lunarcn.injection.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.cubewhy.lunarcn.utils.ClassUtils;
import org.cubewhy.lunarcn.utils.NodeUtils;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class ForgeNetworkTransformer implements IClassTransformer {

    /**
     * Transform a class
     *
     * @param name            of target class
     * @param transformedName of target class
     * @param basicClass      bytecode of target class
     * @return new bytecode
     */
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            try {
                final ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode.methods.stream().filter(methodNode -> {
                    if (methodNode instanceof MethodNode) {
                        return ((MethodNode) methodNode).name.equals("handleVanilla");
                    }
                    return false;
                }).forEach(methodNode -> {
                    final LabelNode labelNode = new LabelNode();
                    if (methodNode instanceof MethodNode) {
                        ((MethodNode) methodNode).instructions.insertBefore(((MethodNode) methodNode).instructions.getFirst(), NodeUtils.INSTANCE.toNodes(
                                new MethodInsnNode(INVOKESTATIC, "org/cubewhy/lunarcn/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false),
                                new JumpInsnNode(IFEQ, labelNode),
                                new InsnNode(ICONST_0),
                                new InsnNode(IRETURN),
                                labelNode
                        ));
                    }
                });

                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (final Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (name.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
            try {
                final ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);

                classNode.methods.stream().filter(method -> {
                    if (method instanceof MethodNode) {
                        return ((MethodNode) method).name.equals("channelRead0");
                    }
                    return false;
                }).forEach(methodNode -> {
                    final LabelNode labelNode = new LabelNode();
                    if (methodNode instanceof MethodNode) {
                        ((MethodNode) methodNode).instructions.insertBefore(((MethodNode) methodNode).instructions.getFirst(), NodeUtils.INSTANCE.toNodes(
                                new MethodInsnNode(INVOKESTATIC,
                                        "org/cubewhy/lunarcn/injection/transformers/ForgeNetworkTransformer",
                                        "returnMethod", "()Z", false
                                ),
                                new JumpInsnNode(IFEQ, labelNode),
                                new InsnNode(RETURN),
                                labelNode
                        ));
                    }
                });

                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (final Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return basicClass;
    }
}

