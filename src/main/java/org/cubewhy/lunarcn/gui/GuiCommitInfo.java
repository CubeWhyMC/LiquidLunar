package org.cubewhy.lunarcn.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.utils.GitUtils;
import org.cubewhy.lunarcn.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.fontRenderer;

public class GuiCommitInfo extends GuiScreen {
    public static final ResourceLocation gitImage = new ResourceLocation("lunarcn/icons/git.png");

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, 70, 30 + fontRenderer.FONT_HEIGHT * 7 + 20, "Back"));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        RenderUtils.drawImage(gitImage, 30, 30, 30, 30);
        String buildUser = GitUtils.gitInfo.getProperty("git.build.user.name");
        String version = GitUtils.gitInfo.getProperty("git.build.version");
        String commitId = GitUtils.gitInfo.getProperty("git.commit.id");
        String commitIdAbbrev = GitUtils.gitInfo.getProperty("git.commit.id.abbrev");
        String commitMessage = GitUtils.gitInfo.getProperty("git.commit.message.short");
        String branch = GitUtils.gitInfo.getProperty("git.branch");
        String repo = GitUtils.gitInfo.getProperty("git.remote.origin.url");
        this.drawString(fontRenderer, "Git Info", 70 ,30, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, Client.clientName + " built by " + buildUser, 70, 30 + fontRenderer.FONT_HEIGHT, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "Version: " + version, 70, 30 + fontRenderer.FONT_HEIGHT * 2 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "CommitId: " + commitId + " (" + commitIdAbbrev + ")", 70, 30 + fontRenderer.FONT_HEIGHT * 3 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "CommitMessage: " + commitMessage, 70, 30 + fontRenderer.FONT_HEIGHT * 4 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "Branch: " + branch, 70, 30 + fontRenderer.FONT_HEIGHT * 5 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "Remote origin: " + repo, 70, 30 + fontRenderer.FONT_HEIGHT * 6 + 5, new Color(255, 255, 255).getRGB());
        this.drawString(fontRenderer, "Developers: " + String.join(" ", Client.clientDev), 70, 30 + fontRenderer.FONT_HEIGHT * 7 + 5, new Color(255, 255, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(null);
        }
    }
}
