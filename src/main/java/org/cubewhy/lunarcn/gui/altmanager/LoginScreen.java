package org.cubewhy.lunarcn.gui.altmanager;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.account.OfflineAccount;
import org.cubewhy.lunarcn.files.configs.AccountConfigFile;
import org.cubewhy.lunarcn.gui.elements.ClientButton;
import org.cubewhy.lunarcn.utils.MicrosoftAccountUtils;
import org.cubewhy.lunarcn.utils.RenderUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class LoginScreen extends GuiScreen {

    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(0, this.width / 3 + 40, this.height / 3 + 100, 110, 20, "Login with Microsoft"));
        this.buttonList.add(new ClientButton(1, this.width / 3 + 155, this.height / 3 + 100, 100, 20, "Offline"));
        this.buttonList.add(new ClientButton(2, this.width / 3 + 45, this.height / 3 + 125, "Quit"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawImage("lunarcn/images/image1.jpg", 0, 0, this.width, this.height);
        this.drawCenteredString(fontRenderer, "Login to use " + Client.clientName, this.width / 2, this.height / 4 + 120, new Color(0, 255, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                MicrosoftAccountUtils.getInstance().loginWithBrowser();
                mc.displayGuiScreen(null);
                break;
            case 1:
                String userName = JOptionPane.showInputDialog("UserName"); // TODO 暂时使用JOptionPane
                if (userName.isEmpty()) {
                    return;
                }
                OfflineAccount account = new OfflineAccount(userName);
                AccountConfigFile.getInstance().addAccount(account);
                account.switchAccount();
                mc.displayGuiScreen(null);
                break;
            case 2:
                mc.shutdown();
        }
    }
}
